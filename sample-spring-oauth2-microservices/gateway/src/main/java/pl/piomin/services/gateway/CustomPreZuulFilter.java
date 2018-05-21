package pl.piomin.services.gateway;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

//1. create filter with type pre
//2. Set the order of filter to greater than 5 because we need to run our filter after preDecoration filter of zuul.
//@Component
public class CustomPreZuulFilter /*extends ZuulFilter*/ {

private final Logger logger = LoggerFactory.getLogger(this.getClass());

//@Override
public Object run() {
  final RequestContext requestContext = RequestContext.getCurrentContext();
  logger.info("in zuul filter " + requestContext.getRequest().getRequestURI());
  System.out.println("@@@@@@@@@@@@@@@in zuul filter " + requestContext.getRequest().getRequestURI());
  byte[] encoded;
  try {
      encoded = Base64.encode("customer-service:secret".getBytes("UTF-8"));
      requestContext.addZuulRequestHeader("Authorization", "Basic " + new String(encoded));

      final HttpServletRequest req = requestContext.getRequest();
      if (requestContext.getRequest().getHeader("Authorization") == null
              && !req.getContextPath().contains("login")) {
          requestContext.unset();
          requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());

      } else {
            //next logic
          }
      }

  catch (final UnsupportedEncodingException e) {
      logger.error("Error occured in pre filter", e);
  }

  return null;
}



//@Override
public boolean shouldFilter() {
  return true;
}

//@Override
public int filterOrder() {
  return 6;
}

//@Override
public String filterType() {
  return "pre";
}

}