package coms309;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.graphql.test.tester.WebSocketGraphQlTester;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import io.restassured.RestAssured;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Autowired
    private TestRestTemplate restTemplate;

    // @Test
    // public void whenMeasureResponseTime_thenOK() {
    // Response response = RestAssured.get("/logs");
    // long timeInMS = response.time();
    // long timeInS = response.timeIn(TimeUnit.SECONDS);

    // assertEquals(timeInS, timeInMS / 1000);
    // }

    @Test
    void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(
                this.restTemplate.getForObject("http://localhost:" + port + "/logs", String.class))
                        .containsWhitespaces();
    }

    @Test
    void SubscriptionTest() throws Exception {
        String url = "http://localhost:" + port + "/graphql";
        WebSocketClient client = new ReactorNettyWebSocketClient();

        WebSocketGraphQlTester tester = WebSocketGraphQlTester.builder(url, client).build();
        Flux<String> greetingFlux = tester.document("subscription { greetings }")
                .executeSubscription().toFlux("greetings", String.class); // decode at JSONPath

        StepVerifier.create(greetingFlux).expectNext("Hi!").expectNext("Bonjour!")
                .expectNext("Hola!").expectNext("Ciao!").expectNext("Zdravo!").verifyComplete();
    }
}
