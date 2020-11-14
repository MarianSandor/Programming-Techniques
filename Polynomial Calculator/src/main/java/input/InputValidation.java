package input;

import model.polynom.Monomial;
import model.polynom.Polynom;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * InputValidation class
 *
 * -deals with the user input
 * -validates if a string can be interpreted as a polynomial
 * -creates a polynomial out of a string
 */

public class InputValidation {

    /**
     * isValid method
     *
     * -validates if a string can or cannot be a polynomial
     * @param polynom
     * -is a string given by the user
     * @return boolean
     */
    public static boolean isValid(String polynom) {

        String POLYNOM_PATTERN = "([+-]?\\d*x\\^[2-9]+)|([+-]?\\d*x)|([+-]?\\d+)";
        Pattern pattern = Pattern.compile(POLYNOM_PATTERN);
        Matcher matcher = pattern.matcher(polynom);

        String foundPoly = "";
        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (matcher.group(i) != null) {
                    foundPoly += matcher.group(i);
                }
            }
        }

        System.out.println(foundPoly);

        if (!foundPoly.equals(polynom) || polynom.isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * buildMonomial method
     *
     * -interprets the string and obtains the monomial (coefficient and degree)
     * @param monomial
     * -a substring from the user input that defines a monomial
     * @return Monomial
     */
    public static Monomial buildMonomial(String monomial) {
        Monomial builtMonomial = null;

        String MONOMIAL_PATTERN = "^([+-]?\\d*)([+-]?x?\\^?)(\\d*)";
        Pattern pattern = Pattern.compile(MONOMIAL_PATTERN);
        Matcher matcher = pattern.matcher(monomial);

        if (matcher.find()) {
            int degree = 0;
            int coeff;

            if (!matcher.group(3).isEmpty()) {
                degree = Integer.parseInt(matcher.group(3));
            } else if (matcher.group(2).equals("x")) {
                degree = 1;
            }

            if (matcher.group(1).equals("+") || matcher.group(1).equals("-") || matcher.group(1).isEmpty()) {
                coeff = Integer.parseInt(matcher.group(1)+"1");
            } else {
                coeff = Integer.parseInt(matcher.group(1));
            }

            builtMonomial = new Monomial(coeff, degree);
        }

        return builtMonomial;
    }

    /**
     * buildPolynomial
     *
     * -receives a string and builds a Polynom object out of it
     * @param polynom
     * -a string given by the user
     * @return Polynom
     */
    public static Polynom buildPolynomial(String polynom) {
        Polynom builtPolynom = new Polynom();

        String POLYNOM_PATTERN = "([+-]?\\d*x\\^[2-9]+)|([+-]?\\d*x)|([+-]?\\d+)";
        Pattern pattern = Pattern.compile(POLYNOM_PATTERN);
        Matcher matcher = pattern.matcher(polynom);

        String monomial;
        while (matcher.find()) {
            monomial = "";

            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (matcher.group(i) != null) {
                    monomial += matcher.group(i);
                }
            }

            builtPolynom.addMonomial(InputValidation.buildMonomial(monomial));
        }

        builtPolynom.suppress();
        return builtPolynom;
    }
}
