package com.ezfun.guess.es.v2;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author SoySauce
 * @date 2020/5/22
 */
public interface ItemRepository extends ElasticsearchRepository<Item,Long> {
}
