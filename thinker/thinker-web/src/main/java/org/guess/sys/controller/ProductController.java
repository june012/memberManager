package org.guess.sys.controller;

import org.guess.core.web.BaseController;
import org.guess.sys.model.Product;
import org.guess.sys.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wan.peng on 2016/10/10.
 */
@Controller
@RequestMapping("/sys/product/")
public class ProductController extends BaseController<Product> {
    {
        editView = "/sys/product/edit";
        listView = "/sys/product/list";
        showView = "/sys/product/show";
    }

    @Autowired
    private ProductService productService;

}