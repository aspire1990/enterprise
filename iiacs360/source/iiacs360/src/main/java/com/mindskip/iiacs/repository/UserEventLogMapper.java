package com.mindskip.iiacs.repository;

import com.mindskip.iiacs.domain.UserEventLog;
import com.mindskip.iiacs.domain.other.KeyValue;
import com.mindskip.iiacs.viewmodel.admin.user.UserEventPageRequestVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserEventLogMapper extends BaseMapper<UserEventLog> {

    List<UserEventLog> getUserEventLogByUserId(Integer id);

    List<UserEventLog> page(UserEventPageRequestVM requestVM);

    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
