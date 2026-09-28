package com.example.lab10.service;

import com.example.lab10.model.Product;
import com.example.lab10.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    // หา 1 ชิ้น — ถ้าไม่เจอให้เป็น error
    public Mono<Product> getById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found: " + id)));
    }

    // เอาทุกชิ้น
    public Flux<Product> getAll() {
        return repository.findAll();
    }

    // บันทึก — ถ้าไม่มี id ให้สุ่มให้
    public Mono<Product> save(Product product) {
        if (product.getId() == null) {
            product.setId(java.util.UUID.randomUUID().toString());
        }
        return repository.save(product);
    }

    // ลบ
    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }

    // กรองตาม category
    public Flux<Product> getByCategory(String category) {
        return repository.findByCategory(category);
    }

    // ราคาหลังลด — หาสินค้าก่อน แล้ว map เอาแค่ราคา
    public Mono<Double> getDiscountedPrice(String id) {
        return getById(id).map(p -> p.getDiscountedPrice());
    }
}