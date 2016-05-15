package edu.umsl.final3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by genebrowder on 5/11/16.
 */
@Controller
public class AnotherTransactionController {



    @RequestMapping(value = {"/another_transaction"}, method= RequestMethod.POST)
    public String checkingBalanceSubmit(HttpServletRequest request, Model model, HttpSession session){

        String transactionOption = request.getParameter("action");


        if(transactionOption.equals("CHECKING")) {

            return "checking_options";

        }else if(transactionOption.equals("SAVINGS"))
        {
            return "savings_options";

        } else{
            model.asMap().clear();
            session.invalidate();

            return "thank_you";
        }

    }
}
