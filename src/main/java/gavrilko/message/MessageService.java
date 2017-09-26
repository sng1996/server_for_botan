package gavrilko.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gavrilko.database.Database;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

/**
 * Created by sergeigavrilko on 21.04.17.
 */
public class MessageService {

    public ResponseEntity contacts(Integer id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        ArrayNode resp = mapper.createArrayNode();
        try {
            Database.select("select id_o from orders where (client = " + id + " or executor = " + id + ") and (status = 1 or status = 2 or status = 3 or status = 4)", result->{
                while (result.next()) {
                    ObjectNode node = mapper.createObjectNode();
                    Database.select("select id_c, first_id, second_id from chats where id_o = "+ result.getInt("id_o"), result1->{
                        result1.next();
                        node.put("chat_id", result1.getInt("id_c"));
                        if (result1.getInt("first_id") == id){
                            Database.select("select name from profile where id_p = " + result1.getInt("second_id"), result2->{
                                result2.next();
                                node.put("target_id", result1.getInt("second_id"));
                                node.put("target_name", result2.getString("name"));
                            });
                        }
                        else{
                            Database.select("select name from profile where id_p = " + result1.getInt("first_id"), result2->{
                                result2.next();
                                node.put("target_id", result1.getInt("first_id"));
                                node.put("target_name", result2.getString("name"));
                            });
                        }
                        node.put("order_id", result.getInt("id_o"));
                    });
                    resp.add(node);
                }
                response.put("code", 110);
                response.set("response", resp);
                System.out.println(mapper.writeValueAsString(response));
                return ResponseEntity.ok().body(mapper.writeValueAsString(response));
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("JSON problem contacts");
            response.put("code", 1);
            response.put("response", 1);
        } catch (SQLException e) {
            System.out.println("SQL problem contacts");
            response.put("code", 2);
            response.put("response", 2);
        }
        return ResponseEntity.ok().body(mapper.writeValueAsString(response));
    }

}
