package org.guess.showcase.sys;

import org.guess.sys.model.Resource;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wan.peng on 2016/9/22.
 */
public class RoleCategory {
    public Set<Resource> merchantAdminRes;
    public Set<Resource> menberAdminRes;
    public Set<Resource> financeAdminRes;

    private  RoleCategory(){
        merchantAdminRes = new HashSet<Resource>();
        menberAdminRes = new HashSet<Resource>();
        financeAdminRes = new HashSet<Resource>();
        //管理员管理
        Resource r1 = new Resource();
        r1.setId(Long.valueOf(ResouceEnum.SYS_MANAGER.getCode()));
        Resource r2 = new Resource();
        r1.setId(Long.valueOf(ResouceEnum.SYS_MANAGER_ADMIN.getCode()));
        merchantAdminRes.add(r1);
        merchantAdminRes.add(r2);
        //会员管理
        Resource r3 = new Resource();
        r1.setId(Long.valueOf(ResouceEnum.MEMBER_MANAGER.getCode()));
        Resource r4 = new Resource();
        r1.setId(Long.valueOf(ResouceEnum.MEMBER_LIST.getCode()));
        merchantAdminRes.add(r3);
        merchantAdminRes.add(r4);
        //财务管理
        Resource r5 = new Resource();
        r1.setId(Long.valueOf(ResouceEnum.FINANCE_MANAGER.getCode()));
        Resource r6 = new Resource();
        r1.setId(Long.valueOf(ResouceEnum.FINANCE_LIST.getCode()));
        Resource r7 = new Resource();
        r1.setId(Long.valueOf(ResouceEnum.FINANCE_ANALYZE.getCode()));
        merchantAdminRes.add(r5);
        merchantAdminRes.add(r6);
        merchantAdminRes.add(r7);
    }
    private static RoleCategory single=null;
    public static RoleCategory getInstance() {
        if (single == null) {
            single = new RoleCategory();
        }
        return single;
    }

    public Set<Resource> getMerchantAdminRes() {
        return merchantAdminRes;
    }

    public Set<Resource> getMenberAdminRes() {
        return menberAdminRes;
    }

    public Set<Resource> getFinanceAdminRes() {
        return financeAdminRes;
    }
}
