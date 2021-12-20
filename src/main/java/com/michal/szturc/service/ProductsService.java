package com.michal.szturc.service;

import com.michal.szturc.model.Product;
import com.michal.szturc.repository.ProductsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

/**
 * @author Michał Szturc
 */
@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public Collection<Product> getAllProducts() {
        return productsRepository.findAll();
    }

    public Product getProductById(long productId) {
        return productsRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not Found"));
    }
}
