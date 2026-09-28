package com.example.lab10.controller;

import com.example.lab10.model.Product;
import com.example.lab10.service.ProductService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // GET /products/{id} → 1 ชิ้น
    @GetMapping("/{id}")
    public Mono<Product> getById(@PathVariable String id) {
        return service.getById(id);
    }

    // GET /products → ทุกชิ้น
    @GetMapping
    public Flux<Product> getAll() {
        return service.getAll();
    }

    // POST /products → เพิ่มสินค้าใหม่ (ส่ง JSON มาใน Body)
    @PostMapping
    public Mono<Product> save(@RequestBody Product product) {
                return service.save(product);
    }

    // DELETE /products/{id} → ลบ
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return service.delete(id);
    }

    // GET /products/category/{category} → กรองตามหมวด
    @GetMapping("/category/{category}")
    public Flux<Product> getByCategory(@PathVariable String category) {
        return service.getByCategory(category);
    }

    // GET /products/{id}/price → ราคาหลังลด
    @GetMapping("/{id}/price")
    public Mono<Double> getDiscountedPrice(@PathVariable String id) {
        return service.getDiscountedPrice(id);
    }
}