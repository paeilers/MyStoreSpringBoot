package mystore.salesOrderMgmt.controls.impl.test;

import java.math.BigDecimal;

import mystore.salesOrderMgmt.controls.api.TaxCalculator;

import org.springframework.stereotype.Service;

@Service
public class TaxCalculatorForTesting implements TaxCalculator {
	// Tax is typically based on the taxing authority associated with the destination
	// For prototype purposes, we will apply a straight 5% tax
		
	public BigDecimal getTaxRate(Integer zipCode) {
		return new BigDecimal(0.05);
	}
}
