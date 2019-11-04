package com.changgou.goods.service.impl;

import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired(required = false)
    private CategoryMapper categoryMapper;

    /**
     * 多条件分页
     * @param category
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Category> findPage(Category category, int page, int size) {
        //分页
        PageHelper.startPage(page,size);
        //构建条件
        Example example=createExample(category);
        //执行搜索
        return new PageInfo<Category>(categoryMapper.selectByExample(example));
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Category> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page,size);
        //执行搜索
        return new PageInfo<Category>(categoryMapper.selectAll());
    }

    /**
     * 多条件查询
     * @param category
     * @return
     */
    @Override
    public List<Category> findList(Category category) {
        //构建条件
        Example example=createExample(category);
        return categoryMapper.selectByExample(example);
    }

    /**
     * 格局id删除
     * @param id
     */
    @Override
    public void delete(int id) {
    categoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新数据
     * @param category
     */
    @Override
    public void update(Category category) {
    categoryMapper.updateByPrimaryKeySelective(category);
    }

    /**
     * 增加数据
     * @param category
     */
    @Override
    public void add(Category category) {
    categoryMapper.insert(category);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Category findById(int id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        return category;
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Category> findAll() {
        List<Category> list = categoryMapper.selectAll();
        return list;
    }

    /**
     * 根据父节点ID查询
     * @param pid 父节点id
     * @return
     */
    @Override
    public List<Category> findByParentId(int pid) {
        //创建分类对象Category;分类
        Category category = new Category();
        //设置id
        category.setId(pid);
        return categoryMapper.select(category);
    }


    /**
     * Category构建查询对象
     * @param category
     * @return
     */
    public Example createExample(Category category){
        Example example=new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        if(category!=null){
            // 分类ID
            if(!StringUtils.isEmpty(category.getId())){
                criteria.andEqualTo("id",category.getId());
            }
            // 分类名称
            if(!StringUtils.isEmpty(category.getName())){
                criteria.andLike("name","%"+category.getName()+"%");
            }
            // 商品数量
            if(!StringUtils.isEmpty(category.getGoodsNum())){
                criteria.andEqualTo("goodsNum",category.getGoodsNum());
            }
            // 是否显示
            if(!StringUtils.isEmpty(category.getIsShow())){
                criteria.andEqualTo("isShow",category.getIsShow());
            }
            // 是否导航
            if(!StringUtils.isEmpty(category.getIsMenu())){
                criteria.andEqualTo("isMenu",category.getIsMenu());
            }
            // 排序
            if(!StringUtils.isEmpty(category.getSeq())){
                criteria.andEqualTo("seq",category.getSeq());
            }
            // 上级ID
            if(!StringUtils.isEmpty(category.getParentId())){
                criteria.andEqualTo("parentId",category.getParentId());
            }
            // 模板ID
            if(!StringUtils.isEmpty(category.getTemplateId())){
                criteria.andEqualTo("templateId",category.getTemplateId());
            }
        }
        return example;
    }
}
