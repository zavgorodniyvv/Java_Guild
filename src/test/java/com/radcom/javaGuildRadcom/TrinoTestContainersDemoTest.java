package com.radcom.javaGuildRadcom;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.TrinoContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static com.radcom.javaGuildRadcom.TrinoTestSQLRequests.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest
public class TrinoTestContainersDemoTest {

    @Container
    public static TrinoContainer trino = new TrinoContainer(DockerImageName.parse("trinodb/trino"));
//            .withDatabaseName("testDB")
//            .withUsername("user")
//            .withPassword("Radcom_01");

    static Connection connection;


    @BeforeAll
    public static void createTable() throws SQLException {

        System.out.println("JDBC: " + trino.getJdbcUrl());
        System.out.println("Database name: " + trino.getDatabaseName());
        System.out.println("User: " + trino.getUsername());
        System.out.println("Password: " + trino.getPassword());
        System.out.println();

        String connectionString = trino.getJdbcUrl();
        Properties properties = new Properties();
        properties.setProperty("user", "admin");
        connection = DriverManager.getConnection(connectionString + "memory/hive", properties);
        var statement = connection.createStatement();
        statement.execute(CREATE_HIVE);
        statement.execute(CREATE_SAMSUNG_TABLE);
        statement.execute(CREATE_DATA);
        System.out.println();
    }

    @Test
    void test() throws SQLException {
        List<Map<String, Object>> resultFromTrino = getResultFromTrino();
        assertEquals(2, resultFromTrino.size());

        System.out.println(resultFromTrino);
    }

    @NotNull
    private static List<Map<String, Object>> getResultFromTrino() throws SQLException {
        Statement statement = connection.createStatement();
        var result = statement.executeQuery("select * from samsung");
        List<Map<String, Object>> resultFromTrino;
        List<String> columns = new ArrayList<>(result.getMetaData().getColumnCount());
        for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
            columns.add(result.getMetaData().getColumnName(i).toUpperCase());
        }
        resultFromTrino = new ArrayList<>();
        while (result.next()) {
            Map<String, Object> row = new HashMap<>(columns.size());
            for (String column : columns) {
                row.put(column, result.getObject(column));
            }
            resultFromTrino.add(row);
        }
        return resultFromTrino;
    }

}
