package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.Ad;
import com.zcb.minimalldb.domain.Role;
import com.zcb.minimalldb.service.IRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: AdminRoleController
 * @projectName minimall
 * @description: TODO
 * @date 2019/8/10 15:24
 */
@RestController
@RequestMapping(value = "/admin/role")
public class AdminRoleController {
    @Autowired
    private IRolesService rolesService;

    @GetMapping(value = "/list")
    public JSONObject list(String name,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @Sort @RequestParam(defaultValue = "add_time") String sort,
                           @Order @RequestParam(defaultValue = "desc") String order) {
        List<Role> roleList = rolesService.query(name, page, limit, sort, order);
        long total = PageInfo.of(roleList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", roleList);
        return ResponseUtil.ok(data);
    }
    @PostMapping(value = "/create")
    public JSONObject create(@RequestBody Role role) {
        Role role1 = rolesService.findByName(role.getName());
        if (role1 != null) {
            return ResponseUtil.fail(1, "角色已经存在");
        }
        rolesService.add(role);
        return ResponseUtil.ok(role);
    }

    @PostMapping(value = "/update")
    public JSONObject update(@RequestBody Role role) {
        if (rolesService.update(role) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }
    @PostMapping(value = "/delete")
    public JSONObject delete(@RequestBody Role role) {
        Integer id = role.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        rolesService.delete(id);
        return ResponseUtil.ok();
    }

    @GetMapping(value = "/options")
    public JSONObject options() {
        List<Role> roleList = rolesService.queryAll();

        List<Map<String, Object>> options = new ArrayList<>(roleList.size());
        for (Role role : roleList) {
            Map<String, Object> option = new HashMap<>(2);
            option.put("value", role.getId());
            option.put("label", role.getName());
            options.add(option);
        }

        return ResponseUtil.ok(options);
    }
}
