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

package org.apache.shardingsphere.sharding.api.config.rule;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.sharding.api.config.strategy.audit.ShardingAuditStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.keygen.KeyGenerateStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.ShardingStrategyConfiguration;

/**
 * Sharding auto table rule configuration.
 */
@Getter
@Setter
public final class ShardingAutoTableRuleConfiguration {
    
    private final String logicTable;
    
    private final String actualDataSources;
    
    private String actualTablePrefix;
    
    private ShardingStrategyConfiguration shardingStrategy;
    
    private KeyGenerateStrategyConfiguration keyGenerateStrategy;
    
    private ShardingAuditStrategyConfiguration auditStrategy;
    
    // TODO prevent user to config this.
    private String actualDataNodes;
    
    public ShardingAutoTableRuleConfiguration(final String logicTable, final String actualDataSources) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(logicTable), "LogicTable is required.");
        this.logicTable = logicTable;
        this.actualDataSources = actualDataSources;
    }
}
