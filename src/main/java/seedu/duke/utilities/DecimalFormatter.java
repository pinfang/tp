package seedu.duke.utilities;

import java.text.DecimalFormat;

//@@author killingbear999
/** It is to convert the amount to 4 dp for storage. */
public class DecimalFormatter {
    
    public DecimalFormatter() {
    }
    
    /** It is to convert the amount to 4 dp for storage. */
    public double convert(double amount) {
        DecimalFormat df = new DecimalFormat("0.0000");
        amount = Double.parseDouble(df.format(amount));
        return amount;
    }
}
