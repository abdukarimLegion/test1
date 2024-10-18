package uz.mango.apps.fidotesttask.service;

import org.springframework.stereotype.Service;
import uz.mango.apps.fidotesttask.model.CreditDetails;
import uz.mango.apps.fidotesttask.service.impl.CalculatorImpl;

import java.util.ArrayList;
import java.util.List;

import static uz.mango.apps.fidotesttask.extension.Extension.round;

@Service
public class CalculatorService implements CalculatorImpl {


    @Override
    public List<CreditDetails> calculateDifferensial(double creditAmount, double annualRate, int amountMonth) {
        double monthlyPercentRate = (annualRate / 100) / 12;
        double mainPayment = creditAmount / amountMonth;
        double qolganCredit = creditAmount;

        List<CreditDetails> payments = new ArrayList<>();

        for (int i = 1; i <= amountMonth; i++) {
            double interestPayment = round(qolganCredit * monthlyPercentRate);
            double totalPayment = round(mainPayment + interestPayment);
            qolganCredit -= mainPayment;

            payments.add(new CreditDetails(i,
                    mainPayment, interestPayment, totalPayment, round(qolganCredit)));
        }

        return payments;
    }

    @Override
    public List<CreditDetails> calculateAnnuite(double creditAmount, double annualRate, int amountMonth) {
        double monthlyPercentRate = (annualRate / 100) / 12;

        // FORMULA: A = P * [r(1 + r)^n] / [(1 + r)^n - 1]
        double annuitPayment = creditAmount * (monthlyPercentRate * Math.pow(1 + monthlyPercentRate, amountMonth)) /
                (Math.pow(1 + monthlyPercentRate, amountMonth) - 1);

        List<CreditDetails> payments = new ArrayList<>();
        double remainingLoan = creditAmount;

        for (int i = 1; i <= amountMonth; i++) {
            double interestPayment = round(remainingLoan * monthlyPercentRate);
            double principalPayment = round(annuitPayment - interestPayment);
            remainingLoan = round(remainingLoan - principalPayment);

            payments.add(new CreditDetails(i,
                    principalPayment, interestPayment, annuitPayment, remainingLoan));
        }

        return payments;
    }
}
