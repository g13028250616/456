package com.changgou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.search.pojo.SkuInfo;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchResultMapperImpl implements SearchResultMapper {
    @Override
    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
        //1.创建一个当前页的记录集合对象
       List<T> content = new ArrayList<>();
        if (response.getHits()==null || response.getHits().getTotalHits()<=0) {
            return new AggregatedPageImpl<T>(content);
        }
        for (SearchHit searchHit : response.getHits()) {
            String sourceAsString = searchHit.getSourceAsString();//指定域
            SkuInfo skuInfo = JSON.parseObject(sourceAsString, SkuInfo.class);

            //高亮
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
            HighlightField highlightField = highlightFields.get("name");//name:高亮字段

            //有高亮则设置高亮的值
            if (highlightFields!=null) {
                StringBuffer stringBuffer = new StringBuffer();
                for (Text text : highlightField.getFragments()) {
                    stringBuffer.append(text.string());
                }
                skuInfo.setName(stringBuffer.toString());
            }
            content.add((T)skuInfo);
        }
        //2.创建分页的对象 已有

        //3.获取总个记录数
        long totalHits = response.getHits().getTotalHits();

        //4.获取所有聚合函数的结果
        Aggregations aggregations = response.getAggregations();

        //5.深度分页的ID
        String scrollId = response.getScrollId();
        return new AggregatedPageImpl<T>(content, pageable, totalHits, aggregations, scrollId);
    }
}
