package com.example.metric;

import com.example.metric.util.AutoConfigurePostgres;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureWebTestClient
@AutoConfigurePostgres
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

}
