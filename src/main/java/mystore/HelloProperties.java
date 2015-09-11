/**
 * 
 */
package mystore;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Patty Eilers
 *
 */
@Component
@ConfigurationProperties("hello")
public class HelloProperties {
	
	/**
	 * Greeting message returned by the Hello Rest service.
	 */
	private String greeting = "Welcome ";
	
	public String getGreeting() {
		return greeting;
	}
	
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

}
