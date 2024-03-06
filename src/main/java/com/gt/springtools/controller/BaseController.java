package com.gt.springtools.controller;

import com.gt.springtools.Constants;
import com.gt.springtools.dto.BaseEntityDTO;
import com.gt.springtools.service.BaseService;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public abstract class BaseController<T extends BaseEntityDTO> implements BaseControllerAPI<T> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected abstract BaseService<T> service();

    @Override
    public ResponseEntity<List<T>> findAll() {
        return ResponseEntity.ok(service().findAll());
    }

    @Override
    public ResponseEntity<T> findByUuid(@PathVariable @Pattern(regexp = Constants.UUID_V4) String uuid) {
        logger.debug("Find with id {}", uuid);
        final T entity = service().findByUuid(uuid);
        return ResponseEntity.ok(entity);
    }

}
