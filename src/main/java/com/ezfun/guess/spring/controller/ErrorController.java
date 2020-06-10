package com.ezfun.guess.spring.controller;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author SoySauce
 * @date 2019/6/13
 */

@RestController
public class ErrorController extends BasicErrorController {
    //ServerProperties 源码:
//   @NestedConfigurationProperty
//    private final ErrorProperties error = new ErrorProperties();

    //    @Autowired
    public ErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
        super(errorAttributes, serverProperties.getError());
        System.out.println(serverProperties);
    }
//    public ErrorController() {
//        super(new DefaultErrorAttributes(), new ErrorProperties());
//    }

    @Override
    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);
//        boolean exception = body.get("message").toString().contains("BusinessExcepiton");
        boolean exception = body.get("message").toString() == null;
//        if(exception||StringUtils.isNotBlank((String)body.get("exception"))&& body.get("exception").equals(BusinessException.class.getName())) {
        if (exception) {
            body.put("status", HttpStatus.FORBIDDEN.value());
            body.put("message", "fuse error");
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(body, status);
    }

}
