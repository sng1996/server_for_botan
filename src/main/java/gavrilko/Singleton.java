package gavrilko;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;

/**
 * Created by sergeigavrilko on 17.03.17.
 */
public class Singleton {

    private static Singleton instance = new Singleton();
    private static HashMap<Integer, WebSocketSession> connectedUsers = new HashMap<>();

    private Singleton(){
    }

    public static Singleton getInstance(){
        return instance;
    }

    public void add(Integer id, WebSocketSession session) {
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
