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
@ConfigurationProperties("mystore")
public class MystoreProperties {
	
	/**
	 * Version number for the release
	 */
	private String release = "";
	
	public String getRelease() {
		return release;
	}
	
	public void setRelease(String release) {
		this.release = release;
	}

}
