package com.changgou.search.controller;

import com.changgou.search.feign.SkuFeign;
import com.changgou.search.pojo.SkuInfo;
import entity.Page;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/search")
public class SkuController {

    @Autowired(required = false)
    private SkuFeign skuFeign;

    @GetMapping("/list")
    public String search(@RequestParam(required = false) Map<String,String> searchMap , Model model){
        //调用changgou-service-search微服务
        Map resultMap = skuFeign.search(searchMap);
        //搜索结果
        model.addAttribute("result",resultMap);
        //搜索条件
        model.addAttribute("searchMap",searchMap);

        //请求地址
//        String url=url(searchMap);
//        model.addAttribute("url",url);
        String[] urls=url(searchMap);
        model.addAttribute("url",urls[0]);
        model.addAttribute("sorturl",urls[1]);
        //分页计算
        Page<SkuInfo> page=new Page<SkuInfo>(
                Long.parseLong(resultMap.get("totalPages").toString()),//总页数
                Integer.parseInt(resultMap.get("pageNum").toString()),//当前页
                Integer.parseInt(resultMap.get("pageSize").toString()));//每页显示条数
        model.addAttribute("page",page);


        //排序
        //2.创建查询对象 的构建对象
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        String sortRule=searchMap.get("sortRule");//排序规则 ASC DESC
        String sortField=searchMap.get("sortField");//排序字段
        if (!StringUtils.isEmpty(sortField)) {
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort(sortField).order(sortRule.equals("DESC")? SortOrder.DESC:SortOrder.ASC));
        }
        return "search";
    }

    /**
     * url组装和处理
     * @param searchMap
     * @return
     */
    private String[] url(Map<String, String> searchMap) {
        //url地址
        String url="/search/list";
        String sorturl="/search/list";
        if (searchMap!=null&&searchMap.size()>0) {
            url+="?";
            sorturl+="?";
            for (Map.Entry<String, String> entry : searchMap.entrySet()) {
                //跳过分页
                if (entry.getKey().equalsIgnoreCase("pageNum")) {
                    continue;
                }
               //如果是排序 则 跳过 拼接排序的地址 因为有数据
                if(entry.getKey().equals("sortField") || entry.getKey().equals("sortRule")){
                    continue;
                }
                url += entry.getKey() + "=" + entry.getValue() + "&";

            }
            //去掉最后一个&
            if(url.lastIndexOf("&")!=-1) {
                url = url.substring(0, url.lastIndexOf("&"));
            }
            if (sorturl.lastIndexOf("&")!=-1) {
                sorturl=sorturl.substring(0,sorturl.lastIndexOf("&"));
            }
        }
        return new String[]{url,sorturl};
    }
}
