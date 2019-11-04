package com.changgou.goods.service;

import com.changgou.goods.pojo.Para;
import com.github.pagehelper.PageInfo;
import entity.Page;

import java.util.List;

public interface ParaService {
    //Para多条件分页查询
    PageInfo<Para> findPage(Para para,int page,int size);
    //分页查询
    PageInfo<Para> findPage(int page,int size);
    //多条件查询
    List<Para> findList(Para para);
    //删除数据
    void delete(int id);
    //更新数据
    void update(Para para);
    //添加数据
    void add(Para para);
    //根据id查询
    Para findById(int id);
    //查询所有
   List<Para> findAll();
   //根据分类ID查询参数列表
    List<Para> findByCategoryId(Integer id);
}
