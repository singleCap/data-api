package org.bibt.data.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户角色关系映射
 *
 * @author zengfanyong
 * @date 2021/1/21 22:56
 */
@Repository
public interface UserRoleMapper {
    /**
     * 新增用户和角色关系
     *
     * @param userId    用户编号
     * @param roleId    角色编号
     */
    void insert(@Param("userId") String userId, @Param("roleId") Integer roleId);

}
