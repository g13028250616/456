package com.changgou.goods.controller;

import com.changgou.goods.pojo.Para;
import com.changgou.goods.service.ParaService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/para")
public class ParaController {

    @Autowired
    private ParaService paraService;

    /***
     * Para分页条件搜索实现
     * @param para
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody Para para, @PathVariable int page, @PathVariable int size) {
        PageInfo<Para> pageInfo = paraService.findPage(para, page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /***
     * Para分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        PageInfo<Para> pageInfo = paraService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param para
     * @return
     */
    @PostMapping("/search")
    public Result<List<Para>> findList(@RequestBody(required = false) Para para) {
        List<Para> list = paraService.findList(para);
        return new Result<List<Para>>(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable int id) {
        paraService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 修改数据
     *
     * @param id
     * @param para
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@PathVariable int id, @RequestBody Para para) {
        //设置id
        para.setId(id);
        paraService.update(para);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @PostMapping
    public Result add(@RequestBody Para para) {
        paraService.add(para);
        return new Result(true, StatusCode.OK, "添加数据成功");
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable int id) {
        Para para = paraService.findById(id);
        return new Result(true, StatusCode.OK, "查询成功", para);
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        List<Para> all = paraService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", all);
    }

    /**
     * 根据分类ID查询参数列表
     * @param id
     * @return
     */
    @GetMapping("/category/{id}")
    public Result<List<Para>> findByCategoryId(@PathVariable(value = "id") Integer id){
        //获取分类信息
        List<Para> paras = paraService.findByCategoryId(id);
        Result<List<Para>> result = new Result<>(true, StatusCode.OK, "查询分类对应的品牌成功",paras);
        return result;
    }
}
