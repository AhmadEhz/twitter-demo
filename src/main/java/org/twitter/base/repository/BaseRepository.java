package org.twitter.base.repository;

import org.twitter.base.entity.BaseEntity;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Optional;

public interface BaseRepository <E extends BaseEntity<ID>, ID extends Serializable>{
    Optional<E> readById(ID id);
    void save(E e);
    void update (E e);
    void delete(E e);

    EntityManager getEntityManager();

    Class<E> getEntityClass();
}
