package com.gcp.subscriber.bigtable;

import com.google.cloud.bigtable.admin.v2.BigtableTableAdminClient;
import com.google.cloud.bigtable.admin.v2.BigtableTableAdminSettings;
import com.google.cloud.bigtable.data.v2.BigtableDataClient;
import com.google.cloud.bigtable.data.v2.BigtableDataSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class BigTableConfiguration {

    @Value("${gcp.bigtable.projectId}")
    private String projectId;

    @Value("${gcp.bigtable.instanceId}")
    private String instanceId;


    @Bean
    public BigtableDataClient dataClient() throws IOException {
        BigtableDataSettings dataSettings = BigtableDataSettings.newBuilder()
                .setProjectId(projectId)
                .setInstanceId(instanceId)
                .build();
        return BigtableDataClient.create(dataSettings);
    }

    @Bean
    public BigtableTableAdminClient adminClient() throws IOException {
        BigtableTableAdminSettings adminSettings =
                BigtableTableAdminSettings.newBuilder()
                        .setProjectId(projectId)
                        .setInstanceId(instanceId)
                        .build();
        return BigtableTableAdminClient.create(adminSettings);
    }
}