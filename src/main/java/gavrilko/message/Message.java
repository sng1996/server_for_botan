package gavrilko.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by sergeigavrilko on 21.04.17.
 */
public class Message {

    private Integer id;
    private Integer sender_id;
    private String text;
    private String date;
    private Integer status;
    private Integer chat_id;

    ObjectMapper mapper = new ObjectMapper();

    public Message(Integer id, Integer sender_id, String text, String date, Integer status, Integer chat_id) {
        this.id = id;
        this.sender_id = sender_id;
        this.text = text;
        this.date = date;
        this.status = status;
        this.chat_id = chat_id;
    }


    public Integer getId() {
        return id;
    }

    public Integer getSender_id() {
        return sender_id;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getChat_id() {
        return chat_id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSender_id(Integer sender_id) {
        this.sender_id = sender_id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setChat_id(Integer chat_id) {
        this.chat_id = chat_id;
    }

    public ObjectNode getMsgInfo(){
        final ObjectNode msgInfoResponse = mapper.createObjectNode();
        msgInfoResponse.put("id", id);
        msgInfoResponse.put("sender_id", sender_id);
        msgInfoResponse.put("text", text);
        msgInfoResponse.put("date", date);
        msgInfoResponse.put("status", status);
        msgInfoResponse.put("chat_id", chat_id);
        return msgInfoResponse;
    }
}
