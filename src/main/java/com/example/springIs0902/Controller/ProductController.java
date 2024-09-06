package com.example.springIs0902.Controller;


import com.example.springIs0902.Model.Product;
import com.example.springIs0902.Repository.ProductRepository;
import com.example.springIs0902.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ProductController {


    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    @ResponseBody
    public String getProductById(@RequestParam("id") Integer id) {
        return productService.getProductById(id);
    }

    //productAll
    @GetMapping("/productAll")
    @ResponseBody
    public List<Product> getProductsAll() {
        return productService.getProductAll();
    }
}
