package edu.umsl.final3.controller;

import edu.umsl.final3.dao.UserLoginInfoDao;
import edu.umsl.final3.domain.Account;
import edu.umsl.final3.domain.Transaction;
import edu.umsl.final3.domain.UserLoginInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by genebrowder on 5/10/16.
 */
@Controller
public class SavingsDepositController {

    @RequestMapping(value = {"/savings_deposit"}, method= RequestMethod.GET)
    public String savingsOptions(Model model){

        return "savings_deposit";

    }

    @RequestMapping(value = {"/savings_deposit"}, method= RequestMethod.POST)
    public String savingsOptionsSubmit(HttpServletRequest request, Model model, HttpSession session) {

        String actionChoice = request.getParameter("action");

        if(actionChoice.equals("SUBMIT")) {
            UserLoginInfo userLoginInfo = (UserLoginInfo) session.getAttribute("userLoginInfo");


            List<Account> accounts =  userLoginInfo.getUser().getAccount();

            Account savingsAccount = new Account();

            for(Account account: accounts)  {
                if(account.getTypeOfAccountName().equals("SAVINGS"))  {
                    savingsAccount =  account;
                }

            }

            String dateOfDeposit = request.getParameter("dateOfDeposit");
            String amountOfDeposit = request.getParameter("amountOfDeposit");

            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

            Date date = new Date();
            try {

                date = formatter.parse(dateOfDeposit);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Boolean dateValid =  savingsAccount.checkSavingsDateIsValid(date);

            Boolean amountValid =  savingsAccount.checkDepostAmount(Double.parseDouble(amountOfDeposit)) ;


            if( dateValid && amountValid) {
                savingsAccount.modifySavingsAccount(savingsAccount,"DEPOSIT", date,Double.parseDouble(amountOfDeposit));

                UserLoginInfoDao.updateUserLoginInfo(userLoginInfo);

                Transaction transaction = savingsAccount.getLastSavingsTransaction();

                model.addAttribute("dateDepositAdded",savingsAccount.getDateAsString(transaction.getDateOfTranaction())) ;
                model.addAttribute("depositAmount",savingsAccount.printNumberAsCurrency(transaction.getAmount()) );
                model.addAttribute("balance",savingsAccount.printNumberAsCurrency(transaction.getBalance())) ;

                return "savings_deposit_status";
            } else{

                String lastSavingsTransactionDate = savingsAccount.getDateAsString(savingsAccount.getLastSavingsTransactionDate());
                model.addAttribute("lastSavingsTransactionDate",lastSavingsTransactionDate) ;

                if( !dateValid ){
                    dateOfDeposit = "INVALID";
                    model.addAttribute("dateOfDeposit",dateOfDeposit) ;
                }

                if (!amountValid)
                {
                    amountOfDeposit = "INVALID";
                    model.addAttribute("amountOfDeposit",amountOfDeposit) ;
                }


                return "savings_deposit";
            }

        }  else{
            return "savings_options";
        }


    }
}
