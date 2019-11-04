package com.changgou.goods.service.impl;

import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.dao.SpecMapper;
import com.changgou.goods.dao.TemplateMapper;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.pojo.Spec;
import com.changgou.goods.pojo.Template;
import com.changgou.goods.service.SpecService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SpecServiceImpl implements SpecService {

    @Autowired(required = false)
    private SpecMapper specMapper;
    @Autowired(required = false)
    private TemplateMapper templateMapper;
    @Autowired(required = false)
    private CategoryMapper categoryMapper;

    /**
     * Spec条件+分页查询
     *
     * @param spec 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Spec> findPage(Spec spec, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //构建条件
        Example example = createExample(spec);
        //执行搜索
        return new PageInfo<Spec>(specMapper.selectByExample(example));
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Spec> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        return new PageInfo<Spec>(specMapper.selectAll());
    }

    /**
     * Spec条件查询
     *
     * @param spec
     * @return
     */
    @Override
    public List<Spec> findList(Spec spec) {
        //构建条件
        Example example = createExample(spec);
        return specMapper.selectByExample(example);
    }


    /***
     * 删除
     * @param id
     */
    @Override
    @Transactional
    public void delete(Integer id) {
        //根据主键查询模板
        Spec spec = specMapper.selectByPrimaryKey(id);
        //变更模板数量
        updateSpecNum(spec, -1);
        //删除模板
        specMapper.deleteByPrimaryKey(id);

    }


    /**
     * 跟新数据
     *
     * @param spec
     */
    @Override
    public void update(Spec spec) {
        specMapper.updateByPrimaryKey(spec);
    }

    /**
     * 添加数据
     *
     * @param spec
     */
    @Override
    @Transactional
    public void add(Spec spec) {
        specMapper.insertSelective(spec);
        //变更模板数量
        updateSpecNum(spec, 1);
    }

    /**
     * 根据id查寻
     *
     * @param id
     * @return
     */
    @Override
    public Spec findById(Integer id) {
        Spec spec = specMapper.selectByPrimaryKey(id);
        return spec;
    }

    /***
     * 查询所有
     * @return
     */
    @Override
    public List<Spec> findAll() {
        List<Spec> all = specMapper.selectAll();
        return all;
    }

    /**
     * 根据分类ID查询规格列表
     * @param categoryid
     * @return
     */
    @Override
    public List<Spec> findByCategoryId(Integer categoryid) {
        //查询分类
        Category category = categoryMapper.selectByPrimaryKey(categoryid);
        //创建规格对象
        Spec spec =new Spec();
        //根据分类的模板ID查询规格
        spec.setTemplateId(category.getTemplateId());

        return specMapper.select(spec);
    }


    //方法提取
    private Example createExample(Spec spec) {
        //构建条件
        Example example = new Example(Spec.class);//select*from tb_spec
        Example.Criteria criteria = example.createCriteria();//条件
        if (spec != null) {
            //主键
            if (!StringUtils.isEmpty(spec.getId())) {
                criteria.andEqualTo("id", spec.getId());
            }
            // 名称
            if (!StringUtils.isEmpty(spec.getName())) {
                criteria.andLike("name", "%" + spec.getName() + "%");
            }
            // 规格选项
            if (!StringUtils.isEmpty(spec.getOptions())) {
                criteria.andEqualTo("options", spec.getOptions());
            }
            // 排序
            if (!StringUtils.isEmpty(spec.getSeq())) {
                criteria.andEqualTo("sep", spec.getSeq());
            }
            if (!StringUtils.isEmpty(spec.getTemplateId())) {
                criteria.andEqualTo("templateId", spec.getTemplateId());
            }
            // 模板ID
        }
        return example;
    }

    /**
     * 修改模板统计数据
     *
     * @param spec:操作的模板
     * @param count:变更的数量
     */
    private void updateSpecNum(Spec spec, int count) {
        //修改模板数量统计
        Template template = templateMapper.selectByPrimaryKey(spec.getTemplateId());
        System.out.println(template.getSpecNum());
        template.setSpecNum(template.getSpecNum() + count);
        templateMapper.updateByPrimaryKeySelective(template);
    }
}
