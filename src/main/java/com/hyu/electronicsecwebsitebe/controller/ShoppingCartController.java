package com.hyu.electronicsecwebsitebe.controller;
//huynt

import com.hyu.electronicsecwebsitebe.model.ShoppingCart;
import com.hyu.electronicsecwebsitebe.service.impl.ShoppingCartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartServiceImpl shoppingCartService;

    @GetMapping("/all")
    public ResponseEntity<List<ShoppingCart>> getAllShoppingCart() {
        List<ShoppingCart> listShoppingCarts = shoppingCartService.getAllShoppingCarts ();
        return ResponseEntity.ok (listShoppingCarts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getShoppingCartById(@PathVariable String id) {
        if (!shoppingCartService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        ShoppingCart foundShoppingCart = shoppingCartService.findById (id);
        return ResponseEntity.ok (foundShoppingCart);
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
    public ResponseEntity<Void> deleteShoppingCart(@PathVariable String id) {
        shoppingCartService.deleteById (id);
        return ResponseEntity.ok ().build ();
    }
}
