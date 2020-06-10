package com.lol.elasticsearch.demo;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liming on 2018/12/29.
 */
@Component
@Slf4j
public class EsIndexDemo {

    @Autowired
    private Client client;

    private String index="a";

    private static final String type = "c32211";

    public void index() throws Exception{
        XContentBuilder mapping=XContentFactory.jsonBuilder()
                .startObject()


                .startObject("properties") //下面是设置文档列属性
                .startObject("type").field("type","string").field("store","yes")
                .endObject()
                .startObject("eventCount").field("type","long").field("store","yes")
                .endObject()
                .startObject("eventDate").field("type","date").field("format","dateOptionalTime").field("store","yes")
                .endObject()
                .startObject("message").field("type","string").field("index","not_analyzed").field("store","yes")
                .endObject()
                .endObject()
                .endObject();
        //1.以XcontentBuilder形式
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                .field("name", "易大师")
                .field("age", 9999)
                .field("sex", "男")
                .endObject();
        PutMappingRequestBuilder pmrb = client.admin().indices()
                .preparePutMapping("blog8").setType("a");

        PutMappingResponse putMappingResponse = pmrb.setSource(mapping).get();
        System.out.println(putMappingResponse.isAcknowledged());

//        client.admin().indices().preparePutMapping(mapping);
        IndexResponse response2 = client.prepareIndex(index, "c", "1")
                .setSource(builder).get();
//        IndexResponse response3 = client.prepareIndex("z", "c", "1")
//                .addMap(mapping).get();
        //2.Map形式
        Map map = new HashMap<String,Object>();
        map.put("name","剑圣");map.put("version",224L);map.put("title","打野");
        //3.Json形式 setSource需要参数XContentType.JSON
        // 4.javabean 形式 -->转换成json
        IndexResponse response = client.prepareIndex(index, "c1", "1")
                .setSource(map).get();

        //测试是否成功
        log.info("res1 id:{} isCreated:{} res2 isCreated:{}",response.getId(),response.isCreated(),response2.isCreated());
    }

    public void getIndex(){
        GetResponse response = client.prepareGet(index, type, "1").get();
        System.out.println(response.getSourceAsString());
    }
    public void mGetIndex(){
        MultiGetResponse responses = client.prepareMultiGet().add(index, type, "1", "2", "3").get();
        for (MultiGetItemResponse respons : responses) {
            GetResponse response = respons.getResponse();
            System.out.println(respons);
        }
    }

    public void deleteId(){
        DeleteResponse response = client.prepareDelete(index, type, "1").get();
        System.out.println(response.getId());
    }

    public void deleteByQuery(){
        //termQuery 不会分词           matchQuery会分词
//        BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
//                .source(index).filter(QueryBuilders.matchQuery("sex", "女人"))
//                .get();
//        System.out.println(response.getDeleted());

    }


}
