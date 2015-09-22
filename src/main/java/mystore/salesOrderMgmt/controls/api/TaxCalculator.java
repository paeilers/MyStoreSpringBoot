package mystore.salesOrderMgmt.controls.api;

import java.math.BigDecimal;

public interface TaxCalculator {

	public BigDecimal getTaxRate(Integer zipCode);
	
}
