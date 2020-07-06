package com.shop.pss.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.pss.common.RestResult;
import com.shop.pss.enums.ResultEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:01
 */
@Component
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 
     * @param httpServletRequest
     * @param resp
     * @param e
     * @throws IOException
     * 
     * @creator 王瑞
     * @createtime 2019/2/26 19:08
     * @description: 
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse resp,
                       AccessDeniedException e) throws IOException {

        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        RestResult error = new RestResult(ResultEnum.ROLE_ERROR);
        out.write(new ObjectMapper().writeValueAsString(error));
        out.flush();
        out.close();
    }
}
