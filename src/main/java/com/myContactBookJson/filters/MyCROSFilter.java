package com.myContactBookJson.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyCROSFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET,PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Access-Control-Allow-Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Access-Control-Allow-Headers");
        chain.doFilter(req, res);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

