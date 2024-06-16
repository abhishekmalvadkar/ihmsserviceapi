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
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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

    @Container
    @ServiceConnection(name = "redis")
    static final GenericContainer<?> redis = new GenericContainer<>("redis:7.2.4-alpine").withExposedPorts(6379);


    @LocalServerPort
    int port;


    @BeforeEach
    void setUp(){
        RestAssured.port = port;
    }


}
