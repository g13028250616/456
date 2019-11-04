package com.changgou.goods.controller;

import com.changgou.goods.pojo.Category;
import com.changgou.goods.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /***
     * Category分页条件搜索实现
     * @param category
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody Category category, @PathVariable int page, @PathVariable int size) {
        //分页查询
        PageInfo<Category> pageInfo = categoryService.findPage(category, page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /**
     * 分页
     *
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, int size) {
        PageInfo<Category> pageInfo = categoryService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", page);
    }

    /**
     * 多条件查询
     *
     * @param category
     * @return
     */
    @PostMapping("/search")
    public Result<List<Category>> findList(@RequestBody(required = false) Category category) {
        List<Category> list = categoryService.findList(category);
        return new Result<List<Category>>(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable int id) {
        categoryService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /***
     * 修改Category数据
     * @param category
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@PathVariable int id, @RequestBody Category category) {
        //设置id
        category.setId(id);
        categoryService.update(category);
        return new Result(true, StatusCode.OK, "更新成功");
    }

    /**
     * 添加数据
     *
     * @param category
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Category category) {
        categoryService.add(category);
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
        Category category = categoryService.findById(id);
        return new Result(true, StatusCode.OK, "查询成功", category);
    }

    @GetMapping
    public Result findAll() {
        List<Category> all = categoryService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", all);
    }

    /**
     * 根据节点ID查询所有子节点分类集合
     *
     * @param pid
     * @return
     */
    @GetMapping("/list/{pid}")
    public Result<List<Category>> findByParentId(@PathVariable int pid) {
        List<Category> list = categoryService.findByParentId(pid);
        return new Result<List<Category>>(true, StatusCode.OK, "查询成功", list);
    }
}
