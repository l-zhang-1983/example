<%--
  Created by IntelliJ IDEA.
  User: zhangliang
  Date: 2018-02-14
  Time: 09:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
	<title>Error Occurred</title>
</head>
<body>
Error Occurred~
<%--${ex}--%>
<p>
exception: ${requestScope.exception.exceptionMessage}
</p>
</body>
</html>
