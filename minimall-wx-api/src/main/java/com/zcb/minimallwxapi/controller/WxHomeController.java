package com.zcb.minimallwxapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.domain.Ad;
import com.zcb.minimalldb.domain.Category;
import com.zcb.minimalldb.domain.Goods;
import com.zcb.minimalldb.service.IAdService;
import com.zcb.minimalldb.service.ICategoryService;
import com.zcb.minimalldb.service.IGoodsService;
import com.zcb.minimalldb.service.impl.GoodsServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@RestController
@RequestMapping(value = "/wx/home")
/**
 * 主页
 */
public class WxHomeController {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private IGoodsService goodsService; //物品
    @Autowired
    private IAdService adService; //广告
    @Autowired
    private ICategoryService categoryService; //分类

    //private final static ArrayBlockingQueue<Runnable> QUEUE = new ArrayBlockingQueue<>(9); //有界阻塞队列，fifo
    /*RejectedExecutionHandler （饱和策略）：当队列和线程池都满了，说明线程池处于饱和状态，那么必须采取一种策略还处理新提交的任务。它可以有如下四个选项：
    AbortPolicy:直接抛出异常，默认情况下采用这种策略
    CallerRunsPolicy:只用调用者所在线程来运行任务
    DiscardOldestPolicy:丢弃队列里最近的一个任务，并执行当前任务
    DiscardPolicy:不处理，丢弃掉*/
    //private final static RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();
    //private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(9, 9, 1000, TimeUnit.MILLISECONDS, QUEUE, HANDLER);

    @RequestMapping(value = "/index")
    public JSONObject index() {
        //创建一个定长的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(9); //return ThreadPoolExecutor

        Callable<List> goods = () -> goodsService.queryByHot(0, 10);
        Callable<List> ads = () -> adService.queryIndex();
        Callable<List> categoriesL1 = () -> categoryService.queryChannel("L1");
        Callable<List> categoriesL3 = () -> categoryService.queryChannel("L3");

        FutureTask<List> goodsTask = new FutureTask<>(goods);
        FutureTask<List> adsTask = new FutureTask<>(ads);
        FutureTask<List> categoriesL1Task = new FutureTask<>(categoriesL1);
        FutureTask<List> categoriesL3Task = new FutureTask<>(categoriesL3);

        executorService.submit(goodsTask);
        executorService.submit(adsTask);
        executorService.submit(categoriesL1Task);
        executorService.submit(categoriesL3Task);

        //List<Goods> goods = goodsService.queryByHot(0,10);
//        List<Ad> ads = adService.queryIndex();
//        List<Category> categoriesL1 = categoryService.queryChannel("L1");
//        List<Category> categoriesL3 = categoryService.queryChannel("L3");
        Map<String, Object> data = new HashMap<>();
        try {
            data.put("hotGoodsList", goodsTask.get());
            data.put("banner", adsTask.get());
            data.put("channel", categoriesL1Task.get());
            data.put("category", categoriesL3Task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return ResponseUtil.ok(data);
    }
}
