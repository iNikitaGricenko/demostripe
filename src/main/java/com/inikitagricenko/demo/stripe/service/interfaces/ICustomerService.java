package com.inikitagricenko.demo.stripe.service.interfaces;

import com.inikitagricenko.demo.stripe.adapter.CustomerInputAdapter;
import com.inikitagricenko.demo.stripe.adapter.CustomerOutputAdapter;
import com.inikitagricenko.demo.stripe.config.annotations.PerformanceMonitor;
import com.inikitagricenko.demo.stripe.model.Customer;

public interface ICustomerService extends CustomerInputAdapter, CustomerOutputAdapter {

}
