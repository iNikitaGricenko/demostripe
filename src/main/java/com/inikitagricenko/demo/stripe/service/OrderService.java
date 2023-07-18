package com.inikitagricenko.demo.stripe.service;

import com.inikitagricenko.demo.stripe.adapter.PaymentAdapter;
import com.inikitagricenko.demo.stripe.config.annotations.ProductValidation;
import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.model.OrderItem;
import com.inikitagricenko.demo.stripe.model.dto.AnalyticsResponse;
import com.inikitagricenko.demo.stripe.model.dto.AnalyticsSearch;
import com.inikitagricenko.demo.stripe.model.enums.OrderStatus;
import com.inikitagricenko.demo.stripe.persistence.CustomerOrderPersistence;
import com.inikitagricenko.demo.stripe.service.interfaces.IOrderService;
import com.inikitagricenko.demo.stripe.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

	private final PaymentAdapter paymentAdapter;
	private final IProductService productService;
	private final AnalyticsService analyticsService;
	private final CustomerOrderPersistence customerOrderPersistence;

	@Override
	@ProductValidation
	public long pay(CustomerOrder customerOrder) {
		String paymentId = paymentAdapter.pay(customerOrder);
		customerOrder.setStripeReference(paymentId);
		reduceProductInDatabase(customerOrder.getOrderItems());

		return customerOrderPersistence.save(customerOrder);
	}

	@Async
	@Override
	public void confirmPay(Long orderId) {
		CustomerOrder retrieved = retrieve(orderId);
		paymentAdapter.confirm(retrieved.getStripeReference());
	}

	@Async
	protected void reduceProductInDatabase(Set<OrderItem> orderItems) {
		orderItems.forEach(orderItem -> productService.reduceQuantity(orderItem.getProductId(), orderItem.getQuantity()));
	}

	@Override
	public long updateDeliveryStatus(Long id, OrderStatus status) {
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

	@Override
	public AnalyticsResponse getAnalytics(AnalyticsSearch analyticsSearch) {
		OrderStatus status = analyticsSearch.status();
		LocalDateTime from = analyticsSearch.from().atTime(LocalTime.MIN);
		LocalDateTime to = analyticsSearch.to().atTime(LocalTime.MIN);
		List<OrderItem> orderItems = customerOrderPersistence.getAllByStatusAndCompletedBetween(status, from, to);
		return analyticsService.getAnalytic(orderItems);
	}
}
