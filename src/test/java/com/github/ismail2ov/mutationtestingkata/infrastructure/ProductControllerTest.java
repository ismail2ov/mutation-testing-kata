package com.github.ismail2ov.mutationtestingkata.infrastructure;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.github.ismail2ov.mutationtestingkata.application.ProductService;
import com.github.ismail2ov.mutationtestingkata.domain.Product;
import com.github.ismail2ov.mutationtestingkata.domain.ProductNotFoundException;
import com.github.ismail2ov.mutationtestingkata.domain.ProductPageDTO;
import com.github.ismail2ov.mutationtestingkata.infrastructure.controller.ProductController;
import com.github.ismail2ov.mutationtestingkata.infrastructure.controller.mapper.ProductMapperImpl;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = ProductController.class)
@Import(ProductMapperImpl.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product(1L, "Dell Latitude 3301 Intel Core i7-8565U/8GB/512GB SSD/13.3", "999,00 €");
        product2 = new Product(2L, "Samsonite Airglow Laptop Sleeve 13.3", "41,34 €");
    }

    @Test
    void return_products_list() throws Exception {
        Product product3 = new Product(3L, "Logitech Wireless Mouse M185", "10,78 €");
        Product product4 = new Product(4L, "Fellowes Mouse Pad Black", "1,34 €");

        when(productService.getAllProducts()).thenReturn(List.of(product1, product2, product3, product4));

        this.mockMvc
            .perform(get("/products"))
            .andExpect(status().isOk());
    }

    @Test
    void return_product_by_id_adn_cross_sell_products() throws Exception {
        Product product3 = new Product(3L, "Logitech Wireless Mouse M185", "10,78 €");

        ProductPageDTO productPageDTO = new ProductPageDTO(product1, List.of(product2, product3));
        when(productService.getProductBy(1L)).thenReturn(productPageDTO);

        this.mockMvc
            .perform(get("/products/1"))
            .andExpect(status().isOk());
    }

    @Test
    void return_not_found() throws Exception {
        when(productService.getProductBy(1001L)).thenThrow(ProductNotFoundException.class);

        this.mockMvc
            .perform(get("/products/1001"))
            .andExpect(status().isNotFound());
    }
}
