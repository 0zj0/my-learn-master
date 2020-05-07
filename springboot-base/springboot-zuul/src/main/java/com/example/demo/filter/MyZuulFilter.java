package com.example.demo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author 张杰
 * @date 2020/5/7 20:07
 */
@Component
@Slf4j
public class MyZuulFilter extends ZuulFilter {

    private static final String PRE_TYPE = "pre";
    private static final String ROUTE_TYPE = "route";
    private static final String POST_TYPE = "post";
    private static final String ERROR_TYPE = "error";

    /**
     * pre 过滤器：他是在请求路由到具体的服务之前执行的，这种类型的过滤器可以做安全验证，例如身份验证、参数验证等
     * route 过滤器： 它用于将请求路由到集体的微服务实例。在默认情况下，它使用Http Client进行网络请求
     * post 过滤器： 它是在请求已被路由到微服务后执行的。一般情况下，用作收集统计信息，指标，以及将响应传输到客户端。
     * error 过滤器：它是在其他过滤器发生错误时执行的。
     * @return
     */

    /**
     * Type ： 决定过滤器在哪个阶段起作用
     * @return
     */
    @Override
    public String filterType() {
        log.info("MyZuulFilter--filterType--pre");
        return PRE_TYPE;
    }

    /**
     * 规定了过滤器的执行顺序，order 越小，越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        log.info("MyZuulFilter--filterOrder--0");
        return 0;
    }

    /**
     * Filter 执行所需的条件
     * @return
     */
    @Override
    public boolean shouldFilter() {
        log.info("MyZuulFilter--shouldFilter--true");
        return true;
    }

    /**
     * 如果符合执行条件，则执行Action 逻辑代码
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        log.info("MyZuulFilter--run");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Object accessToken = request.getParameter("token");
        if(accessToken == null){
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);

            try {
                ctx.getResponse().getWriter().write("token is empty");
            } catch (IOException e) {
                return null;
            }
        }
        log.info("ok");
        return null;
    }
}
