package com.github.ismail2ov.mutationtestingkata.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import org.junit.jupiter.api.Test;

@DataJpaTest(properties = {
        "spring.test.database.replace=NONE",
        "spring.datasource.url=jdbc:tc:postgresql:14:///test"
})
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    @Sql("/scripts/INIT_CROSS_PRODUCTS.sql")
    void return_products_cross_sell() {
        List<Product> actual = productRepository.getCrossSellProducts(1L);

        Product product2 = new Product(2L, "Samsonite Airglow Laptop Sleeve 13.3", "41,34 €");
        Product product3 = new Product(3L, "Logitech Wireless Mouse M185", "10,78 €");

        assertThat(actual).containsAnyElementsOf(List.of(product2, product3));
    }
}
