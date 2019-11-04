package com.changgou.goods.service;

import com.changgou.goods.pojo.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {
    //查询所有品牌
    List<Brand> findAll();

    //根据id查询
    Brand findBy(Integer id);

    //新增品牌
    void add(Brand brand);

    //修改品牌数据
    void update(Brand brand);

    //删除数据
    void delete(Integer id);

    //条件查询
    List<Brand> findList(Brand brand);

    //分页查询
    PageInfo<Brand> findPage(Integer page, Integer size);

    //有条件的分页查询
    PageInfo<Brand> findPage(Brand brand, Integer page, Integer size);
     //根据分类ID查询品牌集合
    List<Brand> findByCategory(Integer categoryid);
//    //根据分类实现品牌列表查询
//    List<Brand> findBrandByCategory(Integer id);
}
