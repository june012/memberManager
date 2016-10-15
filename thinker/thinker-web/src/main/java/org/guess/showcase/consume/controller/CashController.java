package org.guess.showcase.consume.controller;

import com.google.gson.Gson;
import org.guess.core.orm.Page;
import org.guess.core.web.BaseController;
import org.guess.facility.DefinedConstant;
import org.guess.facility.Exception.DefinedException;
import org.guess.facility.Exception.ErrorCode;
import org.guess.showcase.consume.model.CashRecord;
import org.guess.showcase.consume.model.ConsumeLog;
import org.guess.showcase.consume.service.CashService;
import org.guess.showcase.consume.service.ConsumeLogService;
import org.guess.showcase.member.model.Member;
import org.guess.showcase.member.service.MemberService;
import org.guess.sys.model.Product;
import org.guess.sys.model.ProductType;
import org.guess.sys.model.User;
import org.guess.sys.service.ProductService;
import org.guess.sys.service.ProductTypeService;
import org.guess.sys.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private CashService cashService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ConsumeLogService consumeLogService;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(CashController.class);

    @Override
    public String create(@Valid CashRecord object) throws Exception {
        BigDecimal money = object.getMoney();
        Member member = memberService.findUniqueBy("id", object.getUserid());
        boolean log = false;
        if(member == null){
            throw new DefinedException(ErrorCode.NOT_EXSIST_THIS_MEMBER.getCode(),ErrorCode.NOT_EXSIST_THIS_MEMBER.getMessage());
        }
        if(member.getCanBeConsumed().compareTo(money)==-1){
            throw new DefinedException(ErrorCode.ACCOUNT_IS_NOT_ENOUGH.getCode(),ErrorCode.ACCOUNT_IS_NOT_ENOUGH.getMessage());
        }
        if(object.getId() == 0){
            log=true;
            member.setAccount(member.getAccount().subtract(money));
            member.setCanBeConsumed(member.getCanBeConsumed().subtract(money));
            object.setCreateTime(new Date());
        }else{
            CashRecord cashRecord = cashService.findUniqueBy("id",object.getId());
            member.setAccount(member.getAccount().add(cashRecord.getMoney()).subtract(money));
            member.setAccount(member.getCanBeConsumed().add(cashRecord.getMoney()).subtract(money));
        }
        memberService.save(member);
        object.setMoney(new BigDecimal("0").subtract(money));
        cashService.save(object);

        if(log){//新建奖金记录则生成日志
            ConsumeLog consumeLog = new ConsumeLog();
            consumeLog.setCreateTime(new Date());
            consumeLog.setAccount(object.getMoney());
            consumeLog.setTypeId(object.getId());
            consumeLog.setMemberId(member.getId());
            consumeLog.setConsumeType(DefinedConstant.CONSUME_TYPE_CASH);
            consumeLogService.save(consumeLog);
        }
        return "redirect:/consume/cash/list";
    }

    @Override
    public Map<String, Object> page(Page<CashRecord> page, HttpServletRequest request) {
        User currentUser = UserUtil.getCurrentUser();
        String hql = "from CashRecord a where 1=1 ";
        String search_EQL_userid = request.getParameter("search_EQL_userid");
        String search_EQD_createTime = request.getParameter("search_EQD_createTime");
        String search_EQS_phone = request.getParameter("search_EQS_phone");

        if(search_EQL_userid!= null){
            hql+= " and a.userid="+search_EQL_userid;
        }

        if(search_EQD_createTime!= null){
            hql+= " and date_format(a.createTime,'%Y-%c-%d')='"+search_EQD_createTime+"'";
        }
        if(search_EQS_phone != null){
            Member phone = memberService.findUniqueBy("phone", search_EQS_phone);
            if(phone!=null){
                hql+=" and a.userid="+phone.getId();
            }else{
                hql+=" and 1=2";
            }
        }
        if(currentUser.getStoreId() == 0){
            String search_eql_storeId = request.getParameter("search_EQL_storeId");
            if(search_eql_storeId != null){
                String memberIds = memberService.findMemberIds(search_eql_storeId);
                if (memberIds!=null){
                    hql+=" and a.userid in("+memberIds+")";
                }else{
                    hql+=" and 1=2";
                }
            }
        }
        return cashService.findPage(page,hql).returnMap();
    }

    @Override
    public ModelAndView create() throws Exception {
        ModelAndView mav = new ModelAndView("/consume/cash/edit");
        try {
            List<ProductType> productTypes = productTypeService.getAll();
            mav.addObject("types",productTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  mav;
    }

    @RequestMapping("/findProduct")
    public @ResponseBody String getProduct(long productTypeId) throws Exception {
        List<Product> productList = productService.findBy("typeId", productTypeId);
        if(productList==null){
            productList = new ArrayList<Product>();
        }
        String s = new Gson().toJson(productList);
        System.out.println(s);
        return s;
    }

    @RequestMapping("/findPrice")
    public @ResponseBody String findPrice(long productId) throws Exception {
        Product product = productService.findUniqueBy("id", productId);
        if(product==null){
            return "";
        }
        String s = new Gson().toJson(product.getPrice());
        return s;
    }
}
