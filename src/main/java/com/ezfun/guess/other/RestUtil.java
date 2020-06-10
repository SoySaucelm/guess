package com.ezfun.guess.other;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author SoySauce
 * @date 2019/4/9
 */
@Slf4j
public class RestUtil {

    private static final Logger log = LoggerFactory.getLogger(RestUtil.class);

    private static final String URL_REG = "([\\^&=]+)(=?)([\\^&]+)?";

    public static final boolean USE_AUTH = false;


    // 代理隧道验证信息
    final static String proxyUser = "HMQ1F90215N81R9D";

    final static String proxyPass = "61CF4A546477C5AE";
    // 代理服务器
    final static String proxyServer = "http-dyn.xx.com";

    static class MyJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

        public MyJackson2HttpMessageConverter() {
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.TEXT_HTML);
            setSupportedMediaTypes(mediaTypes);
        }
    }

    private static RestTemplate restTemplate;

    static {
        restTemplate = new RestTemplate();

        restTemplate.setRequestFactory(createRequestFactory());

        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

        restTemplate.getMessageConverters().add(new MyJackson2HttpMessageConverter());

        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
    }


    public static List<String> proxyIps = Lists.newArrayList();

    public static String getProxyIp() {
        return proxyIps.get(new Random().nextInt(proxyIps.size()));
    }


    public static RestTemplate createProxyRestTemplate() {
      /*  String proxyIp = getProxyIp();
//        String proxyIp = "47.104.201.136:53281";
        String[] proxyIpArr = proxyIp.split(":", 2);
        log.info("proxyIp:{}", proxyIp);
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(proxyIpArr[0], Integer.valueOf(proxyIpArr[1])),
                new UsernamePasswordCredentials(proxyUser, proxyPass));
        HttpHost myProxy = new HttpHost(proxyIpArr[0], Integer.valueOf(proxyIpArr[1]));
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        if (USE_AUTH) {
            clientBuilder.setProxy(myProxy).setDefaultCredentialsProvider(credsProvider).disableCookieManagement();
        } else {
            clientBuilder.setProxy(myProxy).disableCookieManagement();
        }
        HttpClient httpClient = clientBuilder.build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient);
        factory.setReadTimeout(60000);
        factory.setConnectTimeout(30000);
        return new RestTemplate(factory);*/
        throw new UnsupportedOperationException();
    }


    private static SimpleClientHttpRequestFactory createRequestFactory() {

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        requestFactory.setReadTimeout(6000);

        requestFactory.setConnectTimeout(1000);

        return requestFactory;

    }

    public static String restGet(String url, JSONObject params) {
        String expandURL = expandURL(url, params.keySet());
        log.info("请求地址：[{}]", expandURL);
        String response = restTemplate.getForObject(expandURL, String.class, params);
        return response;
    }

    public static String restGet(String url) {
//        log.info("请求地址：[{}]", url);
        log.debug("请求地址：" + url);
        String response = restTemplate.getForObject(url, String.class);
//        log.debug("restGet >>>>>>>>> result:{}", response);
        log.debug("请求返回值:" + response);
        return response;
    }


    public static <T> T restPost(String url, Map<String, String> headers, Object data, Class<T> clz) {
        T result = restTemplate.postForObject(url, getHttpEntity(headers, data), clz);
        System.out.println(result);
        return result;
    }

    private static HttpEntity<MultiValueMap> getHttpEntity(Map<String, String> headers, Object data) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpHeaders.add(entry.getKey(), entry.getValue());
        }
        return new HttpEntity<>(createMultiValueMap(data), httpHeaders);
    }

    public static <T> ResponseEntity<T> restEntity(String url, Map<String, String> headers, Object data, Class<T> clz) {
        return restTemplate.postForEntity(url, getHttpEntity(headers, data), clz);
    }

    public static String restProxyPost(String url, Map<String, String> headers, Object data) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpHeaders.add(entry.getKey(), entry.getValue());
        }
        HttpEntity<Object> requestEntity = headers.get("Content-Type").equals("application/json;charset=UTF-8")
                ? new HttpEntity<Object>(data, httpHeaders) : new HttpEntity<Object>(null, httpHeaders);
        String expandURL = data == null ? url : expandURI(url, ((JSONObject) data));
        log.info("restProxyPost expandURL:{}", expandURL);
        String result = createProxyRestTemplate().postForObject(URI.create(expandURL), requestEntity, String.class);
        log.debug("restProxyPost >>>>>>>>> result:{}", result);
        return result;
    }

    public static String restPost(String url, JSONObject params, MediaType mediaType) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(mediaType);
        HttpEntity<JSONObject> requestEntity = (mediaType == MediaType.APPLICATION_JSON
                || mediaType == MediaType.APPLICATION_JSON_UTF8) ? new HttpEntity<JSONObject>(params, requestHeaders)
                : new HttpEntity<JSONObject>(null, requestHeaders);
        String result = (mediaType == MediaType.APPLICATION_JSON || mediaType == MediaType.APPLICATION_JSON_UTF8)
                ? restTemplate.postForObject(url, requestEntity, String.class)
                : restTemplate.postForObject(expandURL(url, params.keySet()), requestEntity, String.class, params);
        return result;
    }

    public static <T> T restPost(String url, JSONObject params, MediaType mediaType, Class<T> clz) {
        log.debug("调用POST接口服务, url: " + url + " , params: " + params);
//        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(mediaType);
        HttpEntity<?> requestEntity = (mediaType == MediaType.APPLICATION_JSON || mediaType == MediaType.APPLICATION_JSON_UTF8)
                ? new HttpEntity<JSONObject>(params, requestHeaders) : (mediaType == MediaType.APPLICATION_FORM_URLENCODED
                ? new HttpEntity<MultiValueMap>(createMultiValueMap(params), requestHeaders)
                : new HttpEntity<>(null, requestHeaders));
        T result = (mediaType == MediaType.APPLICATION_JSON || mediaType == MediaType.APPLICATION_JSON_UTF8)
                ? restTemplate.postForObject(url, requestEntity, clz)
                : restTemplate.postForObject(mediaType == MediaType.APPLICATION_FORM_URLENCODED
                ? url
                : expandURL(url, params.keySet()), requestEntity, clz, params);
        log.debug("返回结果" + result);
        return result;
    }

    private static MultiValueMap<String, Object> createMultiValueMap(Object data) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        if (null == data) {
            return map;
        }
        JSONObject params = (JSONObject) (data instanceof JSONObject ? data : JSON.toJSON(data));
        for (String key : params.keySet()) {
            if (params.get(key) instanceof List) {
                for (Iterator<String> it = ((List<String>) params.get(key)).iterator(); it.hasNext(); ) {
                    Object value = it.next();
                    map.add(key, value);
                }
            } else {
                map.add(key, params.getString(key));
            }
        }
        return map;
    }

    private static String expandURL(String url, Set<?> keys) {
        final Pattern reg = Pattern.compile(URL_REG);
        Matcher mc = reg.matcher(url);
        StringBuilder sb = new StringBuilder(url);
        if (mc.find()) {
            sb.append("&");
        } else {
            sb.append("?");
        }
        for (Object key : keys) {
            sb.append(key).append("=").append("{").append(key).append("}").append("&");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    private static String expandURI(String url, JSONObject param) {
        final Pattern reg = Pattern.compile(URL_REG);
        Matcher mc = reg.matcher(url);
        StringBuilder sb = new StringBuilder(url);
        if (mc.find()) {
            sb.append("&");
        } else {
            sb.append("?");
        }
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }


    private static class DefaultResponseErrorHandler implements ResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return response.getStatusCode().value() != HttpServletResponse.SC_OK;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getBody()));
            StringBuilder sb = new StringBuilder();
            String str = null;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            try {
                throw new Exception(sb.toString());
            } catch (Exception e) {
                log.error("handleError:{}", e);
            }
        }
    }


}
