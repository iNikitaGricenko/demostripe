package com.inikitagricenko.demo.stripe.service.stripe;

import com.inikitagricenko.demo.stripe.model.OrderItem;
import com.inikitagricenko.demo.stripe.model.Product;
import com.stripe.exception.StripeException;
import com.stripe.model.Subscription;
import com.stripe.param.SubscriptionCreateParams;
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
		} catch (StripeException e) {
			throw new RuntimeException(e);
		}
	}

	private SubscriptionCreateParams.Item convertProduct(Product product) {
		SubscriptionCreateParams.Item.PriceData priceData = SubscriptionCreateParams.Item.PriceData.builder()
				.setCurrency(product.getCurrency().getAbbreviation())
				.setProduct(product.getStripeReference())
				.setUnitAmount(product.getUnityAmount())
				.build();

		return SubscriptionCreateParams.Item.builder()
				.setPriceData(priceData)
				.setQuantity(product.getQuantity())
				.build();
	}

	public void cancel(String subscriptionId) {

	}

	public List<Subscription> retrieveAll() {
		return null;
	}

	public Subscription retrieve(String subscriptionId) {
		return null;
	}

}
