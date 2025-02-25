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

package org.apache.shardingsphere.data.pipeline.api;

import org.apache.shardingsphere.data.pipeline.api.config.job.YamlPipelineJobConfiguration;
import org.apache.shardingsphere.data.pipeline.api.job.JobType;
import org.apache.shardingsphere.data.pipeline.api.job.PipelineJobId;

/**
 * Pipeline job API.
 */
public interface PipelineJobAPI {
    
    /**
     * Marshal pipeline job id.
     *
     * @param pipelineJobId pipeline job id
     * @return marshaled text
     */
    String marshalJobId(PipelineJobId pipelineJobId);
    
    /**
     * Parse job type.
     *
     * @param jobId job id
     * @return job type
     */
    JobType parseJobType(String jobId);
    
    /**
     * Extend job configuration.
     *
     * @param yamlJobConfig yaml job configuration
     */
    void extendJobConfiguration(YamlPipelineJobConfiguration yamlJobConfig);
    
    /**
     * Start pipeline job by id.
     *
     * @param jobId job id
     */
    void startDisabledJob(String jobId);
    
    /**
     * Stop pipeline job.
     *
     * @param jobId job id
     */
    void stop(String jobId);
    
    /**
     * Remove pipeline job.
     *
     * @param jobId job id
     */
    void remove(String jobId);
}
