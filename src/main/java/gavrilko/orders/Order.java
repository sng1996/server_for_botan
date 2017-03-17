package gavrilko.orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by sergeigavrilko on 05.03.17.
 */
public class Order {

    private Integer id;
    private String subject;
    private Integer type;
    private Integer category;
    private String create_date;
    private String end_date;
    private Integer cost;
    private String description;
    private Integer client;
    private Integer executor;
    private Integer status;
    private String review;
    private Boolean like;

    ObjectMapper mapper = new ObjectMapper();

    public Order() {
    }

    public Order(Integer id, String subject, Integer type, Integer category, String create_date, String end_date, Integer cost, String description, Integer client, Integer executor, Integer status, String review, Boolean like) {
        this.subject = subject;
        this.type = type;
        this.category = category;
        this.create_date = create_date;
        this.end_date = end_date;
        this.cost = cost;
        this.description = description;
        this.client = client;
        this.executor = executor;
        this.status = status;
        this.id = id;
        this.review = review;
        this.like = like;

    }
    public Integer getId() { return id; }

    public String getSubject() {
        return subject;
    }

    public Integer getType() {
        return type;
    }

    public Integer getCategory() {
        return category;
    }

    public String getCreate_date() {
        return create_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public Integer getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public Integer getClient() { return client; }

    public Integer getExecutor() { return executor; }

    public Integer getStatus() { return status; }

    public void setId(Integer id) { this.id = id; }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public void setExecutor(Integer executor) {
        this.executor = executor;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ObjectNode getOrderInfo(){
        final ObjectNode orderInfoResponse = mapper.createObjectNode();
        orderInfoResponse.put("id", id);
        orderInfoResponse.put("subject", subject);
        orderInfoResponse.put("type", type);
        orderInfoResponse.put("description", description);
        orderInfoResponse.put("create_date", create_date);
        orderInfoResponse.put("end_date", end_date);
        orderInfoResponse.put("cost", cost);
        orderInfoResponse.put("client", client);
        orderInfoResponse.put("executor", executor);
        orderInfoResponse.put("status", status);
        return orderInfoResponse;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

}
