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

package org.apache.shardingsphere.infra.metadata.database.schema.loader.dialect;

import org.apache.shardingsphere.infra.database.type.DatabaseTypeFactory;
import org.apache.shardingsphere.infra.metadata.database.schema.loader.model.ColumnMetaData;
import org.apache.shardingsphere.infra.metadata.database.schema.loader.model.IndexMetaData;
import org.apache.shardingsphere.infra.metadata.database.schema.loader.model.SchemaMetaData;
import org.apache.shardingsphere.infra.metadata.database.schema.loader.model.TableMetaData;
import org.apache.shardingsphere.infra.metadata.database.schema.loader.spi.DialectSchemaMetaDataLoader;
import org.apache.shardingsphere.infra.metadata.database.schema.loader.spi.DialectSchemaMetaDataLoaderFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class OracleSchemaMetaDataLoaderTest {
    
    private static final String ALL_CONSTRAINTS_SQL_WITHOUT_TABLES = "SELECT A.OWNER AS TABLE_SCHEMA, A.TABLE_NAME AS TABLE_NAME, B.COLUMN_NAME AS COLUMN_NAME FROM ALL_CONSTRAINTS A"
            + " INNER JOIN ALL_CONS_COLUMNS B ON A.CONSTRAINT_NAME = B.CONSTRAINT_NAME WHERE CONSTRAINT_TYPE = 'P' AND A.OWNER = ?";
    
    private static final String ALL_CONSTRAINTS_SQL_WITH_TABLES = "SELECT A.OWNER AS TABLE_SCHEMA, A.TABLE_NAME AS TABLE_NAME, B.COLUMN_NAME AS COLUMN_NAME FROM ALL_CONSTRAINTS A"
            + " INNER JOIN ALL_CONS_COLUMNS B ON A.CONSTRAINT_NAME = B.CONSTRAINT_NAME WHERE CONSTRAINT_TYPE = 'P' AND A.OWNER = ? AND A.TABLE_NAME IN ('tbl')";
    
    private static final String ALL_INDEXES_SQL = "SELECT OWNER AS TABLE_SCHEMA, TABLE_NAME, INDEX_NAME FROM ALL_INDEXES WHERE OWNER = ? AND TABLE_NAME IN ('tbl')";
    
    private static final String ALL_TAB_COLUMNS_SQL_CONDITION1 = "SELECT OWNER AS TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, DATA_TYPE, COLUMN_ID , IDENTITY_COLUMN, COLLATION"
            + " FROM ALL_TAB_COLUMNS WHERE OWNER = ? AND TABLE_NAME IN ('tbl') ORDER BY COLUMN_ID";
    
    private static final String ALL_TAB_COLUMNS_SQL_CONDITION2 = "SELECT OWNER AS TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, DATA_TYPE, COLUMN_ID , IDENTITY_COLUMN FROM ALL_TAB_COLUMNS WHERE OWNER = ?"
            + " AND TABLE_NAME IN ('tbl') ORDER BY COLUMN_ID";
    
    private static final String ALL_TAB_COLUMNS_SQL_CONDITION3 = "SELECT OWNER AS TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, DATA_TYPE, COLUMN_ID  FROM ALL_TAB_COLUMNS WHERE OWNER = ?"
            + " AND TABLE_NAME IN ('tbl') ORDER BY COLUMN_ID";
    
    private static final String ALL_TAB_COLUMNS_SQL_CONDITION4 = "SELECT OWNER AS TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, DATA_TYPE, COLUMN_ID , IDENTITY_COLUMN, COLLATION FROM ALL_TAB_COLUMNS"
            + " WHERE OWNER = ? AND TABLE_NAME IN ('tbl') ORDER BY COLUMN_ID";
    
    private static final String ALL_TAB_COLUMNS_SQL_CONDITION5 = "SELECT OWNER AS TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, DATA_TYPE, COLUMN_ID , IDENTITY_COLUMN FROM ALL_TAB_COLUMNS"
            + " WHERE OWNER = ? AND TABLE_NAME IN ('tbl') ORDER BY COLUMN_ID";
    
    private static final String ALL_TAB_COLUMNS_SQL_CONDITION6 = "SELECT OWNER AS TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, DATA_TYPE, COLUMN_ID  FROM ALL_TAB_COLUMNS"
            + " WHERE OWNER = ? AND TABLE_NAME IN ('tbl') ORDER BY COLUMN_ID";
    
    @Test
    public void assertLoadCondition1() throws SQLException {
        DataSource dataSource = mockDataSource();
        ResultSet resultSet = mockTableMetaDataResultSet();
        when(dataSource.getConnection().prepareStatement(ALL_TAB_COLUMNS_SQL_CONDITION1).executeQuery()).thenReturn(resultSet);
        ResultSet indexResultSet = mockIndexMetaDataResultSet();
        when(dataSource.getConnection().prepareStatement(ALL_INDEXES_SQL).executeQuery()).thenReturn(indexResultSet);
        ResultSet primaryKeys = mockPrimaryKeysMetaDataResultSet();
        when(dataSource.getConnection().prepareStatement(ALL_CONSTRAINTS_SQL_WITHOUT_TABLES).executeQuery()).thenReturn(primaryKeys);
        when(dataSource.getConnection().getMetaData().getDatabaseMajorVersion()).thenReturn(12);
        when(dataSource.getConnection().getMetaData().getDatabaseMinorVersion()).thenReturn(2);
        Collection<SchemaMetaData> actual = getDialectTableMetaDataLoader().load(dataSource, Collections.singleton("tbl"), "sharding_db");
        assertTableMetaDataMap(actual);
        TableMetaData actualTableMetaData = actual.iterator().next().getTables().iterator().next();
        Iterator<ColumnMetaData> columnsIterator = actualTableMetaData.getColumns().iterator();
        assertThat(columnsIterator.next(), is(new ColumnMetaData("id", 4, false, true, true, true)));
        assertThat(columnsIterator.next(), is(new ColumnMetaData("name", 12, false, false, false, true)));
    }
    
    @Test
    public void assertLoadCondition2() throws SQLException {
        DataSource dataSource = mockDataSource();
        ResultSet resultSet = mockTableMetaDataResultSet();
        when(dataSource.getConnection().prepareStatement(ALL_TAB_COLUMNS_SQL_CONDITION2).executeQuery()).thenReturn(resultSet);
        ResultSet indexResultSet = mockIndexMetaDataResultSet();
        when(dataSource.getConnection().prepareStatement(ALL_INDEXES_SQL).executeQuery()).thenReturn(indexResultSet);
        ResultSet primaryKeys = mockPrimaryKeysMetaDataResultSet();
        when(dataSource.getConnection().prepareStatement(ALL_CONSTRAINTS_SQL_WITHOUT_TABLES).executeQuery()).thenReturn(primaryKeys);
        when(dataSource.getConnection().getMetaData().getDatabaseMajorVersion()).thenReturn(12);
        when(dataSource.getConnection().getMetaData().getDatabaseMinorVersion()).thenReturn(1);
        Collection<SchemaMetaData> actual = getDialectTableMetaDataLoader().load(dataSource, Collections.singleton("tbl"), "sharding_db");
        assertTableMetaDataMap(actual);
        TableMetaData actualTableMetaData = actual.iterator().next().getTables().iterator().next();
        Iterator<ColumnMetaData> columnsIterator = actualTableMetaData.getColumns().iterator();
        assertThat(columnsIterator.next(), is(new ColumnMetaData("id", 4, false, true, false, true)));
        assertThat(columnsIterator.next(), is(new ColumnMetaData("name", 12, false, false, false, true)));
    }
    
    @Test
    public void assertLoadCondition3() throws SQLException {
        DataSource dataSource = mockDataSource();
        ResultSet resultSet = mockTableMetaDataResultSet();
        when(dataSource.getConnection().prepareStatement(ALL_TAB_COLUMNS_SQL_CONDITION3).executeQuery()).thenReturn(resultSet);
        ResultSet indexResultSet = mockIndexMetaDataResultSet();
        when(dataSource.getConnection().prepareStatement(ALL_INDEXES_SQL).executeQuery()).thenReturn(indexResultSet);
        ResultSet primaryKeys = mockPrimaryKeysMetaDataResultSet();
        when(dataSource.getConnection().prepareStatement(ALL_CONSTRAINTS_SQL_WITHOUT_TABLES).executeQuery()).thenReturn(primaryKeys);
        when(dataSource.getConnection().getMetaData().getDatabaseMajorVersion()).thenReturn(11);
        when(dataSource.getConnection().getMetaData().getDatabaseMinorVersion()).thenReturn(2);
        Collection<SchemaMetaData> actual = getDialectTableMetaDataLoader().load(dataSource, Collections.singleton("tbl"), "sharding_db");
        assertTableMetaDataMap(actual);
        TableMetaData actualTableMetaData = actual.iterator().next().getTables().iterator().next();
        Iterator<ColumnMetaData> columnsIterator = actualTableMetaData.getColumns().iterator();
        assertThat(columnsIterator.next(), is(new ColumnMetaData("id", 4, false, false, false, true)));
        assertThat(columnsIterator.next(), is(new ColumnMetaData("name", 12, false, false, false, true)));
    }
    
    @Test
    public void assertLoadCondition4() throws SQLException {
        DataSource dataSource = mockDataSource();
        ResultSet resultSet = mockTableMetaDataResultSet();
        when(dataSource.getConnection().prepareStatement(ALL_TAB_COLUMNS_SQL_CONDITION4).executeQuery()).thenReturn(resultSet);
        ResultSet indexResultSet = mockIndexMetaDataResultSet();
        when(dataSource.getConnection().prepareStatement(ALL_INDEXES_SQL).executeQuery()).thenReturn(indexResultSet);
        ResultSet primaryKeys = mockPrimaryKeysMetaDataResultSet();
        when(dataSource.getConnection().prepareStatement(ALL_CONSTRAINTS_SQL_WITH_TABLES).executeQuery()).thenReturn(primaryKeys);
        when(dataSource.getConnection().getMetaData().getDatabaseMajorVersion()).thenReturn(12);
        when(dataSource.getConnection().getMetaData().getDatabaseMinorVersion()).thenReturn(2);
        Collection<SchemaMetaData> actual = getDialectTableMetaDataLoader().load(dataSource, Collections.singleton("tbl"), "sharding_db");
        assertTableMetaDataMap(actual);
        TableMetaData actualTableMetaData = actual.iterator().next().getTables().iterator().next();
        Iterator<ColumnMetaData> columnsIterator = actualTableMetaData.getColumns().iterator();
        assertThat(columnsIterator.next(), is(new ColumnMetaData("id", 4, true, true, true, true)));
        assertThat(columnsIterator.next(), is(new ColumnMetaData("name", 12, false, false, false, true)));
    }
    
    @Test
    public void assertLoadCondition5() throws SQLException {
        DataSource dataSource = mockDataSource();
        ResultSet resultSet = mockTableMetaDataResultSet();
        when(dataSource.getConnection().prepareStatement(ALL_TAB_COLUMNS_SQL_CONDITION5).executeQuery()).thenReturn(resultSet);
        ResultSet indexResultSet = mockIndexMetaDataResultSet();
        when(dataSource.getConnection().prepareStatement(ALL_INDEXES_SQL).executeQuery()).thenReturn(indexResultSet);
        ResultSet primaryKeys = mockPrimaryKeysMetaDataResultSet();
        when(dataSource.getConnection().prepareStatement(ALL_CONSTRAINTS_SQL_WITH_TABLES).executeQuery()).thenReturn(primaryKeys);
        when(dataSource.getConnection().getMetaData().getDatabaseMajorVersion()).thenReturn(12);
        when(dataSource.getConnection().getMetaData().getDatabaseMinorVersion()).thenReturn(1);
        Collection<SchemaMetaData> actual = getDialectTableMetaDataLoader().load(dataSource, Collections.singleton("tbl"), "sharding_db");
        assertTableMetaDataMap(actual);
        TableMetaData actualTableMetaData = actual.iterator().next().getTables().iterator().next();
        Iterator<ColumnMetaData> columnsIterator = actualTableMetaData.getColumns().iterator();
        assertThat(columnsIterator.next(), is(new ColumnMetaData("id", 4, true, true, false, true)));
        assertThat(columnsIterator.next(), is(new ColumnMetaData("name", 12, false, false, false, true)));
    }
    
    @Test
    public void assertLoadCondition6() throws SQLException {
        DataSource dataSource = mockDataSource();
        ResultSet resultSet = mockTableMetaDataResultSet();
        when(dataSource.getConnection().prepareStatement(ALL_TAB_COLUMNS_SQL_CONDITION6).executeQuery()).thenReturn(resultSet);
        ResultSet indexResultSet = mockIndexMetaDataResultSet();
        when(dataSource.getConnection().prepareStatement(ALL_INDEXES_SQL).executeQuery()).thenReturn(indexResultSet);
        ResultSet primaryKeys = mockPrimaryKeysMetaDataResultSet();
        when(dataSource.getConnection().prepareStatement(ALL_CONSTRAINTS_SQL_WITH_TABLES).executeQuery()).thenReturn(primaryKeys);
        when(dataSource.getConnection().getMetaData().getDatabaseMajorVersion()).thenReturn(11);
        when(dataSource.getConnection().getMetaData().getDatabaseMinorVersion()).thenReturn(2);
        Collection<SchemaMetaData> actual = getDialectTableMetaDataLoader().load(dataSource, Collections.singleton("tbl"), "sharding_db");
        assertTableMetaDataMap(actual);
        TableMetaData actualTableMetaData = actual.iterator().next().getTables().iterator().next();
        Iterator<ColumnMetaData> columnsIterator = actualTableMetaData.getColumns().iterator();
        assertThat(columnsIterator.next(), is(new ColumnMetaData("id", 4, true, false, false, true)));
        assertThat(columnsIterator.next(), is(new ColumnMetaData("name", 12, false, false, false, true)));
    }
    
    private DataSource mockDataSource() throws SQLException {
        DataSource result = mock(DataSource.class, RETURNS_DEEP_STUBS);
        ResultSet typeInfoResultSet = mockTypeInfoResultSet();
        when(result.getConnection().getMetaData().getTypeInfo()).thenReturn(typeInfoResultSet);
        return result;
    }
    
    private ResultSet mockTypeInfoResultSet() throws SQLException {
        ResultSet result = mock(ResultSet.class);
        when(result.next()).thenReturn(true, true, true, false);
        when(result.getString("TYPE_NAME")).thenReturn("int", "varchar", "TIMESTAMP");
        when(result.getInt("DATA_TYPE")).thenReturn(4, 12, 93);
        return result;
    }
    
    private ResultSet mockTableMetaDataResultSet() throws SQLException {
        ResultSet result = mock(ResultSet.class);
        when(result.next()).thenReturn(true, true, true, false);
        when(result.getString("TABLE_NAME")).thenReturn("tbl");
        when(result.getString("COLUMN_NAME")).thenReturn("id", "name", "creation_time");
        when(result.getString("DATA_TYPE")).thenReturn("int", "varchar", "TIMESTAMP(6)");
        when(result.getString("IDENTITY_COLUMN")).thenReturn("YES", "NO", "NO");
        when(result.getString("COLLATION")).thenReturn("BINARY_CS", "BINARY_CI", "BINARY_CI");
        return result;
    }
    
    private ResultSet mockIndexMetaDataResultSet() throws SQLException {
        ResultSet result = mock(ResultSet.class);
        when(result.next()).thenReturn(true, false);
        when(result.getString("INDEX_NAME")).thenReturn("id");
        when(result.getString("TABLE_NAME")).thenReturn("tbl");
        return result;
    }
    
    private ResultSet mockPrimaryKeysMetaDataResultSet() throws SQLException {
        ResultSet result = mock(ResultSet.class);
        when(result.next()).thenReturn(true, false);
        when(result.getString("TABLE_NAME")).thenReturn("tbl");
        when(result.getString("COLUMN_NAME")).thenReturn("id");
        return result;
    }
    
    private DialectSchemaMetaDataLoader getDialectTableMetaDataLoader() {
        Optional<DialectSchemaMetaDataLoader> result = DialectSchemaMetaDataLoaderFactory.findInstance(DatabaseTypeFactory.getInstance("Oracle"));
        assertTrue(result.isPresent());
        return result.get();
    }
    
    private void assertTableMetaDataMap(final Collection<SchemaMetaData> schemaMetaDataList) {
        assertThat(schemaMetaDataList.size(), is(1));
        TableMetaData actualTableMetaData = schemaMetaDataList.iterator().next().getTables().iterator().next();
        assertThat(actualTableMetaData.getColumns().size(), is(3));
        assertThat(actualTableMetaData.getIndexes().size(), is(1));
        Iterator<IndexMetaData> indexesIterator = actualTableMetaData.getIndexes().iterator();
        assertThat(indexesIterator.next(), is(new IndexMetaData("id")));
    }
}
