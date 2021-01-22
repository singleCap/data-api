package org.bibt.data.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bibt.data.domain.TestDomain;
import org.bibt.data.service.TestService;
import org.bibt.data.util.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试资源控制器
 *
 * @author zengfanyong
 * @date 2021/1/21 22:54
 */
@RestController
@RequestMapping("/api/v1/test")
@Api(tags = "测试")
public class TestController {
    /** 测试服务 */
    final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    /**
     * 控制实体
     *
     * @return Result
     *      返回结果
     */
    @RequestMapping(value = "/domain", method = RequestMethod.GET)
    @ApiOperation("控制实体")
    public Result domain() {
        Object data = testService.testDomain();
        if (data != null) {
            return Result.ok(data);
        }else {
            return Result.error();
        }
    }

    /**
     * 控制mybatis插入
     *
     * @param id
     *      编号
     * @param content
     *      内容
     * @return Result
     *      返回结果
     */
    @RequestMapping(value = "/mybatis/add", method = RequestMethod.POST)
    @ApiOperation("控制mybatis插入")
    public Result mybatisAdd(@RequestParam int id, @RequestParam String content) {
        TestDomain domain = new TestDomain(id, content);
        int res = testService.mybatisAdd(domain);
        if (res == 0) {
            return Result.ok(null);
        }else {
            return Result.error();
        }
    }

    /**
     * 控制mybatis列表
     *
     * @return Result
     *      返回结果
     */
    @RequestMapping(value = "/mybatis/list", method = RequestMethod.GET)
    @ApiOperation("控制mybatis列表")
    public Result mybatisList() {
        Object data = testService.mybatisList();
        if (data != null) {
            return Result.ok(data);
        }else {
            return Result.error();
        }
    }

    /**
     * 控制mybatis查询
     *
     * @return Result
     *      返回结果
     */
    @RequestMapping(value = "/mybatis/get", method = RequestMethod.GET)
    @ApiOperation("控制mybatis查询")
    public Result mybatisGet(@RequestParam int id) {
        Object data = testService.mybatisGet(id);
        if (data != null) {
            return Result.ok(data);
        }else {
            return Result.error();
        }
    }

    /**
     * 控制mybatis更新
     *
     * @return Result
     *      返回结果
     */
    @RequestMapping(value = "/mybatis/update", method = RequestMethod.PUT)
    @ApiOperation("控制mybatis更新")
    public Result mybatisUpdate(@RequestParam int id, @RequestParam String content) {
        int res = testService.mybatisUpdate(id, content);
        if (res == 0) {
            return Result.ok(null);
        }else {
            return Result.error();
        }
    }

    /**
     * 控制mybatis删除
     *
     * @return Result
     *      返回结果
     */
    @RequestMapping(value = "/mybatis/delete", method = RequestMethod.DELETE)
    @ApiOperation("控制mybatis删除")
    public Result mybatisDelete(@RequestParam int id) {
        int res = testService.mybatisDelete(id);
        if (res == 0) {
            return Result.ok(null);
        }else {
            return Result.error();
        }
    }
}
