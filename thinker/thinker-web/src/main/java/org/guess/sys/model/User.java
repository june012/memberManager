package org.guess.sys.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.guess.core.Constants;
import org.guess.core.orm.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "SYS_USER")
@JsonIgnoreProperties(value = { "roles","passwd" })
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends IdEntity {

	/** 登录ID */
	@Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$")
	private String loginId;
	/** 密码 */
	private String passwd;
	/** 用户姓名 */
    @NotEmpty
    @Length(min = 2,max = 10)
	private String name;

	/** 性别 */
	private String gender;
	/** 所属门店 */
	@Column(name = "store_id")
	private Long storeId;

	@Column(name = "store_name")
	private String storeName;

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	/** 拥有角色 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "SYS_USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Role> roles = new HashSet<Role>();

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public User() {
		super();
	}

	@JsonIgnore
	public List<Resource> getMenus() {
		List<Resource> recs = new ArrayList<Resource>();
		Set<Resource> allRecs = new HashSet<Resource>();
		for (Role role : this.getRoles()) {
			for (Resource rec : role.getResources()) {
				if (rec.getGrade() == Constants.FIRST_MENU && !recs.contains(rec)) {
					recs.add(rec);
				}
				allRecs.add(rec);
			}
		}
		for (Resource r1 : recs) {
			List<Resource> userRecs = new ArrayList<Resource>();
			for (Resource r2 : allRecs) {
				if (r2.getParent() != null && r2.getGrade() == Constants.SECOND_MENU
						&& r2.getParent().getId() == r1.getId()) {
						userRecs.add(r2);
				}
			}
			r1.setChildRes(userRecs);
		}
		//父菜单排序
		Collections.sort(recs, new Comparator<Resource>(){
			@Override
			public int compare(Resource o1, Resource o2) {
				return o1.getOrderNo()>o2.getOrderNo()?1:-1;
			}
		});
		for (Resource r1 : recs) {
			Collections.sort(r1.getChildRes(), new Comparator<Resource>(){
				@Override
				public int compare(Resource o1, Resource o2) {
					return o1.getOrderNo()>o2.getOrderNo()?1:-1;
				}
			});
		}
		
		return recs;
	}
}
