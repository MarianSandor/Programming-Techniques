package model.calculator;

import model.polynom.Monomial;
import model.polynom.Polynom;

/**
 * PolynomialCalculator class
 *
 * -implements all the basic operations on polynomials
 */
public class PolynomialCalculator {

    /**
     * add method
     *
     * -adds two polynomials and returns the resulted polynomial
     * @param p1
     * -first polynomial
     * @param p2
     * -second polynomial
     * @return Polynom
     */
    public static Polynom add(Polynom p1, Polynom p2) {
        Polynom result = new Polynom();

        for (Monomial monomial : p1.getPolynom()) {
            result.addMonomial(new Monomial(monomial.getCoeff(), monomial.getDegree()));
        }

        for (Monomial monomial : p2.getPolynom()) {
            result.addMonomial(new Monomial(monomial.getCoeff(), monomial.getDegree()));
        }

        result.suppress();
        return result;
    }

    /**
     * subtract method
     *
     * -subtracts two polynomials and returns the resulted polynomial
     * @param p1
     * -first polynomial
     * @param p2
     * -second polynomial
     * @return Polynom
     */
    public static Polynom subtract(Polynom p1, Polynom p2) {
        Polynom result = new Polynom();

        for (Monomial monomial : p1.getPolynom()) {
            result.addMonomial(new Monomial(monomial.getCoeff(), monomial.getDegree()));
        }

        for (Monomial monomial : p2.getPolynom()) {
            result.addMonomial(new Monomial(-monomial.getCoeff(), monomial.getDegree()));
        }

        result.suppress();
        return result;
    }

    /**
     * multiply method
     *
     * multiply two polynomials and returns the resulted polynomial
     * @param p1
     * -first polynomial
     * @param p2
     * -second polynomial
     * @return Polynom
     */
    public static Polynom multiply(Polynom p1, Polynom p2) {
        Polynom result = new Polynom();

        for (Monomial m1 : p1.getPolynom()) {
            for (Monomial m2 : p2.getPolynom()) {
                result.addMonomial(MonomialCalculator.multiply(m1, m2));
            }
        }

        result.suppress();
        return result;
    }

    /**
     * divide method
     *
     * -divides two polynomials and returns the resulted quotient and reminder
     *-the quotient is stored at position 0 of the resulted Polynom array and the reminder on position 1
     * @param p1
     * -first polynomial
     * @param p2
     * -second polynomial
     * @return Polynom[2]
     */
    public static Polynom[] divide(Polynom p1, Polynom p2) {
        Polynom result[] = new Polynom[2];
        result[0] = new Polynom();
        result[1] = new Polynom();

        while (p1.getDegree() >= p2.getDegree()) {
            Monomial m1 = p1.getLeadTerm();
            Monomial m2 = p2.getLeadTerm();
            Monomial r = MonomialCalculator.divide(m1,m2);

            Polynom temp = new Polynom();

            for (Monomial m : p2.getPolynom()) {
                temp.addMonomial(MonomialCalculator.multiply(r,m));
            }

            p1 = PolynomialCalculator.subtract(p1,temp);

            result[0].addMonomial(r);

            if (p1.getDegree() == p2.getDegree() && p1.getDegree() == 0) {
                break;
            }
       }

        result[1] = p1;

        result[0].suppress();
        result[1].suppress();
        return result;
    }

    /**
     * derive method
     *
     * -derives the given polynomial
     * @param p
     * @return Polynom
     */
    public static Polynom derive(Polynom p) {
        Polynom result = new Polynom();

        for (Monomial m : p.getPolynom()) {
            if (m.getDegree() >= 0) {
                result.addMonomial(MonomialCalculator.derive(m));
            }
        }

        result.suppress();
        return result;
    }

    /**
     * integrate method
     *
     * -derives the given polynomial
     * @param p
     * @return Polynom
     */
    public static Polynom integrate(Polynom p) {
        Polynom result = new Polynom();

        for (Monomial m : p.getPolynom()) {
            result.addMonomial(MonomialCalculator.integrate(m));
        }

        result.suppress();
        return result;
    }
}
