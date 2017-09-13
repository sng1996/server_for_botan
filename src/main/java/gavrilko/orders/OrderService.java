package gavrilko.orders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gavrilko.database.Database;
import gavrilko.person.Person;
import org.springframework.http.ResponseEntity;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

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

    public ResponseEntity set_executor(Order body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        try {

            Database.update("update orders set executor = " + body.getExecutor() + ", status = 1, cost = " + body.getCost() + " where id_o = " + body.getId() + ";");
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

    public ResponseEntity getNewOrders(Integer user_id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        final ArrayNode resp = mapper.createArrayNode();
        try {
            Database.select("select * from orders where status = 0 and id_o not in (select id_o from executor where id_p = " + user_id + ")",  result->{ //свободные заказы
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

    public ResponseEntity performOrder(Integer id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        final ArrayNode resp = mapper.createArrayNode();
        try {

            Database.select("select * from orders where executor = " + id + " and status = 1",  result->{ //заказы в процессе
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

    public ResponseEntity orderedOrder(Integer id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        final ArrayNode resp = mapper.createArrayNode();
        try {

            Database.select("select * from orders where client = " + id + " and status = 1",  result->{ //заказы в процессе
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



    /*public ResponseEntity waiting(Integer user_id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        final ArrayNode resp = mapper.createArrayNode();
        try {
            Database.select("select * from executor where id_p = " + user_id,  result->{
                while (result.next()) {
                    resp.add(result.getInt("id_o"));
                }
                response.put("code", 0);
                response.set("response", resp);
            });
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
    }*/
}
