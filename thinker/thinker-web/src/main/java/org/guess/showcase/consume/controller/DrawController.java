package org.guess.showcase.consume.controller;

import org.guess.core.web.BaseController;
import org.guess.showcase.consume.model.DrawRecord;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wan.peng on 2016/9/27.
 */
@Controller
@RequestMapping("/consume/draw")
public class DrawController extends BaseController<DrawRecord>{
    {
        editView = "/consume/draw/edit";
        listView = "/consume/draw/list";
        showView = "/consume/draw/show";
    }
}
