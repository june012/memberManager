package org.guess.showcase.sys.controller;

import org.guess.core.web.BaseController;
import org.guess.showcase.sys.model.Store;
import org.guess.showcase.sys.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wan.peng on 2016/9/25.
 */
@Controller
@RequestMapping("/sysManager/store/")
public class StoreController extends BaseController<Store>{
    {
        editView = "/sysManager/store/edit";
        listView = "/sysManager/store/list";
        showView = "/sysManager/store/show";
    }
    @Autowired
    private StoreService storeService;

}
