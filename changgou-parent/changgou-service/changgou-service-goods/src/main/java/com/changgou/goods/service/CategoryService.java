package com.changgou.goods.service;

import com.changgou.goods.pojo.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryService {
    //多条件分页查询
    PageInfo<Category> findPage(Category category,int page,int size);
    //分页查询
    PageInfo<Category> findPage(int page,int size);
    //多条件查询
    List<Category> findList(Category category);
    //根据id删除数据
    void delete(int id);
    //更新数据
    void update(Category category);
    //添加数据
    void add(Category category);
    //根据id查询
    Category findById(int id);
    //查询所有
    List<Category> findAll();
    //根据父节点ID查询
    List<Category> findByParentId(int pid);

}
