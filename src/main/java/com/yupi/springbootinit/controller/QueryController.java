package com.yupi.springbootinit.controller;

import cn.hutool.json.JSONUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@Controller
@RequestMapping("/query")
@Profile({"dev","local"})
public class QueryController {
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @GetMapping("/add")
    public void add(String name){
        CompletableFuture.runAsync(()->{
            System.out.println("执行人:"+Thread.currentThread().getName()+"任务执行中:"+name);
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },threadPoolExecutor);
    }

    @GetMapping("/get")
    public String get(){
        HashMap<String, Object> map = new HashMap<>();
        int size = threadPoolExecutor.getQueue().size();
        map.put("队列长度:",size);
        long count = threadPoolExecutor.getTaskCount();
        map.put("任务总数:",count);
        long completedTaskCount = threadPoolExecutor.getCompletedTaskCount();
        map.put("已经完成任务数:",completedTaskCount);
        int activeCount = threadPoolExecutor.getActiveCount();
        map.put("当前线程池里的线程数:",activeCount);
        return JSONUtil.toJsonStr(map);

    }
}
