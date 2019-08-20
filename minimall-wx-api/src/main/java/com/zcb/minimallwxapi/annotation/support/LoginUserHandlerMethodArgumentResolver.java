package com.zcb.minimallwxapi.annotation.support;

import com.zcb.minimalldb.domain.User;
import com.zcb.minimalldb.service.IUserService;
import com.zcb.minimallwxapi.annotation.LoginUser;
import com.zcb.minimallwxapi.service.UserTokenManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    public static final String LOGIN_TOKEN_KEY = "X-Minimall-Token";
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Integer.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    /**
     * 获取登录用户id
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

        String token = request.getHeader(LOGIN_TOKEN_KEY);
//        System.out.println("token:"+token);
        if (token == null || token.isEmpty()) {
            return null;
        }
        return UserTokenManager.getUserId(token);

    }
}
