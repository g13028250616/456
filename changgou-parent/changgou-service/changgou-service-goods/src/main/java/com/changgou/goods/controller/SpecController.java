package com.changgou.goods.controller;

import com.changgou.goods.pojo.Brand;
import com.changgou.goods.pojo.Spec;
import com.changgou.goods.pojo.StockBack;
import com.changgou.goods.service.CategoryService;
import com.changgou.goods.service.SpecService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spec")
@CrossOrigin  //跨域
public class SpecController {
    @Autowired
    private SpecService specService;
//    @Autowired
//    private CategoryService categoryService;

    /***
     * Spec分页条件搜索实现
     * @param spec
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody Spec spec, @PathVariable int page, @PathVariable int size) {
        //分页
        PageInfo<Spec> pageInfo = specService.findPage(spec, page, size);
        //执行查询
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /***
     * Spec分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        //分页
        PageInfo<Spec> pageInfo = specService.findPage(page, size);
        //执行查询
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param spec
     * @return
     */
    @PostMapping("/search")
    public Result<List<Spec>> findList(@RequestBody(required = false) Spec spec) {
        List<Spec> list = specService.findList(spec);
        return new Result<List<Spec>>(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable int id) {
        specService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /***
     * 根据id删除
     * @param spec
     * @param id
     * @return
     */
    @PostMapping("/{id}")
    public Result update(@RequestBody Spec spec, @PathVariable int id) {
        //设置主键
        spec.setId(id);
        specService.update(spec);
        return new Result(true, StatusCode.OK, "更新成功");
    }

    /**
     * 添加数据
     *
     * @param spec
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Spec spec) {
        specService.add(spec);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /***
     * 根据ID查询Spec数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable int id) {
        Spec spec = specService.findById(id);
        return new Result(true, StatusCode.OK, "查询成功", spec);
    }

    /***
     * 查询Spec全部数据
     * @return
     */
    @GetMapping
    public Result findAll() {
        List<Spec> all = specService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", all);
    }

    /**
     * 根据分类ID查询对应的规格列表
     * @param categoryid
     * @return
     */
    @GetMapping("/category/{id}")
    public Result<List<Brand>> findByCategoryId(@PathVariable(value = "id") Integer categoryid){
        List<Spec> specList = specService.findByCategoryId(categoryid);
        return new Result<List<Brand>>(true,StatusCode.OK,"查询成功",specList);
    }
}
