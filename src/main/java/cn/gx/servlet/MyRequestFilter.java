package cn.gx.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by always on 16/3/25.
 */
@WebFilter(filterName = "MyRequestFilter")
public class MyRequestFilter implements Filter {
    private FilterConfig config;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest httpReq= (HttpServletRequest) req;

        String remoteUser = httpReq.getRemoteUser();

        if (remoteUser!=null){
            System.out.println("remoteUer:"+remoteUser);
        }

        chain.doFilter(req, resp);//调用过滤器或者 Servlet
    }

    public void init(FilterConfig config) throws ServletException {
        this.config=config;
    }

}
