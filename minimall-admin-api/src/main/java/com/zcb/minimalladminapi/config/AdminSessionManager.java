package com.zcb.minimalladminapi.config;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * 自定义sessionId获取
 */
public class AdminSessionManager extends DefaultWebSessionManager {

    private static final String AUTHORIZATION = "X-Minimall-Admin-Token";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public AdminSessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        //如果请求头中有 Authorization 则其值为sessionId
        if (!StringUtils.isEmpty(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            //System.out.println("Authorization="+id);
            return id;
        } else {
            //否则按默认规则从cookie取sessionId
            //System.out.println("sessionId="+super.getSessionId(request, response));
            return super.getSessionId(request, response);
        }
    }
}

