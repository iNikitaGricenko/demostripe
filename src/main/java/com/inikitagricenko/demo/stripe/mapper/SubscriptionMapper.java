package com.inikitagricenko.demo.stripe.mapper;

import com.inikitagricenko.demo.stripe.model.Subscription;
import com.inikitagricenko.demo.stripe.model.dto.SubscriptionRequestDTO;
import com.inikitagricenko.demo.stripe.model.dto.SubscriptionResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubscriptionMapper {
	Subscription toEntity(SubscriptionRequestDTO subscriptionRequestDTO);

	Subscription toEntity(SubscriptionResponseDTO subscriptionResponseDTO);

	SubscriptionRequestDTO toRequest(Subscription subscription);

	SubscriptionResponseDTO toResponse(Subscription subscription);

	List<SubscriptionResponseDTO> toResponse(List<Subscription> subscriptions);
}