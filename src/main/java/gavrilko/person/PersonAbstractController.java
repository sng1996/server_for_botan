package gavrilko.person;

import com.fasterxml.jackson.core.JsonProcessingException;
import gavrilko.orders.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by sergeigavrilko on 05.03.17.
 */
@CrossOrigin(origins = {"http://127.0.0.1"})
@RestController
public class PersonAbstractController {

    @RequestMapping(path = "/person/register", method = RequestMethod.POST)
    public ResponseEntity registerPerson(@RequestBody Person body) throws JsonProcessingException {
        PersonService personService = new PersonService();
        return personService.registerPerson(body);
    }

    @RequestMapping(path = "/person/enter", method = RequestMethod.POST)
    public ResponseEntity enterPerson(@RequestBody Person body) throws JsonProcessingException {
        PersonService personService = new PersonService();
        return personService.enterPerson(body);
    }

    @RequestMapping(path = "/person/profile", method = RequestMethod.GET)
    public ResponseEntity profilePerson(@RequestParam("id") Integer id) throws JsonProcessingException {
        PersonService personService = new PersonService();
        return personService.profilePerson(id);
    }

    @RequestMapping(path = "/person/get_executors", method = RequestMethod.GET)
    public ResponseEntity get_executors(@RequestParam("id") Integer id) throws JsonProcessingException {
        PersonService personService = new PersonService();
        return personService.get_executors(id);
    }

}
