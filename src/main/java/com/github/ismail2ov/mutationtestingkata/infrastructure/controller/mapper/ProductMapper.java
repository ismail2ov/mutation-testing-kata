package com.github.ismail2ov.mutationtestingkata.infrastructure.controller.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.github.ismail2ov.mutationtestingkata.domain.Product;
import com.github.ismail2ov.mutationtestingkata.domain.ProductPageDTO;
import com.github.ismail2ov.mutationtestingkata.infrastructure.controller.model.ProductPageRDTO;
import com.github.ismail2ov.mutationtestingkata.infrastructure.controller.model.ProductRDTO;

@Mapper
public interface ProductMapper {
    List<ProductRDTO> productRDTOFrom(List<Product> products);

    ProductPageRDTO productPageRDTOFrom(ProductPageDTO productPageDTO);

    Product productFrom(ProductRDTO productRDTO);
}
