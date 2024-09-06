package com.example.springIs0902.Controller;

import com.example.springIs0902.Model.Product;
import com.example.springIs0902.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class TeddyBear {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/teddy")
    @ResponseBody
    public String teddy() {
        Optional<Product> a = productRepository.findById(1);
        if (a.isPresent()) {
            System.out.println(a.get().getName());
        }
        return "안녕하세요 피싱사이트 입니다.";
    }

    @GetMapping("/findTeddy")
    @ResponseBody
    public String findTeddy(@RequestParam("id") Integer id) {
        Optional<Product> a = productRepository.findById(id);
        if (a.isPresent()) {
            return a.get().getName();
        } else {
            return "Product not found";
        }
    }


    @GetMapping("/umm")
    public String umm() {
        return "umm.html";
    }

    @GetMapping("/alive")
    public String alive(Model model) {
        model.addAttribute("name", "나비");
        return "alive.html";
    }

    @GetMapping("/aliveTest")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "Guest") String name, Model model) {
        model.addAttribute("name", name);
        return "alive.html";
    }

}
