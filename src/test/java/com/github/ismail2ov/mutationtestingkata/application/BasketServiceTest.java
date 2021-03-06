package com.github.ismail2ov.mutationtestingkata.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.github.ismail2ov.mutationtestingkata.domain.Basket;
import com.github.ismail2ov.mutationtestingkata.domain.BasketNotFoundException;
import com.github.ismail2ov.mutationtestingkata.domain.BasketRepository;
import com.github.ismail2ov.mutationtestingkata.domain.Items;
import com.github.ismail2ov.mutationtestingkata.domain.Product;

@ExtendWith(SpringExtension.class)
class BasketServiceTest {

    public static final long USER_ID = 101;
    public static final Product PRODUCT = new Product(3L, "Logitech Wireless Mouse M185", "10,78 €");
    public static final Basket EXPECTED_BASKET = new Basket(1L, USER_ID, new Items(List.of(PRODUCT)));

    @Mock
    BasketRepository basketRepository;

    @InjectMocks
    BasketService basketService;

    @Test
    void return_basket_by_id() {
        when(basketRepository.getByUserId(USER_ID)).thenReturn(Optional.of(EXPECTED_BASKET));

        basketService.getBy(USER_ID);

        verify(basketRepository).getByUserId(any());
    }

    @Test
    void throw_exception_when_basket_does_not_exists() {
        when(basketRepository.getByUserId(USER_ID)).thenThrow(BasketNotFoundException.class);

        Throwable thrown = catchThrowable(() -> basketService.getBy(USER_ID));

        AssertionsForClassTypes.assertThat(thrown).isInstanceOf(BasketNotFoundException.class);
    }

    @Test
    void return_basket_if_exists() {
        when(basketRepository.getByUserId(USER_ID)).thenReturn(Optional.of(EXPECTED_BASKET));
        when(basketRepository.save(EXPECTED_BASKET)).thenReturn(EXPECTED_BASKET);

        basketService.addProductToBasket(USER_ID, PRODUCT);
    }

    @Test
    void create_basket_if_not_exists() {
        Basket expectedBasket = new Basket(null, USER_ID, new Items(List.of(PRODUCT)));
        when(basketRepository.getByUserId(USER_ID)).thenReturn(Optional.empty());
        when(basketRepository.save(expectedBasket)).thenReturn(expectedBasket);

        basketService.addProductToBasket(USER_ID, PRODUCT);
    }
}
