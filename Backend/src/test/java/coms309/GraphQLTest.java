package coms309;

import java.net.URI;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.WebSocketGraphQlTester;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import io.restassured.RestAssured;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GraphQLTest {

        @LocalServerPort
        private int port;

        @Value("http://localhost:${local.server.port}${spring.graphql.websocket.path}")
        private String baseUrl;

        private GraphQlTester graphQlTester;

        @BeforeEach
        void setUp() {
                URI url = URI.create(baseUrl);

                RestAssured.port = port;
                RestAssured.baseURI = "http://localhost";

                this.graphQlTester = WebSocketGraphQlTester
                                .builder(url, new ReactorNettyWebSocketClient()).build();
        }

        // @Test
        // void greetingMono() {
        // this.graphQlTester.document("{greetingMono}").execute().path("greetingMono")
        // .entity(String.class).isEqualTo("Hello!");
        // }

        // @Test
        // void greetingsFlux() {
        // this.graphQlTester.document("{greetingsFlux}").execute().path("greetingsFlux")
        // .entityList(String.class)
        // .containsExactly("Hi!", "Bonjour!", "Hola!", "Ciao!", "Zdravo!");
        // }

        @Test
        void subscriptionWithEntityPath() {
                Flux<String> result = this.graphQlTester.document("subscription { greetings }")
                                .executeSubscription().toFlux("greetings", String.class);

                StepVerifier.create(result).expectNext("Hi!").expectNext("Bonjour!")
                                .expectNext("Hola!").expectNext("Ciao!").expectNext("Zdravo!")
                                .verifyComplete();
        }

        @Test
        void subscriptionWithResponse() {
                Flux<GraphQlTester.Response> result =
                                this.graphQlTester.document("subscription { greetings }")
                                                .executeSubscription().toFlux();

                StepVerifier.create(result)
                                .consumeNextWith(response -> response.path("greetings").hasValue())
                                .consumeNextWith(response -> response.path("greetings")
                                                .matchesJson("\"Bonjour!\""))
                                .consumeNextWith(response -> response.path("greetings")
                                                .matchesJson("\"Hola!\""))
                                .expectNextCount(2).verifyComplete();
        }

        @Test
        void SubscriptionTest() throws Exception {
                // String url = "http://localhost:8080/graphql";
                String url = "http://localhost:" + port + "/graphql";
                WebSocketClient client = new ReactorNettyWebSocketClient();

                WebSocketGraphQlTester tester = WebSocketGraphQlTester.builder(url, client).build();
                Flux<String> greetingFlux = tester.document("subscription { greetings }")
                                .executeSubscription().toFlux("greetings", String.class); // decode
                                                                                          // at
                                                                                          // JSONPath

                StepVerifier.create(greetingFlux).expectNext("Hi!").expectNext("Bonjour!")
                                .expectNext("Hola!").expectNext("Ciao!").expectNext("Zdravo!")
                                .verifyComplete();
        }
}
