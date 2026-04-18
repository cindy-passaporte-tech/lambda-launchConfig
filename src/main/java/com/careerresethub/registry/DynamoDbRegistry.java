package com.careerresethub.registry;

import com.careerresethub.dynamo.beans.LaunchConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDbRegistry {
    public static final DynamoDbTable<LaunchConfig> LAUNCH_TABLE;
    private static final DynamoDbClient ddb;
    private static final DynamoDbEnhancedClient enhancedClient;
    public static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();

        // Warm Dynamo
        ddb = DynamoDbClient.builder()
                .httpClientBuilder(UrlConnectionHttpClient.builder())
                .region(Region.US_EAST_1)
                .build();
        enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();

        try {
            ddb.listTables();
        } catch (Exception ignored) {
        }

        LAUNCH_TABLE = enhancedClient.table("LaunchSettings", TableSchema.fromBean(LaunchConfig.class));
        LAUNCH_TABLE.describeTable();
    }
}
