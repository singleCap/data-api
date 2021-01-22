package org.bibt.data.mapper;

import org.apache.ibatis.annotations.Param;
import org.bibt.data.domain.DemoDomain;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 映射Demo
 *
 * @author zengfanyong
 * @date 2021/1/21 22:56
 */
@Repository
public interface DemoMapper {
    /**
     * 增加实体
     *
     * @param domain
     *      实体
     * @exception Exception
     *     错误
     */
    void addDomain(DemoDomain domain) throws Exception;

    /**
     * 列表实体
     *
     * @return List<TestDomain>
     *      列表
     */
    List<DemoDomain> listDomain();

    /**
     * 查询实体
     *
     * @param id
     *      编号
     * @return List<TestDomain>
     *      列表
     */
    DemoDomain getDomain(@Param("id") int id);

    /**
     * 更新实体
     *
     * @param id
     *      编号
     * @param content
     *      内容
     * @exception Exception
     *     错误
     */
    void updateDomain(@Param("id") int id, @Param("content") String content) throws Exception;

    /**
     * 删除实体
     *
     * @param id
     *      编号
     * @exception Exception
     *     错误
     */
    void deleteDomain(@Param("id") int id) throws Exception;


//    Integer updateUserById(User user);

//    Integer deleteUserById(Integer id);
}
