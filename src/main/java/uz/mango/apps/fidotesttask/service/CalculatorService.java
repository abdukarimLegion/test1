package uz.mango.apps.fidotesttask.service;

import org.springframework.stereotype.Service;
import uz.mango.apps.fidotesttask.model.CreditDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalculatorService {

    // Helper method for rounding BigDecimal
    private BigDecimal round(BigDecimal value) {
        return value.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public List<CreditDetails> calculateDifferensial(BigDecimal creditAmount, BigDecimal annualRate, int amountMonth) {
        BigDecimal monthlyPercentRate = annualRate.divide(BigDecimal.valueOf(100), 10, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(12), 10, BigDecimal.ROUND_HALF_UP);
        BigDecimal mainPayment = creditAmount.divide(BigDecimal.valueOf(amountMonth), 10, BigDecimal.ROUND_HALF_UP);
        BigDecimal qolganCredit = creditAmount;

        List<CreditDetails> payments = new ArrayList<>();

        for (int i = 1; i <= amountMonth; i++) {
            BigDecimal interestPayment = round(qolganCredit.multiply(monthlyPercentRate));
            BigDecimal totalPayment = round(mainPayment.add(interestPayment));
            qolganCredit = qolganCredit.subtract(mainPayment);

            payments.add(new CreditDetails(i,
                    mainPayment, interestPayment, totalPayment, round(qolganCredit)));
        }

        return payments;
    }

    public List<CreditDetails> calculateAnnuite(BigDecimal creditAmount, BigDecimal annualRate, int amountMonth) {
        BigDecimal monthlyPercentRate = annualRate.divide(BigDecimal.valueOf(100), 10, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(12), 10, BigDecimal.ROUND_HALF_UP);

        // FORMULA: A = P * [r(1 + r)^n] / [(1 + r)^n - 1]
        BigDecimal annuitPayment = creditAmount.multiply(monthlyPercentRate.multiply(BigDecimal.ONE.add(monthlyPercentRate).pow(amountMonth)))
                .divide((BigDecimal.ONE.add(monthlyPercentRate).pow(amountMonth)).subtract(BigDecimal.ONE), 10, BigDecimal.ROUND_HALF_UP);

        List<CreditDetails> payments = new ArrayList<>();
        BigDecimal remainingLoan = creditAmount;

        for (int i = 1; i <= amountMonth; i++) {
            BigDecimal interestPayment = round(remainingLoan.multiply(monthlyPercentRate));
            BigDecimal principalPayment = round(annuitPayment.subtract(interestPayment));
            remainingLoan = round(remainingLoan.subtract(principalPayment));

            payments.add(new CreditDetails(i,
                    principalPayment, interestPayment, annuitPayment, remainingLoan));
        }

        return payments;
    }
}
