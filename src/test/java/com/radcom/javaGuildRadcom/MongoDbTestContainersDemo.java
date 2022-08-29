package com.radcom.javaGuildRadcom;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class MongoDbTestContainersDemo {

    @Container
    final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @Test
    void mongoBDTest() {
        System.out.println(mongoDBContainer.getConnectionString());
        System.out.println(mongoDBContainer.getContainerInfo());
        System.out.println(mongoDBContainer.getBoundPortNumbers());
        System.out.println(mongoDBContainer.getExposedPorts());
        System.out.println(mongoDBContainer.getFirstMappedPort());
        System.out.println();
    }
}
