package com.wjl.o2o.dao;

import com.wjl.o2o.entity.UserAwardMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserAwardMapDao {
    /**
     * 根据传入的查询条件分页返回用户兑换奖品记录的信息列表
     * @param userAwardCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<UserAwardMap> queryUserAwardMapList(@Param("userAwardCondition")UserAwardMap userAwardCondition, @Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);

    /**
     * 配合queryUserAwardMapList返回相同条件下的兑奖记录数量
     * @param userAwardCondition
     * @return
     */
    int queryUserAwardMapCount(@Param("userAwardCondition")UserAwardMap userAwardCondition);

    /**
     * 根据userAwardId返回某一条奖品兑换的信息
     * @param userAwardId
     * @return
     */
    UserAwardMap queryUserAwardMapById(long userAwardId);

    /**
     * 添加一条奖品信息
     * @param userAwardMap
     * @return
     */
    int insertUserAwardMap(UserAwardMap userAwardMap);

    /**
     * 更新奖品兑换信息，主要更新奖品领取状态
     * @param userAwardMap
     * @return
     */
    int updateUserAwardMap(UserAwardMap userAwardMap);

    /**
     * 删除
     * @return
     */
    int deleteUserAwardMap();
}
