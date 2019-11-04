package com.changgou.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.changgou.content.feign.ContentFeign;
import com.changgou.content.pojo.Content;
import com.changgou.item.feign.PageFeign;
import com.xpand.starter.canal.annotation.*;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@CanalEventListener
public class CanalDataEventListener {

    @Autowired
    private ContentFeign contentFeign;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
//    @CanalEventListener
//    public class MyEventListener {
//        //当数据被添加的时候触发
//        // CanalEntry.EventType eventType  监听到的操作的类型  INSERT  UPDATE ,DELETE ,CREATE INDEX ,GRAND
//        // CanalEntry.RowData rowData 被修改的数据()
//        @InsertListenPoint
//        public void onEvent(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
//            //do something...
//            List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
//            for (CanalEntry.Column column : afterColumnsList) {
//                System.out.println(column.getName() + ":" + column.getValue());
//            }
//        }
//        //当数据被修改的时候触发
//        @UpdateListenPoint
//        public void onEvent1(CanalEntry.RowData rowData) {
//            //do something...
//            List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
//            for (CanalEntry.Column column : afterColumnsList) {
//                System.out.println(column.getName() + ":" + column.getValue());
//            }
//        }
//        //当数据被删除的时候触发
//        @DeleteListenPoint
//        public void onEvent3(CanalEntry.EventType eventType,CanalEntry.RowData rowData) {
//            //do something...
//            List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
//            for (CanalEntry.Column column : afterColumnsList) {
//                System.out.println(column.getName() + ":" + column.getValue());
//            }
//        }
//        //自定义数据修改监听
//        @ListenPoint(destination = "example", schema = "canal-test", table = {"t_user", "test_table"}, eventType = CanalEntry.EventType.UPDATE)
//        public void onEvent4(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
//            //do something...
//            List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
//            for (CanalEntry.Column column : afterColumnsList) {
//                System.out.println(column.getName() + ":" + column.getValue());
//            }
//        }
//    }


    //自定义数据库的 操作来监听
    //destination = "example"
    @ListenPoint(destination = "example",
            schema = "changgou_content",
            table = {"tb_content", "tb_content_category"},
            eventType = {
                    CanalEntry.EventType.UPDATE,
                    CanalEntry.EventType.DELETE,
                    CanalEntry.EventType.INSERT})
    public void onEventCustomUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        //1.获取列名 为category_id的值
        String categoryId = getColumnValue(eventType, rowData);
        //2.调用feign 获取该分类下的所有的广告集合
        Result<List<Content>> categoryresut = contentFeign.findByCategory(Long.valueOf(categoryId));
        List<Content> data = categoryresut.getData();
        //3.使用redisTemplate存储到redis中
        stringRedisTemplate.boundValueOps("content_" + categoryId).set(JSON.toJSONString(data));
    }

    private String getColumnValue(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        String categoryId = "";
        //判断 如果是删除  则获取beforlist
        if (eventType == CanalEntry.EventType.DELETE) {
            for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
                if (column.getName().equalsIgnoreCase("category_id")) {
                    categoryId = column.getValue();
                    return categoryId;
                }
            }
        } else {
            //判断 如果是添加 或者是更新 获取afterlist
            for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
                if (column.getName().equalsIgnoreCase("category_id")) {
                    categoryId = column.getValue();
                    return categoryId;
                }
            }
        }
        return categoryId;
    }

    @Autowired
    private PageFeign pageFeign;

    @ListenPoint(destination = "example",
            schema = "changgou_goods",
            table = {"tb_spu"},
            eventType = {CanalEntry.EventType.UPDATE, CanalEntry.EventType.INSERT, CanalEntry.EventType.DELETE})
    public void onEventCustomSpu(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {

        //判断操作类型
        if (eventType == CanalEntry.EventType.DELETE) {
            String spuId = "";
            List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
            for (CanalEntry.Column column : beforeColumnsList) {
                if (column.getName().equals("id")) {
                    spuId = column.getValue();//spuid
                    break;
                }
            }
            //todo 删除静态页

        }else{
            //新增 或者 更新
            List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
            String spuId = "";
            for (CanalEntry.Column column : afterColumnsList) {
                if (column.getName().equals("id")) {
                    spuId = column.getValue();
                    break;
                }
            }
            //更新 生成静态页
            pageFeign.createHtml(Long.valueOf(spuId));
        }
    }
}
