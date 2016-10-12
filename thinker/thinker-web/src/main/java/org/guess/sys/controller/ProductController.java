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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

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
    public ModelAndView create() throws Exception {
        ModelAndView modelAndView = super.create();
        List<ProductType> productTypes = productTypeService.getAll();
        modelAndView.addObject("types",productTypes);
        return modelAndView;
    }

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

    @RequestMapping("typePage")
    public ModelAndView createType(){
        ModelAndView mav = new ModelAndView("/sys/product/typeEdit");
        try {
            List<ProductType> productTypes = productTypeService.getAll();
            mav.addObject("types",productTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  mav;
    }

    @RequestMapping("typeAdd")
    public String createType(ProductType productType){
        System.out.println(productType.getTypeName());
        User currentUser = UserUtil.getCurrentUser();
        productType.setOaId(currentUser.getId());
        try {
            productTypeService.save(productType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/sys/product/list";
    }
    @RequestMapping("deleteType")
    @ResponseBody
    public void deleteType(String oldTypeId){
        try {
            productTypeService.removeById(Long.valueOf(oldTypeId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
