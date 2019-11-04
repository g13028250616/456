package com.changgou.goods.service.impl;

import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.dao.TemplateMapper;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.pojo.Template;
import com.changgou.goods.service.TemplateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired(required = false)
    private TemplateMapper templateMapper;
    @Autowired(required = false)
    public CategoryMapper categoryMapper;

    /**
     * Template条件+分页查询
     *
     * @param template 查询条件
     * @param page     页码
     * @param size     页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Template> findPage(Template template, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //构建查询条件
        Example example = createExample(template);
        //执行查询
        return new PageInfo<Template>(templateMapper.selectByExample(example));
    }

    /***
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Template> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        //执行查询
        return new PageInfo<Template>(templateMapper.selectAll());
    }

    /**
     * 根据条件查询
     *
     * @param template
     * @return
     */
    @Override
    public List<Template> findList(Template template) {
        //构建查询条件
        Example example = createExample(template);
        //执行查询条件
        return templateMapper.selectByExample(example);
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        templateMapper.deleteByPrimaryKey(id);
    }

    /***
     * 修改数据
     * @param template
     */
    @Override
    public void update(Template template) {
        templateMapper.updateByPrimaryKey(template);
    }

    /**
     * 添加数据
     *
     * @param template
     */
    @Override
    public void add(Template template) {
        templateMapper.insert(template);
    }

    /***
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Template findById(Integer id) {
        Template template = templateMapper.selectByPrimaryKey(id);
        return template;
    }

    /***
     * 查询所有
     * @return
     */
    @Override
    public List<Template> findAll() {
        return templateMapper.selectAll();
    }

    /***
     * 根据分类ID查询模板信息
     * @param id
     * @return
     */
    @Override
    public Template findByCategoryId(Integer id) {
        //查询分类信息
        Category category = categoryMapper.selectByPrimaryKey(id);
        //根据模板id查询模板信息
        return templateMapper.selectByPrimaryKey(category.getTemplateId());
    }


    /**
     * 方法抽取
     */
    private Example createExample(Template template) {
        //构建条件
        Example example = new Example(Template.class);//select*from tb_template
        Example.Criteria criteria = example.createCriteria();//条件
        if (template != null) {
            //主键
            if (!StringUtils.isEmpty(template.getId())) {
                criteria.andEqualTo("id", template.getId());
            }
            // 模板名称
            if (!StringUtils.isEmpty(template.getName())) {
                criteria.andLike("name", "%" + template.getName() + "%");
            }
            // 规格数量
            if (!StringUtils.isEmpty(template.getSpecNum())) {
                criteria.andEqualTo("specNum", template.getSpecNum());
            }
            // 参数数量
            if (!StringUtils.isEmpty(template.getParaNum())) {
                criteria.andEqualTo("paraNum", template.getParaNum());
            }
        }
        return example;
    }
}
