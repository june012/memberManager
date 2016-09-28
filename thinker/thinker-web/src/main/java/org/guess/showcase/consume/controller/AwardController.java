package org.guess.showcase.consume.controller;

import org.guess.core.web.BaseController;
import org.guess.showcase.consume.model.AwardRecord;
import org.guess.showcase.consume.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wan.peng on 2016/9/27.
 */
@Controller
@RequestMapping("/consume/award")
public class AwardController extends BaseController<AwardRecord>{
    {
        editView = "/consume/award/edit";
        listView = "/consume/award/list";
        showView = "/consume/award/show";
    }

    @Autowired
    private AwardService awardService;
}
