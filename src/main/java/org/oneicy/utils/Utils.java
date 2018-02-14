package org.oneicy.utils;

import javax.servlet.http.HttpServletRequest;

public class Utils {

	public static boolean isAjaxRequest(HttpServletRequest httpRequest) {
		if (null != httpRequest.getHeader("accept"))
			if (httpRequest.getHeader("accept").contains("application/json")) return true;
		if (null != httpRequest.getHeader("X-Requested-With"))
			if (httpRequest.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest")) return true;
		return false;
	}
}
