package com.inikitagricenko.demo.stripe.persistence;

import com.inikitagricenko.demo.stripe.entity.ProductEntity;
import com.inikitagricenko.demo.stripe.mapper.ProductMapper;
import com.inikitagricenko.demo.stripe.model.Product;
import com.inikitagricenko.demo.stripe.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductPersistence {

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;

	public long save(Product product) {
		ProductEntity entity = productMapper.toEntity(product);
		return productRepository.save(entity).getId();
	}

	public void delete(long id) {
		productRepository.deleteById(id);
	}

	public Product findById(long id) {
		return productRepository.findById(id)
				.map(productMapper::toProduct)
				.orElseThrow(EntityNotFoundException::new);
	}

	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable).map(productMapper::toProduct);
	}

	public List<Product> findAll(List<Long> ids) {
		return productMapper.toProduct(productRepository.findAllById(ids));
	}

	@Transactional
	public boolean exists(List<Long> ids) {
		return productRepository.existsByIdIn(ids);
	}

	@Transactional
	public void reduceQuantity(long id, long quantity) {
		productRepository.reduceQuantity(id, quantity);
	}

	@Transactional
	public void increaseQuantity(long id, long quantity) {
		productRepository.increaseQuantity(id, quantity);
	}
}
