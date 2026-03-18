package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.Product;
import com.hyu.electronicsecwebsitebe.model.ShoppingCart;
import com.hyu.electronicsecwebsitebe.repository.ProductRepository;
import com.hyu.electronicsecwebsitebe.repository.ShoppingCartRepository;
import com.hyu.electronicsecwebsitebe.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ShoppingCart> getAllShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    @Override
    public ShoppingCart findById(String id) {
        return shoppingCartRepository.findById(id).orElse(null);
    }

    @Override
    public List<ShoppingCart> findByCustomerId(String customerId) {
        return shoppingCartRepository.findByCustomerId(customerId);
    }

    @Override
    public ShoppingCart saveShoppingCart(ShoppingCart shoppingCart) {
        if (shoppingCart.getId() == null || shoppingCart.getId().isEmpty()) {
            shoppingCart.setId(generateShoppingCartId());
        }
        Product product = productRepository
                .findById(shoppingCart.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        int quantity = shoppingCart.getQuantity();

        validateStock(product, quantity);

        shoppingCart.setProduct(product);

        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {

        ShoppingCart existingCart = shoppingCartRepository
                .findById(shoppingCart.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));

        Product product = existingCart.getProduct();
        int quantity = shoppingCart.getQuantity();

        validateStock(product, quantity);

        existingCart.setQuantity(quantity);

        return shoppingCartRepository.save(existingCart);
    }

    @Override
    public boolean existsById(String id) {
        return shoppingCartRepository.existsById(id);
    }

    @Override
    public void deleteById(String id) {
        shoppingCartRepository.deleteById(id);
    }

    private String generateShoppingCartId() {
        // tạo mã random
        return "GH" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    private void validateStock(Product product, int quantity) {
        if (quantity <= 0) {
            throw new RuntimeException("Số lượng phải lớn hơn 0");
        }

        if (quantity > product.getStock()) {
            throw new RuntimeException("Số lượng sản phẩm trong giỏ vượt quá tồn kho");
        }
    }
}
