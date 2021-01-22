package org.bibt.data.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.bibt.data.domain.TestDomain;
import org.bibt.data.mapper.TestMapper;
import org.bibt.data.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * 测试服务实现类
 *
 * @author ZengFanyong
 * @date 2021/1/21
 */
@Component
@Slf4j
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public Object testDomain() {
        return new TestDomain(111, "hello spring boot");
    }

    @Override
    public int mybatisAdd(TestDomain domain) {
        try {
            testMapper.addDomain(domain);
            return 0;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }

    @Override
    public Object mybatisList() {
        return testMapper.listDomain();
    }

    @Override
    public Object mybatisGet(int id) {
        return testMapper.getDomain(id);
    }

    @Override
    public int mybatisUpdate(int id, String content) {
        try {
            testMapper.updateDomain(id, content);
            return 0;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }

    @Override
    public int mybatisDelete(int id) {
        try {
            testMapper.deleteDomain(id);
            return 0;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }
}
