package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimalladminapi.annotation.RequiresPermissionsDesc;
import com.zcb.minimalladminapi.service.LogHelper;
import com.zcb.minimallcore.advice.Log;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.Admin;
import com.zcb.minimalldb.service.IAdminService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: AdminAdminController
 * @projectName minimall
 * @description: 管理员
 * @date 2019/8/10 22:47
 */
@RestController
@RequestMapping(value = "/admin/admin")
public class AdminAdminController {
    @Autowired
    private IAdminService adminService;
    @Autowired
    private LogHelper logHelper;

    /**
     * 列表
     *
     * @param username
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:admin:list")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员管理"}, button = "查询")
    @GetMapping(value = "/list")
    @Log(desc = "管理员列表", clazz = AdminAdminController.class)
    public JSONObject list(String username,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @Sort @RequestParam(defaultValue = "add_time") String sort,
                           @Order @RequestParam(defaultValue = "desc") String order) {
        List<Admin> adminList = adminService.query(username, page, limit, sort, order);
        long total = PageInfo.of(adminList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", adminList);
        return ResponseUtil.ok(data);
    }

    /**
     * 添加
     *
     * @param admin
     * @return
     */
    @RequiresPermissions("admin:admin:create")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员管理"}, button = "添加")
    @PostMapping(value = "/create")
    @Log(desc = "管理员添加", clazz = AdminAdminController.class)
    public JSONObject create(@RequestBody Admin admin) {
        String password = admin.getPassword();
        String username = admin.getUsername();
        int count = adminService.findCountByUsername(null, username);
        if (count > 0) {
            return ResponseUtil.fail(1, "管理员已存在");
        }
        Md5Hash md5Hash = new Md5Hash(password, username, 1024);
        admin.setPassword(String.valueOf(md5Hash));

        adminService.add(admin);
        logHelper.logAuthSucceed("添加管理员", username);
        return ResponseUtil.ok(admin);
    }

    /**
     * 编辑
     *
     * @param admin
     * @return
     */
    @RequiresPermissions("admin:admin:update")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员管理"}, button = "编辑")
    @PostMapping(value = "/update")
    @Log(desc = "管理员编辑", clazz = AdminAdminController.class)
    public JSONObject update(@RequestBody Admin admin) {
        Integer id = admin.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        String username = admin.getUsername();
        int count = adminService.findCountByUsername(id, username);
        if (count > 0) {
            return ResponseUtil.fail(1, "管理员已存在");
        }
        admin.setPassword(null);
        if (adminService.update(admin) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        logHelper.logAuthSucceed("编辑管理员", username);
        return ResponseUtil.ok();
    }

    /**
     * 删除
     *
     * @param admin
     * @return
     */
    @RequiresPermissions("admin:admin:delete")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员管理"}, button = "删除")
    @PostMapping(value = "/delete")
    @Log(desc = "管理员删除", clazz = AdminAdminController.class)
    public JSONObject delete(@RequestBody Admin admin) {
        Integer id = admin.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        adminService.delete(id);
        logHelper.logAuthSucceed("删除管理员", admin.getUsername());
        return ResponseUtil.ok();
    }
}
