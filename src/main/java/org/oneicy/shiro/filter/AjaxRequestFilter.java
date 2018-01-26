/**
 *
 * @ Title: AjaxRequestFilter.java
 * @ Package: org.cps.shiro.filter
 * @ Description: TODO
 * @ author: Liang
 * @ date: 2016年4月27日 下午4:07:33
 * @ version:
 *
 */
package org.oneicy.shiro.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @ClassName: AjaxRequestFilter
 * @Description: TODO
 * @author: Liang
 * @date: 2016年4月27日 下午4:07:33
 *
 *
 */
public class AjaxRequestFilter extends FormAuthenticationFilter {

	@Value("#{configProperties['sessionIsInvalid']}")
	private String sessionIsInvalid;
	
	/**
	 * shrio-context.xml 中配置的名为 authc 的 filter 的实现为 {@link FormAuthenticationFilter} <br>
	 * 但其中并没有处理 ajax 请求 所以当 session 超时后 再次发出 ajax 请求  {@link FormAuthenticationFilter} 会将页面重定向到登录页面 <br> 
	 * 前端页面得到这个响应后无法处理 只能解析为 parseerror <br>
	 * 以上过程在 {@link FormAuthenticationFilter} 的 {@link #onAccessDenied(javax.servlet.ServletRequest, javax.servlet.ServletResponse) onAccessDenied} 方法中实现 <br>
	 * 所以在此处继承 {@link FormAuthenticationFilter} 重构该方法 加入对于 ajax 请求的处理
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		Subject subject = getSubject(request, response);
		if (!subject.isAuthenticated()) {
			// 当前用户未经认证
			HttpServletRequest httpRequest = WebUtils.toHttp(request);
			HttpServletResponse httpResponse = WebUtils.toHttp(response);
			
			if ((httpRequest.getHeader("accept").indexOf("application/json") > -1 || (
					httpRequest.getHeader("X-Requested-With") != null
					&& httpRequest.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest"))
			)) {
				// 如果是 Ajax 请求
				try {
					httpResponse.setContentType("application/json;charset=UTF-8");
					httpResponse.setCharacterEncoding("UTF-8");
					PrintWriter writer = response.getWriter();
					writer.write(sessionIsInvalid);
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return false;
			} else {
				return super.onAccessDenied(request, response, mappedValue);
			}
		} else {
			return super.onAccessDenied(request, response, mappedValue);
		}
	}
}
