package com.gt.springtools.controller;

import com.gt.springtools.Constants;
import com.gt.springtools.dto.BaseEntityDTO;
import com.gt.springtools.service.BaseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.text.MessageFormat;
import java.util.List;

public abstract class BaseController<T extends BaseEntityDTO> implements BaseControllerAPI<T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected abstract BaseService<T> service();

    protected abstract String path();

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

    @Override
    public ResponseEntity<T> create(@RequestBody @Valid final T entity) {
        final T createdEntity = service().save(entity, null);
        return ResponseEntity
                .created(URI.create(MessageFormat.format("/{0}/{1}", path(), createdEntity.getExternalId())))
                .body(createdEntity);
    }

    @Override
    public ResponseEntity<T> update(@PathVariable @Pattern(regexp = Constants.UUID_V4) String uuid,
                                          @RequestBody @Valid final T user) {
        final T updatedEntity = service().save(user, uuid);
        return ResponseEntity.ok(updatedEntity);
    }

}
