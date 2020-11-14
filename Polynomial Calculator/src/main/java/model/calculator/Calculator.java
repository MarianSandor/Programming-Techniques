package model.calculator;

import model.polynom.Polynom;

/**
 * Calculator class
 *
 * -defines a calculator which works on polynomials
 * -it is characterized by the two inputs polynomA and polynomB and the result polynomR[]
 * -the result is always in polynomR[0] only in case of division polynomR[0] holds the quotient and polynomR[1] the reminder
 * -the derive and integrate operations consider the polynomA as input
 */

public class Calculator {
    private Polynom polynomA;
    private Polynom polynomB;
    private Polynom polynomR[];

    public Calculator() {
        this.polynomA = null;
        this.polynomB = null;
        this.polynomR = new Polynom[2];
    }

    public void setPolynomA(Polynom polynom) {
        this.polynomA = polynom;
    }

    public void setPolynomB(Polynom polynom) {
        this.polynomB = polynom;
    }

    public Polynom getPolynomA() {
        return this.polynomA;
    }

    public Polynom getPolynomB() {
        return this.polynomB;
    }

    public Polynom[] getPolynomR() {
        return this.polynomR;
    }

    /**
     * add method
     *
     * -adds polynomA and polynomB and stores the result in polynomR[0]
     */
    public void add() {
        if (this.polynomA != null && this.polynomB != null) {
            this.polynomR[0] = PolynomialCalculator.add(this.polynomA, this.polynomB);
        }
    }

    /**
     * subtract method
     *
     * -subtracts polynomA and polynomB and stores the result in polynomR[0]
     */
    public void subtract() {
        if (this.polynomA != null && this.polynomB != null) {
            this.polynomR[0] = PolynomialCalculator.subtract(this.polynomA, this.polynomB);
        }
    }

    /**
     * multiply method
     *
     * -multiplies polynomA and polynomB and stores the result in polynomR[0]
     */
    public void multiply() {
        if (this.polynomA != null && this.polynomB != null) {
            this.polynomR[0] = PolynomialCalculator.multiply(this.polynomA, this.polynomB);
        }
    }

    /**
     * divide method
     *
     * -divides polynomA and polynomB and stores the result in polynomR[]
     * -the quotient is in polynomR[0] and the reminder polynomR[1]
     */
    public void divide() {
        if (this.polynomA != null && this.polynomB != null) {
            this.polynomR = PolynomialCalculator.divide(this.polynomA, this.polynomB);
        }
    }

    /**
     * derive method
     *
     * -derives polynomA and stores the result in polynomR[0]
     */
    public void derive() {
        if (this.polynomA != null) {
            this.polynomR[0] = PolynomialCalculator.derive(this.polynomA);
        }
    }

    /**
     * integrate method
     *
     * -integrates polynomA and stores the result in polynomR[0]
     */
    public void integrate() {
        if (this.polynomA != null) {
            this.polynomR[0] = PolynomialCalculator.integrate(this.polynomA);
        }
    }

}
