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
public class CheckingWithdrawalController {

    @RequestMapping(value = {"/checking_withdrawal"}, method= RequestMethod.GET)
    public String checkingOptions(Model model){

        return "checking_withdrawal";

    }

    @RequestMapping(value = {"/checking_withdrawal"}, method= RequestMethod.POST)
    public String checkingOptionsSubmit(HttpServletRequest request, Model model, HttpSession session) {

        String actionChoice = request.getParameter("action");

        if(actionChoice.equals("SUBMIT")) {
            UserLoginInfo userLoginInfo = (UserLoginInfo) session.getAttribute("userLoginInfo");


            List<Account> accounts =  userLoginInfo.getUser().getAccount();

            Account checkingAccount = new Account();

            for(Account account: accounts)  {
                if(account.getTypeOfAccountName().equals("CHECKING"))  {
                    checkingAccount =  account;
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

            Boolean dateValid =  checkingAccount.checkCheckingDateIsValid(date);

            Boolean amountValid =  checkingAccount.checkCheckingWithdrawalAmount(Double.parseDouble(amountOfWithdrawal)) ;


            if( dateValid && amountValid) {
                checkingAccount.modifyCheckingAccount(checkingAccount, "WITHDRAW", date, Double.parseDouble(amountOfWithdrawal));

                UserLoginInfoDao.updateUserLoginInfo(userLoginInfo);

                Transaction transaction = checkingAccount.getLastCheckingTransaction();

                model.addAttribute("dateOfWithdrawal", checkingAccount.getDateAsString(transaction.getDateOfTranaction()));
                model.addAttribute("withdrawalAmount", checkingAccount.printNumberAsCurrency(transaction.getAmount()));
                model.addAttribute("balance", checkingAccount.printNumberAsCurrency(transaction.getBalance()));

                return "checking_withdrawal_status";
            }else{

                String lastCheckingTransactionDate = checkingAccount.getDateAsString(checkingAccount.getLastCheckingTransactionDate());
                model.addAttribute("lastCheckingTransactionDate",lastCheckingTransactionDate) ;

                if( !dateValid ){
                    dateOfWithdrawal = "INVALID";
                    model.addAttribute("dateOfWithdrawal",dateOfWithdrawal) ;
                }

                if (!amountValid)
                {
                    amountOfWithdrawal = "INVALID";
                    model.addAttribute("amountOfWithdrawal",amountOfWithdrawal) ;
                }


                return "checking_withdrawal";
            }

        } else{
            return "checking_options";
        }

    }
}
