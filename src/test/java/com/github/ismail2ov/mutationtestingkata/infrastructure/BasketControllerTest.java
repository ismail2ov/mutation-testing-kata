package com.github.ismail2ov.mutationtestingkata.infrastructure;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ismail2ov.mutationtestingkata.application.BasketService;
import com.github.ismail2ov.mutationtestingkata.domain.Basket;
import com.github.ismail2ov.mutationtestingkata.domain.Items;
import com.github.ismail2ov.mutationtestingkata.domain.Product;
import com.github.ismail2ov.mutationtestingkata.infrastructure.controller.BasketController;
import com.github.ismail2ov.mutationtestingkata.infrastructure.controller.mapper.BasketMapperImpl;
import com.github.ismail2ov.mutationtestingkata.infrastructure.controller.mapper.ProductMapperImpl;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = BasketController.class)
@Import({ ProductMapperImpl.class, BasketMapperImpl.class })
class BasketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BasketService basketService;

    @Test
    void return_basket_without_items() throws Exception {

        Basket basket = new Basket(1L, 1L, new Items());

        when(basketService.getBy(1L)).thenReturn(basket);

        this.mockMvc
            .perform(get("/users/1/basket"))
            .andExpect(status().isOk());
    }

    @Test
    void add_product_to_basket() throws Exception {
        Product product = new Product(3L, "Logitech Wireless Mouse M185", "10,78 €");
        Basket basket = new Basket(1L, 1L, new Items(List.of(product)));

        when(basketService.addProductToBasket(1L, product)).thenReturn(basket);

        this.mockMvc
            .perform(post("/users/1/basket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(product)))
            .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
