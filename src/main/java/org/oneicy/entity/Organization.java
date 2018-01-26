package org.oneicy.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Organization implements Serializable {
	private int id;
	private String orgCode;
	private String orgName;
	private Organization organization;

	private Set<User> users;
	private Set<Organization> organizations;


	@Id
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "org_code")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Basic
	@Column(name = "org_name")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_code", referencedColumnName = "org_code")
	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organization")
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organization")
	public Set<Organization> getOrganizations() {
		return this.organizations;
	}

	public void setOrganizations(Set<Organization> organizations) {
		this.organizations =   organizations;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Organization that = (Organization) o;

		if (id != that.id) return false;
		if (orgCode != null ? !orgCode.equals(that.orgCode) : that.orgCode != null) return false;
		if (orgName != null ? !orgName.equals(that.orgName) : that.orgName != null) return false;
		if (organization != null ? !organization.equals(that.organization) : that.organization != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (orgCode != null ? orgCode.hashCode() : 0);
		result = 31 * result + (orgName != null ? orgName.hashCode() : 0);
		result = 31 * result + (organization != null ? organization.hashCode() : 0);
		return result;
	}
}
