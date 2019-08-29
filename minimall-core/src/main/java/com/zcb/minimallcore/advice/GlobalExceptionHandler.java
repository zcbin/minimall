package com.zcb.minimallcore.advice;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.util.ResponseUtil;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author zcbin
 * @title: GlobalExceptionHandler
 * @projectName minimall
 * @description: 统一异常处理类
 * @date 2019/8/29 14:45
 */
@ControllerAdvice
public class GlobalExceptionHandler {


		@ExceptionHandler(value = UnauthenticatedException.class)
		@Log(desc = "会话超时", clazz = GlobalExceptionHandler.class)
		public JSONObject unauthenticatedException(UnauthenticatedException e) {
				return ResponseUtil.unloginTimeOut();
		}
		@ExceptionHandler(value = Exception.class)
		@Log(desc = "系统异常", clazz = GlobalExceptionHandler.class)
		public JSONObject exception(Exception e) {
				return ResponseUtil.serious();
		}

}
