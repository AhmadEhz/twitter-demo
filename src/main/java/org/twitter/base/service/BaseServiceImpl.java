package org.twitter.base.service;

import org.twitter.base.entity.BaseEntity;
import org.twitter.base.repository.BaseRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
        execute(e, entity -> entityManager.persist(entity));
    }

    @Override
    public void update(E e) {
        execute(e, entity -> entityManager.merge(entity));
    }

    @Override
    public void delete(E e) {
        execute(e, entity -> entityManager.remove(entity));
    }

    private void execute(E e, Consumer<E> consumer) {
        try {
            entityManager.getTransaction().begin();
            consumer.accept(e);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
}
