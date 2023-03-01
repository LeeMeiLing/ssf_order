package ssf_pizza.PizzaOrder.controllers;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import ssf_pizza.PizzaOrder.services.PizzaOrderService;


@RestController
@RequestMapping(path="/order", produces=MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {

    @Autowired
    private PizzaOrderService orderSvc;
    
    @GetMapping(path="{orderId}")
    public ResponseEntity<String> getOrder(@PathVariable String orderId){

        // retrieve data from redis, this may return a not found messag
        Optional<JsonObject> order = orderSvc.getOrder(orderId);

        
        if(order.isEmpty()){  // dont use null == order
            
            // if orderID not found
            JsonObject json = Json.createObjectBuilder()
            .add("message","Order %s not found".formatted(orderId))
            .build();

            return ResponseEntity.status(404).body(json.toString());

        }else{
            // return as JSON with status ok
            return ResponseEntity.status(200).body(order.toString());
        }
           
    }
}
