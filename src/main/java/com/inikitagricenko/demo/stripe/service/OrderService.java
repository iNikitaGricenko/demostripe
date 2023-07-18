package com.inikitagricenko.demo.stripe.service;

import com.inikitagricenko.demo.stripe.adapter.PaymentAdapter;
import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.model.OrderItem;
import com.inikitagricenko.demo.stripe.model.enums.OrderStatus;
import com.inikitagricenko.demo.stripe.persistence.CustomerOrderPersistence;
import com.inikitagricenko.demo.stripe.service.interfaces.IOrderService;
import com.inikitagricenko.demo.stripe.service.interfaces.IProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

	private final PaymentAdapter paymentAdapter;
	private final IProductService productService;
	private final CustomerOrderPersistence customerOrderPersistence;

	@Override
	public long pay(CustomerOrder customerOrder) {
		validateOrderItems(customerOrder.getOrderItems());
		String paymentId = paymentAdapter.pay(customerOrder);
		customerOrder.setStripeReference(paymentId);
		reduceProductInDatabase(customerOrder.getOrderItems());

		return customerOrderPersistence.save(customerOrder);
	}

	@Async
	protected void validateOrderItems(Set<OrderItem> orderItems) {
		List<Long> productIds = orderItems.stream().map(OrderItem::getProductId).collect(Collectors.toList());
		if (!productService.exists(productIds)) {
			throw new EntityNotFoundException("Product not found");
		}
	}

	@Async
	protected void reduceProductInDatabase(Set<OrderItem> orderItems) {
		orderItems.forEach(orderItem -> {
			productService.reduceQuantity(orderItem.getProductId(), orderItem.getQuantity());
		});
	}

	@Override
	public long updateStatus(Long id, OrderStatus status) {
		CustomerOrder retrieved = retrieve(id);
		retrieved.setStatus(status);

		return customerOrderPersistence.save(retrieved);
	}


	@Override
	public List<CustomerOrder> retrieveAll() {
		return customerOrderPersistence.findAll();
	}

	@Override
	public CustomerOrder retrieve(long id) {
		return customerOrderPersistence.findById(id);
	}
}
