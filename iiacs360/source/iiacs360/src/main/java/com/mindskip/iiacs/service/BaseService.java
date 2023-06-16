package com.mindskip.iiacs.service;

/**
 * service接口，和mybatis generator 配套使用
 *
 * @param <T>
 */
public interface BaseService<T> {
    /**
     * 根据id删除数据，硬删除
     *
     * @param id id
     * @return int
     */
    int deleteById(Integer id);

    /**
     * 插入数据,完整数据插入
     *
     * @param record record
     * @return int
     */
    int insert(T record);

    /**
     * 插入不为null的数据
     *
     * @param record
     * @return int
     */
    int insertByFilter(T record);

    /**
     * 插入不为null的数据,page
     *
     * @param record
     * @return int
     */
    int insertByFilterPaper(T record);


    /**
     * 插入不为null的数据,page
     *
     * @param record
     * @return int
     */
    int updateByIdframeTextContentId(T record);


    /**
     * 根据id查询数据
     *
     * @param id
     * @return
     */
    T selectById(Integer id);

    /**
     * 根据id查询数据
     *
     * @param id
     * @return
     */
    T selectByIdPaper(Integer id);

    /**
     * 根据id和pageIndex查询
     *
     * @param id
     * @param pageIndex
     * @return
     */
    T selectByFtcidPageIndex(Integer id, Integer pageIndex);


    /**
     * 更新不为null的数据
     *
     * @param record
     * @return int
     */
    int updateByIdFilter(T record);

    /**
     * 更新完整的数据
     *
     * @param record
     * @return int
     */
    int updateById(T record);
}
