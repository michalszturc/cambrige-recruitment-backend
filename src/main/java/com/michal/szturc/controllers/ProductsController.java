package com.michal.szturc.controllers;

import com.michal.szturc.model.Product;
import com.michal.szturc.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author Michał Szturc
 */
@RequestMapping("/products")
@RestController
public class ProductsController {

    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    Collection<Product> getProducts() {
        return productsService.getAllProducts();
    }

    @GetMapping("/{id}")
    Product getProduct(@PathVariable(name = "id") long id) {
        return productsService.getProductById(id);
    }
}
