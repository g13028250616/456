package com.changgou.goods.service.impl;
import com.changgou.goods.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {


    @Autowired(required = false)
    private BrandMapper brandMapper;
//    @Autowired(required = false)
//    private CategoryMapper categoryMapper;

    //查找全部
    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    //根据id查询
    @Override
    public Brand findBy(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    //新增品牌
    @Override
    public void add(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    //修改品牌数据
    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    //删除数据
    @Override
    public void delete(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }


    //根据条件查询
    @Override
    public List<Brand> findList(Brand brand) {
        //创建查询条件
        Example example = createExample(brand);//example:标准
        //根据构建的条件查询
        List<Brand> brands = brandMapper.selectByExample(example);
        return brands;
    }

    //分页查询
    @Override
    public PageInfo<Brand> findPage(Integer page, Integer size) {
        //静态分页
        PageHelper.startPage(page, size);
        //分页查询
        return new PageInfo<Brand>(brandMapper.selectAll());
    }


    //有条件的分页
    @Override
    public PageInfo<Brand> findPage(Brand brand, Integer page, Integer size) {
        //分页
        PageHelper.startPage(page, size);
        //创建查询条件
        Example example = createExample(brand);
        return new PageInfo<Brand>(brandMapper.selectByExample(example));
    }

    @Override
    public List<Brand> findByCategory(Integer categoryid) {
        //两种方案:
        //1. 自己写sql语句直接执行  推荐
        //2. 调用通用的mapper的方法 一个个表查询
        return brandMapper.findByCategory(categoryid);
    }




    private Example createExample(Brand brand) {
        Example example = new Example(Brand.class);//select * from tb_brand
        Example.Criteria criteria = example.createCriteria();//Criteria:条件
        //判断,拼接条件
        if (brand != null) {
            if (!StringUtils.isEmpty(brand.getName())) {//where name like ?
                //第一个参数:指定要条件比较的 属性的名称(POJO的属性名)
                //第二个参数:指定要比较的值
                criteria.andLike("name", "%" + brand.getName() + "%");
            }
            if (!StringUtils.isEmpty(brand.getLetter())) {//where letter ?
                //第一个参数:指定要条件比较的 属性的名称(POJO的属性名)
                //第二个参数:指定要比较的值
                criteria.andLike("letter", brand.getLetter());
            }
        }
        return example;
    }
}
