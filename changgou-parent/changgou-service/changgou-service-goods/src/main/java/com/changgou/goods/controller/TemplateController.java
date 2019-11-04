package com.changgou.goods.controller;

import com.changgou.goods.pojo.Template;
import com.changgou.goods.service.TemplateService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/template")
@CrossOrigin  //跨域
public class TemplateController {

    @Autowired
    public TemplateService templateService;

    /***
     * Template分页条件搜索实现
     * @param template
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody Template template, @PathVariable int page, @PathVariable int size) {
        //执行查询
        PageInfo<Template> pageInfo = templateService.findPage(template, page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /***
     * Template分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        PageInfo<Template> pageInfo = templateService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param template
     * @return
     */
    @PostMapping("/search")
    public Result<List<Template>> findList(@RequestBody Template template) {
        List<Template> list = templateService.findList(template);
        return new Result<List<Template>>(true, StatusCode.OK, "查询成功", list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        templateService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /***
     * 修改Template数据
     * @param template
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody Template template) {
        //设置主键
        template.setId(id);
        templateService.update(template);
        return new Result(true, StatusCode.OK, "更新成功");
    }

    /***
     * 新增Template数据
     * @param template
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Template template) {
        templateService.add(template);
        return new Result(true, StatusCode.OK, "添加成功");
    }


    /***
     * 根据ID查询Template数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Template> findById(@PathVariable Integer id) {
        Template template = templateService.findById(id);
        return new Result<>(true, StatusCode.OK, "查询成功", template);
    }

    /**
     * 查询所有
     * @return
     */
    @GetMapping
    public Result<Template> findAll() {
        List<Template> all = templateService.findAll();
        return new Result<>(true, StatusCode.OK, "查询成功", all);
    }

    /***
     * 根据分类查询模板数据
     * @param id:分类ID
     */
    @GetMapping(value = "/category/{id}")
    public Result<Template> findByCategoryId(@PathVariable(value = "id")Integer id){
        //调用Service查询
        Template template = templateService.findByCategoryId(id);
        return new Result<Template>(true, StatusCode.OK,"查询成功",template);
    }
}
