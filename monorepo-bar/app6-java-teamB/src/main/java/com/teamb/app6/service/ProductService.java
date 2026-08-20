package com.teamb.app6.service;

import com.teamb.app6.model.Product;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public ProductService() {
        products.put(1L, new Product(1L, "Laptop", "High-performance laptop", 1299.99, "Electronics"));
        products.put(2L, new Product(2L, "Mouse", "Wireless mouse", 29.99, "Electronics"));
        products.put(3L, new Product(3L, "Desk Chair", "Ergonomic office chair", 249.99, "Furniture"));
        idCounter.set(4L);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public Optional<Product> getProductById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    public Product createProduct(Product product) {
        Long id = idCounter.getAndIncrement();
        product.setId(id);
        products.put(id, product);
        return product;
    }

    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        if (!products.containsKey(id)) {
            return Optional.empty();
        }
        updatedProduct.setId(id);
        products.put(id, updatedProduct);
        return Optional.of(updatedProduct);
    }

    public boolean deleteProduct(Long id) {
        return products.remove(id) != null;
    }

    public List<Product> getProductsByCategory(String category) {
        return products.values().stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
}
