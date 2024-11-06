package uz.mango.apps.fidotesttask.model;

import java.math.BigDecimal;

public class CreditDetails {

    private int month;
    private BigDecimal totalSum;
    private BigDecimal interestPayment;
    private BigDecimal totalPayment;
    private BigDecimal remainingLoan;

    public CreditDetails(int month, BigDecimal principalPayment, BigDecimal interestPayment, BigDecimal totalPayment, BigDecimal remainingLoan) {
        this.month = month;
        this.totalSum = principalPayment;
        this.interestPayment = interestPayment;
        this.totalPayment = totalPayment;
        this.remainingLoan = remainingLoan;
    }
    public int getMonth() { return month; }
    public BigDecimal getTotalSum() { return totalSum; }
    public BigDecimal getInterestPayment() { return interestPayment; }
    public BigDecimal getTotalPayment() { return totalPayment; }
    public BigDecimal getRemainingLoan() { return remainingLoan; }
}
