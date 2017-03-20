/**
 * File: MyUtil.java
 *
 * Description: Static Helper Functions
 *
 * @author Pranav Bheda
 * @author Gurneev Sareen
 */

package finalProject;

import java.text.DecimalFormat;

public class MyUtil{

    /**
     * Builds a string from the given parameter with the format XX.XX. Example
     * 27.411231 will be returned to 27.41
     *
     * @param d The double to be formated
     * @return A formated String with two decimal places
     */
    public static String formatDouble(final double d){
        return new DecimalFormat("#0.00").format(d);
    }
}