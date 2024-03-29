package com.changgou.goods.service.impl;


import com.changgou.goods.dao.AlbumMapper;


import com.changgou.goods.pojo.Album;
import com.changgou.goods.service.AlbumService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired(required = false)
    private AlbumMapper albumMapper;

    /**
     * 根据条件分页
     *
     * @param album
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Album> findPage(Album album, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //创建查询条件
        Example example = createExample(album);
        //执行搜索
        return new PageInfo<Album>(albumMapper.selectByExample(example));
    }

    /**
     * 分页
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Album> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        //执行搜索
        return new PageInfo<Album>(albumMapper.selectAll());
    }

    /**
     * Album条件查询
     *
     * @param album
     * @return
     */
    @Override
    public List<Album> findList(Album album) {
        //构建搜索条件
        Example example = createExample(album);
        //执行搜索
        return albumMapper.selectAll();
    }

    /**
     * 根据id删除Album
     *
     * @param id
     */
    @Override
    public void delete(long id) {
        albumMapper.deleteByPrimaryKey(id);
    }

    /***
     * 更新Album数据
     * @param album
     */
    @Override
    public void update(Album album) {
        albumMapper.updateByPrimaryKey(album);
    }

    /**
     * 添加Album数据
     *
     * @param album
     */
    @Override
    public void add(Album album) {
        albumMapper.insert(album);
    }

    /***
     * 根据id查询Album数据
     * @param id
     * @return
     */
    @Override
    public Album findById(long id) {
        return albumMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Album全部数据
     *
     * @return
     */
    @Override
    public List<Album> findAll() {
        return albumMapper.selectAll();
    }

    /**
     * Album构建查询对象
     *
     * @param album
     * @return
     */
    private Example createExample(Album album) {
        //构建一个查询条件example
        Example example = new Example(Album.class);//select * from tb_album
        Example.Criteria criteria = example.createCriteria();//条件
        if (album != null) {
            //编号
            if (!StringUtils.isEmpty(album.getId())) {
                criteria.andEqualTo("id", album.getId());//where id=?
            }
            //相册名称
            if (!StringUtils.isEmpty(album.getTitle())) {
                criteria.andLike("title", "%" + album.getTitle() + "%");//where titlt like
            }
            //相册封面
            if (!StringUtils.isEmpty(album.getImage())) {//where imsge=?
                criteria.andEqualTo("image", album.getImage());
            }
            //图片列表
            if (!StringUtils.isEmpty(album.getImageItems())) {//where imageItems=?
                criteria.andEqualTo("imageItems", album.getImageItems());
            }
        }
        return example;
    }
}
