package model.calculator;

import model.polynom.Monomial;

/**
 * MonomialCalculator class
 *
 * -implements all the basic operations on monomials
 */
public class MonomialCalculator {

    /**
     * add method
     *
     * -adds two monomials and returns the resulted monomial
     * @param m1
     * -first monomial
     * @param m2
     * -second monomial
     * @return Monomial
     */
    public static Monomial add(Monomial m1, Monomial m2) {
        if (m1.getDegree() != m2.getDegree()) {
            return null;
        }

        return new Monomial(m1.getCoeff() + m2.getCoeff(), m1.getDegree());
    }

    /**
     * subtract method
     *
     * -subtracts two monomials and returns the resulted monomial
     * @param m1
     * -first monomial
     * @param m2
     * -second monomial
     * @return Monomial
     */
    public static Monomial subtract(Monomial m1, Monomial m2) {
        if (m1.getDegree() != m2.getDegree()) {
            return null;
        }

        return new Monomial(m1.getCoeff() - m2.getCoeff(), m1.getDegree());
    }

    /**
     * multiply method
     *
     * -multiplies two monomials and returns the resulted monomial
     * @param m1
     * -first monomial
     * @param m2
     * -second monomial
     * @return Monomial
     */
    public static Monomial multiply(Monomial m1, Monomial m2) {
        return new Monomial(m1.getCoeff() * m2.getCoeff(), m1.getDegree() + m2.getDegree());
    }

    /**
     * add method
     *
     * -divides two monomials and returns the resulted monomial
     * @param m1
     * -first monomial
     * @param m2
     * -second monomial
     * @return Monomial
     */
    public static Monomial divide(Monomial m1, Monomial m2) {
        return new Monomial(m1.getCoeff() / m2.getCoeff(), m1.getDegree() - m2.getDegree());
    }

    /**
     * derive method
     *
     * -derives the given monomial
     * @param m
     * @return Monomial
     */
    public static Monomial derive(Monomial m) {
        return new Monomial(m.getCoeff() * m.getDegree(), m.getDegree() - 1);
    }

    /**
     * integrate method
     *
     * -integrates the given monomial
     * @param m
     * @return Monomial
     */
    public static Monomial integrate(Monomial m) {
        return new Monomial(m.getCoeff() / (m.getDegree() + 1), m.getDegree() + 1);
    }
}
