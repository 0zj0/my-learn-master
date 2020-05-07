package com.example.demo.remote;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhangjie
 * @date 2020/5/7 11:33
 */
@FeignClient(value = "springboot-client",
        //configuration = FeignConfig.class,
        fallbackFactory = EurekaClientFeign.EurekaClientHystrixFactory.class)
public interface EurekaClientFeign {

    @GetMapping("/hi")
    String sayHi(@RequestParam(value = "name") String name);

    @GetMapping("/hello")
    String sayHello(@RequestParam(value = "name") String name);

    @Component
    class EurekaClientHystrixFactory implements FallbackFactory<EurekaClientFeign>{

        @Override
        public EurekaClientFeign create(Throwable throwable) {
            return new EurekaClientFeign() {

                @Override
                public String sayHi(String name) {
                    return "hi error";
                }

                @Override
                public String sayHello(String name) {
                    return "hello error";
                }
            };
        }
    }
}
