/**
 * Cross-cutting aspect to log all exceptions.
 */
package mystore.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Patty Eilers
 * 
 */
@Aspect
@Component
public class MystoreExceptionHandler {
	
	private final Logger boundaryLogger = LoggerFactory.getLogger("boundary");
	private final Logger serviceLogger = LoggerFactory.getLogger("service");
	private final Logger entityLogger = LoggerFactory.getLogger("entity");

	public MystoreExceptionHandler() {}
	
	@Pointcut("execution(* mystore.salesOrderMgmt.boundaries.*.*(..))")
	public void allRestControllerMethods() {}
	
	@Pointcut("execution(* mystore.salesOrderMgmt.controls.api.*.*(..))")
	public void allServiceMethods() {}
	
	@Pointcut("execution(* mystore.salesOrderMgmt.entities.*.*(..))")
	public void allEntityMethods() {}
	
	@AfterThrowing(pointcut="allRestControllerMethods()", throwing="exc")
	public void logRESTException(ProceedingJoinPoint joinPoint, Throwable exc) throws Throwable {
		boundaryLogger.error("**MystoreExceptionHandler** Boundary exception thrown in: " + joinPoint.getSignature().getName() + " ***/n" +
								 "   with Arguments: " + joinPoint.getArgs());
	}
	
	@AfterThrowing(pointcut="allServiceMethods()", throwing="exc")
	public void logServiceException(JoinPoint joinPoint, Throwable exc) throws Throwable {
		serviceLogger.error("**MystoreExceptionHandler** Service exception thrown in: " + joinPoint.getSignature().getName() + " ***/n" +
							"   with Arguments: " + joinPoint.getArgs());		
	}
	
	@AfterThrowing(pointcut="allEntityMethods()", throwing="exc")
	public void logEntityException(JoinPoint joinPoint, Throwable exc) throws Throwable {
		entityLogger.error("**MystoreExceptionHandler** Entity exception thrown in: " + joinPoint.getSignature().getName() + " ***/n" +
						   "   with Arguments: " + joinPoint.getArgs());	
	}
		
}
