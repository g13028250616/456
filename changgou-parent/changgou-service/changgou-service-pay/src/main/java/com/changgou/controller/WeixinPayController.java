package com.changgou.controller;

import com.alibaba.fastjson.JSON;
import com.changgou.seckill.service.SeckillOrderService;
import com.changgou.service.WeixinPayService;
import com.github.wxpay.sdk.WXPayUtil;
import com.sun.javafx.collections.MappingChange;
import entity.Result;
import entity.StatusCode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/weixin/pay")
@CrossOrigin
public class WeixinPayController {


    @Value("${mq.pay.exchange.order}")
    private String exchange;

    @Value("${mq.pay.queue.order}")
    private String queue;

    @Value("${mq.pay.routing.key}")
    private String routing;




    @Autowired
    private WeixinPayService weixinPayService;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Autowired
    private SeckillOrderService seckillOrderService;


    /***
     * 生成二维码
     * @param parameter
     * @return
     */
    @RequestMapping("/create/native")
    public Result createNative(@RequestParam Map<String,StatusCode> parameter){
        Map<String,String> resultMap = weixinPayService.createNative(parameter);
        return new Result(true, StatusCode.OK,"创建二维码预付订单成功",resultMap);
    }

    /**
     * 查询状态
     * @param outtradeno
     * @return
     */
    @GetMapping("/status/query")
    public Result queryStatus(@RequestParam("outtradeno") String outtradeno){
        Map<String,String> statusMap = weixinPayService.queryPayStatus(outtradeno);
        return new Result(true,StatusCode.OK,"查询状态成功",statusMap);
    }

    /**
     * 支付回掉
     * @param request
     * @return
     */
    @RequestMapping("/notify/url")
    public String notifyUrl(HttpServletRequest request){
        InputStream inStream;
        try {
            //读取支付回调数据
             inStream = request.getInputStream();//输入流
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len=0;

            while ((len=inStream.read(buffer))!=-1){
                outSteam.write(buffer,0,len);
            }
            inStream.close();
            outSteam.close();
            //把支付回调数据装换成xml字符串
            String result = new String(outSteam.toByteArray(), "utf-8");

            //把xml字符创装换map文件
            Map<String, String> map = WXPayUtil.xmlToMap(result);
            String attach = map.get("attach");
            Map parameters = JSON.parseObject(attach, Map.class);//转成json格式
            System.out.println("====================你好================");
            System.out.println(parameters);

            rabbitTemplate.convertAndSend(parameters.get("exchange").toString(),parameters.get("routingkey").toString(),JSON.toJSONString(map));

            //将消息发送到mq
            //rabbitTemplate.convertAndSend(exchange,routing, JSON.toJSONString(map));

            //响应数据设置
            Map respMap=new HashMap();
            respMap.put("return_code","SUCCESS");
            respMap.put("return_msg","OK");
            return WXPayUtil.mapToXml(respMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /***
     * 关闭订单
     * @param orderId
     * @return
     */
    @RequestMapping("/orderId")
    public  Result<Map<String, String>> closePay(@RequestParam("orderId") Long orderId)throws Exception{
        //返回订单
        Map<String, String> closePay = weixinPayService.closePay(orderId);

        return new Result<Map<String, String>>(true,StatusCode.OK,"关闭订单成功",closePay);
    }
}
