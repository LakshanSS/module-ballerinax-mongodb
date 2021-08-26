// Copyright (c) 2020 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
//
// WSO2 Inc. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.ballerinalang.mongodb.exceptions;

import io.ballerina.runtime.api.creators.ErrorCreator;
import io.ballerina.runtime.api.creators.ValueCreator;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BError;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BString;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.ballerinalang.mongodb.MongoDBConstants.APPLICATION_ERROR;
import static org.ballerinalang.mongodb.MongoDBConstants.BAL_PACKAGE;
import static org.ballerinalang.mongodb.MongoDBConstants.DatabaseError.DETAIL_FIELD_MONGODB_EXCEPTION;
import static org.ballerinalang.mongodb.MongoDBConstants.DatabaseError.DETAIL_RECORD_NAME;
import static org.ballerinalang.mongodb.MongoDBConstants.DatabaseError.NAME;

/**
 * Map Java Exception to Ballerina MongoDB Error.
 */
public class BallerinaErrorGenerator {
    private static final Logger log = Logger.getLogger(BallerinaErrorGenerator.class.getName());

    public static BError createBallerinaDatabaseError(Exception e) {
        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put(DETAIL_FIELD_MONGODB_EXCEPTION, e.getClass().getSimpleName());
        log.info("this is a log from line 44 ");
        BMap<BString, Object> recordValue = ValueCreator
                .createRecordValue(BAL_PACKAGE, DETAIL_RECORD_NAME, valueMap);
        log.info("this is a log from line 47 ");
        return ErrorCreator.createDistinctError(NAME, BAL_PACKAGE, StringUtils.fromString(e.getMessage()), recordValue);
    }

    public static BError createBallerinaApplicationError(Exception e) {
        log.info("this is a log from line 53 ");
        //return ErrorCreator.createDistinctError(APPLICATION_ERROR, BAL_PACKAGE, StringUtils.fromString(e.getMessage()));
        BError bError = ErrorCreator.createError(e);
        log.info("this is a log from line 56 ");
        return ErrorCreator.createError(BAL_PACKAGE, APPLICATION_ERROR, 
                StringUtils.fromString(e.getMessage()), bError, e);
    }

}
