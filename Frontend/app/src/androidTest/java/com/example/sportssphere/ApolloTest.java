package com.example.sportssphere;

import org.junit.Test;
import static org.junit.Assert.*;

public class ApolloTest {

    @Test
    public void testServerUrlIsCorrect() {
        assertEquals("http://10.90.74.30:8080/graphql", ApolloClientProvider.SERVER_URL);
    }

    @Test
    public void testWebSocketUrlIsCorrect() {
        assertEquals("ws://10.90.74.30:8080/graphql", ApolloClientProvider.WEBSOCKET_SERVER_URL);
    }
}
