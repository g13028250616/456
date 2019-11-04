package com.changgou.order.consumer;

import com.alibaba.fastjson.JSON;
import com.changgou.order.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = {"${mq.pay.queue.order}"})
public class OrderPayMessageListener {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderService orderService;


    @RabbitHandler
    public void consumeMessage(String msg){
        //将数据转成map
        Map<String,String> result = JSON.parseObject(msg, Map.class);

        //return_code=SUCCESS
        String return_code = result.get("return_code");
        //业务结构
        String result_code = result.get("result_code");

        //业务结果 result_code=SUCCESS/FAIL，修改订单状态
        if (return_code.equalsIgnoreCase("success")) {
            //获取订单号
            String outTradeNo = result.get("out_trade_no");
            //业务结构
            if (result_code.equalsIgnoreCase("success")) {
                if (outTradeNo!=null) {
                    //修改订单状态
                    orderService.updateStatus(outTradeNo,result.get("transaction_id"));
                }else {
                    //订单删除
                    orderService.delete(outTradeNo);
                }
            }
        }

    }
}
