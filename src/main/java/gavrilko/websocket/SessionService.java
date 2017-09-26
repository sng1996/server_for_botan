package gavrilko.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gavrilko.Singleton;
import gavrilko.database.Database;
import gavrilko.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by sergeigavrilko on 15.03.17.
 */
@Component
public class SessionService {

    /*@Autowired
    SessionPool pool;*/

    Singleton singleton = Singleton.getInstance();
    ObjectMapper mapper = new ObjectMapper();

    public void registerSession(WebSocketSession session, Integer id){
        try {
            singleton.add(id, session);
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("client number " + singleton.size() + " was added!");
    }

    public void sendMessageToClient(Integer from_id, String msg, Integer to_id, Integer chat_id) throws IOException {
        final ObjectNode jsonMessage = mapper.createObjectNode();
        final ObjectNode jsonResponse = mapper.createObjectNode();
        jsonMessage.put("code", 0);
        jsonResponse.put("message", msg);
        jsonResponse.put("from_id", from_id);
        jsonResponse.put("to_id", to_id);
        jsonResponse.put("chat_id", chat_id);
        jsonMessage.set("response", jsonResponse);
        System.out.println(mapper.writeValueAsString(jsonMessage));
        singleton.get(to_id).sendMessage(new TextMessage(mapper.writeValueAsString(jsonMessage)));

    }

    public void logout(Integer id){
        try {
            singleton.remove(id);
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("Logout " + id);
    }

    public boolean isExist(Integer id){
        return singleton.isExist(id);
    }

    public void addMsgToDB(Integer from_id, String msg, Integer chat_id){
        try {
            System.out.println("Add message to DB");
            Database.update("insert into messages VALUES (NULL, " + from_id + ", \'" + msg + "\', NULL, 1, " + chat_id + ");");
        } catch (SQLException e) {
            System.out.println("SQL error, while get message via websocket");
        }
    }

    public void syncData(Integer id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        final ArrayNode msges = mapper.createArrayNode();
        final ArrayNode chats = mapper.createArrayNode();
        try {
            Database.select("select id_c from chats where first_id = " + id + " or second_id = " + id,  result->{
                while (result.next()) {
                    final ObjectNode chat = mapper.createObjectNode();
                    Database.select("select * from messages where id_c = " + result.getInt("id_c"),  result1->{
                        while (result1.next()) {
                            Message msg = new Message(result1.getInt("id_m"), result1.getInt("sender_id"), result1.getString("text"), result1.getString("date"), result1.getInt("status"), result1.getInt("id_c"));
                            msges.add(msg.getMsgInfo());
                        }
                    });
                    chat.put("chat_id", result.getInt("id_c"));
                    chat.set("msges", msges);
                    chats.add(chat);
                }
                response.put("code", 4);
                response.set("response", chats);
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.put("code", 404);
            response.put("response", "JSON error");
        } catch (SQLException e) {
            response.put("code", 404);
            response.put("response", "SQL error");
        }
        System.out.println("SYNC DATA " + mapper.writeValueAsString(response));
        singleton.get(id).sendMessage(new TextMessage(mapper.writeValueAsString(response)));
    }

    public void messageDelivered(Integer id) throws IOException {
        final ObjectNode jsonMessage = mapper.createObjectNode();
        jsonMessage.put("code", 5);
        singleton.get(id).sendMessage(new TextMessage(mapper.writeValueAsString(jsonMessage)));
    }

}
