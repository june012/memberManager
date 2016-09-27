package org.guess.showcase.consume.controller;

import org.guess.core.web.BaseController;
import org.guess.showcase.consume.model.CashRecord;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wan.peng on 2016/9/27.
 */
@Controller
@RequestMapping("/consume/cash")
public class CashController extends BaseController<CashRecord>{
    {
        editView = "/consume/cash/edit";
        listView = "/consume/cash/list";
        showView = "/consume/cash/show";
    }
}
