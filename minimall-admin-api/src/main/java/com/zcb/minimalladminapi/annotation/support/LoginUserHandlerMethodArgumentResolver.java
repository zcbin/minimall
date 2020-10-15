package com.zcb.minimalladminapi.annotation.support;

import com.zcb.minimalladminapi.annotation.LoginUser;
import com.zcb.minimalldb.domain.Admin;
import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.service.IAdminService;
import com.zcb.minimalldb.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private IAdminService adminService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Integer.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    /**
     * 获取登录用户id
     *
     * @param parameter
     * @param container
     * @param request
     * @param factory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        if (subject == null || subject.getPrincipal() == null) {
            return null;
        }
        List<Admin> adminList = adminService.findByUsername((String) subject.getPrincipal());
        if (adminList == null || adminList.size() != 1) {
            return null;
        }
        Admin admin = adminList.get(0);
        if (admin != null) {
            return admin.getId();
        }
        return null;

    }
}
