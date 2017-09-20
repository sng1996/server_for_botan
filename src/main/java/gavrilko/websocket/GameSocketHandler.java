package gavrilko.websocket;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.naming.AuthenticationException;
import java.io.IOException;

/**
 * Created by sergeigavrilko on 05.03.17.
 */

public class GameSocketHandler extends TextWebSocketHandler {

    SessionService sessionService = new SessionService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws AuthenticationException {

        System.out.println("CONNECTED");

    }

    @SuppressWarnings("OverlyBroadCatchBlock")
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws AuthenticationException, IOException {
        System.out.println(message.getPayload());
        final String jsonString = message.getPayload();

        final JsonParser parser = new JsonParser();
        final JsonObject root = parser.parse(jsonString).getAsJsonObject();

        final Integer code = Integer.valueOf(root.get("code").toString());

        switch (code){
            case 1://message
                final String msg = root.getAsJsonObject("response").get("message").toString();
                final Integer from_id = Integer.valueOf(root.getAsJsonObject("response").get("from_id").toString());
                final Integer to_id = Integer.valueOf(root.getAsJsonObject("response").get("to_id").toString());
                sessionService.sendMessageToClient(from_id, msg, to_id);
                break;
            case 2://register session
                Integer id = Integer.valueOf(root.get("id").toString());
                sessionService.registerSession(session,  id);
            break;
            default: break;

        }

    }


    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        System.out.println("Websocket transport problem");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
