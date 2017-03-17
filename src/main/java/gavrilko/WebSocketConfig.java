package gavrilko;

import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.validation.constraints.NotNull;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by sergeigavrilko on 05.03.17.
 */

@EnableWebSocket
 public class WebSocketConfig implements WebSocketConfigurer {

    @NotNull
    private final WebSocketHandler webSocketHandler;

    public WebSocketConfig(@NotNull WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/gameapi")
                               .addInterceptors(new HttpSessionHandshakeInterceptor())
                                .setAllowedOrigins("*");
    }

}

