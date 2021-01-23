package org.bibt.data.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.bibt.data.entity.DemoDomain;
import org.bibt.data.mapper.DemoMapper;
import org.bibt.data.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务实现类Demo
 *
 * @author ZengFanyong
 * @date 2021/1/21
 */
@Service
@Slf4j
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoMapper demoMapper;

    @Override
    public Object testDomain() {
        return new DemoDomain(111, "hello spring boot");
    }

    @Override
    public int mybatisAdd(DemoDomain domain) {
        try {
            demoMapper.addDomain(domain);
            return 0;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }

    @Override
    public Object mybatisList() {
        return demoMapper.listDomain();
    }

    @Override
    public Object mybatisGet(int id) {
        return demoMapper.getDomain(id);
    }

    @Override
    public int mybatisUpdate(int id, String content) {
        try {
            demoMapper.updateDomain(id, content);
            return 0;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }

    @Override
    public int mybatisDelete(int id) {
        try {
            demoMapper.deleteDomain(id);
            return 0;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }
}
