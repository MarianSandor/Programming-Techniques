package businessLogic.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Validator Class
 *
 * Provides the method for validating different attributes from the database.
 */

public class Validator {

    /**
     * Checks if the price is logically correct.
     * @param price The price.
     * @return True if it as logically valid price.
     */
    public static boolean validPrice(double price) {

        return price > 0;
    }

    /**
     * Checks if the amount is logically correct.
     * @param amount The amount.
     * @return True if it as logically valid amount.
     */
    public static boolean validAmount(int amount) {

        return amount > 0;
    }

    /**
     * Checks if the name respects a person name format.
     * @param name A name.
     * @return True if it respects a naming convention.
     */
    public static boolean validName(String name) {
        String NAME_PATTERN = "([A-Z][a-z]*)";
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        StringBuilder foundName = new StringBuilder();

        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (matcher.group(i) != null && !matcher.group(i).equals("")) {
                    foundName.append(matcher.group(i));
                    foundName.append(" ");
                }
            }
        }
        foundName.deleteCharAt(foundName.length() - 1);

        return foundName.toString().equals(name);
    }

    /**
     * Checks if the address respects the address format.
     * @param name An address.
     * @return True if it respects a naming convention for an address.
     */
    public static boolean validProductName(String name) {
        String NAME_PATTERN = "([A-Z][a-z]*)|([a-z]*)";
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        StringBuilder foundName = new StringBuilder();

        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (matcher.group(i) != null && !matcher.group(i).equals("")) {
                    foundName.append(matcher.group(i));
                    foundName.append(" ");
                }
            }
        }
        foundName.deleteCharAt(foundName.length() - 1);

        return foundName.toString().equals(name);
    }

    public static boolean validAddress(String address) {
        String ADDRESS_PATTERN = "([A-Z][a-z]*-[A-Z][a-z]*)|([A-Z][a-z]*)";
        Pattern pattern = Pattern.compile(ADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(address);
        StringBuilder foundAddress = new StringBuilder();

        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (matcher.group(i) != null && !matcher.group(i).equals("")) {
                    foundAddress.append(matcher.group(i));
                    foundAddress.append(" ");
                }
            }
        }
        foundAddress.deleteCharAt(foundAddress.length() - 1);

        return foundAddress.toString().equals(address);
    }
}
