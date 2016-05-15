package edu.umsl.final3.controller;

import edu.umsl.final3.dao.UserLoginInfoDao;
import edu.umsl.final3.domain.UserLoginInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by genebrowder on 3/3/16.
 */
@Controller
public class IndexController {

    @RequestMapping(value = {"/index","/"}, method= RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("userLoginInfo",new UserLoginInfo());
        return "index";

    }
    @RequestMapping(value = {"/index","/"}, method= RequestMethod.POST)
    public String indexSubmit(@ModelAttribute UserLoginInfo userLoginInfo, Model model, HttpSession session){

        userLoginInfo = UserLoginInfoDao.getUser(userLoginInfo);

        if(userLoginInfo != null){


            session.setAttribute("userLoginInfo",userLoginInfo);

            model.addAttribute("lastName",userLoginInfo.getUser().getLastName());
            model.addAttribute("firstName",userLoginInfo.getUser().getFirstName());
            return "checkings_or_savings";
        }
        else{
            model.addAttribute("userLoginInfo",new UserLoginInfo());
            return "index";
        }

    }





}
