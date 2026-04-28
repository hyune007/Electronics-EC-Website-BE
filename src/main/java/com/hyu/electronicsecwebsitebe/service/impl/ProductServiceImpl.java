package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.Product;
import com.hyu.electronicsecwebsitebe.repository.ProductRepository;
import com.hyu.electronicsecwebsitebe.service.ProductService;
import com.hyu.electronicsecwebsitebe.util.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Value("${app.upload-dir:/app/uploads}")
    private String uploadBaseDir;

    @Override
    public Page<Product> getProducts(Pageable pageable, String categoryId, List<String> brandIds, String keyword, List<String> priceRanges, BigDecimal minPrice, BigDecimal maxPrice) {
        Specification<Product> spec = Specification
                .where(ProductSpecification.hasCategory(categoryId))
                .and(ProductSpecification.hasBrands(brandIds))
                .and(ProductSpecification.hasKeyword(keyword));
        if (priceRanges != null && !priceRanges.isEmpty()) {
            spec = spec.and(ProductSpecification.priceInRanges(priceRanges));
        }
        else {
            spec = spec.and(ProductSpecification.priceBetween(minPrice, maxPrice));
        }
        return productRepository.findAll(spec, pageable);
    }

    private static final String UNSAFE_PATH_CHARS_PATTERN = "[^a-zA-Z0-9_\\-]";
    private static final String ALLOWED_EXTENSIONS = "jpg|jpeg|png|gif|webp|bmp";

    @Override
    public void uploadPhoto(Product product, MultipartFile file) {
        String safeCategoryId = product.getCategory().getId().replaceAll(UNSAFE_PATH_CHARS_PATTERN, "_");
        String safeProductId = product.getId().replaceAll(UNSAFE_PATH_CHARS_PATTERN, "_");
        String originalFilename = file.getOriginalFilename();
        int dotIndex = originalFilename == null ? -1 : originalFilename.lastIndexOf(".");
        String extension = dotIndex >= 0
                ? originalFilename.substring(dotIndex).replaceAll("[^a-zA-Z0-9.]", "").toLowerCase()
                : "";
        if (extension.isEmpty() || !extension.substring(1).matches(ALLOWED_EXTENSIONS)) {
            throw new IllegalArgumentException("Unsupported file extension");
        }
        try {
            Path baseDir = Paths.get(uploadBaseDir, "photos", "products").toAbsolutePath().normalize();
            Path dirPath = baseDir.resolve(safeCategoryId).normalize();
            if (!dirPath.startsWith(baseDir)) {
                throw new SecurityException("Invalid category path");
            }
            Files.createDirectories(dirPath);
            Path path = dirPath.resolve(safeProductId + extension);
            Files.write(path, file.getBytes());
            System.out.println(path);
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        return;
    }

    @Override
    public Product findById(String id) {
        return productRepository.findById (id).orElse (null);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save (product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save (product);
    }

    @Override
    public void deleteById(String id) {
        productRepository.deleteById (id);
    }

    @Override
    public boolean existsById(String id) {
        return productRepository.existsById (id);
    }
}
