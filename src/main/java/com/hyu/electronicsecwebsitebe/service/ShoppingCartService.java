package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    List<ShoppingCart> getAllShoppingCarts();

    ShoppingCart getShoppingCartById(String id);

    ShoppingCart findByCustomerId(String customerId);

    ShoppingCart saveShoppingCart(ShoppingCart shoppingCart);

    ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);

    void deleteById(String id);
}
