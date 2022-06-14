package com.github.ismail2ov.mutationtestingkata.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.github.ismail2ov.mutationtestingkata.application.BasketService;
import com.github.ismail2ov.mutationtestingkata.domain.Basket;
import com.github.ismail2ov.mutationtestingkata.domain.Product;
import com.github.ismail2ov.mutationtestingkata.infrastructure.controller.api.UsersApi;
import com.github.ismail2ov.mutationtestingkata.infrastructure.controller.mapper.BasketMapper;
import com.github.ismail2ov.mutationtestingkata.infrastructure.controller.mapper.ProductMapper;
import com.github.ismail2ov.mutationtestingkata.infrastructure.controller.model.BasketRDTO;
import com.github.ismail2ov.mutationtestingkata.infrastructure.controller.model.ProductRDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BasketController implements UsersApi {

    private final BasketService basketService;

    private final ProductMapper productMapper;

    private final BasketMapper basketMapper;

    @Override
    public ResponseEntity<BasketRDTO> addProductToBasket(Long userId, ProductRDTO productRDTO) {
        Product product = productMapper.productFrom(productRDTO);
        Basket basket = basketService.addProductToBasket(userId, product);

        return ResponseEntity.ok(basketMapper.basketRDTOFrom(basket));
    }

    @Override
    public ResponseEntity<BasketRDTO> getByUserId(Long userId) {
        Basket basket = basketService.getBy(userId);

        return ResponseEntity.ok(basketMapper.basketRDTOFrom(basket));
    }
}
