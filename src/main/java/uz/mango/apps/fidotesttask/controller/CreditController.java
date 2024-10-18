package uz.mango.apps.fidotesttask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.mango.apps.fidotesttask.model.CreditDetails;
import uz.mango.apps.fidotesttask.service.CalculatorService;

import java.util.List;

@Controller
public class CreditController {

    private final CalculatorService calculatorService;

    public CreditController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/kredit")
    public String creditForm() {
        return "index";
    }

    @PostMapping("/kreditHisoblash")
    public String credit(
            @RequestParam String creditAmount,
            @RequestParam String annualRate,
            @RequestParam String amountMonth,
            @RequestParam String calculationType,
            Model model
    ) {
        try {
            double creditAmountVal = Double.parseDouble(creditAmount);
            double annualRateVal = Double.parseDouble(annualRate);
            int amountMonthVal = Integer.parseInt(amountMonth);

            List<CreditDetails> creditDetails;

            if ("annuitet".equals(calculationType)) {
                creditDetails = calculatorService.calculateAnnuite(creditAmountVal, annualRateVal, amountMonthVal);
            } else {
                creditDetails = calculatorService.calculateDifferensial(creditAmountVal, annualRateVal, amountMonthVal);
            }

            model.addAttribute("payments", creditDetails);
            model.addAttribute("creditAmount", creditAmountVal);
            model.addAttribute("annualRate", annualRateVal);
            model.addAttribute("amountMonth", amountMonthVal);
            model.addAttribute("calculationType", calculationType);

            return "result";

        } catch (NumberFormatException e) {
            model.addAttribute("error", "Xato kiritdingiz. Iltimos, faqat son kiriting.");
            return "index";
        }
    }
}
