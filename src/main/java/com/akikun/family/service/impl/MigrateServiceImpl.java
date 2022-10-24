package com.akikun.family.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import com.akikun.family.handler.migrate.MigrateHandlers;
import com.akikun.family.model.enums.MigrateType;
import com.akikun.family.service.MigrateService;

/**
 * Migrate service implementation.
 *
 * @author ryanwang
 * @date 2019-10-29
 */
@Service
public class MigrateServiceImpl implements MigrateService {

    private final MigrateHandlers migrateHandlers;

    public MigrateServiceImpl(MigrateHandlers migrateHandlers) {
        this.migrateHandlers = migrateHandlers;
    }

    @Override
    public void migrate(MultipartFile file, MigrateType migrateType) {
        Assert.notNull(file, "Multipart file must not be null");
        Assert.notNull(migrateType, "Migrate type must not be null");

        migrateHandlers.upload(file, migrateType);
    }
}
