package edu.umsl.final3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by genebrowder on 5/10/16.
 */
@Controller
public class SavingsBalanceController {

    @RequestMapping(value = {"/savings_balance"}, method= RequestMethod.GET)
    public String savingsBalance(Model model){

        return "savingsbalance";

    }

    @RequestMapping(value = {"/savings_balance"}, method= RequestMethod.POST)
    public String savingsBalanceSubmit(HttpServletRequest request, Model model, HttpSession session){

        return "another_transaction";
    }

}

