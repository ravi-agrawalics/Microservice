package pl.piomin.microservices.customer.feignClient;

//import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;




@Component
public class FeignInterceptor implements RequestInterceptor {
    //@Autowired
    //private OAuth2ClientContext context;

/*    @Override
    public void apply(RequestTemplate template) {
        if(context.getAccessToken() != null 
                && context.getAccessToken().getValue() != null
                && OAuth2AccessToken.BEARER_TYPE.equalsIgnoreCase(context.getAccessToken().getTokenType()) ){
            template.header("Authorization", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, context.getAccessToken().getValue()));
        }
        
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        if (request == null) {
            return;
        }
        String token = request.getHeader("Authorization");
        if (token == null) {
            return;
        }
        template.header("Authorization", token);
    }*/
    
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        if (request == null) {
            return;
        }
        String token = request.getHeader("Authorization");
        if (token == null) {
            return;
        }
        requestTemplate.header("Authorization", token);
    }

}