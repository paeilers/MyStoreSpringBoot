package mystore.salesOrderMgmt.controls;

import java.math.BigDecimal;

public interface DiscountCalculator {
	
	public BigDecimal getDiscount(String promoCode);

}
