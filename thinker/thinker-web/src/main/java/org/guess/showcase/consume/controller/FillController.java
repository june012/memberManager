package org.guess.showcase.consume.controller;

import org.guess.core.web.BaseController;
import org.guess.showcase.consume.model.FillRecord;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wan.peng on 2016/9/27.
 */
@Controller
@RequestMapping("/consume/fill")
public class FillController extends BaseController<FillRecord>{
    {
        editView = "/consume/fill/edit";
        listView = "/consume/fill/list";
        showView = "/consume/fill/show";
    }
}
