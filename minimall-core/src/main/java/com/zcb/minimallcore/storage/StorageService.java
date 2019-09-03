package com.zcb.minimallcore.storage;


import com.zcb.minimallcore.util.CharUtil;
import com.zcb.minimalldb.domain.Storage;
import com.zcb.minimalldb.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * 提供存储服务类，所有存储服务均由该类对外提供
 */
public class StorageService {
    private String active;
    private IStorage iStorage;
    @Autowired
    private IStorageService storageService;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public IStorage getStorage() {
        return iStorage;
    }

    public void setStorage(IStorage iStorage) {
        this.iStorage = iStorage;
    }

    /**
     * 存储一个文件对象
     *
     * @param inputStream   文件输入流
     * @param contentLength 文件长度
     * @param contentType   文件类型
     * @param fileName      文件索引名
     */
    public Storage store(InputStream inputStream, long contentLength, String contentType, String fileName) {
        String key = generateKey(fileName);
        iStorage.store(inputStream, contentLength, contentType, key);

        String url = generateUrl(key);
        Storage storageInfo = new Storage();
        storageInfo.setName(fileName);
        storageInfo.setSize((int) contentLength);
        storageInfo.setType(contentType);
        storageInfo.setKey(key);
        storageInfo.setUrl(url);
        storageService.add(storageInfo);

        return storageInfo;
    }

    private String generateKey(String originalFilename) {
        int index = originalFilename.lastIndexOf('.');
        String suffix = originalFilename.substring(index);

        String key = null;
        Storage storageInfo = null;

        do {
            key = CharUtil.getRandomString(20) + suffix;
            storageInfo = storageService.findByKey(key);
        }
        while (storageInfo != null);

        return key;
    }

    public Stream<Path> loadAll() {
        return iStorage.loadAll();
    }

    public Path load(String keyName) {
        return iStorage.load(keyName);
    }

    public Resource loadAsResource(String keyName) {
        return iStorage.loadAsResource(keyName);
    }

    public void delete(String keyName) {
        iStorage.delete(keyName);
    }

    private String generateUrl(String keyName) {
        return iStorage.generateUrl(keyName);
    }
}
