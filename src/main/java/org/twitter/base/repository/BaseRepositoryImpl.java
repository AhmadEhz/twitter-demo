package org.twitter.base.repository;

import org.twitter.base.entity.BaseEntity;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Optional;

public abstract class BaseRepositoryImpl<E extends BaseEntity<ID>, ID extends Serializable> implements BaseRepository<E, ID> {
    protected final EntityManager entityManager;

    public BaseRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<E> readById(ID id) {
        return Optional.ofNullable(entityManager.find(getEntityClass(), id));
    }

    @Override
    public void save(E e) {
        entityManager.persist(e);
    }

    @Override
    public void update(E e) {
        entityManager.merge(e);
    }

    @Override
    public void delete(E e) {
        entityManager.remove(e);
    }
    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
    @Override
    public abstract Class<E> getEntityClass();

}
