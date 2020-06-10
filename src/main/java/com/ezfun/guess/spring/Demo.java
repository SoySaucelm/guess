package com.ezfun.guess.spring;

import com.ezfun.guess.spring.redis.ReceiveMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import java.io.*;
import java.lang.annotation.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author SoySauce
 * @date 2020/1/14
 */
@Configuration
@RestController
public class Demo {


    class CustomAfterInit implements InitializingBean {
        @Override
        public void afterPropertiesSet() throws Exception {
            System.out.println("InitializingBean>>>>>>afterPropertiesSet");
        }
    }

    class CustomDestroy implements DisposableBean {
        @Override
        public void destroy() throws Exception {
            System.out.println("DisposableBean>>>>>>destroy");
        }
    }

    class CustomBeanPostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            return bean;
        }
    }

    //申明由spring管理配置Bean
//    @Configuration
    //申明这是一个切面类
    @Aspect
    class AopConfig {
        @Around("@within(org.springframework.stereotype.Controller)") //环绕通知 执行前后调用 @within 表示目标类型带有注解
        public Object simpleAop(ProceedingJoinPoint jp) {
            Object[] args = jp.getArgs();
            System.out.println("args:" + args);
            Object obj = null;
            try {
                //调用原有方法
                obj = jp.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return obj;
        }

        @Before("@annotation(transactionSource))")
        public void before(JoinPoint point, TransactionSource transactionSource) {
            System.out.println(transactionSource.value());
        }

        @Pointcut("execution(* com.ezfun.guess.spring.controller.*.*(..))")
        public void point() {
        }

        @Around(value = "point()")
        public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
            Object obj = joinPoint.proceed(joinPoint.getArgs());
            if (obj instanceof String) {
                return "{" + obj + "}";
            }
            return obj;
        }

        @Pointcut("@target(transactionSource)")
        public void point2(TransactionSource transactionSource) {
        }

        @Around(value = "point2(transactionSource)")
        public Object around2(ProceedingJoinPoint joinPoint, TransactionSource transactionSource) throws Throwable {
            System.out.println(transactionSource.value());
            Object obj = joinPoint.proceed(joinPoint.getArgs());
            if (obj instanceof String) {
                return "{" + obj + "}";
            }
            return obj;
        }

    }

    @RestController
    class InitBinderDemo {
        @PostMapping("/demo")
        public Object getCapitalStatistics(@RequestParam(value = "startTime", required = false) Date startTime) {
            System.out.println("☺☺^_^☺☺");
            return startTime;
        }

        /**
         * 只对当前Controller 有效
         *
         * @param binder
         */
        @InitBinder
        protected void initBinder(WebDataBinder binder) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        }
    }

    @RestController
    static class CustomValidator {
        @RequestMapping("validate")
        public String validate(@Validated @RequestBody WorkInfo info, BindingResult result) {
            StringBuilder sb = new StringBuilder();
            try {

                if (result.hasErrors()) {
                    List<ObjectError> allErrors = result.getAllErrors();
                    allErrors.forEach(obj -> {
                        System.out.println(obj.getDefaultMessage());
                        sb.append(obj.getDefaultMessage()).append("\r\n");
                    });
                } else {
                    sb.append(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Data
        static class WorkInfo {
            @WorkOver(max = 20, message = "工作时间不能超过?{max}小时")
            private Integer workTime;
            @NotBlank(message = "工作不能为空")
            private String job;
        }

        @Documented
        @Constraint(validatedBy = {WorkValidator.class})
        @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
        @Retention(RetentionPolicy.RUNTIME)
        public @interface WorkOver {
            String message() default "工作时间不能超过>>>>{max}小时";

            int max() default 50;

            Class<?>[] groups() default {}; //验证规则分组 比如 add update 可以分为两个组

            Class<? extends Payload>[] payload() default {}; //定义了验证的有效负荷。
        }

        static class WorkValidator implements ConstraintValidator<WorkOver, Object> {
            WorkOver workOver;

            @Override
            public void initialize(WorkOver workOver) {
                this.workOver = workOver;
            }

            @Override
            public boolean isValid(Object value, ConstraintValidatorContext context) {
                String messageTemplate = context.getDefaultConstraintMessageTemplate();
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(messageTemplate)
//                        .addNode(verifyField)
                        .addConstraintViolation();
                if (value == null) {
                    return false;
                }
                return 20 < workOver.max();
            }
        }
    }

    @Configuration
    static class CustomMvcConfig implements WebMvcConfigurer {
        @Override
        public void addFormatters(FormatterRegistry registry) {
            //格式化
            registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss")); //DateF ormatter 类实现将字符串转为日期类型java. util.Data 。
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            //拦截器
            registry.addInterceptor(new HandlerInterceptor() {
                @Override
                public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                    if ("test".equals(request.getHeader("token"))) {
                        return true;
                    } else {
                        response.sendRedirect("/login");
                        return false;
                    }
                }

                @Override
                public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                    //在调用Controller 方法结束后、页面渲染之前调用此方法，比如可以在这
                    //里将渲染的视图名称更改为其他视图名称
                }

                @Override
                public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
                    //页面渲染完毕后调用此方法,通常用来清除某些资源
                }
            }).addPathPatterns("/api/**");

        }

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            //跨域访问
            registry.addMapping("/api/**")
                    .allowedOrigins("*")
                    .allowCredentials(true)
                    .allowedMethods("POST", "GET");
        }

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            //URI 到视图的映射
            registry.addViewController("/swagger2/index.html").setViewName("/index.html");
            registry.addRedirectViewController("/**/*.do", "/index.html");
//            对于swagger2/index.html 的请求，设置返回的视图为index.html 。
//            所有以. do 结尾的请求重定向到/index.html 请求。
        }

        @Override
        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
            converters.forEach(obj -> {
                if (obj instanceof MappingJackson2HttpMessageConverter) {
                    MappingJackson2HttpMessageConverter jacksonConverter = (MappingJackson2HttpMessageConverter) obj;
                    ObjectMapper mapper = jacksonConverter.getObjectMapper();
                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    //[备注1.0]
//                    mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
                }
            });
        }

        //.....More config
    }

    @Configuration
    static class JackSonConfig {
        /**
         * 效果同 [备注1.0] (注意:会被1.0 覆盖)
         * 会使Spring Boot 使用自定义的Jackson 来序列化而非默认配置的。
         *
         * @return
         */
        @Bean
        public ObjectMapper objectMapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            return mapper;
        }

    }

    //    在Java中异常可以分为Error和Exception，@ControllerAdvice用来处理系统中的Exception，
