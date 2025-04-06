package com.timposulabs.springboot.pagination.util;

import com.timposulabs.springboot.pagination.dto.ProductDTO;
import com.timposulabs.springboot.pagination.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductDTO productDTO);

    ProductDTO toDTO(Product product);

}
