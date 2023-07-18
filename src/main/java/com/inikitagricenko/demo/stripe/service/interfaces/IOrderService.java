package com.inikitagricenko.demo.stripe.service.interfaces;

import com.inikitagricenko.demo.stripe.adapter.CustomerOrderInputAdapter;
import com.inikitagricenko.demo.stripe.adapter.CustomerOrderOutputAdapter;

public interface IOrderService extends CustomerOrderInputAdapter, CustomerOrderOutputAdapter {
}
