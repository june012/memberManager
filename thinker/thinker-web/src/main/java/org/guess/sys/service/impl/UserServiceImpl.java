package org.guess.sys.service.impl;

import org.guess.core.service.BaseServiceImpl;
import org.guess.core.utils.security.Coder;
import org.guess.showcase.sys.dao.StoreDao;
import org.guess.showcase.sys.model.Store;
import org.guess.showcase.sys.model.StoreUserRelation;
import org.guess.sys.dao.UserDao;
import org.guess.sys.model.Role;
import org.guess.sys.model.User;
import org.guess.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

	@Autowired
	public UserDao userDao;

	@Autowired
	private StoreDao storeDao;

	@Override
	public User findByLoginId(String loginId) {
		return userDao.findUniqueBy("loginId", loginId);
	}


	@Override
	public void save(User user, String[] roleIds , String oldpwd) throws Exception {
		this.save(user);
		//修改密码时，判断
		if (user.getId() != null) {
			if (!oldpwd.equals(user.getPasswd())) {
				user.setPasswd(Coder.encryptMD5(user.getLoginId() + user.getPasswd()));
			}
		} else {
			user.setPasswd(Coder.encryptMD5(user.getLoginId() + user.getPasswd()));
		}
		//插入角色
		if(roleIds != null){
			Set<Role> roles = new HashSet<Role>();
			for (String roleId : roleIds) {
				Role role = new Role();
				role.setId(Long.valueOf(roleId));
				roles.add(role);
			}
			user.setRoles(roles);
		}
	}

	@Override
	public void save(User user, String roleId, String oldpwd, String storeId) throws Exception {
		this.save(user);
		//修改密码时，判断
		if (user.getId() != null) {
			if (!oldpwd.equals(user.getPasswd())) {
				user.setPasswd(Coder.encryptMD5(user.getLoginId() + user.getPasswd()));
			}
		} else {
			user.setPasswd(Coder.encryptMD5(user.getLoginId() + user.getPasswd()));
		}
		//插入角色
		Set<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setId(Long.valueOf(roleId));
		roles.add(role);
		user.setRoles(roles);
		//插入门店
		user.setStoreId(Long.valueOf(storeId));
		if(storeId.equals("0")){
			user.setStoreName("总店");
		}else{
			Store store = storeDao.findUniqueBy("id", Long.valueOf(storeId));
			if(store==null){
				System.out.println("logger:没有此门店");
				return;
			}
			user.setStoreName(store.getStoreName());
		}
		//门店-管理员关系
		StoreUserRelation storeUserRelation = new StoreUserRelation();
		storeUserRelation.setLoginId(user.getLoginId());
		storeUserRelation.setStoreId(Long.valueOf(storeId));
	}
}