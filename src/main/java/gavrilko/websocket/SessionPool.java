package gavrilko.websocket;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;

/**
 * Created by sergeigavrilko on 16.03.17.
 */
@Component
public class SessionPool {

    private HashMap<Integer, WebSocketSession> connectedUsers = new HashMap<>();

    public SessionPool() {
        connectedUsers = new HashMap<>();
    }

    public void add(Integer id, WebSocketSession session) {
        System.out.println("HELLO WORLD");
        connectedUsers.put(id, session);
    }

    public WebSocketSession get(Integer id) {
        return connectedUsers.get(id);
    }

    public void remove(Integer id) {
        connectedUsers.remove(id);
    }

    public Integer size() {
        return connectedUsers.size();
    }

}
