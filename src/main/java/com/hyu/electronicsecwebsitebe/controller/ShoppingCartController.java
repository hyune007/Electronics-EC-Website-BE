package com.hyu.electronicsecwebsitebe.controller;
//huynt

import com.hyu.electronicsecwebsitebe.model.ShoppingCart;
import com.hyu.electronicsecwebsitebe.service.impl.ShoppingCartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ShoppingCart> getShoppingCartByCustomerId(@PathVariable String customerId) {
        ShoppingCart foundShoppingCart = shoppingCartService.findByCustomerId (customerId);
        if (foundShoppingCart == null) {
            return ResponseEntity.notFound ().build ();
        }
        return ResponseEntity.ok (foundShoppingCart);
    }

    @PostMapping("/save")
    public ResponseEntity<ShoppingCart> saveShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        if (shoppingCartService.existsById (shoppingCart.getId ())) {
            return ResponseEntity.badRequest ().build ();
        }
        ShoppingCart savedShoppingCart = shoppingCartService.saveShoppingCart (shoppingCart);
        return ResponseEntity.status (HttpStatus.CREATED).body (savedShoppingCart);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ShoppingCart> updateShoppingCart(@PathVariable String id, @RequestBody ShoppingCart shoppingCart) {
        if (!shoppingCartService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        shoppingCart.setId (id);
        ShoppingCart updatedShoppingCart = shoppingCartService.updateShoppingCart (shoppingCart);
        return ResponseEntity.ok (updatedShoppingCart);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteShoppingCart(@PathVariable String id) {
        if (!shoppingCartService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        shoppingCartService.deleteById (id);
        return ResponseEntity.ok ().build ();
    }
}
