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

    @RequestMapping(path = "/order/take", method = RequestMethod.GET)
    public ResponseEntity takeOrder(@RequestParam("id") Integer id,
                                    @RequestParam("executor") Integer executor,
                                    @RequestParam("cost") Integer cost,
                                    @RequestParam("date") String date) throws JsonProcessingException {
        OrderService orderService = new OrderService();
        return orderService.takeOrder(id, executor, cost, date);
    }

    @RequestMapping(path = "/order/set_executor", method = RequestMethod.GET)
    public ResponseEntity set_executorOrder(@RequestParam("id_order") Integer id,
                                            @RequestParam("id_executor") Integer executor) throws JsonProcessingException {
        OrderService orderService = new OrderService();
        return orderService.set_executor(id, executor);
    }

    @RequestMapping(path = "/order/get_all_orders", method = RequestMethod.GET)
    public ResponseEntity get_all_Order() throws JsonProcessingException {
        OrderService orderService = new OrderService();
        return orderService.get_all_Order();
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

}