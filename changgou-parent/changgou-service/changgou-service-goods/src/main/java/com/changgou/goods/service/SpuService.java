package com.changgou.goods.service;

        import com.changgou.goods.pojo.Goods;
        import com.changgou.goods.pojo.Spu;
        import com.github.pagehelper.PageInfo;

        import java.util.List;

/****
 * @Author:admin
 * @Description:Spu业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface SpuService {

    /***
     * Spu多条件分页查询
     * @param spu
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spu> findPage(Spu spu, int page, int size);

    /***
     * Spu分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spu> findPage(int page, int size);

    /***
     * Spu多条件搜索方法
     * @param spu
     * @return
     */
    List<Spu> findList(Spu spu);

    /***
     * 删除Spu
     * @param id
     */
    void delete(Long id);

    /***
     * 修改Spu数据
     * @param spu
     */
    void update(Spu spu);

    /***
     * 新增Spu
     * @param spu
     */
    void add(Spu spu);

    /**
     * 根据ID查询Spu
     *
     * @param id
     * @return
     */
    Spu findById(Long id);

    /***
     * 查询所有Spu
     * @return
     */
    List<Spu> findAll();

    //保存商品
    void saveGoods(Goods goods);

    //根据SPU的ID查找SPU以及对应的SKU集合
    Goods findGoodsById(long spuId);

    //商品审核
    void audit(long spuId);

    //商品下架
    void pull(long spuId);
    //商品上架
    void put(long spuId);
    //批量上架
    int putMany(Long[] ids);
    //批量下架
    int pullMany(Long[] ids);
    //逻辑删除
    void logicDelete(long spuId);
    //还原被删除商品
    void restore(long spuId);

}
