package ssf_pizza.PizzaOrder.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ssf_pizza.PizzaOrder.models.Delivery;
import ssf_pizza.PizzaOrder.models.PizzaOrder;

@Controller
@RequestMapping(path = {"/", "/pizza"})
public class PizzaController {

    private Logger logger = Logger.getLogger(PizzaController.class.getName());

    @GetMapping
    public String home (Model model){

        model.addAttribute("order", new PizzaOrder());
        return "index";
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String next (@Valid @ModelAttribute("order") PizzaOrder order, BindingResult result, Model model){

        if(result.hasErrors()){

            logger.log(Level.INFO, "result has error"); //debug
            //model.addAttribute("order", order); // DONT DO THIS!!!
            return "index";

        }else{

            // check for other errors
            if (!(order.checkType(order.getPizza()))){
                ObjectError err = new ObjectError("wrongType", "%s is not available on the menu".formatted(order.getPizza()));
                result.addError(err);
                logger.log(Level.INFO, "pizza type has error"); //debug

                return "index";
            }
            if (!(order.checkSize(order.getSize()))){
                ObjectError err = new ObjectError("wrongSize", "Select Small, Medium or Large");
                result.addError(err);
                logger.log(Level.INFO, "pizza size has error"); //debug

                return "index";
            }

            // if no error, proceed to delivery address page
            logger.log(Level.INFO, "Pizza is %s\n".formatted(order.getPizza())); //debug
            logger.log(Level.INFO, "Size is %s\n".formatted(order.getSize())); //debug
            logger.log(Level.INFO, "Qty is %s\n".formatted(order.getQuantity())); //debug

            model.addAttribute("delivery", new Delivery());
            return "delivery";
        
        }

    }

    

}
