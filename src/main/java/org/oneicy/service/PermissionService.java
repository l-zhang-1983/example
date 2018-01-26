/*@(#)PermissionService.java   2015-12-17 
 * Copy Right 2015 Bank of Communications Co.Ltd.
 * All Copyright Reserved
 */

package org.oneicy.service;

import org.oneicy.dao.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Document PermissionService
 * <p>
 * 
 * @version 1.0.0,2015-12-17
 * @since 1.0.0
 */
@Service
public class PermissionService {
	@Autowired
	private OrganizationRepository userDao;
	
}
