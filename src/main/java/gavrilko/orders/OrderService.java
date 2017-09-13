package gavrilko.orders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gavrilko.database.Database;
import gavrilko.person.Person;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

/**
 * Created by sergeigavrilko on 05.03.17.
 */
public class OrderService {

    public ResponseEntity createOrder(Order body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        try {
            Database.update("insert into orders VALUES (NULL, \'" + body.getSubject() + "\', " + body.getType() + ", " + body.getCategory() + ", \'" + body.getDescription() + "\', \'" + body.getCreate_date() + "\', \'" + body.getEnd_date() + "\', " + body.getCost() + ", " + body.getClient() + ", NULL, 0, NULL, NULL)");
            Database.select("select max(id_o) as m from orders;",  result->{
                result.next();
                response.put("code", 104);
                response.put("id", result.getInt("m"));
                return ResponseEntity.ok().body(mapper.writeValueAsString(response));
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.put("code", 1);
            response.put("response", "JSON error");
        } catch (SQLException e) {
            response.put("code", 2);
            response.put("response", "SQL error");
        }
        return ResponseEntity.ok().body(mapper.writeValueAsString(response));
    }

    public ResponseEntity removeOrder(Integer id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        try {
            Database.update("DELETE from orders where id_o = " + id + ";");
            response.put("code", 101);
            response.put("response", 0);
            return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.put("code", 0);
            response.put("response", 1);
            return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        } catch (SQLException e) {
            response.put("code", 0);
            response.put("response", 2);
            return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        }
    }

    public ResponseEntity editOrder(Order body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        try {

            Database.update("update orders SET subject = \'" + body.getSubject() + "\'," +
                    "type =  " + body.getType() + ", " +
                    "category = " + body.getCategory() + ", " +
                    "discription = \'" + body.getDescription() + "\'," +
                    "create_date = \'" + body.getCreate_date() + "\'," +
                    "end_date = \'" + body.getEnd_date() + "\'," +
                    "cost = " + body.getCost() + "," +
                    "client = " + body.getClient() + "," +
                    "executor = " + body.getExecutor() + "," +
                    "status = " + body.getStatus() + " where id_o = " + body.getId() + ";");
            response.put("code", 105);
            response.put("response", 0);
            return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.put("code", 1);
            response.put("response", 1);
            return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        } catch (SQLException e) {
            response.put("code", 2);
            response.put("response", 2);
            return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        }
    }

    public ResponseEntity takeOrder(Order body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        try {

            Database.update("insert into executor values (NULL, " + body.getId() + ", " + body.getExecutor() + ", " + body.getCost() + ", \'" + body.getDate() + "\')");
            response.put("code", 0);
            response.put("response", 0);
            return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.put("code", 1);
            response.put("response", 1);
            return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        } catch (SQLException e) {
            response.put("code", 2);
            response.put("response", 2);
            return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        }
    }

    public ResponseEntity set_executor(Integer id, Integer executor) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        try {

            Database.update("update orders set executor = " + executor + ", status = 1 where id_o = " + id + ";");
            response.put("code", 108);
            response.put("response", 0);
            return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.put("code", 0);
            response.put("response", 1);
            return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        } catch (SQLException e) {
            response.put("code", 0);
            response.put("response", 2);
            return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        }
    }

    public ResponseEntity getNewOrders() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        final ArrayNode resp = mapper.createArrayNode();
        try {
            Database.select("select * from orders where status = 0",  result->{ //свободные заказы
                while (result.next()) {
                    Order order = new Order(result.getInt("id_o"), result.getString("subject"), result.getInt("type"), result.getInt("category"), result.getString("create_date"), result.getString("end_date"), result.getInt("cost"), result.getString("discription"), result.getInt("client"), result.getInt("executor"), result.getInt("status"), result.getString("review"), result.getBoolean("likes"), "");
                    resp.add(order.getOrderInfo());
                }
                response.put("code", 0);
                response.set("response", resp);
                return ResponseEntity.ok().body(mapper.writeValueAsString(response));
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.put("code", 1);
            response.put("response", 0);
            //return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        } catch (SQLException e) {
            response.put("code", 2);
            response.put("response", 0);
            //return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        }
        return ResponseEntity.ok().body(mapper.writeValueAsString(response));
    }

    public ResponseEntity currentOrder(Integer id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        final ArrayNode resp = mapper.createArrayNode();
        try {

            Database.select("select * from orders where (client = " + id + " or executor = " + id + ")",  result->{ //заказы в процессе
                while (result.next()) {
                    Order order = new Order(result.getInt("id_o"), result.getString("subject"), result.getInt("type"), result.getInt("category"), result.getString("create_date"), result.getString("end_date"), result.getInt("cost"), result.getString("description"), result.getInt("client"), result.getInt("executor"), result.getInt("status"), result.getString("review"), result.getBoolean("likes"), "");
                    resp.add(order.getOrderInfo());
                }
                response.put("code", 0);
                response.set("response", resp);
                return ResponseEntity.ok().body(mapper.writeValueAsString(response));
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.put("code", 0);
            response.put("response", 1);
            //return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        } catch (SQLException e) {
            response.put("code", 0);
            response.put("response", 2);
            //return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        }
        return ResponseEntity.ok().body(mapper.writeValueAsString(response));
    }

    public ResponseEntity historyOrder(Order body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        final ArrayNode resp = mapper.createArrayNode();
        try {

            Database.select("select * from orders where (client = " + body.getId() + " or executor = " + body.getId() + ") and status = 2",  result->{ //заказы выполненные
                while (result.next()) {
                    Order order = new Order(result.getInt("id_o"), result.getString("subject"), result.getInt("type"), result.getInt("category"), result.getString("create_date"), result.getString("end_date"), result.getInt("cost"), result.getString("description"), result.getInt("client"), result.getInt("executor"), result.getInt("status"), result.getString("review"), result.getBoolean("likes"), "");
                    resp.add(order.getOrderInfo());
                }
                response.put("code", 0);
                response.set("response", resp);
                return ResponseEntity.ok().body(mapper.writeValueAsString(response));
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.put("code", 0);
            response.put("response", 1);
            //return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        } catch (SQLException e) {
            response.put("code", 0);
            response.put("response", 2);
            //return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        }
        return ResponseEntity.ok().body(mapper.writeValueAsString(response));
    }

    public ResponseEntity addReviewOrder(Order body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        try {
            Database.update("update orders set review = \'" + body.getReview() + "\' where id_o = " + body.getId() + ";");
            response.put("code", 0);
            response.put("response", 0);
        } catch (SQLException e) {
            response.put("code", 0);
            response.put("response", 1);
        }
        return ResponseEntity.ok().body(mapper.writeValueAsString(response));
    }

    public ResponseEntity addLikeOrder(Order body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        try {
            Database.update("update orders set likes = \'" + body.getLike() + "\' where id_o = " + body.getId() + ";");
            response.put("code", 0);
            response.put("response", 0);
        } catch (SQLException e) {
            response.put("code", 0);
            response.put("response", 1);
        }
        return ResponseEntity.ok().body(mapper.writeValueAsString(response));
    }

    public ResponseEntity change_status(Integer id, Integer status) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        try {
            Database.update("update orders set status = " + status + " where id_o = " + id + ";");
            response.put("code", 102);
            response.put("response", 0);
            return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.put("code", 0);
            response.put("response", 1);
            return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        } catch (SQLException e) {
            response.put("code", 0);
            response.put("response", 2);
            return ResponseEntity.ok().body(mapper.writeValueAsString(response));
        }
    }
}
