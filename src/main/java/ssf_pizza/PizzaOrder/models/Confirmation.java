package ssf_pizza.PizzaOrder.models;

import java.io.Serializable;

public class Confirmation implements Serializable{
    
    private String orderId;
    private String name;
    private String address;
    private String phone;
    private Boolean rush;
    private String comments;
    private String pizza;
    private String size;
    private Integer quantity;
    private Double total;

    
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Boolean getRush() {
        return rush;
    }
    public void setRush(Boolean rush) {
        this.rush = rush;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public String getPizza() {
        return pizza;
    }
    public void setPizza(String pizza) {
        this.pizza = pizza;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }
    @Override
    public String toString() {
        return "Confirmation [orderId=" + orderId + ", name=" + name + ", address=" + address + ", phone=" + phone
                + ", rush=" + rush + ", comments=" + comments + ", pizza=" + pizza + ", size=" + size + ", quantity="
                + quantity + ", total=" + total + "]";
    }
    
}
