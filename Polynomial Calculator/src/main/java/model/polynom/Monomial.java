package model.polynom;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Monomial class
 *
 * -defines the monomial entity characterized by a coefficient and the degree
 * -implements Comparable such that the polynomial could be sorted by its monomial's degrees
 */

public class Monomial implements Comparable {

    private double coeff;
    private int degree;

    public Monomial(double coeff, int degree) {
        this.coeff = coeff;
        this.degree = degree;
    }

    public int getDegree() {
        return this.degree;
    }

    public double getCoeff() {
        return this.coeff;
    }

    /**
     * coefficientRepresentation method
     *
     * -checks if the coefficient is a valid int or not
     * -if it is then the representation is the int form otherwise it's double with two decimals
     * @return String
     */
    private String coefficientRepresentation() {

        if (this.coeff != (int) this.coeff) {
            return "" +  new DecimalFormat("#.##").format(this.coeff);
        }

        return "" + (int) this.coeff;
    }

    @Override
    public String toString() {
        String toString = "";

        if (this.degree == 0) {
            toString += this.coefficientRepresentation();
        } else {
            if (this.coeff == -1) {
                toString += "-";
            } else if (this.coeff != 1) {
                toString += this.coefficientRepresentation();
            }
        }

        if (this.degree == 1) {
            toString += "x";
        } else if (this.degree > 1) {
            toString += "x^";
            toString += this.degree;
        }

        return toString;
    }

    @Override
    public int compareTo(Object o) {
        Monomial monomial = (Monomial) o;
        return monomial.degree - this.degree;
    }
}
