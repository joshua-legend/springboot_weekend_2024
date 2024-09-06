package com.example.springIs0902.Repository;

import com.example.springIs0902.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
