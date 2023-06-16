package com.mindskip.iiacs.repository;

public interface BaseMapper<T> {

    int deleteByPrimaryKey(Integer id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

    int insertSelectivePaper(T record);

    T selectByFrameTextContentId(Integer id);

    T selectByPrimaryKeyPageIndex(Integer id, Integer pageIndex);

    int updateFrameTextContentId(T record);
}
