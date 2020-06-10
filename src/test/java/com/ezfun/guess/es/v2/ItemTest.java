package com.ezfun.guess.es.v2;

import com.google.common.collect.Lists;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SoySauce
 * @date 2020/5/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemTest {
    @Autowired
    private ElasticsearchTemplate esTemplate;
    @Autowired
    private ElasticsearchOperations es;
    @Autowired
    private ItemRepository itemRepository;

    Client client;

    String index = "item2";

    @Before
    public void setUp() throws Exception {
//        Client client = null;
        try {
            Settings settings = Settings.builder().put("cluster.name", "demo")
                    .put("client.node", false)
                    .put("client.transport.sniff", true)
                    .put("client.transport.ping_timeout", "20s").build();
//            client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("47.99.40.191"), 9300));
//            client = TransportClient.builder().settings(settings).build()
//                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("47.99.40.191"), 9300));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void index() throws Exception {
        XContentBuilder mapping = XContentFactory.jsonBuilder()
                .startObject()


                .startObject("properties") //下面是设置文档列属性
                .startObject("type").field("type", "string").field("store", "yes")
                .endObject()
                .startObject("eventCount").field("type", "long").field("store", "yes")
                .endObject()
                .startObject("eventDate").field("type", "date").field("format", "dateOptionalTime").field("store", "yes")
                .endObject()
                .startObject("message").field("type", "string").field("index", "not_analyzed").field("store", "yes")
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
        Map map = new HashMap<String, Object>();
        map.put("name", "剑圣");
        map.put("version", 224L);
        map.put("title", "打野");
        //3.Json形式 setSource需要参数XContentType.JSON
        // 4.javabean 形式 -->转换成json
        IndexResponse response = client.prepareIndex(index, "c1", "1")
                .setSource(map).get();
        System.out.println(response.getResult().getLowercase());

        //测试是否成功
//        log.info("res1 id:{} isCreated:{} res2 isCreated:{}",response.getId(),response.isCreated(),response2.isCreated());
    }

    @After
    public void tearDown() throws Exception {
    }

    static {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    @Test
    public void createIndex() throws Exception {
//        index();
        Client client = es.getClient();
        Map<String, Settings> asGroups = client.settings().getAsGroups();
        // 创建索引，会根据Item类的@Document注解信息来创建
        esTemplate.createIndex(Item.class);
        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        esTemplate.putMapping(Item.class);
    }

    @Test
    public void deleteIndex() {
        esTemplate.deleteIndex(Item.class);
        // 根据索引名字删除
        //esTemplate.deleteIndex("item1");
    }

    @Test
    public void templateIndex() {
        Item item = new Item(1L, "小米手机10", "手机", "小米", 3499.00, "http://image.baidu.com/13123.jpg");
//        itemRepository.save(item);
        IndexQuery query = new IndexQuery();
        query.setObject(item);
        esTemplate.index(query);

        itemRepository.save(item);
    }

    @Test
    public void templatePIndex() {
        Item item;
        List<IndexQuery> list = Lists.newArrayList();
        for (int i = 2; i < 10; i++) {
            item = new Item((long) i, "苹果手机" + i, "手机", "iPhone", 13499.00 + i, "http://image.baidu.com/" + i + ".jpg");
            IndexQuery query = new IndexQuery();
            query.setId(i + 20 + "");
            query.setObject(item);
            list.add(query);


        }
//        for (int i = 2; i < 10; i++) {
//            item=new Item((long) i, "新鲜特大苹果"+i+"斤", "水果","红富士", 19.00+i, "http://image.baidu.com/66"+i+".jpg");
//            IndexQuery query=new IndexQuery();
//            query.setObject(item);
//            list.add(query);
//        }
        esTemplate.bulkIndex(list);
    }

    @Test
    public void query() {
//        Item item = new Item(1L, "小米手机10", "手机","小米", 3499.00, "http://image.baidu.com/13123.jpg");
//        itemRepository.save(item);
//        IndexQuery query=new IndexQuery();
//        query.setObject(item);
        Criteria criteria = new Criteria();
        criteria.contains("苹果红富士");
        CriteriaQuery param = new CriteriaQuery(criteria);
//        Item item1 = esTemplate.queryForObject(param, Item.class);
        List<Item> items = esTemplate.queryForList(param, Item.class);


        Criteria criteria2 = new Criteria();
        criteria2.fuzzy("苹果红富士");
        CriteriaQuery param2 = new CriteriaQuery(criteria2);
//        Item item1 = esTemplate.queryForObject(param, Item.class);
        List<Item> items2 = esTemplate.queryForList(param2, Item.class);


        Criteria criteria3 = new Criteria();
//        criteria3.between()
        criteria3.expression("苹果红富士");
        CriteriaQuery param3 = new CriteriaQuery(criteria3);
//        Item item1 = esTemplate.queryForObject(param, Item.class);
        List<Item> items3 = esTemplate.queryForList(param3, Item.class);

        Criteria criteria4 = new Criteria();
        criteria4.endsWith("苹果红富士");
        CriteriaQuery param4 = new CriteriaQuery(criteria4);
//        Item item1 = esTemplate.queryForObject(param, Item.class);
        List<Item> items4 = esTemplate.queryForList(param4, Item.class);

        System.out.println(items);

        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
//        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("红富士", "title", "brand");
        QueryBuilder matchQueryBuilder = QueryBuilders.termQuery("title", "特大苹果");
        queryBuilder.withQuery(matchQueryBuilder);
//        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "红富士"));
        NativeSearchQuery build = queryBuilder.build();
        // 搜索，获取结果
        ResultsExtractor<?> res = new ResultsExtractor<Object>() {
            @Override
            public Object extract(SearchResponse searchResponse) {
                return null;
            }
        };
        List<Item> items1 = esTemplate.queryForList(build, Item.class);
        System.out.println(items1);
        SearchQuery query = (new NativeSearchQueryBuilder()).build();
        NativeSearchQueryBuilder price = new NativeSearchQueryBuilder().withQuery(QueryBuilders.rangeQuery("price").from(21).to(23));
        AggregatedPage<Item> items5 = esTemplate.queryForPage(price.build(), Item.class);
        System.out.println(items5);
    }
    public static void main(String[] args){
        System.out.println(0.005*1024*6);
    }


}
/*

        Keyword	Sample	Elasticsearch Query String
        And	findByNameAndPrice	{"bool" : {"must" : [ {"field" : {"name" : "?"}}, {"field" : {"price" : "?"}} ]}}
        Or	findByNameOrPrice	{"bool" : {"should" : [ {"field" : {"name" : "?"}}, {"field" : {"price" : "?"}} ]}}
        Is	findByName	{"bool" : {"must" : {"field" : {"name" : "?"}}}}
        Not	findByNameNot	{"bool" : {"must_not" : {"field" : {"name" : "?"}}}}
        Between	findByPriceBetween	{"bool" : {"must" : {"range" : {"price" : {"from" : ?,"to" : ?,"include_lower" : true,"include_upper" : true}}}}}
        LessThanEqual	findByPriceLessThan	{"bool" : {"must" : {"range" : {"price" : {"from" : null,"to" : ?,"include_lower" : true,"include_upper" : true}}}}}
        GreaterThanEqual	findByPriceGreaterThan	{"bool" : {"must" : {"range" : {"price" : {"from" : ?,"to" : null,"include_lower" : true,"include_upper" : true}}}}}
        Before	findByPriceBefore	{"bool" : {"must" : {"range" : {"price" : {"from" : null,"to" : ?,"include_lower" : true,"include_upper" : true}}}}}
        After	findByPriceAfter	{"bool" : {"must" : {"range" : {"price" : {"from" : ?,"to" : null,"include_lower" : true,"include_upper" : true}}}}}
        Like	findByNameLike	{"bool" : {"must" : {"field" : {"name" : {"query" : "?*","analyze_wildcard" : true}}}}}
        StartingWith	findByNameStartingWith	{"bool" : {"must" : {"field" : {"name" : {"query" : "?*","analyze_wildcard" : true}}}}}
        EndingWith	findByNameEndingWith	{"bool" : {"must" : {"field" : {"name" : {"query" : "*?","analyze_wildcard" : true}}}}}
        Contains/Containing	findByNameContaining	{"bool" : {"must" : {"field" : {"name" : {"query" : "**?**","analyze_wildcard" : true}}}}}
        In	findByNameIn(Collection<String>names)	{"bool" : {"must" : {"bool" : {"should" : [ {"field" : {"name" : "?"}}, {"field" : {"name" : "?"}} ]}}}}
        NotIn	findByNameNotIn(Collection<String>names)	{"bool" : {"must_not" : {"bool" : {"should" : {"field" : {"name" : "?"}}}}}}
        Near	findByStoreNear	Not Supported Yet !
        True	findByAvailableTrue	{"bool" : {"must" : {"field" : {"available" : true}}}}
        False	findByAvailableFalse	{"bool" : {"must" : {"field" : {"available" : false}}}}
        OrderBy	findByAvailableTrueOrderByNameDesc	{"sort" : [{ "name" : {"order" : "desc"} }],"bool" : {"must" : {"field" : {"available" : true}}}}

        */
