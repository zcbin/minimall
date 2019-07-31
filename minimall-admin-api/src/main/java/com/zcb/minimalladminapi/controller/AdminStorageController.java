package com.zcb.minimalladminapi.controller;

import com.zcb.minimallcore.storage.StorageService;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.domain.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author zcbin
 * @title: AdminStorageController
 * @projectName minimall
 * @description: TODO
 * @date 2019/7/25 20:10
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/admin/storage")
public class AdminStorageController {
    @Autowired
    private StorageService storageService;

    @PostMapping("/create")
    public Object create(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        Storage storage = storageService.store(file.getInputStream(), file.getSize(), file.getContentType(), originalFilename);
        return ResponseUtil.ok(storage);
    }
}
