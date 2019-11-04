package com.changgou.goods.service;

import com.changgou.goods.pojo.Spec;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SpecService {
    //Spec多条件分页查询
    PageInfo<Spec> findPage(Spec spec,int page,int size);
    //Spec分页查询
    PageInfo<Spec> findPage(int page,int size);
    //Spec多条件搜索方法
    List<Spec> findList(Spec spec);
    //删除Spec
    void delete(Integer id);
    //更新spec
    void update(Spec spec);
    //添加spec
    void add(Spec spec);
    //根据id查询
    Spec findById(Integer id);
    //查询所有
    List<Spec> findAll();
    //根据分类ID查询规格列表
    List<Spec> findByCategoryId(Integer categoryid);
}
