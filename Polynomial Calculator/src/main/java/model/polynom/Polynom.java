package model.polynom;

import model.calculator.MonomialCalculator;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Polynom class
 *
 * -defines the entity of a polynomial characterized by a list of monomial objects
 */

public class Polynom {
    ArrayList<Monomial> polynom;

    public Polynom() {
        this.polynom = new ArrayList<Monomial>();
    }

    public void addMonomial(Monomial monomial) {
        this.polynom.add(monomial);
    }

    public ArrayList<Monomial> getPolynom() {
        return this.polynom;
    }

    /**
     * getLeadTerm method
     *
     * -checks if the polynomial has any monomial objects
     * -if the list is not empty return the monomial with the highest degree
     * @return Monomial
     */
    public Monomial getLeadTerm() {
        if (this.polynom.size() > 0) {
            return this.polynom.get(0);
        }

        return null;
    }

    /**
     * getDegree method
     *
     * -the degree of a polynomial is equal to the highest degree of its monomial objects
     * @return int
     */
    public int getDegree() {
        if (this.polynom.size() > 0) {
            return this.polynom.get(0).getDegree();
        }

        return 0;
    }

    /**
     * arrange method
     *
     * -sorts the polynomial by its monomials degree
     * -it uses the compareTo method implemented by the class Monomial
     */
    public void arrange() {
        Collections.sort(this.polynom);
    }

    /**
     * suppress method
     *
     * -it first sorts the polynomial
     * -then it parses through the monomial objects adding all the monomials with the same degree
     * -the list of monomial objects is then updated
     */
    public void suppress() {
        this.arrange();
        ArrayList<Monomial> suppressedP = new ArrayList<Monomial>();

        for (int i = 0; i < this.polynom.size(); i++) {
            Monomial monom = this.polynom.get(i);

            while (i < this.polynom.size() - 1 && this.polynom.get(i).getDegree() == this.polynom.get(i+1).getDegree()) {
                monom = MonomialCalculator.add(monom, this.polynom.get(i + 1));
                i++;
            }

            if (monom.getCoeff() != 0) {
                suppressedP.add(monom);
            }
        }

        this.polynom = suppressedP;
    }

    @Override
    public String toString() {
        String toString = "";

        if (this.polynom.size() == 0) {
            toString += "0";
        } else {
            for (Monomial monomial : this.polynom) {
                if (monomial != this.polynom.get(0) && monomial.getCoeff() >= 0)
                    toString += "+";
                toString += monomial.toString();
            }
        }

        return toString;
    }
}
