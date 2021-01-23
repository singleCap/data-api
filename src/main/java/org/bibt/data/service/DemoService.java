package org.bibt.data.service;

import org.bibt.data.entity.DemoDomain;

/**
 * 服务Demo
 *
 * @author zengfanyong
 * @date 2021/1/21 22:57
 */
public interface DemoService {

    /**
     * 测试domain
     *
     * @return Object
     * 实体
     */
    Object testDomain();

    /**
     * mybatis插入
     *
     * @return Object
     * 实体
     */
    int mybatisAdd(DemoDomain domain);

    /**
     * mybatis列表
     *
     * @return Object
     * 实体
     */
    Object mybatisList();

    /**
     * mybatis查询
     *
     * @return Object
     * 实体
     */
    Object mybatisGet(int id);

    /**
     * mybatis更新
     *
     * @param id      编号
     * @param content 内容
     * @return Object
     * 实体
     */
    int mybatisUpdate(int id, String content);

    /**
     * mybatis删除
     *
     * @param id 编号
     * @return Object
     * 实体
     */
    int mybatisDelete(int id);
}