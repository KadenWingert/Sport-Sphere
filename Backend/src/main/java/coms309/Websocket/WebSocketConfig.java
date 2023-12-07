
// package coms309.Websocket;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// /** @brief The org.springframework.context.annotation. bean */
// import org.springframework.context.annotation.Bean;
// /** @brief The org.springframework.context.annotation. configuration */
// import org.springframework.context.annotation.Configuration;
// /** @brief The org.springframework.web.socket.server.standard. server endpoint exporter */
// import org.springframework.web.socket.server.standard.ServerEndpointExporter;

// /**********************************************************************************************/
// /**
// * @class WebSocketConfig
// *
// * @brief A web socket configuration.
// *
// * @author Arie
// * @date 10/20/2023
// **************************************************************************************************/

// @Configuration
// public class WebSocketConfig {

// private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

// /**********************************************************************************************/
// /**
// * @fn @Bean public ServerEndpointExporter serverEndpointExporter()
// *
// * @brief Server endpoint exporter
// *
// * @author Arie
// * @date 10/20/2023
// *
// * @returns ServerEndpointExporter.
// **************************************************************************************************/

// @Bean
// public ServerEndpointExporter serverEndpointExporter() {
// logger.info("Create websocket endpoint");
// return new ServerEndpointExporter();
// }
// }
