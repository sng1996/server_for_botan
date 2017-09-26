package gavrilko.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.messaging.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import gavrilko.database.Database;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.sql.SQLException;

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
        Integer id;
        final Integer code = Integer.valueOf(root.get("code").toString());

        switch (code){
            case 1://message
                final String msg = root.getAsJsonObject("response").get("message").toString();
                final Integer from_id = Integer.valueOf(root.getAsJsonObject("response").get("from_id").toString());
                final Integer to_id = Integer.valueOf(root.getAsJsonObject("response").get("to_id").toString());
                final Integer chat_id = Integer.valueOf(root.getAsJsonObject("response").get("chat_id").toString());
                sessionService.addMsgToDB(from_id, msg, chat_id);
                if (sessionService.isExist(to_id)){
                    sessionService.sendMessageToClient(from_id, msg, to_id, chat_id);
                }
                sessionService.messageDelivered(from_id);
                break;
            case 2://register session
                id = Integer.valueOf(root.get("id").toString());
                sessionService.registerSession(session,  id);
                sessionService.syncData(id);
                break;
            case 3://logout session
                id = Integer.valueOf(root.get("id").toString());
                sessionService.logout(id);
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
