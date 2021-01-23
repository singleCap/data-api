package org.bibt.data.mapper;

import org.apache.ibatis.annotations.Param;
import org.bibt.data.entity.User;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;

/**
 * 用户映射
 *
 * @author zengfanyong
 * @date 2021/1/21 22:56
 */
@Repository
public interface UserMapper {

    /**
     * 通过用户编号查询用户信息
     *
     * @param userId    用户编号
     * @return User     用户信息
     */
    User selectById(@Param("userId") String userId);

    /**
     * 通过用户编号查询用户权限信息
     *
     * @param userId    用户编号
     * @return LinkedHashMap<String, Object> 用户的权限信息
     */
    LinkedHashMap<String, Object> selectUserPermissionById(@Param("userId") String userId);

    /**
     * 通过用户名称查询用户信息
     *
     * @param userName  用户名称
     * @return User     用户信息
     */
    User selectByName(@Param("userName") String userName);

    /**
     * 通过用户名称查询用户权限信息
     *
     * @param userName  用户名称
     * @return LinkedHashMap<String, Object> 用户权限信息
     */
    LinkedHashMap<String, Object> selectUserPermissionByName(@Param("userName") String userName);

    /**
     * 新增用户信息
     *
     * @param user  用户信息
     */
    void insertUser(@Param("user") User user);
}
