package com.inikitagricenko.demo.stripe.mapper;

import com.inikitagricenko.demo.stripe.entity.ProductEntity;
import com.inikitagricenko.demo.stripe.model.dto.ProductRequestDTO;
import com.inikitagricenko.demo.stripe.model.dto.ProductResponseDTO;
import com.inikitagricenko.demo.stripe.model.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
	Product toProduct(ProductResponseDTO productResponseDTO);

	Product toProduct(ProductRequestDTO productRequestDTO);

	List<ProductResponseDTO> toResponses(List<Product> productEntity);

	ProductResponseDTO toResponse(Product productEntity);

	ProductRequestDTO toRequest(Product productEntity);

	ProductEntity toEntity(Product product);

	Product toProduct(ProductEntity entity);

	List<Product> toProduct(List<ProductEntity> entity);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	ProductEntity partialUpdate(Product updater, @MappingTarget ProductEntity entity);
}