package org.geekbang.time.commonmistakes.springpart1.aopmetrics;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping("test")
@RestController
public class TestController {

//    RequestContextHolder

    @Autowired
    private HttpServletRequest request;
    @GetMapping
    public void test() {
        log.info("I am {}",request.hashCode());
        log.info("I am {}",request.toString());

    }
}
