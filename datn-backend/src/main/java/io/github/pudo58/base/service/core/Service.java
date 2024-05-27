package io.github.pudo58.base.service.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface Service <T>{
    T save(T t);
    T update(UUID uuid, T t);
    void delete(T t);

    void deleteById(UUID uuid);

    T findById(UUID uuid);

    List<T> findAll();

    Page<T> findAll(T entity, Pageable pageable);
}
