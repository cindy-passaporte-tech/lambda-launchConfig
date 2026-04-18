package com.careerresethub.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.careerresethub.enums.LaunchRedirectEnum;
import com.careerresethub.registry.DynamoDbRegistry;
import com.careerresethub.service.LaunchConfigService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class LaunchConfigHandler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {
    private static final LaunchConfigService launchConfigService = new LaunchConfigService();
    private static final String ALLOWED_ORIGIN = "https://careerresethub.com";
    private static final Logger logger = Logger.getLogger(LaunchConfigHandler.class.getName());

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent event, Context context) {
        String launchId = event.getQueryStringParameters().get("launchId");
        LaunchRedirectEnum redirect = launchConfigService.getRedirect(launchId);
        ObjectMapper mapper = DynamoDbRegistry.mapper;
        try {
            String method = event.getRequestContext().getHttp().getMethod();
            logger.info("HTTP Method: " + method);

            // Handle preflight OPTIONS
            if ("OPTIONS".equalsIgnoreCase(method)) {
                logger.info("INFO: handling OPTIONS");
                return APIGatewayV2HTTPResponse.builder()
                        .withStatusCode(204)
                        .withBody("")
                        .withHeaders(Map.of(
                                "Access-Control-Allow-Origin", ALLOWED_ORIGIN,
                                "Access-Control-Allow-Methods", "POST, GET, OPTIONS",
                                "Access-Control-Allow-Headers", "Content-Type"
                        ))
                        .build();
            }

            String json = mapper.writeValueAsString(redirect);
            return APIGatewayV2HTTPResponse.builder()
                    .withStatusCode(200)
                    .withHeaders(Map.of("Content-Type", "application/json"))
                    .withBody(json)
                    .build();
        } catch (JsonProcessingException e) {
            logger.severe(e.toString());
            return APIGatewayV2HTTPResponse.builder()
                    .withStatusCode(500)
                    .withHeaders(Map.of("Content-Type", "application/json"))
                    .withBody("")
                    .build();
        } catch (Exception e) {
            APIGatewayV2HTTPResponse responseError = APIGatewayV2HTTPResponse.builder().withStatusCode(404).withBody("").build();
            logger.severe(responseError.toString() + e);
            return responseError;
        }
    }
}
