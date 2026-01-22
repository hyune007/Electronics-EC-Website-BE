package com.hyu.electronicsecwebsitebe.controller;
//huynt

import com.hyu.electronicsecwebsitebe.model.ShoppingCart;
import com.hyu.electronicsecwebsitebe.service.impl.ShoppingCartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartServiceImpl shoppingCartService;

    @GetMapping("/all")
    public List<ShoppingCart> getAllShoppingCart() {
        return shoppingCartService.getAllShoppingCarts ();
    }

    @GetMapping("/{id}")
    public ShoppingCart findById(@PathVariable String id) {
        return shoppingCartService.getShoppingCartById (id);
    }

    @GetMapping("/customer/{customerId}")
    public ShoppingCart findByCustomerId(@PathVariable String customerId) {
        return shoppingCartService.findByCustomerId (customerId);
    }

    @PostMapping("/save")
    public ShoppingCart saveShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        return shoppingCartService.saveShoppingCart (shoppingCart);

    }

    @PutMapping("/update")
    public ShoppingCart updateShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        return shoppingCartService.updateShoppingCart (shoppingCart);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable String id) {
        shoppingCartService.deleteById (id);
    }
}
