package com.changgou.goods.controller;

import com.changgou.goods.pojo.Album;
import com.changgou.goods.service.AlbumService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
@CrossOrigin//跨域
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Album album, @PathVariable int page, @PathVariable int size) {
        //执行搜索
        PageInfo<Album> pageInfo = albumService.findPage(album, page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /**
     * Album分页搜索实现
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        //分页
        PageHelper.startPage(page, size);
        //执行查询功能
        PageInfo<Album> pageInfo = albumService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", pageInfo);

    }

    /**
     * 多条件搜索品牌数据
     *
     * @param album
     * @return
     */
    @PostMapping("/search")
    public Result<List<Album>> findList(@PathVariable(required = false) Album album) {
        List<Album> list = albumService.findList(album);
        return new Result<List<Album>>(true, StatusCode.OK, "查询成功", list);
    }

    /***
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable long id) {
        albumService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 修改Album数据
     *
     * @param album
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Album album, @PathVariable long id) {
        //设置id
        album.setId(id);
        albumService.update(album);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /***
     * 新增Album数据
     * @param album
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Album album) {
        albumService.add(album);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Album> findById(@PathVariable long id) {
        Album album = albumService.findById(id);
        return new Result<>(true, StatusCode.OK, "查询成功", album);
    }

    /***
     * 查询所有
     * @return
     */
    @GetMapping
    public Result<Album> findAll() {
        List<Album> all = albumService.findAll();
        return new Result<Album>(true, StatusCode.OK, "查询成功", all);
    }
}
