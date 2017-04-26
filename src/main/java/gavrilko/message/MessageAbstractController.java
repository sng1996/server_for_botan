package gavrilko.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by sergeigavrilko on 21.04.17.
 */
@CrossOrigin(origins = {"http://127.0.0.1"})
@RestController
public class MessageAbstractController {

    @RequestMapping(path = "/message/get_contacts", method = RequestMethod.GET)
    public ResponseEntity get_contacts(@RequestParam("id") Integer id) throws JsonProcessingException {
        MessageService messageService = new MessageService();
        return messageService.get_contacts(id);
    }

}
