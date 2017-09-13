package gavrilko.orders;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by sergeigavrilko on 05.03.17.
 */
@CrossOrigin(origins = {"http://127.0.0.1"})
@RestController
public class OrderAbstractController {

    @RequestMapping(path = "/order/create", method = RequestMethod.POST)
    public ResponseEntity createOrder(@RequestBody Order body) throws JsonProcessingException {
        OrderService orderService = new OrderService();
        return orderService.createOrder(body);
    }

    @RequestMapping(path = "/order/remove", method = RequestMethod.GET)
    public ResponseEntity removeOrder(@RequestParam("id") Integer id) throws JsonProcessingException {
        OrderService orderService = new OrderService();
        return orderService.removeOrder(id);
    }

    @RequestMapping(path = "/order/edit", method = RequestMethod.POST)
    public ResponseEntity editOrder(@RequestBody Order body) throws JsonProcessingException {
        OrderService orderService = new OrderService();
        return orderService.editOrder(body);
    }

    @RequestMapping(path = "/order/take", method = RequestMethod.POST)
    public ResponseEntity takeOrder(@RequestBody Order body) throws JsonProcessingException {
        OrderService orderService = new OrderService();
        return orderService.takeOrder(body);
    }

    @RequestMapping(path = "/order/set_executor", method = RequestMethod.GET)
    public ResponseEntity set_executorOrder(@RequestParam("id_order") Integer id,
                                            @RequestParam("id_executor") Integer executor) throws JsonProcessingException {
        OrderService orderService = new OrderService();
        return orderService.set_executor(id, executor);
    }

    @RequestMapping(path = "/order/new", method = RequestMethod.GET)
    public ResponseEntity getNewOrders() throws JsonProcessingException {
        OrderService orderService = new OrderService();
        return orderService.getNewOrders();
    }

    @RequestMapping(path = "/order/current_orders", method = RequestMethod.GET)
    public ResponseEntity currentOrder(@RequestParam("id") Integer id) throws JsonProcessingException {
        OrderService orderService = new OrderService();
        return orderService.currentOrder(id);
    }

    @RequestMapping(path = "/order/add_review", method = RequestMethod.POST)
    public ResponseEntity addReviewOrder(@RequestBody Order body) throws JsonProcessingException {
        OrderService orderService = new OrderService();
        return orderService.addReviewOrder(body);
    }

    @RequestMapping(path = "/order/rating", method = RequestMethod.POST)
    public ResponseEntity addLikeOrder(@RequestBody Order body) throws JsonProcessingException {
        OrderService orderService = new OrderService();
        return orderService.addLikeOrder(body);
    }

    @RequestMapping(path = "/order/change_status", method = RequestMethod.GET)
    public ResponseEntity change_status(@RequestParam("id") Integer id, @RequestParam("status") Integer status) throws JsonProcessingException {
        OrderService orderService = new OrderService();
        return orderService.change_status(id, status);
    }

    @RequestMapping(path = "/order/waiting", method = RequestMethod.GET)
    public ResponseEntity waiting(@RequestParam("user_id") Integer user_id) throws JsonProcessingException {
        OrderService orderService = new OrderService();
        return orderService.waiting(user_id);
    }

}
