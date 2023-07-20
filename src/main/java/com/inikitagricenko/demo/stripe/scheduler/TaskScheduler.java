package com.inikitagricenko.demo.stripe.scheduler;

import com.inikitagricenko.demo.stripe.entity.CustomerOrderEntity;
import com.inikitagricenko.demo.stripe.model.enums.OrderStatus;
import com.inikitagricenko.demo.stripe.persistence.CustomerOrderPersistence;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@EnableAsync
@EnableScheduling
public class TaskScheduler {

	private CustomerOrderPersistence customerOrderPersistence;

	@Scheduled
	public void onCaptureFailed() {
		try (Stream<CustomerOrderEntity> entityStream = customerOrderPersistence.findAllUncaptured()) {
			entityStream.filter(entity -> entity.getCreated().isBefore(LocalDateTime.now().minusDays(7)))
					.forEach(entity -> {
						entity.setStatus(OrderStatus.REFUNDED);
						entity.setDeleted(true);
						entity.setDeletedAt(LocalDateTime.now());
					});
		}
	}

}
