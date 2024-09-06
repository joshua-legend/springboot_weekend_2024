package com.example.springIs0902.Service;

import com.example.springIs0902.Constants.Message;
import com.example.springIs0902.Model.Product;
import com.example.springIs0902.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String getProductById(Integer id) {
        return productRepository.findById(id)
                .map(Product::getName)
                .orElse(Message.PRODUCT_NOT_FOUND.getMessage() + id);
    }

    // 새로운 메서드 getProductAll 추가
    public List<Product> getProductAll() {
        return productRepository.findAll();
    }

}
