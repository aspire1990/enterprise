package com.mindskip.iiacs.service.impl;

import com.mindskip.iiacs.repository.BaseMapper;
import com.mindskip.iiacs.service.BaseService;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

    private final BaseMapper<T> baseMapper;

    public BaseServiceImpl(BaseMapper<T> baseMapper) {
        this.baseMapper = baseMapper;
    }

    @Override
    public int deleteById(Integer id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T record) {
        return baseMapper.insert(record);
    }

    @Override
    public int insertByFilter(T record) {
        return baseMapper.insertSelective(record);
    }

    @Override
    public int insertByFilterPaper(T record) {
        return baseMapper.insertSelectivePaper(record);
    }

    @Override
    public int updateByIdframeTextContentId(T record) {
        return baseMapper.updateFrameTextContentId(record);
    }

    @Override
    public T selectById(Integer id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public T selectByIdPaper(Integer id) {
        return baseMapper.selectByFrameTextContentId(id);
    }

    @Override
    public T selectByFtcidPageIndex(Integer id, Integer pageIndex) {
        return baseMapper.selectByPrimaryKeyPageIndex(id, pageIndex);
    }


    @Override
    public int updateByIdFilter(T record) {
        return baseMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateById(T record) {
        return baseMapper.updateByPrimaryKey(record);
    }
}
