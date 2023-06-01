package com.lk.tacocloud.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.lk.tacocloud.domain.Taco;
import com.lk.tacocloud.domain.TacoOrder;
import com.lk.tacocloud.domain.User;
import com.lk.tacocloud.repository.OrderRepository;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Just as with the showDesignForm() method, processTaco() finishes by returning a
String value. And just like showDesignForm(), the value returned indicates a view
that will be shown to the user. But what’s different is that the value returned from
processTaco() is prefixed with "redirect:", indicating that this is a redirect view.
More specifically, it indicates that after processTaco() completes, the user’s browser
should be redirected to the relative path /orders/current.
 The idea is that after creating a taco, the user will be redirected to an order form
from which they can place an order to have their taco creations delivered. But you
don’t yet have a controller that will handle a request for /orders/current. (p 45)
 */

@Controller
@RequestMapping("/orders")
@Slf4j
@SessionAttributes("order")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String orderForm(@AuthenticationPrincipal User user,
            @ModelAttribute("order") TacoOrder order) {
        log.info("Order in current: " + order);

        if (order.getDeliveryName() == null) {
            order.setDeliveryName(user.getFullname());
        }
        if (order.getDeliveryStreet() == null) {
        order.setDeliveryStreet(user.getStreet());
        }
        if (order.getDeliveryCity() == null) {
        order.setDeliveryCity(user.getCity());
        }
        if (order.getDeliveryState() == null) {
        order.setDeliveryState(user.getState());
        }
        if (order.getDeliveryZip() == null) {
        order.setDeliveryZip(user.getZip());
        }
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus,
            @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            log.info("errors on order", errors.getAllErrors());
            return "orderForm";
        }

        order.setUser(user);

        // log.info("Order submitted: {}", order);
        this.orderRepository.save(order);
        sessionStatus.setComplete();

        return "redirect:/"; // home. HomeController Request Mapping = "/"
    }

    
    
}
