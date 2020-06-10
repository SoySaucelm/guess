package com.lol.elasticsearch.demo;

import com.lol.elasticsearch.ElasticsearchDemoApplicationTests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by liming on 2018/12/5.
 */
public class ElasticsearchDemoTest extends ElasticsearchDemoApplicationTests {

    @Autowired
    private ElasticsearchDemo elasticsearchDemo;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createIndex() throws IOException {
        elasticsearchDemo.createIndex();
    }

    @Test
    public void createDocument() throws IOException {
        elasticsearchDemo.createDocument();
    }

    @Test
    public void updateDocument() throws Exception {
        elasticsearchDemo.updateDocument();
    }

    @Test
    public void saveOrUpdateDocument() throws Exception {
        elasticsearchDemo.saveOrUpdateDocument();
    }

    @Test
    public void queryDocument() {
        elasticsearchDemo.queryDocument();
    }

    public static void main(String[] args){
    }

    @Test
    public void deleteDocument() {
        elasticsearchDemo.deleteDocument();
    }

    @Test
    public void deleteIndex() throws IOException {
        elasticsearchDemo.deleteIndex();
    }

    @Test
    public void createMapping() throws IOException {
        elasticsearchDemo.createMapping();
    }
}