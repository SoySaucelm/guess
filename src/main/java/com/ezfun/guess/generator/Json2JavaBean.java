package com.ezfun.guess.generator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.springframework.util.FileCopyUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

/**
 * @author SoySauce
 * @date 2019/10/10
 */
public class Json2JavaBean {
    public static void main(String[] args) throws IOException {
        String type = getType(new Date());
        System.out.println(type);
        String source = "{\n" +
                "  \"BCode\": 0,\n" +
                "  \"Code\": 0,\n" +
                "  \"Data\": {\n" +
                "    \"AirportInfos\": [\n" +
                "      {\n" +
                "        \"Address\": \"string\",\n" +
                "        \"AirportCode\": \"string\",\n" +
                "        \"AirportDistance\": \"string\",\n" +
                "        \"AirportLatitude\": \"string\",\n" +
                "        \"AirportLongitude\": \"string\",\n" +
                "        \"AirportName\": \"string\",\n" +
                "        \"AirportNamePinYinFirstChar\": \"string\",\n" +
                "        \"AirportNamePinyin\": \"string\",\n" +
                "        \"AirportName_CN\": \"string\",\n" +
                "        \"AirportName_CN_Format\": \"string\",\n" +
                "        \"AirportName_DE\": \"string\",\n" +
                "        \"AirportName_EN\": \"string\",\n" +
                "        \"AirportName_EN_Format\": \"string\",\n" +
                "        \"AirportName_FR\": \"string\",\n" +
                "        \"AirportName_Format\": \"string\",\n" +
                "        \"AirportName_GB\": \"string\",\n" +
                "        \"AirportName_IT\": \"string\",\n" +
                "        \"AirportName_JA\": \"string\",\n" +
                "        \"AirportName_KO\": \"string\",\n" +
                "        \"AirportName_MS\": \"string\",\n" +
                "        \"AirportName_RU\": \"string\",\n" +
                "        \"AirportName_Short\": \"string\",\n" +
                "        \"AirportName_TH\": \"string\",\n" +
                "        \"AmapLatitude\": 0,\n" +
                "        \"AmapLongitude\": 0,\n" +
                "        \"Areas\": \"string\",\n" +
                "        \"BaiduLatitude\": 0,\n" +
                "        \"BaiduLongitude\": 0,\n" +
                "        \"City\": \"string\",\n" +
                "        \"CityCode\": \"string\",\n" +
                "        \"CityID\": \"string\",\n" +
                "        \"CityIsWaypoint\": true,\n" +
                "        \"CityLatitude\": \"string\",\n" +
                "        \"CityLongitude\": \"string\",\n" +
                "        \"CityNamePinYinFirstChar\": \"string\",\n" +
                "        \"CityNamePinyin\": \"string\",\n" +
                "        \"CityName_CN\": \"string\",\n" +
                "        \"CityName_CN_Format\": \"string\",\n" +
                "        \"CityName_DE\": \"string\",\n" +
                "        \"CityName_EN\": \"string\",\n" +
                "        \"CityName_EN_Format\": \"string\",\n" +
                "        \"CityName_FR\": \"string\",\n" +
                "        \"CityName_GB\": \"string\",\n" +
                "        \"CityName_IT\": \"string\",\n" +
                "        \"CityName_JA\": \"string\",\n" +
                "        \"CityName_KO\": \"string\",\n" +
                "        \"CityName_MS\": \"string\",\n" +
                "        \"CityName_RU\": \"string\",\n" +
                "        \"CityName_Short\": \"string\",\n" +
                "        \"CityName_TH\": \"string\",\n" +
                "        \"Country\": \"string\",\n" +
                "        \"CountryCode\": \"string\",\n" +
                "        \"CountryType\": \"string\",\n" +
                "        \"Duration\": \"string\",\n" +
                "        \"GoogleLatitude\": 0,\n" +
                "        \"GoogleLongitude\": 0,\n" +
                "        \"HotAirportSortValue_TicketM\": 0,\n" +
                "        \"HotCitySortValue_AirRailway\": 0,\n" +
                "        \"HotCitySortValue_TicketM\": 0,\n" +
                "        \"ID\": \"string\",\n" +
                "        \"Images\": \"string\",\n" +
                "        \"IsActive\": true,\n" +
                "        \"IsDisplay\": true,\n" +
                "        \"IsForAirRailway\": true,\n" +
                "        \"IsForTicketM\": true,\n" +
                "        \"IsHot_TicketM\": true,\n" +
                "        \"IsWaypoint\": true,\n" +
                "        \"Open\": \"string\",\n" +
                "        \"Phone\": \"string\",\n" +
                "        \"Polyline\": \"string\",\n" +
                "        \"QueryRadius\": 0,\n" +
                "        \"RegionID\": 0,\n" +
                "        \"Remark\": \"string\",\n" +
                "        \"Score\": 0,\n" +
                "        \"SortValue\": 0,\n" +
                "        \"Summary\": \"string\",\n" +
                "        \"Terminal\": \"string\",\n" +
                "        \"Ticket\": \"string\",\n" +
                "        \"TimeZone\": 0,\n" +
                "        \"Traffic\": \"string\",\n" +
                "        \"Website\": \"string\",\n" +
                "        \"active\": true,\n" +
                "        \"display\": true,\n" +
                "        \"waypoint\": true\n" +
                "      }\n" +
                "    ],\n" +
                "    \"ConsumeTime\": 0,\n" +
                "    \"EndTime\": \"string\",\n" +
                "    \"Message\": \"string\",\n" +
                "    \"ResponseIP\": \"string\",\n" +
                "    \"Result\": \"string\",\n" +
                "    \"StartTime\": \"string\"\n" +
                "  },\n" +
                "  \"Msg\": \"string\"\n" +
                "}";

        converter(source, "AirPortInfo");


        if (true) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        String className = "TestGenerator";
//        String resource = FlightStatusServiceImpl.class.getClassLoader().getResource("").getFile();
//        String resource11 = FlightStatusServiceImpl.class.getClassLoader().getResource("").getPath();
//        ClassLoader resource12 = FlightStatusServiceImpl.class.getClassLoader().getParent();
//        String resource2 = FlightStatusServiceImpl.class.getResource("/").getFile();
//        String resource22 = FlightStatusServiceImpl.class.getResource("/").getPath();
//        String resource23 = FlightStatusServiceImpl.class.getResource("").getPath();
//        System.out.println();
        Class<Json2JavaBean> clz = Json2JavaBean.class;
        String packageName = clz.getPackage().getName();
        String file = clz.getResource("").getFile();
        String url = file.replace("target/classes", "src/main/java");
        System.out.println(url);
        sb.append("package  ").append(packageName).append(";\n\n");
        sb.append("import lombok.Data;\n\n");
        sb.append("/**\n*\n* @author SoySauce\n");
        sb.append("* @date ").append(LocalDate.now()).append("\n*/\n");
        sb.append("@Data\n");
        sb.append("public class ").append(className).append("{\n\n}");
        String javaBeanInfo = sb.toString();
        Writer w = new OutputStreamWriter(new FileOutputStream(url + className + ".java"));
        FileCopyUtils.copy(javaBeanInfo, w);
    }

