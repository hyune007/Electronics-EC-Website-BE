package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.ShoppingCart;
import com.hyu.electronicsecwebsitebe.repository.ShoppingCartRepository;
import com.hyu.electronicsecwebsitebe.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Override
    public List<ShoppingCart> getAllShoppingCarts() {
        return shoppingCartRepository.findAll ();
    }

    @Override
    public ShoppingCart findById(String id) {
        return shoppingCartRepository.findById (id).orElse (null);
    }

    @Override
    public ShoppingCart findByCustomerId(String customerId) {
        return shoppingCartRepository.findByCustomerId (customerId);
    }

    @Override
    public ShoppingCart saveShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save (shoppingCart);
    }

    @Override
    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save (shoppingCart);
    }

    @Override
    public boolean existsById(String id) {
        return shoppingCartRepository.existsById (id);
    }

    @Override
    public void deleteById(String id) {
        shoppingCartRepository.deleteById (id);
    }
}
