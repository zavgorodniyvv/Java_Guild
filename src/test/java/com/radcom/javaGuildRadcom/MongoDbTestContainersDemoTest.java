package com.radcom.javaGuildRadcom;

import com.radcom.javaGuildRadcom.mongo.CustomerMongoRepository;
import com.radcom.javaGuildRadcom.mongo.Customer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest
public class MongoDbTestContainersDemoTest {

    @Container
    static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @Autowired
    private CustomerMongoRepository customerMongoRepository;

    @DynamicPropertySource
    public static void changeConnectionString (DynamicPropertyRegistry registry){
        registry.add("mongodb.connectionstring", mongoDBContainer::getConnectionString);
    }

    @BeforeAll
    static void mongoBDTest() {
        System.out.println("===================== Before All");
        var containerConnectionString = mongoDBContainer.getConnectionString();
        System.out.println("Connection string: " + containerConnectionString);
        System.out.println(mongoDBContainer.getContainerInfo());
        System.out.println(mongoDBContainer.getExposedPorts());
        System.out.println(mongoDBContainer.getFirstMappedPort());
    }

    @BeforeEach
    void name() {
        List<Customer> customerDTOs  = List.of(new Customer("customer01@gmail.com", 10), new Customer("customer02@gmail.com", 9));
        customerMongoRepository.insert(customerDTOs);
    }

    @Test
    void checkUser(){
        var customers = customerMongoRepository.findAll();
        assertAll(
                () -> assertEquals(2, customers.size()),
                () -> assertEquals(10, customers.get(0).getRating().intValue()),
                () -> assertEquals(9, customers.get(1).getRating().intValue())
        );
        System.out.println();
    }
}
