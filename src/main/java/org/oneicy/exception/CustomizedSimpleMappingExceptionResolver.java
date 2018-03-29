package org.oneicy.exception;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.oneicy.utils.Utils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomizedSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
		// Expose ModelAndView for chosen error view.
		String viewName = determineViewName(exception, request);
		if (Utils.isAjaxRequest(request)) {
			// ajax 请求 使用 JSON 返回
			try {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("application/json");
				PrintWriter writer = response.getWriter();
				/*
				 * JSONObject jo = new JSONObject(); jo.element("success",
				 * false); jo.element("message", ex.getMessage());
				 */
				writer.write(((ExampleException) makeException(exception)).getExceptionMessage());
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			// JSP 返回
			// 如果不是异步请求
			// Apply HTTP status code for error views, if specified.
			// Only apply it if we're processing a top-level request.
			Integer statusCode = determineStatusCode(request, viewName);
			applyStatusCodeIfPossible(request, response, statusCode);

			return getModelAndView(viewName, makeException(exception), request);
		}
	}

	private RuntimeException makeException(Exception exception) {
		ExampleException exampleException = new ExampleException();
		if (exception instanceof UnauthenticatedException) {
			exampleException.setExceptionMessage("没有权限访问吖");
		} else if (exception instanceof  UnauthorizedException) {
			exampleException.setExceptionMessage("还是没有权限访问吖");
		} else {
			exampleException.setExceptionMessage("出现异常: " + exception.getMessage());
		}
		return  exampleException;
	}
}
