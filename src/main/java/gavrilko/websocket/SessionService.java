package gavrilko.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gavrilko.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * Created by sergeigavrilko on 15.03.17.
 */
@Component
public class SessionService {

    /*@Autowired
    SessionPool pool;*/

    Singleton singleton = Singleton.getInstance();
    ObjectMapper mapper = new ObjectMapper();
    final ObjectNode jsonMessage = mapper.createObjectNode();
    final ObjectNode jsonResponse = mapper.createObjectNode();

    public void registerSession(WebSocketSession session, Integer id){
        System.out.println("register");
        try {
            singleton.add(id, session);
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("add");
        System.out.println("client number " + singleton.size() + " was added!");
    }

    public void sendMessageToClient(Integer from_id, String msg, Integer to_id) throws IOException {

                jsonMessage.put("code", 0);
                jsonResponse.put("message", msg);
                jsonResponse.put("from_id", from_id);
                jsonResponse.put("to_id", to_id);
                jsonMessage.set("response", jsonResponse);
                System.out.println(mapper.writeValueAsString(jsonMessage));
                singleton.get(to_id).sendMessage(new TextMessage(mapper.writeValueAsString(jsonMessage)));

    }

}
