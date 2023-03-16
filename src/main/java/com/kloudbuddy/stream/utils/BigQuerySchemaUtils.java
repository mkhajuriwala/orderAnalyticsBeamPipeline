package com.kloudbuddy.stream.utils;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableSchema;

import java.util.Arrays;

public class BigQuerySchemaUtils {
    public static TableSchema createSchema(){
        TableSchema schema =
                new TableSchema()
                        .setFields(
                                Arrays.asList(
                                        new TableFieldSchema()
                                                .setName("orderId")
                                                .setType("STRING")
                                                .setMode("REQUIRED"),
                                        new TableFieldSchema()
                                                .setName("customerId")
                                                .setType("STRING")
                                                .setMode("REQUIRED"),
                                        new TableFieldSchema()
                                                .setName("customerAddressId")
                                                .setType("STRING")
                                                .setMode("REQUIRED")
                                )
                        );
        return schema;
    }
}
