package com.changgou.goods.controller;

import com.changgou.goods.pojo.Brand;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.pojo.Para;
import com.changgou.goods.service.BrandService;
import com.changgou.goods.service.ParaService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
@CrossOrigin
public class BrandController {
    @Autowired
    private BrandService brandService;
    @Autowired
    private ParaService paraService;

    /**
     * 查询所有
     */
    @GetMapping
    public Result<Brand> findAll() {
        List<Brand> brands = brandService.findAll();
        return new Result<Brand>(true, StatusCode.OK, "查询全部品牌成功", brands);
    }


    //根据id查询
    @PostMapping("/{id}")
    public Result<Brand> findById(@PathVariable Integer id) {
        Brand brand = brandService.findBy(id);
        return new Result<Brand>(true, StatusCode.OK, "查询成功", brand);
    }

    //新增品牌
    @PostMapping
    public Result add(@RequestBody Brand brand) {
        brandService.add(brand);
        return new Result(true, StatusCode.OK, "新增成功");
    }

    //修改品牌数据
    @PutMapping("/{id}")
    public Result update(@RequestBody Brand brand, @PathVariable Integer id) {
        //设置id
        brand.setId(id);
        //修改数据
        brandService.update(brand);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    //删除数据
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        brandService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    //根据条件查询
    @PostMapping("/search")
    public Result<List<Brand>> findList(@RequestBody(required = false) Brand brand) {
        List<Brand> list = brandService.findList(brand);
        return new Result<List<Brand>>(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 分页查询
     * 当前页
     * 每页多少条
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable Integer page, @PathVariable Integer size) {
        PageInfo<Brand> pageInfo = brandService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页成功", pageInfo);
    }

    /*****
     * 有条件的分页查询
     *
     *
     *
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Brand brand, @PathVariable Integer page, @PathVariable Integer size) {
        //搜索执行
        PageInfo<Brand> pageInfo = brandService.findPage(brand, page, size);
        int i = 10 / 0;
        return new Result<PageInfo>(true, StatusCode.OK, "查询分页成功", pageInfo);
    }



    /**
     * 根据分类ID查询参数列表
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/category/{id}")
    public Result<List<Brand>> findByCategory(@PathVariable(value = "id") int categoryId) {
        //根据分类ID查询对应的参数信息
        List<Brand> brandList = brandService.findByCategory(categoryId);


//       return new Result<List<Para>>(true,StatusCode.OK,"查询分类对应的品牌成功",paras);
        Result<List<Brand>> result = new Result<List<Brand>>(true, StatusCode.OK, "查询分类对应的品牌成功", brandList);
        return result;
    }
}
