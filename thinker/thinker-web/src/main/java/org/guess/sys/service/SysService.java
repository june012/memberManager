package org.guess.sys.service;

import org.guess.sys.model.User;

/**
 * Created by wan.peng on 2016/9/25.
 */
public interface SysService{

    void save(User user , String roleId , String oldpwd,String storeId) throws Exception;


}
