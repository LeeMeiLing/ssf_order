package ssf_pizza.PizzaOrder.models;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Delivery {
    
    @NotBlank(message = "Name is mandatory field")
    @Size(min=3, message = "Name must be more than 3 characters")
    private String name;

    @NotBlank(message = "Address is mandatory field")
    private String address;

    @NotBlank(message = "Phone number is mandatory field")
    @Digits(integer=8, fraction=0, message = "Invalid phone number format")
    @Size(min=8, message = "Invalid phone number format")
    private String phone;

    private Boolean rush;

    private String comments;

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

    @Override
    public String toString() {
        return "Delivery [name=" + name + ", address=" + address + ", phone=" + phone + ", rush=" + rush + ", comments="
                + comments + "]";
    }

    


}
