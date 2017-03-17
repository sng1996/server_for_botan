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
    public ResponseEntity addOrder(@RequestBody Order body) throws JsonProcessingException {
        OrderService orderService = new OrderService();
        return orderService.addOrder(body);
    }

    @RequestMapping(path = "/order/remove", method = RequestMethod.POST)
    public ResponseEntity removeOrder(@RequestBody Order body) throws JsonProcessingException {
        OrderService orderService = new OrderService();
        return orderService.removeOrder(body);
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

    @RequestMapping(path = "/order/set_executor", method = RequestMethod.POST)
    public ResponseEntity set_executorOrder(@RequestBody Order body) throws JsonProcessingException {
        OrderService orderService = new OrderService();
        return orderService.set_executorOrder(body);
    }

    @RequestMapping(path = "/order/get_all_orders", method = RequestMethod.GET)
    public ResponseEntity get_all_Order() throws JsonProcessingException {
        OrderService orderService = new OrderService();
        return orderService.get_all_Order();
    }

    @RequestMapping(path = "/order/current_orders", method = RequestMethod.POST)
    public ResponseEntity currentOrder(@RequestBody Order body) throws JsonProcessingException {
        OrderService orderService = new OrderService();
        return orderService.currentOrder(body);
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

}
