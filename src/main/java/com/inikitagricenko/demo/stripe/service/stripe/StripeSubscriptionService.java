package com.inikitagricenko.demo.stripe.service.stripe;

import com.inikitagricenko.demo.stripe.model.Product;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.Subscription;
import com.stripe.model.SubscriptionCollection;
import com.stripe.model.SubscriptionItem;
import com.stripe.param.SubscriptionCreateParams;
import com.stripe.param.SubscriptionItemCreateParams;
import com.stripe.param.SubscriptionListParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StripeSubscriptionService {

	public Subscription subscribeCustomer(String customerId, List<Product> productList) {
		try {
			List<SubscriptionCreateParams.Item> items = productList.stream().map(this::convertProduct).toList();

			SubscriptionCreateParams subscriptionCreateParams = SubscriptionCreateParams.builder()
					.setCustomer(customerId)
					.addAllItem(items)
					.build();

			return Subscription.create(subscriptionCreateParams);
		} catch (InvalidRequestException exception) {
			throw new RuntimeException("Exception occurs", exception); // TODO throw backend exception here
		} catch (StripeException e) {
			throw new RuntimeException(e);
		}
	}

	public void cancel(String subscriptionId) {
		try {
			Subscription subscription = retrieve(subscriptionId);

			subscription.cancel();
		} catch (StripeException e) {
			throw new RuntimeException(e);
		}
	}

	public String resume(String subscriptionId) {
		try {
			Subscription subscription = retrieve(subscriptionId);

			return subscription.resume().getId();
		} catch (StripeException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Subscription> retrieveAll() {
		try {
			SubscriptionListParams subscriptionListParams = SubscriptionListParams.builder()
					.setLimit(5L)
					.build();

			SubscriptionCollection subscriptions = Subscription.list(subscriptionListParams);
			return subscriptions.getData();
		} catch (StripeException e) {
			throw new RuntimeException(e);
		}
	}

	public Subscription retrieve(String subscriptionId) {
		try {
			return Subscription.retrieve(subscriptionId);
		} catch (StripeException e) {
			throw new RuntimeException(e);
		}
	}

	public void addItem(String subscriptionId, String productId, long quantity) {
		try {
			SubscriptionItemCreateParams subscriptionItemCreateParams = SubscriptionItemCreateParams.builder()
					.setSubscription(subscriptionId)
					.setPrice(productId)
					.setQuantity(quantity)
					.build();

			SubscriptionItem.create(subscriptionItemCreateParams);
		} catch (StripeException e) {
			throw new RuntimeException(e);
		}
	}

	private SubscriptionCreateParams.Item convertProduct(Product product) {
		SubscriptionCreateParams.Item.PriceData.Recurring recurring = SubscriptionCreateParams.Item.PriceData.Recurring.builder()
				.setInterval(SubscriptionCreateParams.Item.PriceData.Recurring.Interval.MONTH)
				.setIntervalCount(1L)
				.build();

		SubscriptionCreateParams.Item.PriceData priceData = SubscriptionCreateParams.Item.PriceData.builder()
				.setCurrency(product.getCurrency().getAbbreviation())
				.setProduct(product.getStripeReference())
				.setUnitAmount(product.getUnitAmount())
				.setRecurring(recurring)
				.build();

		return SubscriptionCreateParams.Item.builder()
				.setPriceData(priceData)
				.setQuantity(product.getQuantity())
				.build();
	}
}
