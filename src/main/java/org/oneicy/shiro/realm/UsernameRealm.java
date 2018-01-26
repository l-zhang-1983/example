package org.oneicy.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class UsernameRealm extends AuthorizingRealm {
	Logger logger = LoggerFactory.getLogger(UsernameRealm.class);
//	@Autowired
//	private PermissionService permissionService;
//	@Autowired
//	private CpsDeputyService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal(); // 从这里可以从cas
		logger.debug("shiro username:" + username);
		// 也可以从 Subject subject = SecurityUtils.getSubject();
		// return (String)subject.getPrincipals().asList().get(0); 中取得，因为已经整合后
		// cas 交给了 shiro-cas
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//		authorizationInfo.addRoles(permissionService.getUserRolesByLoginName(username));
		authorizationInfo.addRoles(null);
		return authorizationInfo.getRoles().isEmpty() ? null : authorizationInfo;
	}

	/**
	 * 对当前登录subject进行验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authToken;
//		logger.debug("验证当前Subject token:" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
//		CpsUser user = userService.getUserByLoginName(token.getUsername());
		
//		if (null != user && user.getUserStatus() != CpsConstants.STATUS_LOCK && user.getUserStatus() != CpsConstants.STATUS_REGCODE) {
			SimplePrincipalCollection spc = new SimplePrincipalCollection();
			Map<String, Object> props = new HashMap<String, Object>();
			spc.add(token.getUsername(), getName());
//			props.put("id", user.getId());
			props.put("id", "");
//			props.put("type", user.getUserType());
			props.put("type", "");
//			props.put("name", user.getUserName());
			props.put("name", "");
			spc.add(props, getName());
//			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(spc, user.getPassword());
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(spc, "");
			// this.setSession("currentUser",user);
			return authcInfo;
//		} else {
//			return null;
//		}
	}

}