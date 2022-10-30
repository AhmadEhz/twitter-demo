package org.twitter.base.service;

import jakarta.validation.ConstraintViolation;
import org.twitter.base.entity.BaseEntity;
import org.twitter.base.repository.BaseRepository;
import org.twitter.util.HibernateUtil;
import org.twitter.util.exception.CustomizedIllegalArgumentException;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

public abstract class BaseServiceImpl<E extends BaseEntity<ID>, ID extends Serializable, R extends BaseRepository<E, ID>> implements BaseService<E, ID, R> {
    protected final R repository;
    protected EntityManager entityManager;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
        entityManager = repository.getEntityManager();
    }

    @Override
    public Optional<E> loadById(ID id) {
        return repository.readById(id);
    }

    @Override
    public void save(E e) {
        checkEntity(e);
        execute(() -> entityManager.persist(e));
    }

    @Override
    public void update(E e) {
        checkEntity(e);
        execute(() -> entityManager.merge(e));
    }

    @Override
    public void delete(E e) {
        execute(() -> entityManager.remove(e));
    }
    @Override
    public void detach(E e) {
        entityManager.detach(e);
    }

    protected void execute(Runnable runnable) {
        try {
            entityManager.getTransaction().begin();
            runnable.run();
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            try {
                entityManager.getTransaction().rollback();
            } catch (Exception ex2) {
                ex2.addSuppressed(ex);
                throw ex2;
            }
            throw ex;
        }
    }

    protected void checkEntity(E e) {
        Set<ConstraintViolation<E>> constraintViolations = HibernateUtil.getValidator().validate(e);
        if (!constraintViolations.isEmpty()) {
            throw new CustomizedIllegalArgumentException(constraintViolations.toString());
        }
    }
}
