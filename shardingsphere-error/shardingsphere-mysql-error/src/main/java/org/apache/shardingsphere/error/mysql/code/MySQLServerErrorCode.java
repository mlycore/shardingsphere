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

package org.apache.shardingsphere.error.mysql.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.shardingsphere.error.code.SQLErrorCode;

/**
 * Server error code for MySQL.
 * 
 * @see <a href="https://dev.mysql.com/doc/mysql-errors/8.0/en/server-error-reference.html">Server Error Message Reference</a>
 */
@RequiredArgsConstructor
@Getter
public enum MySQLServerErrorCode implements SQLErrorCode {
    
    ER_DBACCESS_DENIED_ERROR(1044, "42000", "Access denied for user '%s'@'%s' to database '%s'"),
    
    ER_ACCESS_DENIED_ERROR(1045, "28000", "Access denied for user '%s'@'%s' (using password: %s)"),
    
    ER_NO_DB_ERROR(1046, "3D000", "No database selected"),
    
    ER_BAD_DB_ERROR(1049, "42000", "Unknown database '%s'"),
    
    ER_INTERNAL_ERROR(1815, "HY000", "Internal error: %s"),
    
    ER_UNSUPPORTED_PS(1295, "HY000", "This command is not supported in the prepared statement protocol yet"),
    
    ER_DB_CREATE_EXISTS_ERROR(1007, "HY000", "Can't create database '%s'; database exists"),
    
    ER_DB_DROP_NOT_EXISTS_ERROR(1008, "HY000", "Can't drop database '%s'; database doesn't exist"),
    
    ER_TABLE_EXISTS_ERROR(1050, "42S01", "Table '%s' already exists"),
    
    ER_NO_SUCH_TABLE(1146, "42S02", "Table '%s' doesn't exist"),
    
    ER_NOT_SUPPORTED_YET(1235, "42000", "This version of ShardingSphere-Proxy doesn't yet support this SQL. '%s'"),
    
    ER_SP_DOES_NOT_EXIST(1305, "42000", "Message: Data Source or ShardingSphere rule does not exist"),
    
    ER_CON_COUNT_ERROR(1040, "HY000", "Too many connections"),
    
    ER_UNKNOWN_CHARACTER_SET(1115, "42000", "Unknown character set: '%s'"),
    
    ER_WRONG_VALUE_COUNT_ON_ROW(1136, "21S01", "Column count doesn't match value count at row %d"),
    
    ER_ERROR_ON_MODIFYING_GTID_EXECUTED_TABLE(3176, "HY000",
            "Please do not modify the %s table with an XA transaction. This is an internal system table used to store GTIDs for committed transactions. "
                    + "Although modifying it can lead to an inconsistent GTID state, if necessary you can modify it with a non-XA transaction.");
    
    private final int errorCode;
    
    private final String sqlState;
    
    private final String errorMessage;
}
