
package com.example.sportssphere;

/** @brief	The com.apollographql.apollo. apollo client */

import androidx.annotation.NonNull;
import com.apollographql.apollo3.runtime.java.ApolloClient;

/** @brief	The okhttp 3. ok HTTP client */
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**********************************************************************************************//**
 * @class	ApolloClientProvider
 *
 * @brief	An apollo client provider.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

public class ApolloClientProvider {

    /**********************************************************************************************//**
     * @fn	public static ApolloClient setupApolloClient()
     *
     * @brief	Sets up the apollo client
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	ApolloClient.
     **************************************************************************************************/

    public static final String SERVER_URL = "http://10.90.74.30:8080/graphql";
    public static final String WEBSOCKET_SERVER_URL = "ws://10.90.74.30:8080/graphql";

    public static ApolloClient setupApolloClient() {
        ApolloClient apolloClient = new ApolloClient.Builder()
                .serverUrl(SERVER_URL)
                .webSocketServerUrl(WEBSOCKET_SERVER_URL)
                .build();

        return apolloClient;
    }
}
