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
public class SavingWithdrawalController {

    @RequestMapping(value = {"/savings_withdrawal"}, method= RequestMethod.GET)
    public String savingsOptions(Model model){

        return "savings_withdrawal";

    }

    @RequestMapping(value = {"/savings_withdrawal"}, method= RequestMethod.POST)
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

            String dateOfWithdrawal = request.getParameter("dateOfWithdrawal");
            String amountOfWithdrawal = request.getParameter("amountOfWithdrawal");

            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

            Date date = new Date();
            try {

                date = formatter.parse(dateOfWithdrawal);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Boolean dateValid =  savingsAccount.checkSavingsDateIsValid(date);

            Boolean amountValid =  savingsAccount.checkSavingsWithdrawalAmount(Double.parseDouble(amountOfWithdrawal)) ;

            if( dateValid && amountValid) {
                savingsAccount.modifySavingsAccount(savingsAccount, "WITHDRAW", date, Double.parseDouble(amountOfWithdrawal));

                UserLoginInfoDao.updateUserLoginInfo(userLoginInfo);

                Transaction transaction = savingsAccount.getLastSavingsTransaction();

                model.addAttribute("dateOfWithdrawal", savingsAccount.getDateAsString(transaction.getDateOfTranaction()));
                model.addAttribute("withdrawalAmount", savingsAccount.printNumberAsCurrency(transaction.getAmount()));
                model.addAttribute("balance", savingsAccount.printNumberAsCurrency(transaction.getBalance()));

                return "savings_withdrawal_status";
            }else{

                String lastSavingsTransactionDate = savingsAccount.getDateAsString(savingsAccount.getLastSavingsTransactionDate());
                model.addAttribute("lastSavingsTransactionDate",lastSavingsTransactionDate) ;

                if( !dateValid ){
                    dateOfWithdrawal = "INVALID";
                    model.addAttribute("dateOfWithdrawal",dateOfWithdrawal) ;
                }

                if (!amountValid)
                {
                    amountOfWithdrawal = "INVALID";
                    model.addAttribute("amountOfWithdrawal",amountOfWithdrawal) ;
                }


                return "savings_withdrawal";

            }
        } else{
            return "savings_options";
        }

    }
}
