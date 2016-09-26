package org.guess.sys.service;

import org.guess.core.service.BaseService;
import org.guess.sys.model.User;

public interface UserService extends BaseService<User, Long>{

	User findByLoginId(String loginId);
	
	void save(User user , String[] roleIds , String oldpwd) throws Exception;

	void save(User user , String roleId , String oldpwd,String storeId) throws Exception;
}
