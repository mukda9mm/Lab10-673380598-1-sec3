package com.example.lab10.repository;

import com.example.lab10.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProductRepository {

    private final Map<String, Product> store = new ConcurrentHashMap<>();

    public ProductRepository() {
        
        store.put("1", new Product("1", "iPhone 17 Pro - มุกดา บุญประจันทร์ (673380598-1 SEC 3)",
                "Electronics", "Apple", 20, 555555.0, "MEMBER"));
        store.put("2", new Product("2", "MacBook Air M3",
                "Electronics", "Apple", 60, 676767.0, "NONE"));
        store.put("3", new Product("3", "Samsung Galaxy S24",
                "Electronics", "Samsung", 40, 67676968.0, "SEASONAL"));
    }

    // หา 1 ชิ้นจาก id — ไม่เจอคืน Mono ว่าง
    public Mono<Product> findById(String id) {
        Product product = store.get(id);
        if (product == null) {
            return Mono.empty();
        }
        return Mono.just(product);
    }

    // เอาทุกชิ้น — หลายค่าจึงใช้ Flux
    public Flux<Product> findAll() {
        return Flux.fromIterable(store.values());
    }

    // บันทึกลง store แล้วคืนตัวที่บันทึก
    public Mono<Product> save(Product product) {
        store.put(product.getId(), product);
        return Mono.just(product);
    }

    // ลบ — ไม่ต้องคืนค่าอะไร
    public Mono<Void> deleteById(String id) {
        store.remove(id);
        return Mono.empty();
    }

    // กรองเฉพาะ category ที่ตรงกัน
    public Flux<Product> findByCategory(String category) {
        return findAll()
                .filter(p -> p.getCategory().equalsIgnoreCase(category));
    }
}