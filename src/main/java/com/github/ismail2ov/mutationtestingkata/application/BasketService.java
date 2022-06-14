package com.github.ismail2ov.mutationtestingkata.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.ismail2ov.mutationtestingkata.domain.Basket;
import com.github.ismail2ov.mutationtestingkata.domain.BasketNotFoundException;
import com.github.ismail2ov.mutationtestingkata.domain.BasketRepository;
import com.github.ismail2ov.mutationtestingkata.domain.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;

    public Basket getBy(Long userId) {
        return basketRepository.getByUserId(userId).orElseThrow(BasketNotFoundException::new);
    }

    public Basket addProductToBasket(Long userId, Product product) {
        Optional<Basket> optBasket = basketRepository.getByUserId(userId);
        Basket basket;
        if (optBasket.isPresent()) {
            basket = optBasket.get();
        } else {
            basket = new Basket();
            basket.setUserId(userId);
        }

        basket.addItem(product);

        return basketRepository.save(basket);
    }
}
