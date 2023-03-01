package ssf_pizza.PizzaOrder.services;

import java.io.StringReader;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.servlet.http.HttpSession;
import ssf_pizza.PizzaOrder.models.Confirmation;
import ssf_pizza.PizzaOrder.models.Delivery;
import ssf_pizza.PizzaOrder.models.PizzaOrder;
import ssf_pizza.PizzaOrder.repositories.PizzaOrderRepo;

@Service
public class PizzaOrderService {
    
@Autowired
private PizzaOrderRepo orderRepo;

    public Confirmation processOrder(HttpSession session){

        // retrieve delivery & pizzaorder from session
        Delivery delivery = (Delivery) session.getAttribute("delivery");
        PizzaOrder pizzaOrder = (PizzaOrder) session.getAttribute("pizzaOrder");
        
        Confirmation confirmation = new Confirmation();

        confirmation.setName(delivery.getName());
        confirmation.setAddress(delivery.getAddress());
        confirmation.setPhone(delivery.getPhone());
        confirmation.setRush(delivery.getRush()); 
        confirmation.setComments(delivery.getComments());

        confirmation.setPizza(pizzaOrder.getPizza());
        confirmation.setSize(pizzaOrder.getSize());
        confirmation.setQuantity(pizzaOrder.getQuantity());
        
        // generate order ID
        confirmation.setOrderId(UUID.randomUUID().toString().substring(0,8));

        // calculate order cost
        Double total = 0.0;

        switch(confirmation.getPizza()){
            case "bella": case "marinara": case "spianatacalabrese":
                total = 30.0;
                break;
            case "margherita":
                total = 22.0;
                break;
            case "trioformaggio":
                total = 25.0;
                break;
            
            default: 
                System.out.println("no pizza selected, this should never get print");
                break;
        }

        switch(confirmation.getSize()){
            case "sm":
                break;
            case "md":
                total = total * 1.2;
                break;
            case "lg":
                total = total * 1.5;
                break;
            default: 
                System.out.println("no pizza selected, this should never get print");
                break;
        }

        total = total * (confirmation.getQuantity());

        if(confirmation.getRush()){
            total = total + 2;
        }

        confirmation.setTotal(total);

        session.setAttribute("confirmation", confirmation);

        // save to redis as JSON
        orderRepo.saveOrder(session);

        // generate confirmation & return view page
        return confirmation;

    }

    public Optional<JsonObject> getOrder(String orderId) {

        Optional<String> order = orderRepo.getOrder(orderId);

        if(null == order){
            return Optional.empty();
        }else{
            JsonReader reader = Json.createReader(new StringReader(order.get()));
            JsonObject json = reader.readObject();
            return Optional.of(json);
        }

    }

}
