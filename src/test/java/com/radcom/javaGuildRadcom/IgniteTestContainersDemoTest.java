package com.radcom.javaGuildRadcom;

import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.function.Consumer;

@Testcontainers
public class IgniteTestContainersDemoTest {
    protected static Consumer<CreateContainerCmd> cmd = e -> e.withPortBindings(new PortBinding(Ports.Binding.bindPort(10800), new ExposedPort(10800)));

    @Container
    protected static GenericContainer ignite = new GenericContainer(DockerImageName.parse("apacheignite/ignite:2.8.1"))
            .withExposedPorts(10800).withCreateContainerCmdModifier(cmd);

    @Test
    void igniteTest(){
        ClientConfiguration cfg = new ClientConfiguration().setAddresses(ignite.getHost() + ":10800");
        try (IgniteClient client = Ignition.startClient(cfg)) {
            ClientCache<Integer, String> cache = client.getOrCreateCache("myCache");
            // Get data from the cache
            cache.put(11111, "TestValue");
            cache.put(22222, "TestValue_22222");

            var actual = cache.get(11111);
            Assertions.assertEquals("TestValue", actual);
            actual = cache.get(22222);
            Assertions.assertEquals("TestValue_22222", actual);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
