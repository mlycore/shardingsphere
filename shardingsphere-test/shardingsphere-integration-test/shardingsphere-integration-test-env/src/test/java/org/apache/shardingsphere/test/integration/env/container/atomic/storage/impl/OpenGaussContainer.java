/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.test.integration.env.container.atomic.storage.impl;

import com.google.common.base.Strings;
import org.apache.shardingsphere.infra.database.type.DatabaseTypeFactory;
import org.apache.shardingsphere.test.integration.env.container.atomic.storage.DockerStorageContainer;
import org.apache.shardingsphere.test.integration.env.container.atomic.storage.config.StorageContainerConfiguration;

import java.util.Optional;

/**
 * OpenGauss container.
 */
public final class OpenGaussContainer extends DockerStorageContainer {
    
    private final StorageContainerConfiguration storageContainerConfiguration;
    
    public OpenGaussContainer(final String dockerImageName, final String scenario, final StorageContainerConfiguration storageContainerConfiguration) {
        super(DatabaseTypeFactory.getInstance("openGauss"), Strings.isNullOrEmpty(dockerImageName) ? "enmotech/opengauss:3.0.0" : dockerImageName, scenario);
        this.storageContainerConfiguration = storageContainerConfiguration;
    }
    
    @Override
    protected void configure() {
        setCommands(storageContainerConfiguration.getCommands());
        addEnvs(storageContainerConfiguration.getEnvs());
        mapResources(storageContainerConfiguration.getResourceMappings());
        withPrivilegedMode(true);
        super.configure();
    }
    
    @Override
    public int getPort() {
        return 5432;
    }
    
    @Override
    protected Optional<String> getDefaultDatabaseName() {
        return Optional.of("postgres");
    }
}
