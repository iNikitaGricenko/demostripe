package com.inikitagricenko.demo.stripe.mapper;

import com.inikitagricenko.demo.stripe.entity.SubscriptionEntity;
import com.inikitagricenko.demo.stripe.model.Subscription;
import com.inikitagricenko.demo.stripe.model.dto.SubscriptionCreateDTO;
import com.inikitagricenko.demo.stripe.model.dto.SubscriptionResponseDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
		componentModel = MappingConstants.ComponentModel.SPRING, uses = {CustomerMapper.class, ProductMapper.class})
public interface SubscriptionMapper {
	Subscription toEntity(SubscriptionCreateDTO subscriptionRequestDTO);

	Subscription toEntity(SubscriptionResponseDTO subscriptionResponseDTO);

	SubscriptionEntity toEntity(Subscription subscription);

	SubscriptionCreateDTO toRequest(Subscription subscription);

	SubscriptionResponseDTO toResponse(Subscription subscription);

	List<SubscriptionResponseDTO> toResponse(List<Subscription> subscriptions);

	Subscription toSubscription(SubscriptionEntity entity);

	List<Subscription> toSubscriptions(List<SubscriptionEntity> entity);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	SubscriptionEntity partialUpdate(Subscription updater, @MappingTarget SubscriptionEntity entity);
}