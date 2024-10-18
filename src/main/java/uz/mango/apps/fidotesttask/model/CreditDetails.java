package uz.mango.apps.fidotesttask.model;

public class CreditDetails {

    private int month;
    private double totalSum;
    private double interestPayment;
    private double totalPayment;
    private double remainingLoan;


    public CreditDetails(int month, double principalPayment, double interestPayment, double totalPayment, double remainingLoan) {
        this.month = month;
        this.totalSum = principalPayment;
        this.interestPayment = interestPayment;
        this.totalPayment = totalPayment;
        this.remainingLoan = remainingLoan;
    }

    public int getMonth() { return month; }
    public double getTotalSum() { return totalSum; }
    public double getInterestPayment() { return interestPayment; }
    public double getTotalPayment() { return totalPayment; }
    public double getRemainingLoan() { return remainingLoan; }
}
