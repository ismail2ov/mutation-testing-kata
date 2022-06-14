package com.github.ismail2ov.mutationtestingkata.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.ismail2ov.mutationtestingkata.domain.Product;
import com.github.ismail2ov.mutationtestingkata.domain.ProductNotFoundException;
import com.github.ismail2ov.mutationtestingkata.domain.ProductPageDTO;
import com.github.ismail2ov.mutationtestingkata.domain.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public ProductPageDTO getProductBy(Long id) {
        Product product = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        List<Product> crossSellProducts = productRepository.getCrossSellProducts(id);

        return new ProductPageDTO(product, crossSellProducts);
    }
}
