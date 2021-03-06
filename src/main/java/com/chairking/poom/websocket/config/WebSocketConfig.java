package com.chairking.poom.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // js 통해 endpoint 연결될 url
        registry.addEndpoint("/ws","/scno").withSockJS();
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메세지를 응답할 prefix
        registry.setApplicationDestinationPrefixes("/app","/send");
        // 메시지를 송신할 prefix
        registry.enableSimpleBroker("/topic","/receive");
    }
}
