package ssf_pizza.PizzaOrder.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import ssf_pizza.PizzaOrder.models.Confirmation;
import ssf_pizza.PizzaOrder.models.Delivery;
import ssf_pizza.PizzaOrder.models.PizzaOrder;
import ssf_pizza.PizzaOrder.services.PizzaOrderService;

@Controller
@RequestMapping(path = { "/", "/pizza" })
public class PizzaController {

    @Autowired
    private PizzaOrderService orderSvc;

    private Logger logger = Logger.getLogger(PizzaController.class.getName());

    @GetMapping
    public String home(Model model, HttpSession session) {

        PizzaOrder pizzaOrder = (PizzaOrder) session.getAttribute("pizzaOrder");

        if (null == pizzaOrder) {

            pizzaOrder = new PizzaOrder();
            session.setAttribute("pizzaOrder", pizzaOrder);

        }

        model.addAttribute("pizzaOrder", pizzaOrder);
        return "index";

    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String next(@Valid @ModelAttribute("pizzaOrder") PizzaOrder pizzaOrder, BindingResult result,
            HttpSession session, Model model) {

        if (result.hasErrors()) {

            // model.addAttribute("pizzaOrder", pizzaOrder); // DONT DO THIS!!!
            return "index";

        } else {

            // check for other errors
            if (!(pizzaOrder.checkType(pizzaOrder.getPizza()))) {
                ObjectError err = new ObjectError("wrongType",
                        "%s is not available on the menu".formatted(pizzaOrder.getPizza()));
                result.addError(err);
                logger.log(Level.INFO, "pizza type has error"); // debug

                return "index";
            }
            if (!(pizzaOrder.checkSize(pizzaOrder.getSize()))) {
                ObjectError err = new ObjectError("wrongSize", "Select Small, Medium or Large");
                result.addError(err);
                logger.log(Level.INFO, "pizza size has error"); // debug

                return "index";
            }

            // update pizzaOrder in session
            session.setAttribute("pizzaOrder",pizzaOrder);

            // if no error proceed to delivery address page
            Delivery delivery = (Delivery) session.getAttribute("delivery");

            if (null == delivery) {

                delivery = new Delivery();
                session.setAttribute("delivery", delivery);
            }

            model.addAttribute("delivery", delivery);
            return "delivery";

        }

    }

    @PostMapping(path = { "/order" }, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String submit(@Valid Delivery delivery, BindingResult result, HttpSession session, Model model) {

        if (result.hasErrors()) {
            return "delivery";
        }

        // update delivery in session 
        session.setAttribute("delivery", delivery);

        orderSvc.processOrder(session); // dont pass in delivery as arg, retrieve from session, this method is called after validation
        Confirmation confirmation = (Confirmation) session.getAttribute("confirmation");
        model.addAttribute("confirmation", confirmation);

        // invalidate session
        session.invalidate();

        return "confirmation";

        
    }

}
