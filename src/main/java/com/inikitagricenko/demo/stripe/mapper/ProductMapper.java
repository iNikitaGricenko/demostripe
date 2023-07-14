package com.inikitagricenko.demo.stripe.mapper;

import com.inikitagricenko.demo.stripe.entity.ProductEntity;
import com.inikitagricenko.demo.stripe.model.Product;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
	ProductEntity toEntity(Product product);

	Product toProduct(ProductEntity entity);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	ProductEntity partialUpdate(Product updater, @MappingTarget ProductEntity entity);
}