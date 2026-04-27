package com.s008.smartcanteen.controller;

import com.s008.smartcanteen.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success("智慧食堂系统启动成功！");
    }
}