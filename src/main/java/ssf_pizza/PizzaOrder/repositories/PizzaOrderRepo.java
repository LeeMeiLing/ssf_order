package ssf_pizza.PizzaOrder.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpSession;
import ssf_pizza.PizzaOrder.models.Confirmation;

@Repository
public class PizzaOrderRepo {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveOrder(HttpSession session){

        // retrieve the confirmation
        Confirmation conf = (Confirmation) session.getAttribute("confirmation");

        // convert into JSON object
        JsonObject json = Json.createObjectBuilder()
            .add("orderId", conf.getOrderId() + ", string")
            .add("name", conf.getName() + ", string" + ", delivery")
            .add("address", conf.getAddress() + ", string" + ", delivery")
            .add("phone", conf.getPhone() + ", string" + ", delivery")
            .add("rush", conf.getRush() + ", boolean" + ", delivery")
            .add("comments", conf.getComments() + ", string" + ", delivery")
            .add("pizza", conf.getPizza() + ", string" + ", index")
            .add("size", conf.getSize() + ", string" + ", index")
            .add("quantity", conf.getQuantity() + ", number" + ", index")
            .add("total", conf.getTotal() + ", number")
            .build();

        // save to redis
        redisTemplate.opsForValue().set(conf.getOrderId(), json.toString());

    }

    public Optional<String> getOrder(String orderId) {

        String result = (String) redisTemplate.opsForValue().get(orderId);
        if (null == result){
            return Optional.empty();
        }else{
            return Optional.of(result);
        }

    }
    
}
