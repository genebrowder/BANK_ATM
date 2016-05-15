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
public class CheckingDepositController {

    @RequestMapping(value = {"/checking_deposit"}, method= RequestMethod.GET)
    public String checkingOptions(Model model){

        return "checkings_deposit";

    }

    @RequestMapping(value = {"/checking_deposit"}, method= RequestMethod.POST)
    public String checkingOptionsSubmit(HttpServletRequest request, Model model, HttpSession session) {

        String actionChoice = request.getParameter("action");

        if(actionChoice.equals("SUBMIT")) {

            UserLoginInfo userLoginInfo = (UserLoginInfo) session.getAttribute("userLoginInfo");


            List<Account> accounts = userLoginInfo.getUser().getAccount();

            Account checkingAccount = new Account();

            for (Account account : accounts) {
                if (account.getTypeOfAccountName().equals("CHECKING")) {
                    checkingAccount = account;
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

            Boolean dateValid = checkingAccount.checkCheckingDateIsValid(date);

            Boolean amountValid = checkingAccount.checkDepostAmount(Double.parseDouble(amountOfDeposit));

            if (dateValid && amountValid) {
                checkingAccount.modifyCheckingAccount(checkingAccount, "DEPOSIT", date, Double.parseDouble(amountOfDeposit));

                UserLoginInfoDao.updateUserLoginInfo(userLoginInfo);

                Transaction transaction = checkingAccount.getLastCheckingTransaction();

                model.addAttribute("dateDepositAdded", checkingAccount.getDateAsString(transaction.getDateOfTranaction()));
                model.addAttribute("depositAmount", checkingAccount.printNumberAsCurrency(transaction.getAmount()));
                model.addAttribute("balance", checkingAccount.printNumberAsCurrency(transaction.getBalance()));

                return "checking_deposit_status";
            } else {

                String lastCheckingTransactionDate = checkingAccount.getDateAsString(checkingAccount.getLastCheckingTransactionDate());
                model.addAttribute("lastCheckingTransactionDate", lastCheckingTransactionDate);

                if (!dateValid) {
                    dateOfDeposit = "INVALID";
                    model.addAttribute("dateOfDeposit", dateOfDeposit);
                }

                if (!amountValid) {
                    amountOfDeposit = "INVALID";
                    model.addAttribute("amountOfDeposit", amountOfDeposit);
                }


                return "checking_deposit";
            }

        }  else{
            return "checking_options";
        }

    }
}
