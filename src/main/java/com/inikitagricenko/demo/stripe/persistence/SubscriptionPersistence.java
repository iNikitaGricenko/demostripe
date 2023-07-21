package com.inikitagricenko.demo.stripe.persistence;

import com.inikitagricenko.demo.stripe.entity.SubscriptionEntity;
import com.inikitagricenko.demo.stripe.mapper.CustomerMapper;
import com.inikitagricenko.demo.stripe.mapper.SubscriptionMapper;
import com.inikitagricenko.demo.stripe.model.Subscription;
import com.inikitagricenko.demo.stripe.repository.SubscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionPersistence {

	private final SubscriptionRepository subscriptionRepository;
	private final SubscriptionMapper subscriptionMapper;
	private final CustomerMapper customerMapper;

	public long save(Subscription subscription) {
		SubscriptionEntity entity = subscriptionMapper.toEntity(subscription);
		return subscriptionRepository.save(entity).getId();
	}

	public Subscription findById(long id) {
		return subscriptionRepository.findById(id)
				.map(subscriptionMapper::toSubscription)
				.orElseThrow(EntityNotFoundException::new);
	}

	public Page<Subscription> findAll(Pageable pageable) {
		return subscriptionRepository.findAll(pageable).map(subscriptionMapper::toSubscription);
	}

	public void delete(long id) {
		subscriptionRepository.deleteById(id);
	}

	@Transactional
	public void undelete(Long subscriptionId) {
		subscriptionRepository.undelete(subscriptionId);
	}

	public long update(long id, Subscription subscription) {
		SubscriptionEntity entity = subscriptionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		SubscriptionEntity updated = subscriptionMapper.partialUpdate(subscription, entity);
		updated.setCustomerList(subscription.getCustomerList().stream().map(customerMapper::toEntity).distinct().collect(Collectors.toSet()));
		return subscriptionRepository.save(updated).getId();
	}
}
