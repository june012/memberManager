package org.guess.sys.controller;

import org.guess.core.web.BaseController;
import org.guess.sys.model.Product;
import org.guess.sys.model.ProductType;
import org.guess.sys.model.User;
import org.guess.sys.service.ProductService;
import org.guess.sys.service.ProductTypeService;
import org.guess.sys.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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

    @Autowired
    private ProductTypeService productTypeService;


    @Override
    public String create(@Valid Product object) throws Exception {
        if (object.getOaId()==0){
            User currentUser = UserUtil.getCurrentUser();
            object.setOaId(currentUser.getId());
        }
        if (object.getTypeName()==null){
            ProductType productType = productTypeService.findUniqueBy("id", object.getTypeId());
            object.setTypeName(productType.getTypeName());
        }

        return super.create(object);
    }


    public String createType(){
        return null;
    }

    public String createType(ProductType productType){

        return null;
    }

}
