package com.inikitagricenko.demo.stripe.persistence;

import com.inikitagricenko.demo.stripe.entity.SubscriptionEntity;
import com.inikitagricenko.demo.stripe.mapper.SubscriptionMapper;
import com.inikitagricenko.demo.stripe.model.Subscription;
import com.inikitagricenko.demo.stripe.repository.SubscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionPersistence {

	private final SubscriptionRepository subscriptionRepository;
	private final SubscriptionMapper subscriptionMapper;

	public long save(Subscription subscription) {
		SubscriptionEntity entity = subscriptionMapper.toEntity(subscription);
		return subscriptionRepository.save(entity).getId();
	}

	public Subscription findById(long id) {
		return subscriptionRepository.findById(id)
				.map(subscriptionMapper::toSubscription)
				.orElseThrow(EntityNotFoundException::new);
	}

	public List<Subscription> findAll() {
		return subscriptionMapper.toSubscriptions(subscriptionRepository.findAll());
	}

	public void delete(long id) {
		subscriptionRepository.deleteById(id);
	}
}
