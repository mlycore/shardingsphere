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

package org.apache.shardingsphere.scaling.core.job.importer;

import java.util.Collection;
import java.util.LinkedList;
import org.apache.shardingsphere.data.pipeline.api.config.rulealtered.ImporterConfiguration;
import org.apache.shardingsphere.data.pipeline.api.ingest.channel.PipelineChannel;
import org.apache.shardingsphere.data.pipeline.api.job.progress.listener.PipelineJobProgressListener;
import org.apache.shardingsphere.data.pipeline.core.datasource.PipelineDataSourceManager;
import org.apache.shardingsphere.data.pipeline.core.importer.DefaultImporter;
import org.apache.shardingsphere.data.pipeline.spi.importer.Importer;

/**
 * Default importer creator.
 */
public final class DefaultImporterCreator implements ImporterCreator {
    
    @Override
    public Importer createImporter(final ImporterConfiguration importerConfig,
                                   final PipelineDataSourceManager dataSourceManager, final PipelineChannel channel,
                                   final PipelineJobProgressListener jobProgressListener) {
        return new DefaultImporter(importerConfig, dataSourceManager, channel, jobProgressListener);
    }
    
    @Override
    public String getType() {
        return "MySQL";
    }
    
    @Override
    public Collection<String> getTypeAliases() {
        Collection<String> aliases = new LinkedList<>();
        aliases.add("PostgreSQL");
        aliases.add("openGauss");
        return aliases;
    }
}