// ErrorController处理系统中的Error
//    @Controller
    static class MyErrorController implements ErrorController {
        private static final String ERROR_PATH = "/error";

        @Autowired
        private ErrorAttributes errorAttributes;

        @Override
        public String getErrorPath() {
            return ERROR_PATH;
        }

   /* @Autowired
    private AppErrorController(ErrorAttributes errorAttributes){
        this.errorAttributes = errorAttributes;
    }*/

        /**
         * web页面错误处理
         *
         * @param request
         * @param response
         * @return
         */
        @RequestMapping(value = ERROR_PATH, produces = "text/html")
        public String errorPageHandler(HttpServletRequest request, HttpServletResponse response) {
            int status = response.getStatus();
            switch (status) {
                case 403:
                    return "403.html";
                case 404:
                    return "404.html";
                case 500:
                    return "500.html";
                default:
            }
            return "index.html";
        }

        /**
         * 除web页面外的错误处理，如JSON/XML等
         */
        @RequestMapping(value = ERROR_PATH)
        @ResponseBody
        public Object errorApiHandler(HttpServletRequest request) {
            ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(request);
            ServletWebRequest attributes = new ServletWebRequest(request);
            Map<String, Object> attr = this.errorAttributes.getErrorAttributes(attributes, false);
            int status = getStatus(request);
            Map<String, Object> res = Maps.newHashMap();
            res.put("status", status);
            res.put("error", String.valueOf(attr.getOrDefault("message", "error")));
            return res;
        }

        private int getStatus(HttpServletRequest request) {
            Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");//固定写法
            if (status != null) {
                return status;
            }
            return 500;
        }

    }


    //    @Controller
    static class CustomErrorController extends AbstractErrorController {
        private final ErrorProperties errorProperties;

        @Autowired
        public CustomErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
            super(errorAttributes);
            this.errorProperties = serverProperties.getError();
        }

        @Override
        public String getErrorPath() {
            return errorProperties.getPath();
        }
    }
