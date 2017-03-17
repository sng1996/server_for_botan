package gavrilko.person;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gavrilko.database.Database;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

/**
 * Created by sergeigavrilko on 05.03.17.
 */
public class PersonService {

    public ResponseEntity registerPerson(Person body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        try {
            Database.update("insert into profile VALUES (NULL, \'" + body.getEmail() + "\', NULL, \'" + body.getName() + "\', \'" + body.getPassword() + "\', NULL, NULL, NULL)");
            Database.select("select max(id_p) as id from profile;",  result->{
                result.next();
                response.put("code", 0);
                response.put("id", result.getInt("id"));
                return ResponseEntity.ok().body(mapper.writeValueAsString(response));
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.put("code", 1);
            response.put("response", 0);
        } catch (SQLException e) {
            response.put("code", 2);
            response.put("response", 0);
        }
        return ResponseEntity.ok().body(mapper.writeValueAsString(response));
    }

    public ResponseEntity enterPerson(Person body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        try {
            Database.select("select id_p from profile where email = \'" + body.getEmail() + "\' and password = \'" + body.getPassword() + "\';",  result->{
                result.next();
                response.put("code", 0);
                response.put("id", result.getInt("id_p"));
                return ResponseEntity.ok().body(mapper.writeValueAsString(response));
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.put("code", 1);
            response.put("response", "JSON error");
            System.out.println("JSON");
        } catch (SQLException e) {
            response.put("code", 2);
            response.put("response", "SQL error");
            System.out.println("SQL");
        }
        return ResponseEntity.ok().body(mapper.writeValueAsString(response));
    }

    public ResponseEntity profilePerson(Integer id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        try {
            Database.select("select * from profile join review using(id_p) where id_p = " + id + ";",  result->{
                result.next();
                Person person = new Person(result.getInt("id_p"), result.getString("email"), result.getString("phone"), result.getString("name"), result.getInt("rating"), result.getString("photo"), result.getInt("balance"), result.getString("password"));
                response.put("code", 0);
                response.set("response", person.getProfileInfo());
                System.out.println(mapper.writeValueAsString(response));
                return ResponseEntity.ok().body(mapper.writeValueAsString(response));
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.put("code", 0);
            response.put("response", 1);
        } catch (SQLException e) {
            response.put("code", 0);
            response.put("response", 2);
        }
        return ResponseEntity.ok().body(mapper.writeValueAsString(response));
    }

}
