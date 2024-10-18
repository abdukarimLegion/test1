package uz.mango.apps.fidotesttask.service.impl;

import uz.mango.apps.fidotesttask.model.CreditDetails;

import java.util.List;

public interface CalculatorImpl {
    List<CreditDetails> calculateDifferensial(double creditAmount, double annualRate, int amountMonth);

    List<CreditDetails> calculateAnnuite(double creditAmount, double annualRate, int amountMonth);
}
