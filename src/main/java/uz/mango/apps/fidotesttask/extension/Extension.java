package uz.mango.apps.fidotesttask.extension;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Extension {

    public static double round(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
