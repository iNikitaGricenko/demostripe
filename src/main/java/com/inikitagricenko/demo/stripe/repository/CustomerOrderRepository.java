package com.inikitagricenko.demo.stripe.repository;

import com.inikitagricenko.demo.stripe.entity.CustomerOrderEntity;
import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrderEntity, Long> {

	List<CustomerOrderEntity> findAllByStatusAndCompletedBetween(OrderStatus status, LocalDateTime from, LocalDateTime to);

	Stream<CustomerOrderEntity> streamAllByStatusIsNullAndCreated(LocalDateTime created);
}
