/**
 * 
 */
package mystore.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Patty Eilers
 * Aspect class used to implement logging on all services
 */
@Aspect	
@Component
public class MystoreLogger {

	private final Logger logger = LoggerFactory.getLogger(MystoreLogger.class);
	
	public MystoreLogger() {
	}
	
	@Pointcut("execution(* mystore.salesOrderMgmt.controls.api.*.*(..))")
	public void allServiceMethods() {}
	
	@Before("allServiceMethods()")
	public void logBefore(JoinPoint joinPoint) {
		logger.debug("**MystoreLogger** Entering: " + joinPoint.getSignature().getName() + " ***");
	}
	
	@After("allServiceMethods()")
	public void logAfter(JoinPoint joinPoint) {
		logger.debug("**MystoreLogger** Leaving: " + joinPoint.getSignature().getName() + " ***");
	}
}
