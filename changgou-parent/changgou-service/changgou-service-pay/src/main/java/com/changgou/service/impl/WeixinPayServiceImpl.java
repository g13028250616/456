package com.changgou.service.impl;

import com.changgou.service.WeixinPayService;
import com.github.wxpay.sdk.WXPayUtil;
import entity.HttpClient;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    @Value("${weixin.appid}")
    private String appid;

    @Value("${weixin.partner}")
    private String partner;

    @Value("${weixin.partnerkey}")
    private String partnerkey;


    @Value("${weixin.notifyurl}")
    private String notifyurl;

    /***
     * 创建二维码
     * @param parameter  其他附加信息
     * @return
     */
    @Override
    public Map createNative(Map<String, StatusCode> parameter) {
        //封装参数
        try {
            Map param = new HashMap();
            param.put("appid", appid);                                //应用id
            param.put("mch_id", partner);                           //商户ID号
            param.put("nonce_str", WXPayUtil.generateNonceStr());   //随机数
            param.put("body", "畅购");                                //订单描述
            param.put("out_trade_no",parameter.get("outtradeno"));                 //商户订单号
            param.put("total_fee",parameter.get("money"));                      //交易金额
            param.put("spbill_create_ip", "127.0.0.1");           //终端IP
            param.put("notify_url", notifyurl);                    //回调地址
            param.put("trade_type", "NATIVE");


            //交易类型

            String paramXml = WXPayUtil.generateSignedXml(param, partnerkey);

            //执行请求
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(paramXml);
            httpClient.post();


            //获取参数
            String content = httpClient.getContent();
            Map<String, String> stringMap = WXPayUtil.xmlToMap(content);
            System.out.println("stringMap:" + stringMap);

            //、获取部分页面所需参数
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("code_url", stringMap.get("code_url"));
            System.out.println("code_url:");
            dataMap.put("out_trade_no",parameter.get("outtradeno").toString());
            dataMap.put("total_fee",parameter.get("money").toString());
            return dataMap;

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    /**
     * 查询订单状态
     * @param out_trade_no;客户端自定义订单编号
     * @return
     */
    @Override
    public Map queryPayStatus(String out_trade_no) {

        try {
            //封装参数
            Map param=new HashMap();
            param.put("appid",appid);//应用id
            param.put("mch_id",partner);//商户号
            param.put("out_trade_no",out_trade_no);//商户订单号
            param.put("nonce_str",WXPayUtil.generateNonceStr());//随机字符


            //2、将参数转成xml字符，并携带签名
            String paramXml = WXPayUtil.generateSignedXml(param, partnerkey);

            //3.发送请求
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            httpClient.setHttps(true);
            httpClient.setXmlParam(paramXml);
            httpClient.post();

            //4、获取返回值，并将返回值转成Map
            String content = httpClient.getContent();
            return WXPayUtil.xmlToMap(content);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 关闭微信支付
     * @param orderId
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> closePay(Long orderId) throws Exception {
        //参数设置
        Map<String,String> paramMap=new HashMap<String,String>();
        paramMap.put("appid",appid);//应用id
        paramMap.put("mch_id",partner);//商品id
        paramMap.put("nonce_str",WXPayUtil.generateNonceStr());//随机字符
        paramMap.put("out_trade_no",String.valueOf(orderId));//商家唯一编号



        //将map数据装换成xml数据
        String signedXml = WXPayUtil.generateSignedXml(paramMap, partnerkey);

        //确定url
        String url = "https://api.mch.weixin.qq.com/pay/closeorder";

        //发送请求
        HttpClient httpClient = new HttpClient(url);
        //http
        httpClient.setHttps(true);
        //请求类型
        httpClient.post();
        //获取返回数据
        String content = httpClient.getContent();

        //将返回数据解析成map
        return WXPayUtil.xmlToMap(content);


    }
}
