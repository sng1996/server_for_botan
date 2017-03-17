package gavrilko.person;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by sergeigavrilko on 05.03.17.
 */
public class Person {

    private Integer id;
    private String email;
    private String phone;
    private String name;
    private Integer rating;
    private String photo;
    private Integer balance;
    private String password;
    private WebSocketSession session;


    ObjectMapper mapper = new ObjectMapper();

    public Person() {
    }

    public Person(Integer id, String email, String phone, String name, Integer rating, String photo, Integer balance, String password) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.rating = rating;
        this.photo = photo;
        this.balance = balance;
        this.password = password;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ObjectNode getProfileInfo(){
        final ObjectNode profileInfoResponse = mapper.createObjectNode();
        profileInfoResponse.put("id", id);
        profileInfoResponse.put("email", email);
        profileInfoResponse.put("phone", phone);
        profileInfoResponse.put("name", name);
        profileInfoResponse.put("rating", rating);
        profileInfoResponse.put("photo", photo);
        profileInfoResponse.put("balance", balance);
        profileInfoResponse.put("password", password);
        return profileInfoResponse;
    }

}
