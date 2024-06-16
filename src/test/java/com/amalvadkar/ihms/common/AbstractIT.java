package com.amalvadkar.ihms.common;

import com.amalvadkar.ihms.TestConfig;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Tag("it")
@Import({ContainersConfig.class , TestConfig.class})
@ActiveProfiles("it")
@Testcontainers
public abstract class AbstractIT {

    @RegisterExtension
    protected static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("user", "admin"))
            .withPerMethodLifecycle(false);
    static final GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:7.2.4-alpine"))
            .withExposedPorts(6379);

    static {
        redis.start();
        System.out.println("Fetch settings");
        System.setProperty("spring.data.redis.host", redis.getHost());
        System.setProperty("spring.data.redis.host", redis.getMappedPort(6379).toString());
        System.out.println("The local port mapped to 6379 is " + redis.getMappedPort(6379).toString());
    }

    @LocalServerPort
    int port;


    @BeforeEach
    void setUp(){
        RestAssured.port = port;
    }


}
