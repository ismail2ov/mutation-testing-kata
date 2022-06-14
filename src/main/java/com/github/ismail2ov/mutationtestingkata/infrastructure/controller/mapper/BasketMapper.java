package com.github.ismail2ov.mutationtestingkata.infrastructure.controller.mapper;

import org.mapstruct.Mapper;

import com.github.ismail2ov.mutationtestingkata.domain.Basket;
import com.github.ismail2ov.mutationtestingkata.infrastructure.controller.model.BasketRDTO;

@Mapper
public interface BasketMapper {
    BasketRDTO basketRDTOFrom(Basket basket);
}
