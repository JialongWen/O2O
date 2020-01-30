package com.wjl.o2o.dao;

import com.wjl.o2o.entity.Award;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AwardDao {
    /**
     * 分页查询AwardList
     * @param awardCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Award> queryAwardList(@Param("awardCondition")Award awardCondition,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);

    /**
     * 同样条件查询数量
     * @param awardcondition
     * @return
     */
    int queryAwardCount(@Param("awardCondition")Award awardcondition);

    /**
     * 根据id查询奖品信息
     * @param awardId
     * @return
     */
    Award queryAwardByAwardId(long awardId);

    /**
     * 增加
     * @param award
     * @return
     */
    int insertAard(Award award);

    /**
     * 更新
     * @param award
     * @return
     */
    int updateAward(Award award);

    /**
     * 删除奖品信息
     * @param awardId
     * @param shopId
     * @return
     */
    int deleteAward(@Param("awardId")long awardId,@Param("shopId")long shopId);
}
