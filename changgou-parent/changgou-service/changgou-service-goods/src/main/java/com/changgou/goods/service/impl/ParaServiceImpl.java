package com.changgou.goods.service.impl;

import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.dao.ParaMapper;
import com.changgou.goods.dao.TemplateMapper;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.pojo.Para;
import com.changgou.goods.pojo.Template;
import com.changgou.goods.service.ParaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ParaServiceImpl implements ParaService {

    @Autowired(required = false)
    private ParaMapper paraMapper;

    @Autowired(required = false)
    private TemplateMapper templateMapper;
    @Autowired(required = false)
    private CategoryMapper categoryMapper;

    /**
     * 多条件分页查寻
     *
     * @param para
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Para> findPage(Para para, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //构建条件
        Example example = createExample(para);
        //执行搜索
        return new PageInfo<Para>(paraMapper.selectByExample(example));
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Para> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        //分页查询
        return new PageInfo<Para>(paraMapper.selectAll());
    }

    /**
     * 多条件查询
     *
     * @param para
     * @return
     */
    @Override
    public List<Para> findList(Para para) {
        //构建条件
        Example example = createExample(para);
        //执行搜索
        return paraMapper.selectByExample(example);
    }


    /**
     * 根据id删除
     *
     * @param id
     */
    @Override
    public void delete(int id) {
        //根据id查询
        Para para = paraMapper.selectByPrimaryKey(id);
        //修改模板统计数
        updateParaNum(para, -1);

        paraMapper.deleteByPrimaryKey(id);
    }


    /**
     * 更新数据
     *
     * @param para
     */
    @Override
    public void update(Para para) {
        paraMapper.updateByPrimaryKey(para);
    }

    /**
     * 添加数据
     *
     * @param para
     */
    @Override
    public void add(Para para) {
        paraMapper.insert(para);
        //修改模板统计数据
        updateParaNum(para, 1);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public Para findById(int id) {
        Para para = paraMapper.selectByPrimaryKey(id);

        return para;
    }

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<Para> findAll() {
        return paraMapper.selectAll();
    }

    /**
     * 根据分类id查询参数信息
     * @param id
     * @return
     */
    @Override
    public List<Para> findByCategoryId(Integer id) {
        //查询分类信息
        Category category = categoryMapper.selectByPrimaryKey(id);
        //创建参数对象
        Para para=new Para();
        //设置模板id
        para.setTemplateId(category.getTemplateId());
        return paraMapper.select(para);
    }


    //抽取方法
    private Example createExample(Para para) {
        //构建条件
        Example example = new Example(Para.class);
        Example.Criteria criteria = example.createCriteria();
        if (para != null) {
            // id
            if (!StringUtils.isEmpty(para.getId())) {
                criteria.andEqualTo("id", para.getId());
            }
            //名称
            if (!StringUtils.isEmpty(para.getName())) {
                criteria.andLike("name", "%" + para.getName() + "%");
            }
            // 选项
            if (!StringUtils.isEmpty(para.getOptions())) {
                criteria.andEqualTo("options", para.getOptions());
            }
            //排序
            if (!StringUtils.isEmpty(para.getSeq())) {
                criteria.andEqualTo("seq", para.getSeq());
            }
            //模板id
            if (!StringUtils.isEmpty(para.getTemplateId())) {
                criteria.andEqualTo("templateId", para.getTemplateId());
            }
        }
        return example;
    }

    /**
     * 修改模板统计数据
     *
     * @param para:操作的参数
     * @param count:变更的数量
     */

    private void updateParaNum(Para para, int count) {
        //修改模板数量统计
        Template template = new Template();
        template.setSpecNum(template.getParaNum() + count);
        templateMapper.updateByPrimaryKeySelective(template);
    }

}
