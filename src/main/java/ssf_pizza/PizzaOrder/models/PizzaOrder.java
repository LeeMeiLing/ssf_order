package ssf_pizza.PizzaOrder.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PizzaOrder {

    public static final String[] pizzaType = {"bella","margherita", "marinara", "spianatacalabrese", "trioformaggio"};
    public static final String[] pizzaSize = {"sm","md", "lg"};

    @NotBlank(message="Please select a pizza type")
    private String pizza;

    @NotBlank(message = "Please indicate pizza size")
    private String size;

    @NotNull // @NotBlank & NotEmpty only for String
    @Min(value = 1 , message = "Minimum qty per order is 1")
    @Max(value = 10 , message = "Maximum qty per order is 10")
    private Integer quantity;

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
    
    public Boolean checkType(String type){
        
        for (String t : pizzaType) {
            if (type.equals(t)){
                return true;
            }
        }
        
        return false;
        
    }

    public Boolean checkSize(String size){
        
        for (String s : pizzaSize) {
            if (size.equals(s)){
                return true;
            }
        }
        
        return false;
        
    }

    @Override
    public String toString() {
        return "PizzaOrder [pizza=" + pizza + ", size=" + size + ", quantity=" + quantity + "]";
    }

    
}
