package org.guess.sys.controller;

import org.guess.core.web.BaseController;
import org.guess.sys.model.Store;
import org.guess.sys.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wan.peng on 2016/9/25.
 */
@Controller
@RequestMapping("/sys/store/")
public class StoreController extends BaseController<Store>{
    {
        editView = "/sys/store/edit";
        listView = "/sys/store/list";
        showView = "/sys/store/show";
    }
    @Autowired
    private StoreService storeService;

}
