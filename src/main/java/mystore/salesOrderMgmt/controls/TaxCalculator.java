package mystore.salesOrderMgmt.controls;

import java.math.BigDecimal;

public interface TaxCalculator {

	public BigDecimal getTaxRate(Integer zipCode);
	
}
