package com.gcp.subscriber.bigtable.repo;

import com.google.cloud.bigtable.admin.v2.BigtableTableAdminClient;
import com.google.cloud.bigtable.admin.v2.models.CreateTableRequest;
import com.google.cloud.bigtable.data.v2.BigtableDataClient;
import com.google.cloud.bigtable.data.v2.models.RowMutation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Random;

@Repository
public class BigTableRepository {

    private static final Log LOGGER = LogFactory.getLog(BigTableRepository.class);


    private static final String COLUMN_FAMILY = "messages";
    private static final String COLUMN_QUALIFIER_MESSAGE_ID = "message_id";
    private static final String COLUMN_QUALIFIER_MESSAGE_TEXT = "message_text";
    private static final String COLUMN_QUALIFIER_MESSAGE_TIMESTAMP = "message_timestamp";
    private static final String ROW_KEY_PREFIX = "rowKey";
    private final String tableId = "data";

    private final BigtableDataClient dataClient;
    private final BigtableTableAdminClient adminClient;

    @Autowired
    public BigTableRepository(BigtableDataClient dataClient, BigtableTableAdminClient adminClient) {
        this.dataClient = dataClient;
        this.adminClient = adminClient;
    }

    public void insert(String messageText) {
        if (!adminClient.exists(tableId)) {
            createTable();
        }
        LOGGER.info("Inserting message: " + messageText);
        RowMutation rowMutation =
                RowMutation.create(tableId, ROW_KEY_PREFIX)
                        .setCell(COLUMN_FAMILY, COLUMN_QUALIFIER_MESSAGE_ID, new Random().nextInt())
                        .setCell(COLUMN_FAMILY, COLUMN_QUALIFIER_MESSAGE_TEXT, messageText)
                        .setCell(COLUMN_FAMILY, COLUMN_QUALIFIER_MESSAGE_TIMESTAMP, System.currentTimeMillis());
        dataClient.mutateRow(rowMutation);

    }

    private void createTable() {
        LOGGER.info("Creating table " + tableId);

        CreateTableRequest createTableRequest =
                CreateTableRequest.of(tableId).addFamily(COLUMN_FAMILY);
        adminClient.createTable(createTableRequest);

        LOGGER.info("Table created successfully");
    }
}