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

package org.apache.shardingsphere.test.sql.parser.parameterized.asserts.statement.distsql.ral.impl.migration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.shardingsphere.distsql.parser.statement.ral.scaling.UpdatableScalingRALStatement;
import org.apache.shardingsphere.migration.distsql.statement.ApplyMigrationStatement;
import org.apache.shardingsphere.migration.distsql.statement.DropMigrationStatement;
import org.apache.shardingsphere.migration.distsql.statement.ResetMigrationStatement;
import org.apache.shardingsphere.migration.distsql.statement.RestoreMigrationSourceWritingStatement;
import org.apache.shardingsphere.migration.distsql.statement.StopMigrationSourceWritingStatement;
import org.apache.shardingsphere.migration.distsql.statement.StopMigrationStatement;
import org.apache.shardingsphere.migration.distsql.statement.StartMigrationStatement;
import org.apache.shardingsphere.test.sql.parser.parameterized.asserts.SQLCaseAssertContext;
import org.apache.shardingsphere.test.sql.parser.parameterized.asserts.statement.distsql.ral.impl.migration.update.ApplyMigrationStatementAssert;
import org.apache.shardingsphere.test.sql.parser.parameterized.asserts.statement.distsql.ral.impl.migration.update.DropMigrationStatementAssert;
import org.apache.shardingsphere.test.sql.parser.parameterized.asserts.statement.distsql.ral.impl.migration.update.ResetMigrationStatementAssert;
import org.apache.shardingsphere.test.sql.parser.parameterized.asserts.statement.distsql.ral.impl.migration.update.RestoreMigrationSourceWritingStatementAssert;
import org.apache.shardingsphere.test.sql.parser.parameterized.asserts.statement.distsql.ral.impl.migration.update.StopMigrationSourceWritingStatementAssert;
import org.apache.shardingsphere.test.sql.parser.parameterized.asserts.statement.distsql.ral.impl.migration.update.StopMigrationStatementAssert;
import org.apache.shardingsphere.test.sql.parser.parameterized.asserts.statement.distsql.ral.impl.migration.update.StartMigrationStatementAssert;
import org.apache.shardingsphere.test.sql.parser.parameterized.jaxb.cases.domain.statement.SQLParserTestCase;
import org.apache.shardingsphere.test.sql.parser.parameterized.jaxb.cases.domain.statement.distsql.ral.migration.ApplyMigrationStatementTestCase;
import org.apache.shardingsphere.test.sql.parser.parameterized.jaxb.cases.domain.statement.distsql.ral.migration.ResetMigrationStatementTestCase;
import org.apache.shardingsphere.test.sql.parser.parameterized.jaxb.cases.domain.statement.distsql.ral.migration.DropMigrationStatementTestCase;
import org.apache.shardingsphere.test.sql.parser.parameterized.jaxb.cases.domain.statement.distsql.ral.migration.RestoreMigrationSourceWritingStatementTestCase;
import org.apache.shardingsphere.test.sql.parser.parameterized.jaxb.cases.domain.statement.distsql.ral.migration.StopMigrationSourceWritingStatementTestCase;
import org.apache.shardingsphere.test.sql.parser.parameterized.jaxb.cases.domain.statement.distsql.ral.migration.StopMigrationStatementTestCase;
import org.apache.shardingsphere.test.sql.parser.parameterized.jaxb.cases.domain.statement.distsql.ral.migration.StartMigrationStatementTestCase;

/**
 * Updatable Scaling RAL statement assert.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UpdatableScalingRALStatementAssert {
    
    /**
     * Assert updatable scaling RAL statement is correct with expected parser result.
     *
     * @param assertContext assert context
     * @param actual actual updatable scaling RAL statement
     * @param expected expected updatable scaling RAL statement test case
     */
    public static void assertIs(final SQLCaseAssertContext assertContext, final UpdatableScalingRALStatement actual, final SQLParserTestCase expected) {
        // TODO add more test case
        if (actual instanceof StopMigrationSourceWritingStatement) {
            StopMigrationSourceWritingStatementAssert.assertIs(assertContext, (StopMigrationSourceWritingStatement) actual, (StopMigrationSourceWritingStatementTestCase) expected);
        } else if (actual instanceof RestoreMigrationSourceWritingStatement) {
            RestoreMigrationSourceWritingStatementAssert.assertIs(assertContext, (RestoreMigrationSourceWritingStatement) actual, (RestoreMigrationSourceWritingStatementTestCase) expected);
        } else if (actual instanceof ApplyMigrationStatement) {
            ApplyMigrationStatementAssert.assertIs(assertContext, (ApplyMigrationStatement) actual, (ApplyMigrationStatementTestCase) expected);
        } else if (actual instanceof StopMigrationStatement) {
            StopMigrationStatementAssert.assertIs(assertContext, (StopMigrationStatement) actual, (StopMigrationStatementTestCase) expected);
        } else if (actual instanceof ResetMigrationStatement) {
            ResetMigrationStatementAssert.assertIs(assertContext, (ResetMigrationStatement) actual, (ResetMigrationStatementTestCase) expected);
        } else if (actual instanceof DropMigrationStatement) {
            DropMigrationStatementAssert.assertIs(assertContext, (DropMigrationStatement) actual, (DropMigrationStatementTestCase) expected);
        } else if (actual instanceof StartMigrationStatement) {
            StartMigrationStatementAssert.assertIs(assertContext, (StartMigrationStatement) actual, (StartMigrationStatementTestCase) expected);
        }
    }
}
