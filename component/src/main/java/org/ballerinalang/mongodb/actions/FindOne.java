/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.ballerinalang.mongodb.actions;

import org.ballerinalang.bre.Context;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.mongodb.Constants;
import org.ballerinalang.mongodb.MongoDBDataSource;
import org.ballerinalang.mongodb.MongoDBDataSourceUtils;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.natives.annotations.ReturnType;
/**
 * {@code FindOne} action selects the first document that satisfies the given query criteria.
 *
 * @since 0.5.5
 */
@BallerinaFunction(
            orgName = "wso2",
            packageName = "mongodb:0.0.0",
            functionName = "findOne",
            receiver = @Receiver(type = TypeKind.OBJECT, structType = Constants.CALLER_ACTIONS),
            args = {@Argument(name = "collectionName", type = TypeKind.STRING),
                    @Argument(name = "queryString", type = TypeKind.JSON)
            },
            returnType = { @ReturnType(type = TypeKind.JSON) }
        )
public class FindOne extends AbstractMongoDBAction {

    @Override
    public void execute(Context context) {
        BMap<String, BValue> bConnector = (BMap<String, BValue>) context.getRefArgument(0);
        String collectionName = context.getStringArgument(0);
        BMap query = (BMap) context.getNullableRefArgument(1);
        MongoDBDataSource datasource = (MongoDBDataSource) bConnector.getNativeData(Constants.CALLER_ACTIONS);
        try {
            BValue result = findOne(datasource, collectionName, query);
            context.setReturnValues(result);
        } catch (Throwable e) {
            context.setReturnValues(MongoDBDataSourceUtils.getMongoDBConnectorError(context, e));
        }
    }
}
