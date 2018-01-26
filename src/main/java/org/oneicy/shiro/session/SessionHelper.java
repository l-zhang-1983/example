/**
 *
 * @ Title: SessionHelper.java
 * @ Package: org.cps.shiro.session
 * @ Description: TODO
 * @ author: Liang
 * @ date: 2016年5月4日 下午6:29:04
 * @ version:
 *
 */
package org.oneicy.shiro.session;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;

/**
 *
 * @ClassName: SessionHelper
 * @Description: TODO
 * @author: Liang
 * @date: 2016年5月4日 下午6:29:04
 *
 *
 */
public class SessionHelper {
	private static SessionDAO sessionDao;
	
	public static Collection<Session> getAuthenticatedSessions() {
		Collection<Session> sessions = new ArrayList<Session>();
		Collection<Session> activeSessions = getSessionDao().getActiveSessions();
		for (Session session : activeSessions) {
			if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) != null) {
				sessions.add(session);
			}
		}
		return sessions; 
	}
	
	public static SessionDAO getSessionDao() {
		return sessionDao;
	}

	public static void setSessionDao(SessionDAO sessionDao) {
		SessionHelper.sessionDao = sessionDao;
	}

}
