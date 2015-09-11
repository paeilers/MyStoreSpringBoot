/**
 * 
 */
package mystore.salesOrderMgmt.boundaries;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * @author Patty Eilers
 *
 */
@Component
public class CORSResponseFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:8090");
		response.setHeader("Access-Control-Allow-Methods", "OPTIONS, GET, POST, DELETE, PUT");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Credentials", "true"); // Cannot set allow-credentials to "true" AND allow-origin to "*"
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Access-Control-Allow-Methods, "
        		+ "					Access-Control-Allow-Headers, Authorization, x-requested-with, headers" );
        response.setHeader( "X-Served-By", "MyStoreJEE" );

        chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) throws ServletException {}

	public void destroy() {}

}