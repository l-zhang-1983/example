/*@(#)PermissionService.java   2015-12-17 
 * Copy Right 2015 Bank of Communications Co.Ltd.
 * All Copyright Reserved
 */

package org.oneicy.service;

import org.oneicy.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
	@Autowired
	private OrganizationRepository organizationRepository;
	
}
