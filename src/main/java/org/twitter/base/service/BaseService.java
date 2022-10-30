package org.twitter.base.service;

import org.twitter.base.entity.BaseEntity;
import org.twitter.base.repository.BaseRepository;

import java.io.Serializable;
import java.util.Optional;

public interface BaseService<E extends BaseEntity<ID>, ID extends Serializable,R extends BaseRepository<E,ID>> {
    Optional<E> loadById(ID id);
    void save(E e);
    void update(E e);
    void delete (E e);

    void detach(E e);
}
