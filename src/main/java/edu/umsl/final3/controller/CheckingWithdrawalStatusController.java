package edu.umsl.final3.domain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



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
public class CheckingWithdrawalStatusController {

    @RequestMapping(value = {"/checking_withdrawal_status"}, method= RequestMethod.GET)
    public String checkingBalance(Model model){

        return "checking_withdrawal_status";

    }

    @RequestMapping(value = {"/checking_withdrawal_status"}, method= RequestMethod.POST)
    public String checkingBalanceSubmit(HttpServletRequest request, Model model, HttpSession session){

        return "another_transaction";
    }
}
