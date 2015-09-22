package mystore.salesOrderMgmt.controls.api;

import java.math.BigDecimal;

public interface DiscountCalculator {
	
	public BigDecimal getDiscount(String promoCode);

}
