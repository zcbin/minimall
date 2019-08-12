package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimalladminapi.annotation.RequiresPermissionsDesc;
import com.zcb.minimalladminapi.util.Permission;
import com.zcb.minimalladminapi.util.PermissionUtil;
import com.zcb.minimalladminapi.vo.PermVo;
import com.zcb.minimallcore.util.ParseJsonUtil;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.Ad;
import com.zcb.minimalldb.domain.Role;
import com.zcb.minimalldb.service.IPermissionService;
import com.zcb.minimalldb.service.IRolesService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Autowired
    private IPermissionService permissionService;

    @RequiresPermissions("admin:role:list")
    @RequiresPermissionsDesc(menu={"系统管理" , "角色管理"}, button="查询")
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
    @RequiresPermissions("admin:role:create")
    @RequiresPermissionsDesc(menu={"系统管理" , "角色管理"}, button="添加")
    @PostMapping(value = "/create")
    public JSONObject create(@RequestBody Role role) {
        Role role1 = rolesService.findByName(role.getName());
        if (role1 != null) {
            return ResponseUtil.fail(1, "角色已经存在");
        }
        rolesService.add(role);
        return ResponseUtil.ok(role);
    }

    @RequiresPermissions("admin:role:update")
    @RequiresPermissionsDesc(menu={"系统管理" , "角色管理"}, button="编辑")
    @PostMapping(value = "/update")
    public JSONObject update(@RequestBody Role role) {
        if (rolesService.update(role) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    @RequiresPermissions("admin:role:delete")
    @RequiresPermissionsDesc(menu={"系统管理" , "角色管理"}, button="删除")
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
    @Autowired
    private ApplicationContext context;
    private List<PermVo> systemPermissions = null;
    private Set<String> systemPermissionsString = null;

    private List<PermVo> getSystemPermissions(){
        final String basicPackage = "com.zcb.minimalladminapi";
        if(systemPermissions == null){
            List<Permission> permissions = PermissionUtil.listPermission(context, basicPackage);
            systemPermissions = PermissionUtil.listPermVo(permissions);
            systemPermissionsString = PermissionUtil.listPermissionString(permissions);
        }
        return systemPermissions;
    }

    private Set<String> getAssignedPermissions(Integer roleId){
        // 这里需要注意的是，如果存在超级权限*，那么这里需要转化成当前所有系统权限。
        // 之所以这么做，是因为前端不能识别超级权限，所以这里需要转换一下。
        Set<String> assignedPermissions = null;
        if(permissionService.checkSuperPermission(roleId)){
            getSystemPermissions();
            assignedPermissions = systemPermissionsString;
        }
        else{
            assignedPermissions = permissionService.queryByRoleId(roleId);
        }

        return assignedPermissions;
    }

    /**
     * 管理员的权限情况
     *
     * @return 系统所有权限列表和管理员已分配权限
     */
    @RequiresPermissions("admin:role:permission:get")
    @RequiresPermissionsDesc(menu={"系统管理" , "角色管理"}, button="权限详情")
    @GetMapping("/permissions")
    public JSONObject getPermissions(Integer roleId) {
        List<PermVo> systemPermissions = getSystemPermissions();
        Set<String> assignedPermissions = getAssignedPermissions(roleId);

        Map<String, Object> data = new HashMap<>();
        data.put("systemPermissions", systemPermissions);
        data.put("assignedPermissions", assignedPermissions);
        return ResponseUtil.ok(data);
    }


    /**
     * 更新管理员的权限
     *
     * @param body
     * @return
     */
    @RequiresPermissions("admin:role:permission:update")
    @RequiresPermissionsDesc(menu={"系统管理" , "角色管理"}, button="权限变更")
    @PostMapping("/permissions")
    public Object updatePermissions(@RequestBody String body) {
        Integer roleId = ParseJsonUtil.parseInteger(body, "roleId");
        List<String> permissions = ParseJsonUtil.parseStringList(body, "permissions");
        if(roleId == null || permissions == null){
            return ResponseUtil.badArgument();
        }

        // 如果修改的角色是超级权限，则拒绝修改。
        if(permissionService.checkSuperPermission(roleId)){
            return ResponseUtil.fail(1, "当前角色的超级权限不能变更");
        }

        // 先删除旧的权限，再更新新的权限
        permissionService.deleteByRoleId(roleId);
        for(String permission : permissions){
            com.zcb.minimalldb.domain.Permission permission1 = new com.zcb.minimalldb.domain.Permission();
            permission1.setRoleId(roleId);
            permission1.setPermission(permission);
            permissionService.add(permission1);
        }
        return ResponseUtil.ok();
    }
}
