package com.changgou.pay.feign;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "pay")
@RequestMapping("/weixin/pay")
public interface Weixinfeign {
    @RequestMapping("/orderId")
     Result<Map<String,String>> closePay(@RequestParam("ordwrId") Long orderId);
}
