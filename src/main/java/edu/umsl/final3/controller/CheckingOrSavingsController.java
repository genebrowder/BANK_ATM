package edu.umsl.final3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by genebrowder on 3/13/16.
 */
@Controller
public class CheckingOrSavingsController {

    @RequestMapping(value = {"/checkings_or_savings"}, method= RequestMethod.GET)
    public String checkingOrSavings(Model model){

        return "checkings_or_savings";

    }

    @RequestMapping(value = {"/checkings_or_savings"}, method= RequestMethod.POST)
    public String checkingOrSavingsSubmit(HttpServletRequest request, Model model){
        String accountChoice = request.getParameter("action");

         if(accountChoice.equals("CHECKING")) {
             model.addAttribute("accountChoice", accountChoice);
             return "checking_options";
         }else
         {
        	 model.addAttribute("accountChoice", accountChoice);
             return "savings_options";
         }



    }
}
