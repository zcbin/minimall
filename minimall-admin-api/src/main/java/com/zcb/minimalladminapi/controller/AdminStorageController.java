package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimalladminapi.annotation.RequiresPermissionsDesc;
import com.zcb.minimallcore.storage.StorageService;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.Storage;
import com.zcb.minimalldb.service.IStorageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: AdminStorageController
 * @projectName minimall
 * @description: 对象存储
 * @date 2019/7/25 20:10
 */
@RestController
@RequestMapping(value = "/admin/storage")
public class AdminStorageController {
    @Autowired
    private StorageService storageService;
    @Autowired
    private IStorageService iStorageService;

    @RequiresPermissions("admin:storage:list")
    @RequiresPermissionsDesc(menu={"系统管理" , "对象存储"}, button="查询")
    @GetMapping("/list")
    public JSONObject list(String key, String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<Storage> storageList = iStorageService.query(key, name, page, limit, sort, order);
        long total = PageInfo.of(storageList).getTotal();
        Map<String, Object> data = new HashMap<>(2);
        data.put("total", total);
        data.put("items", storageList);

        return ResponseUtil.ok(data);
    }
    @RequiresPermissions("admin:storage:create")
    @RequiresPermissionsDesc(menu={"系统管理" , "对象存储"}, button="上传")
    @PostMapping("/create")
    public JSONObject create(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        Storage storage = storageService.store(file.getInputStream(), file.getSize(), file.getContentType(), originalFilename);
        return ResponseUtil.ok(storage);
    }
    @RequiresPermissions("admin:storage:update")
    @RequiresPermissionsDesc(menu={"系统管理" , "对象存储"}, button="编辑")
    @PostMapping("/update")
    public JSONObject update(@RequestBody Storage storage) throws IOException {
       Integer id = storage.getId();
       if (id == null) {
           return ResponseUtil.badArgument();
       }
       if (iStorageService.update(storage) == 0) {
           return ResponseUtil.updatedDataFailed();
       }
       return ResponseUtil.ok(storage);

    }
    @RequiresPermissions("admin:storage:delete")
    @RequiresPermissionsDesc(menu={"系统管理" , "对象存储"}, button="删除")
    @PostMapping("/delete")
    public JSONObject delete(@RequestBody Storage storage) throws IOException {
        String key = storage.getKey();
        if (StringUtils.isEmpty(key)) {
            return ResponseUtil.badArgument();
        }
        iStorageService.deleteByKey(key);
        storageService.delete(key);
        return ResponseUtil.ok();

    }
}
