package org.bibt.data.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.bibt.data.entity.DemoDomain;
import org.bibt.data.service.DemoService;
import org.bibt.data.dto.response.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资源控制器Demo
 *
 * @author zengfanyong
 * @date 2021/1/21 22:54
 */
@RestController
@RequestMapping("/api/v1/demo")
@Api(tags = "样例")
public class DemoController {
    /**
     * 测试服务
     */
    final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * 控制实体
     *
     * @return Result 返回结果
     */
    @RequiresRoles(value = {"admin","common"}, logical = Logical.OR)
    @RequestMapping(value = "/domain", method = RequestMethod.GET)
    @ApiOperation("控制实体")
    public JsonResult domain() {
        Object data = demoService.testDomain();
        if (data != null) {
            return JsonResult.ok(data);
        } else {
            return JsonResult.error();
        }
    }

    /**
     * 控制mybatis插入
     *
     * @param id      编号
     * @param content 内容
     * @return Result 返回结果
     */
    @RequiresRoles(value = {"admin","common"}, logical = Logical.OR)
    @RequestMapping(value = "/mybatis/add", method = RequestMethod.POST)
    @ApiOperation("控制mybatis插入")
    public JsonResult mybatisAdd(@RequestParam int id, @RequestParam String content) {
        DemoDomain demoDomain = new DemoDomain(id, content);
        int res = demoService.mybatisAdd(demoDomain);
        if (res == 0) {
            return JsonResult.ok(null);
        } else {
            return JsonResult.error("插入失败");
        }
    }

    /**
     * 控制mybatis列表
     *
     * @return Result 返回结果
     */
    @RequiresRoles(value = {"admin","common"}, logical = Logical.OR)
    @RequestMapping(value = "/mybatis/list", method = RequestMethod.GET)
    @ApiOperation("控制mybatis列表")
    public JsonResult mybatisList() {
        return JsonResult.ok(demoService.mybatisList());
    }

    /**
     * 控制mybatis查询
     *
     * @return Result 返回结果
     */
    @RequiresRoles(value = {"admin","common"}, logical = Logical.OR)
    @RequestMapping(value = "/mybatis/get", method = RequestMethod.GET)
    @ApiOperation("控制mybatis查询")
    public JsonResult mybatisGet(@RequestParam int id) {
        final Object data = demoService.mybatisGet(id);
        if (data != null) {
            return JsonResult.ok(demoService.mybatisGet(id));
        }else {
            return JsonResult.error("id = " + id + "不存在");
        }
    }

    /**
     * 控制mybatis更新
     *
     * @return Result 返回结果
     */
    @RequiresRoles(value = {"admin","common"}, logical = Logical.OR)
    @RequestMapping(value = "/mybatis/update", method = RequestMethod.PUT)
    @ApiOperation("控制mybatis更新")
    public JsonResult mybatisUpdate(@RequestParam int id, @RequestParam String content) {
        int res = demoService.mybatisUpdate(id, content);
        if (res == 0) {
            return JsonResult.ok(null);
        } else {
            return JsonResult.error("更新失败");
        }
    }

    /**
     * 控制mybatis删除
     *
     * @return Result 返回结果
     */
    @RequiresRoles(value = {"admin","common"}, logical = Logical.OR)
    @RequestMapping(value = "/mybatis/delete", method = RequestMethod.DELETE)
    @ApiOperation("控制mybatis删除")
    public JsonResult mybatisDelete(@RequestParam int id) {
        int res = demoService.mybatisDelete(id);
        if (res == 0) {
            return JsonResult.ok(null);
        } else {
            return JsonResult.error("删除失败");
        }
    }
}
