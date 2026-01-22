package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    List<ShoppingCart> getAllShoppingCarts();

    ShoppingCart findById(String id);

    ShoppingCart findByCustomerId(String customerId);

    ShoppingCart saveShoppingCart(ShoppingCart shoppingCart);

    ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);

    boolean existsById(String id);

    void deleteById(String id);
}