//    如何抛弃掉或者覆盖掉Spring Boot默认异常处理呢，Spring Boot开发指南上提供了以下四种方法：
//
//            1、自定义一个bean，实现ErrorController接口，那么默认的错误处理机制将不再生效。
//            2、自定义一个bean，继承BasicErrorController类，使用一部分现成的功能，自己也可以添加新的public方法，使用@RequestMapping及其produces属性指定新的地址映射。
//            3、自定义一个ErrorAttribute类型的bean，那么还是默认的两种响应方式，只不过改变了内容项而已。
//            4、继承AbstractErrorController
//    或者通过spring boot的配置直接完成，这样配置的顾名思义，就是在没有handler的时候直接作为异常抛出，这样我们的全局异常捕获器就能够捕获到了，然后根据不同的状态码返回就行，但是这里需要看到的是有个add-mappings:false，就是这种情况下静态资源是要不能访问的，其实是因为静态资源是直接访问，不需要控制器，所以要配置成false；如果你的项目仅仅是作为接口服务的话，那么这种方式来的更加的简单，我本来也是这么做的，但是我的项目里面使用了swagger，这个东西暴露出去的api是动态生成的静态文件，如果这样配置之后，swagger也不能用了，所以我还是使用了第一种方式进行捕获error
// spring:
//    mvc:
//      throw-exception-if-no-handler-found: true
//    resources:
//      add-mappings: false

    @ControllerAdvice
    public static class GlobalExceptionHandler {

        @ExceptionHandler(value = {Exception.class, NullPointerException.class})//这里可以写多个异常
        public Object exceptionHandler(HttpServletRequest request, Exception e, HttpServletResponse response) {
            int status = response.getStatus();
            HashMap<Object, Object> res = Maps.newHashMap();
            res.put("status", status);
            res.put("msg", e.getMessage());
            return res;
        }
    }

    static class RestTemplateConfig implements RestTemplateCustomizer {

        @Override
        public void customize(RestTemplate restTemplate) {
            SimpleClientHttpRequestFactory requestFactory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
            requestFactory.setConnectTimeout(2000);
            requestFactory.setReadTimeout(6000);
        }
    }

    @Configuration
    static class RedisConfig {
        @Bean
        public MessageListenerAdapter messageListenerAdapter(ReceiveMessage receiveMessage) {
            return new MessageListenerAdapter(receiveMessage,"handleMessage");
        }

        @Bean
        public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory factory,
                                                                           ReceiveMessage receiveMessage,MessageListenerAdapter messageListenerAdapter) {
            RedisMessageListenerContainer container = new RedisMessageListenerContainer();
            container.setConnectionFactory(factory);
//            Map<MessageListener, Collection<? extends Topic>> listeners = new HashMap<>();
//            List<Topic> topics = Lists.newArrayList();
            Topic topic = new ChannelTopic("test");
            Topic tName = new PatternTopic("name");
//            topics.add(topic);
//            listeners.put(receiveMessage, topics);
//            container.setMessageListeners(listeners);
//            container.addMessageListener(receiveMessage, topic);
            container.addMessageListener(messageListenerAdapter,tName);

            return container;
        }

    }


    //

    @Autowired
    private Environment env;

    @RequestMapping("test")
    public Object test() {

        HashMap<Object, Object> map = Maps.newHashMap();
        map.put("data", new Date());
        map.put("time", System.currentTimeMillis());
        map.put("param", env.getProperty("param"));
        return map;
    }


    //     ConditionalOnJava.Range.EQUAL_OR_NEWER, JavaVersion.EIGHT, JavaVersion.NINE, true);

    public static String[] getThrowableStrRep(Throwable throwable) {
        if (throwable == null) {
            return new String[0];
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        pw.flush();
        LineNumberReader reader = new LineNumberReader(new StringReader(
                sw.toString()));
        ArrayList lines = new ArrayList();
        try {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (IOException ex) {
            lines.add(ex.toString());
        }
        String[] rep = new String[lines.size()];
        lines.toArray(rep);
        return rep;
    }

    public static String getStackTrace(Throwable throwable) {
        String[] ms = getThrowableStrRep(throwable);
        return String.join(System.getProperty("line.separator"), ms);
    }

    public static void main(String[] args){
        System.out.println(41.94+45+45+45+38.42+37.84+29.77+30.55+34.5+30.55+30.45+15.5+22.05+22.97+30.5+30.55+30.25
                +23.06+23.12+23.06+15.5+23.01+23.06+22.92+23.06+22.19+15.45+26.2+15.45+7.85+7.85+15.5+15.5+15.45+15.5
                +21.94+7.79+7.85+7.79+7.79+7.82+22.17+7.79+7.85+7.82+19.74+19.74+7.82+7.82+7.82+7.79+7.79+17.67+0.04
                +19.74+7.85+17.9+17.67+17.9+7.85+17.9+14.07+16.01+9.04+8.97+11.48+27.93);
    }


}
