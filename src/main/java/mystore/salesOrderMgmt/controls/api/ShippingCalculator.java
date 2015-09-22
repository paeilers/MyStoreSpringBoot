package mystore.salesOrderMgmt.controls.api;

import java.math.BigDecimal;

public interface ShippingCalculator {
	
	public BigDecimal getShippingCost(Integer zipCode, BigDecimal subTotal); 
	
}
