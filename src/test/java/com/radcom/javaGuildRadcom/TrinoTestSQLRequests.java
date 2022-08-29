package com.radcom.javaGuildRadcom;

public interface TrinoTestSQLRequests {
    String CREATE_HIVE = "CREATE schema hive";
    String CREATE_SAMSUNG_TABLE = "CREATE table hive.samsung(\n" +
            "envelop row(srcip varchar, srcport integer, dstip varchar, dstport integer),\n" +
            "data row(raw_size bigint, raw_buffer varbinary),\n" +
            "indexes row(time_stamp bigint, uuid varchar, edr_type varchar, mcptt_calling_party_id varchar, mcptt_group_id varchar, mcptt_called_party_id varchar, p25_called_subscriber_id varchar, p25_calling_subscriber_id varchar, p25_group_id varchar, mcptt_party_id varchar, mcptt_dst_party_id varchar, p25_source_id varchar, message_name varchar, src_subscriber_id varchar, dest_subscriber_id varchar, group_id varchar, iwf_group_id varchar, mcptt_src_party_id varchar, group_name varchar),\n" +
            "ts varchar,\n" +
            "topic varchar\n" +
            ")";
    String CREATE_DATA = "INSERT INTO samsung (envelop, \"data\", indexes, ts, topic) VALUES\n" +
            "\t(\n" +
            "\t\trow ('172.29.121.224', 8080, '10.233.81.199', 8080), \n" +
            "\t\trow (8080, X'4c89874e'),\n" +
            "\t\trow(1626940597431, '9K44Ihk', 'EDR_DIAM_MCPTX_RECORD', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),\n" +
            "\t\t'2021-07-22--07',\n" +
            "\t\t'samsung-1'\n" +
            "\t),\n" +
            "\t(\n" +
            "\t\trow ('172.29.121.224', 8080, '10.233.81.199', 8080), \n" +
            "\t\trow (8080, X'4c89874e'),\n" +
            "\t\trow(1626940597431, '9K44Ihk', 'EDR_DIAM_MCPTX_RECORD', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),\n" +
            "\t\t'2021-07-22--07',\n" +
            "\t\t'samsung-1'\n" +
            "\t)";

}
