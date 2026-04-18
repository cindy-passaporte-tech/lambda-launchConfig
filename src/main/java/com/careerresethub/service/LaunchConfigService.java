package com.careerresethub.service;

import com.careerresethub.dynamo.beans.LaunchConfig;
import com.careerresethub.enums.LaunchRedirectEnum;
import com.careerresethub.registry.DynamoDbRegistry;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.time.Instant;


public class LaunchConfigService {
    private final DynamoDbTable<LaunchConfig> launchConfigTable;

    public LaunchConfigService() {
        this.launchConfigTable = DynamoDbRegistry.LAUNCH_TABLE;
    }

    public LaunchRedirectEnum getRedirect(String launchId) {
        try {
            // Busca no DynamoDB usando Enhanced Client
            LaunchConfig config = launchConfigTable.getItem(Key.builder().partitionValue(launchId).build());

            if (config == null) {
                return LaunchRedirectEnum.PRE_CAPTURE;
            }

            // Lógica de Datas
            Instant now = Instant.now();
            Instant capture = Instant.parse(config.getStartCaptureDate());
            Instant sales = Instant.parse(config.getStartSalesDate());
            Instant end = Instant.parse(config.getEndSalesDate());

            if (now.isBefore(capture)) {
                return LaunchRedirectEnum.PRE_CAPTURE;
            } else if (now.isBefore(sales)) {
                return LaunchRedirectEnum.CAPTURING;
            } else if (now.isBefore(end)) {
                return LaunchRedirectEnum.SALES_OPEN;
            } else {
                return LaunchRedirectEnum.EXPIRED;
            }

        } catch (Exception e) {
            return LaunchRedirectEnum.CAPTURING;
        }

    }
}
