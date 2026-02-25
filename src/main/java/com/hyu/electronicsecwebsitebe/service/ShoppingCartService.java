package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.ShoppingCart;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartService {
    List<ShoppingCart> getAllShoppingCarts();

    ShoppingCart findById(String id);

    List<ShoppingCart> findByCustomerId(String customerId);

    ShoppingCart saveShoppingCart(ShoppingCart shoppingCart);

    ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);

    boolean existsById(String id);

    void deleteById(String id);
}
