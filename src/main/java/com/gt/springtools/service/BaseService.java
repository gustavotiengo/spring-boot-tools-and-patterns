package com.gt.springtools.service;

import java.util.List;

public interface BaseService<T> {

    T findByUuid(String uuid);

    List<T> findAll();
}
