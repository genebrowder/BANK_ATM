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
public class SavingsOptionsController {
    @RequestMapping(value = {"/savings_options"}, method= RequestMethod.GET)
    public String savingsOptions(Model model){

        return "savings_options";

    }

    @RequestMapping(value = {"/savings_options"}, method= RequestMethod.POST)
    public String savingsOptionsSubmit(HttpServletRequest request, Model model, HttpSession session){


        UserLoginInfo userLoginInfo = (UserLoginInfo) session.getAttribute("userLoginInfo");

        List<Account> accounts =  userLoginInfo.getUser().getAccount();

        Account savingsAccount = new Account();

        for(Account account: accounts)  {
            if(account.getTypeOfAccountName().equals("SAVINGS"))  {
               savingsAccount =  account;
            }

        }


        String savingsOption = request.getParameter("action");

        if(savingsOption.equals("DEPOSIT")) {

            String lastSavingsTransactionDate = savingsAccount.getDateAsString(savingsAccount.getLastSavingsTransactionDate());
            model.addAttribute("lastSavingsTransactionDate",lastSavingsTransactionDate) ;
            return "savings_deposit";
        }else if(savingsOption.equals("WITHDRAWAL"))
        {
            String lastSavingsTransactionDate = savingsAccount.getDateAsString(savingsAccount.getLastSavingsTransactionDate());
            model.addAttribute("lastSavingsTransactionDate",lastSavingsTransactionDate) ;

            return "savings_withdrawal";
        }
        else if(savingsOption.equals("BALANCE"))
        {
            model.addAttribute("balance",savingsAccount.printNumberAsCurrency(savingsAccount.getSavingsBalance()) );
            return "savings_balance";
        }
        else if(savingsOption.equals("TRANSACTIONS")) {
            List<TransactionAsStrings> savingsTransactions= savingsAccount.formatTransactionList(savingsAccount.getSavingsTransactions());
            model.addAttribute("savingsTransactions",savingsTransactions) ;
            return "savings_transactions";
        }else{
                return "another_transaction";
        }

    }
}
