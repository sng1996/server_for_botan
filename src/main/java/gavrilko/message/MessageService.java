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

    public ResponseEntity get_contacts(Integer id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        ArrayNode resp = mapper.createArrayNode();
        try {
            Database.select("select id_o from orders where (client = " + id + " or executor = " + id + ") and status = 1", result->{
                while (result.next()) {
                    ObjectNode node = mapper.createObjectNode();
                    Database.select("select message, sender, date, reciever from messages where date = (select max(date) from messages where id_o = " + result.getInt("id_o") + ")", result1->{
                        result1.next();
                        node.put("order_id", result.getInt("id_o"));
                        if (result1.getInt("sender") == id){
                            node.put("is_my", 1);
                            Database.select("select name from profile where id_p = " + result1.getInt("reciever") + "", result2->{
                                result2.next();
                                node.put("companion_id", result1.getInt("reciever"));
                                node.put("name", result2.getString("name"));
                            });
                        }
                        else{
                            node.put("is_my", 0);
                            Database.select("select name from profile where id_p = " + result1.getInt("sender") + "", result2->{
                                result2.next();
                                node.put("companion_id", result1.getInt("sender"));
                                node.put("name", result2.getString("name"));
                            });
                        }
                        node.put("message", result1.getString("message"));
                        node.put("date", result1.getString("date"));
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
            response.put("code", 1);
            response.put("response", 1);
        } catch (SQLException e) {
            response.put("code", 2);
            response.put("response", 2);
        }
        return ResponseEntity.ok().body(mapper.writeValueAsString(response));
    }

}
