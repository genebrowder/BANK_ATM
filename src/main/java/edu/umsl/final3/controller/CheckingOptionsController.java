package edu.umsl.final3.controller;

import edu.umsl.final3.TransactionAsStrings;
import edu.umsl.final3.domain.Account;
import edu.umsl.final3.domain.UserLoginInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by genebrowder on 5/10/16.
 */
@Controller
public class CheckingOptionsController {
    @RequestMapping(value = {"/checking_options"}, method= RequestMethod.GET)
    public String checkingOptions(Model model){

        return "checking_options";

    }

    @RequestMapping(value = {"/checking_options"}, method= RequestMethod.POST)
    public String checkingOptionsSubmit(HttpServletRequest request, Model model, HttpSession session){


        UserLoginInfo userLoginInfo = (UserLoginInfo) session.getAttribute("userLoginInfo");

        List<Account> accounts =  userLoginInfo.getUser().getAccount();

        Account checkingAccount = new Account();

        for(Account account: accounts)  {
            if(account.getTypeOfAccountName().equals("CHECKING"))  {
               checkingAccount =  account;
            }

        }


        String checkingOption = request.getParameter("action");

        if(checkingOption.equals("DEPOSIT")) {

            String lastCheckingTransactionDate = checkingAccount.getDateAsString(checkingAccount.getLastCheckingTransactionDate());
            model.addAttribute("lastCheckingTransactionDate",lastCheckingTransactionDate) ;
            return "checking_deposit";
        }else if(checkingOption.equals("WITHDRAWAL"))
        {
            String lastCheckingTransactionDate = checkingAccount.getDateAsString(checkingAccount.getLastCheckingTransactionDate());
            model.addAttribute("lastCheckingTransactionDate",lastCheckingTransactionDate) ;

            return "checking_withdrawal";
        }
        else if(checkingOption.equals("BALANCE"))
        {
            model.addAttribute("balance",checkingAccount.printNumberAsCurrency(checkingAccount.getCheckingBalance()) );
            return "checking_balance";
        }
        else if(checkingOption.equals("TRANSACTIONS")) {
            List<TransactionAsStrings> checkingTransactions = checkingAccount.formatTransactionList(checkingAccount.getCheckingTransactions());
            model.addAttribute("checkingTransactions", checkingTransactions);
            return "checking_transactions";
        }else{
            return "another_transaction";
        }

    }
}
