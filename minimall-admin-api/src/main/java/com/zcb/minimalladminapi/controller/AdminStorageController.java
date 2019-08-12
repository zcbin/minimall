package com.zcb.minimalladminapi.controller;

import com.zcb.minimalladminapi.annotation.RequiresPermissionsDesc;
import com.zcb.minimallcore.storage.StorageService;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.domain.Storage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @RequiresPermissions("admin:storage:create")
    @RequiresPermissionsDesc(menu={"系统管理" , "对象存储"}, button="上传")
    @PostMapping("/create")
    public Object create(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        Storage storage = storageService.store(file.getInputStream(), file.getSize(), file.getContentType(), originalFilename);
        return ResponseUtil.ok(storage);
    }
}
