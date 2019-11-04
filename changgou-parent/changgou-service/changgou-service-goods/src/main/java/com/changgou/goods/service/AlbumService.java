package com.changgou.goods.service;

import com.changgou.goods.pojo.Album;
import com.github.pagehelper.PageInfo;
import entity.Page;

import java.util.List;

public interface AlbumService {
    /**
     * Album多条件分页查询
     *
     * @param album
     * @param page
     * @param size
     * @return
     */
    PageInfo<Album> findPage(Album album, int page, int size);

    /**
     * Album分页查询
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<Album> findPage(int page, int size);

    /**
     * Album多条件搜索方法
     *
     * @param album
     * @return
     */
    List<Album> findList(Album album);

    /**
     * 根据id删除album
     *
     * @param id
     */
    void delete(long id);

    /**
     * 修改Album的数据
     *
     * @param album
     */
    void update(Album album);//跟新数据最好不有用id

    /**
     * 新增Album的数据
     *
     * @param album
     */
    void add(Album album);//增加数据不能有id.id是自增的

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Album findById(long id);

    /**
     * 查询所有Album
     *
     * @return
     */
    List<Album> findAll();
}