    public static String getType(Object obj) {
        String typeName = obj.getClass().getTypeName();
        return typeName.substring(typeName.lastIndexOf(".") + 1);
    }

    public static void converter(String source, String clzName) throws IOException {
        StringBuilder sb = new StringBuilder();
        Class<Json2JavaBean> clz = Json2JavaBean.class;
        String packageName = clz.getPackage().getName();
        String file = clz.getResource("").getFile();
        String url = file.replace("target/classes", "src/main/java");
        System.out.println(url);
        sb.append("package  ").append(packageName).append(";\n\n");
        sb.append("import lombok.Data;\n\n");
        sb.append("/**\n*\n* @author SoySauce\n");
        sb.append("* @date ").append(LocalDate.now()).append("\n*/\n");
        sb.append("@Data\n");
        sb.append("public class ").append(clzName).append("{\n");
        Map<String, Object> sourceMap = JSON.parseObject(source, new TypeReference<Map<String, Object>>() {
        });
        for (Map.Entry<String, Object> entry : sourceMap.entrySet()) {
            String key = entry.getKey();
            String type = getType(entry.getValue());
            if (JSONObject.class.getTypeName().contains(type)) {
                innerConverter(key, (JSONObject) entry.getValue());
            }
            sb.append("private\t").append(type).append("\t").append(key).append(";\n");
        }
        sb.append("\n}");
        String javaBeanInfo = sb.toString();
        Writer w = new OutputStreamWriter(new FileOutputStream(url + clzName + ".java"));
        FileCopyUtils.copy(javaBeanInfo, w);
    }

    public static void innerConverter(String k, JSONObject v) {
        StringBuilder sb = new StringBuilder();
        sb.append("@Data\n");
        String sub = k.substring(1);
        sb.append("public class ").append(k.substring(0, 1).toUpperCase()).append(sub).append("{\n");
        for (Map.Entry<String, Object> entry : v.entrySet()) {
            String key = entry.getKey();
            String type = getType(entry.getValue());
            if (JSONObject.class.getTypeName().contains(type)) {
                innerConverter(key, (JSONObject) entry.getValue());
            } else if (JSONArray.class.getTypeName().contains(type)) {
                JSONArray value = (JSONArray) entry.getValue();
                for (Object o : value) {
                    if (JSONObject.class.getTypeName().contains(type)){

                    }
                }

                innerConverter(key, (JSONObject) entry.getValue());
            }
        }
    }
}
