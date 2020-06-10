package com.ezfun.guess.multithread.case004.other;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SoySauce
 * @date 2019/10/16
 */
public class SSRUtil {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    private static boolean flag = true;

    public static Map<String, Object> ssrDecoder(String text) throws UnsupportedEncodingException {
        text = text.split("//")[1];
        text = text.replace("-", "+").replace("_", "/");
        org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
//        byte[] decode4 = Base64.getMimeDecoder().decode(text);
//        byte[] bytes = Base64Utils.decodeFromString(text);
        byte[] decode = base64.decode(text);
        String data = new String(decode);
        String[] arr = data.split(":");
        String arr2 = arr[5].split("/\\?")[1];
        byte[] decode1 = Base64.getDecoder().decode(arr[5].split("/\\?")[0]);
        String password = new String(decode1);
        String ip = arr[0];
        String port = arr[1];
        String protocol = arr[2];
        String method = arr[3];
        String obfs = arr[4];
        String[] split = arr2.split("&");
        String obfsparam = "";
        String protoparam = "";
        String remarks = "";
        String remarksBase64 = "";
        String group = "";
        for (String s : split) {

            String[] split1 = s.split("=");
            switch (split1[0]) {
                case "obfsparam":
                    obfsparam = new String(base64.decode(split1[1]));
                    break;
                case "protoparam":
                    protoparam = new String(base64.decode(split1[1]));
                    break;
                case "remarks":
                    remarksBase64 = split1[1];
                    remarks = new String(base64.decode(remarksBase64));
                    break;
                case "group":
                    group = new String(base64.decode(split1[1]));
                    break;
                default:
                    break;
            }
        }
        HashMap<String, Object> map = Maps.newHashMap();
//        System.out.println("========================SSR解析结果============================");
        map.put("server", ip);
//        System.out.println("ip:" + ip);
        map.put("server_port", Integer.parseInt(port));
//        System.out.println("port:" + port);
        map.put("password", password);
//        System.out.println("password:" + password);
        map.put("protocol", protocol);
//        System.out.println("protocol:" + protocol);
        map.put("method", method);
//        System.out.println("method:" + method);
        map.put("obfs", obfs);
//        System.out.println("obfs:" + obfs);
        map.put("obfsparam", obfsparam);
//        System.out.println("obfsparam:" + obfsparam);
        map.put("protocolparam", protoparam);
//        System.out.println("protoparam:" + protoparam);
        map.put("remarks", remarks);
        map.put("remarks_base64", remarksBase64);
//        System.out.println("remarks:" + remarks);
        map.put("group", "spider");
//        System.out.println("group:" + group);
        map.put("enable", true);
//        System.out.println("================================================================");
        return map;
    }

    static String url = "https://onessr.ml/ssr/getRandomLink";
//    static SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

    static RestTemplate restTemplate;
    static HttpHeaders headers = new HttpHeaders();
    //        headers.add("cookie", "__cfduid=d6c0a7bee426226cd144ce80bdc9f15e81571106885; UM_distinctid=16dcd44e103338-01f3a4ce0530bd-b78173e-1fa400-16dcd44e10416b; CNZZDATA1278050332=505400657-1571104567-%7C1571184752");
//        String cookie = "__cfduid=d6c0a7bee426226cd144ce80bdc9f15e81571106885; UM_distinctid=16dcd44e103338-01f3a4ce0530bd-b78173e-1fa400-16dcd44e10416b; CNZZDATA1278050332=505400657-1571104567-%7C1571184752";
    static List<String> list = Lists.newArrayList();

