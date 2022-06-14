package com.github.ismail2ov.mutationtestingkata.infrastructure.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.github.ismail2ov.mutationtestingkata.application.ProductService;
import com.github.ismail2ov.mutationtestingkata.domain.Product;
import com.github.ismail2ov.mutationtestingkata.domain.ProductNotFoundException;
import com.github.ismail2ov.mutationtestingkata.domain.ProductPageDTO;
import com.github.ismail2ov.mutationtestingkata.infrastructure.controller.api.ProductsApi;
import com.github.ismail2ov.mutationtestingkata.infrastructure.controller.mapper.ProductMapper;
import com.github.ismail2ov.mutationtestingkata.infrastructure.controller.model.ProductPageRDTO;
import com.github.ismail2ov.mutationtestingkata.infrastructure.controller.model.ProductRDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController implements ProductsApi {

    private final ProductService productService;

    private final ProductMapper productMapper;

    @Override
    public ResponseEntity<List<ProductRDTO>> getAll() {
        List<Product> products = productService.getAllProducts();

        return ResponseEntity.ok(productMapper.productRDTOFrom(products));
    }

    @Override
    public ResponseEntity<ProductPageRDTO> getById(Long id) {
        try {
            ProductPageDTO productPageDTO = productService.getProductBy(id);

            return ResponseEntity.ok(productMapper.productPageRDTOFrom(productPageDTO));

        } catch (ProductNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
