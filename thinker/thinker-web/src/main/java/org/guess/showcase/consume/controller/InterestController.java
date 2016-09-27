package org.guess.showcase.consume.controller;

import org.guess.core.web.BaseController;
import org.guess.showcase.consume.model.InterestRecord;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wan.peng on 2016/9/27.
 */
@Controller
@RequestMapping("/consume/interest")
public class InterestController extends BaseController<InterestRecord>{
    {
        editView = "/consume/interest/edit";
        listView = "/consume/interest/list";
        showView = "/consume/interest/show";
    }
}
