package mystore.salesOrderMgmt.controls;

import java.math.BigDecimal;

public interface ShippingCalculator {
	
	public BigDecimal getShippingCost(Integer zipCode, BigDecimal subTotal); 
	
}