    public static HttpClientConnectionManager poolingConnectionManager() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        poolingConnectionManager.setMaxTotal(1000); // 连接池最大连接数
        poolingConnectionManager.setDefaultMaxPerRoute(100); // 每个主机的并发
        return poolingConnectionManager;
    }

    public static HttpClientBuilder httpClientBuilder() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //设置HTTP连接管理器
        httpClientBuilder.setConnectionManager(poolingConnectionManager());
        return httpClientBuilder;
    }

    public static ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClientBuilder().build());
        clientHttpRequestFactory.setConnectTimeout(2000); // 连接超时，毫秒
        clientHttpRequestFactory.setReadTimeout(2000); // 读写超时，毫秒
        return clientHttpRequestFactory;
    }


    static {
//        requestFactory.setConnectTimeout(2000);// 设置超时
//        requestFactory.setReadTimeout(2000);
        restTemplate = new RestTemplate(clientHttpRequestFactory());
        list.add("__cfduid=d6c0a7bee426226cd144ce80bdc9f15e81571106885");
        list.add("UM_distinctid=16dcd44e103338-01f3a4ce0530bd-b78173e-1fa400-16dcd44e10416b");
        list.add("CNZZDATA1278050332=505400657-1571104567-%7C1571271444");
//        list.add(cookie);
        headers.put(HttpHeaders.COOKIE, list);
        headers.add("origin", "https://onessr.ml");
        headers.add("referer", "https://onessr.ml/");
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36");
    }

    static HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);

    public static void start() {
        try {
            JSONObject res = restTemplate.postForObject(url, httpEntity, JSONObject.class);
            System.out.println(res);
            JSONObject data = res.getJSONObject("data");
            if (data != null && data.getInteger("ping") == 1) {
                Map<String, Object> result = ssrDecoder(data.getString("ssrLink"));
                //            System.out.println(JSON.toJSONString(result));
//                FileOutputStream outputStream = new FileOutputStream("ssr.txt");
                String filePath = "D:\\mysoft\\vpn\\Win - ShadowsocksR\\gui-config.json";
                FileInputStream inputStream = new FileInputStream(filePath);
                JSONObject obj = JSONObject.parseObject(new String(FileCopyUtils.copyToByteArray(inputStream), StandardCharsets.UTF_8),
                        JSONObject.class);
                //            FileCopyUtils.copy(new FileInputStream(filePath), outputStream);
//                JSONObject obj = JSON.parseObject(inputStream, JSONObject.class);
                //            InputStream inputStream = new FileInputStream("ssr.txt");
                //            JSONObject obj = JSON.parseObject(inputStream, JSONObject.class);
                JSONArray jsonArray = obj.getJSONArray("configs");
//                jsonArray.add(result);
                List<Map> configs = jsonArray.toJavaList(Map.class);
//                List<Map<String, Object>> lists = new ArrayList<>(16) ;
//
////                ArrayList<Map<String, Object>> datas = lists.stream().collect(
////                        Collectors.collectingAndThen(Collectors.toCollection(
////                                () -> new TreeSet<>(Comparator.comparing(m -> m.get("key1")))), ArrayList::new));
//                if (flag) {
//                    configs = configs.stream().collect(
//                            Collectors.collectingAndThen(
//                                    Collectors.toCollection(() -> new TreeSet<>(
//                                            Comparator.comparing(m -> (String) m.get("server")
//                                            ))), ArrayList::new));
//                    flag = false;
//                }
                boolean hasExit = configs.stream().noneMatch(map -> map.get("server").equals(result.get("server")));
                System.out.println("hasExit:"+hasExit);
                if (hasExit) {
                    configs.add(result);
                } else {
                    return;
                }
                obj.put("configs", configs);
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                ObjectOutputStream oos = new ObjectOutputStream(bos);
//                oos.writeObject(obj);
//                oos.flush();
//                oos.close();
//                InputStream is = new ByteArrayInputStream(bos.toByteArray());
//                FileOutputStream out = new FileOutputStream(filePath);
                FileCopyUtils.copy(obj.toJSONString(), new FileWriter(filePath));
                System.out.println("success:" + atomicInteger.incrementAndGet());
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
//        String filePath = "D:\\mysoft\\vpn\\Win - ShadowsocksR\\gui-config.json";
//        FileInputStream inputStream = new FileInputStream(filePath);
//        JSONObject obj = JSONObject.parseObject(new String(FileCopyUtils.copyToByteArray(inputStream), StandardCharsets.UTF_8),
//                JSONObject.class);
//        System.out.println(obj);
//        ssrDecoder("ssr://Mi41Ni4yNDIuMTAwOjU4MDphdXRoX2FlczEyOF9zaGExOmFlcy0yNTYtY2ZiOmh0dHBfc2ltcGxlOk5qbFRhMk5ELz9vYmZzcGFyYW09WW1Oak5qZ3hNakkzTkM1dGFXTnliM052Wm5RdVkyOXQmcHJvdG9wYXJhbT1NVEl5TnpRNlVXWjVXWHA2JnJlbWFya3M9YjI1bGMzTnlJQzBnNUwtRTU3Mlg1cGF2Jmdyb3VwPWIyNWxjM055Jg==");
//        String filePath = "D:\\mysoft\\vpn\\Win - ShadowsocksR\\gui-config.json";
//        FileInputStream inputStream = new FileInputStream(filePath);
//        JSONObject obj=JSONObject.parseObject(new String(FileCopyUtils.copyToByteArray(inputStream),StandardCharsets.UTF_8
//                ),
//                JSONObject.class);
//        FileCopyUtils.copy(obj.toJSONString(), new FileWriter(filePath));
//        System.out.println("su");

//        FileInputStream inputStream = new FileInputStream(filePath);
        //            FileCopyUtils.copy(new FileInputStream(filePath), outputStream);
//        JSONObject obj = JSON.parseObject(inputStream, JSONObject.class);

        //            InputStream inputStream = new FileInputStream("ssr.txt");
        //            JSONObject obj = JSON.parseObject(inputStream, JSONObject.class);
//        obj.getJSONArray("configs").add(result);
//        System.out.println(obj.getJSONArray("configs"));
//        ssrDecoder("ssr://NDcuMjQwLjQ5LjE5MDo2NDE5MTpvcmlnaW46cmM0LW1kNTpwbGFpbjpZemsxVURKWi8_cmVtYXJrcz1iMjVsYzNOeUlDMGc2YWFaNXJpdiZncm91cD1iMjVsYzNOeSY=");
//        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
//        ScheduledExecutorService pool = Executors.newSingleThreadScheduledExecutor();
//        new ScheduledThreadPoolExecutor(corePoolSize);
        ExecutorService pool = Executors.newSingleThreadScheduledExecutor();
        ((ScheduledExecutorService) pool).scheduleWithFixedDelay(() -> {
            start();
        }, 100, 3000, TimeUnit.MILLISECONDS); //间隔耗时 3000 以上一个任务结束时间计算
    }
}
