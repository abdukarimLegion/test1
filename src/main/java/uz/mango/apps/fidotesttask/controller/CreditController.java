package uz.mango.apps.fidotesttask.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.mango.apps.fidotesttask.model.CreditDetails;
import uz.mango.apps.fidotesttask.service.CalculatorService;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CreditController {

    private final CalculatorService calculatorService;

    public CreditController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/kredit")
    @Hidden
    @Operation(summary = "Kredit olish uchun forma", description = "Kreditni hisoblash uchun forma sahifasini qaytaradi")
    public String creditForm(Model model) {
        model.addAttribute("result", null);
        return "index";
    }

    @PostMapping("/kreditHisoblash")
    @Operation(summary = "Kreditni hisoblash", description = "Kredit summasi, yillik foiz stavkasi va kredit muddatini hisoblash")
    @Hidden
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kredit hisoblash muvaffaqiyatli tugadi"),
    })
    public String credit(
            @RequestParam String creditAmount,
            @RequestParam String annualRate,
            @RequestParam String amountMonth,
            @RequestParam String calculationType,
            Model model
    ) {
        try {
            BigDecimal creditAmountVal = new BigDecimal(creditAmount);
            BigDecimal annualRateVal = new BigDecimal(annualRate);
            int amountMonthVal = Integer.parseInt(amountMonth);

            List<CreditDetails> creditDetails;

            if ("annuitet".equals(calculationType)) {
                creditDetails = calculatorService.calculateAnnuite(creditAmountVal, annualRateVal, amountMonthVal);
            } else {
                creditDetails = calculatorService.calculateDifferensial(creditAmountVal, annualRateVal, amountMonthVal);
            }

            model.addAttribute("payments", creditDetails);
            model.addAttribute("result", true);
            model.addAttribute("creditAmount", creditAmountVal);
            model.addAttribute("annualRate", annualRateVal);
            model.addAttribute("amountMonth", amountMonthVal);
            model.addAttribute("calculationType", calculationType);

            return "index";

        } catch (NumberFormatException e) {
            model.addAttribute("error", "Xato kiritdingiz. Iltimos, faqat son kiriting.");
            return "index";
        }
    }

    @PostMapping("/kreditHisoblash2")
    @Operation(summary = "Kreditni hisoblash", description = "Kredit summasi, yillik foiz stavkasi va kredit muddatini hisoblash")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kredit hisoblash muvaffaqiyatli tugadi"),
    })
    public ResponseEntity<List<CreditDetails>> kreditHisoblash(
            @RequestParam String creditAmount,
            @RequestParam String annualRate,
            @RequestParam String amountMonth,
            @RequestParam String calculationType
    ) {
        try {
            BigDecimal creditAmountVal = new BigDecimal(creditAmount);
            BigDecimal annualRateVal = new BigDecimal(annualRate);
            int amountMonthVal = Integer.parseInt(amountMonth);

            List<CreditDetails> creditDetails;

            if ("annuitet".equals(calculationType)) {
                creditDetails = calculatorService.calculateAnnuite(creditAmountVal, annualRateVal, amountMonthVal);
            } else {
                creditDetails = calculatorService.calculateDifferensial(creditAmountVal, annualRateVal, amountMonthVal);
            }

            return ResponseEntity.ok(creditDetails);  // JSON formatida Response qaytaradi

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null);  // Bad Request va null qaytaradi
        }
    }
}
