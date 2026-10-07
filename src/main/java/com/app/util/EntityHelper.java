package com.app.util;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EntityHelper {
    @Autowired
    private EntityManager entityManager;

    /**
     * Flushes pending changes to the DB and refreshes the entity state.
     */
    @Transactional
    public <T> T refresh(T entity) {
        entityManager.flush();
        entityManager.refresh(entity);
        return entity;
    }
}
