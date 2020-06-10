package com.lol.elasticsearch.demo;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * Created by liming on 2018/12/5.
 */
@Component
@Slf4j
public class ElasticsearchDemo {

    @Autowired
    private Client client;

    private String indexName="language";

    private static final String type = "java";

    public void createIndex() throws IOException {
        XContentBuilder mapping=XContentFactory.jsonBuilder()
                .startObject()
                    .startObject("settings")
                        .field("number_of_shards",2) //设置分片数量
                        .field("number_of_replicas",1) //设置副本数量
                    .endObject()
                    .startObject("mappings")
                        .startObject(type)//type名称
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
                        .endObject()
                    .endObject()
                .endObject();
        System.out.println(mapping.string());
        CreateIndexRequestBuilder cirb = client.admin().indices()
                .prepareCreate(indexName) //index名称
                .setSource(mapping);
        CreateIndexResponse response = cirb.execute().actionGet();
        System.out.println(response);
        if (response.isAcknowledged()){
            System.out.println("index created.");
        }else{
            System.out.println("index creation failed.");
        }
    }

    public void createMapping() throws IOException {
        // 以XContentBuilder形式
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .startObject("properties")
                .startObject("name")
                .field("type", "text")
                .endObject()
                .startObject("title")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word")
                .endObject()
                .endObject()
                .endObject();

        PutMappingResponse response = client.admin().indices().preparePutMapping()
                .setIndices("dragon").setType("ccc")
                .setSource(builder).get();
        System.out.println(response.isAcknowledged());
    }

    public void createDocument() throws IOException {
        IndexResponse response = client.prepareIndex(indexName, type, "1")
                .setSource( //这里可以直接用json字符串
                          XContentFactory.jsonBuilder()
                                .startObject()
                                    .field("type", "syslog")
                                    .field("eventCount", 1)
                                    .field("eventDate", new Date())
                                    .field("message", "seciLog insert doc test")
                                .endObject()
                        ).get();
        System.out.println("index:"+response.getIndex()+" insert doc id:"+response.getId()+" result:"+response.isCreated());
    }

    private XContentBuilder jsonBuilder(){
        XContentBuilder xContentBuilder = null;
        try {
            xContentBuilder = XContentFactory.jsonBuilder();
        } catch (Exception e) {
            log.error("json builder error:{}",e);
        }
        return xContentBuilder;
    }

    public void updateDocument() throws Exception {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(indexName);
        updateRequest.type(type);
        updateRequest.id("1");
        updateRequest.doc(jsonBuilder().startObject().field("type", "file").endObject());
        UpdateResponse updateResponse = client.update(updateRequest).get();
        System.out.println(updateResponse.isCreated());
    }

    public void saveOrUpdateDocument() throws Exception {
        IndexRequest indexRequest = new IndexRequest(indexName, type, "2")
                .source(jsonBuilder()
                        .startObject()
                        .field("type", "syslog")
                        .field("eventCount", 2)
                        .field("eventDate", new Date())
                        .field("message", "secilog insert doc test")
                        .endObject()
                );
        UpdateRequest updateRequest = new UpdateRequest(indexName, type, "2")
                .doc(jsonBuilder().startObject().field("type", "file").endObject())
                .upsert(indexRequest);
        UpdateResponse updateResponse = client.update(updateRequest).get();
        System.out.println(updateResponse.isCreated());
    }

    public void queryDocument(){
//        SearchResponse doc = client.prepareSearch(indexName).setTypes(type).setQuery("doc").execute().actionGet();
//        System.out.println(doc);
        GetResponse response = client.prepareGet(indexName, type, "2").get();
        String source = response.getSource().toString();
        long version = response.getVersion();
        String index = response.getIndex();
        String type = response.getType();
        String id = response.getId();
        System.out.println("source:"+source+"\n version:"+version+"\n index:"+index+"\n type:"+type+"\n id:"+id);
    }

    public void deleteDocument(){
        DeleteResponse deleteResponse = client.prepareDelete(indexName, type, "2").get();
        System.out.println(deleteResponse.isFound());//文档存在返回true 否则false
    }
    public void deleteIndex(){
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        DeleteIndexResponse deleteIndexResponse = client.admin().indices().delete(request).actionGet();
        System.out.println(deleteIndexResponse.isAcknowledged());
    }


}
