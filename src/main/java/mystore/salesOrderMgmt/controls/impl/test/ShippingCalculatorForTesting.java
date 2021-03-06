package mystore.salesOrderMgmt.controls.impl.test;

import java.math.BigDecimal;

import mystore.salesOrderMgmt.controls.api.ShippingCalculator;

import org.springframework.stereotype.Service;

@Service
public class ShippingCalculatorForTesting implements ShippingCalculator {
	
	private static final BigDecimal minimumThreshold = new BigDecimal(25.00);
	
	// Normally the shipping is dependent upon either a) The sub-total of the order, or b) the cumulative weight
	// For prototype purposes, assume shipping cost is based on sub-total price of order
	
	public BigDecimal getShippingCost(Integer zipCode, BigDecimal subTotal) {
		// Ignore zipCode for prototype purposes
		if (subTotal.compareTo(minimumThreshold) <= 0) {
			return new BigDecimal(5.00);
		}
		else {
			return new BigDecimal(10.00);
		}
	}

}
