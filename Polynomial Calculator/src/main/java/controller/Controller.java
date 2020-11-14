package controller;

import input.InputValidation;
import model.calculator.Calculator;
import model.polynom.Polynom;
import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class
 *
 * -links the view with the model
 * -implements all the action listeners needed for interacting with the user
 */

public class Controller {

    private Calculator calculator;
    private View view;

    public Controller(Calculator calculator, View view) {
        this.calculator = calculator;
        this.view = view;

        this.view.addAddListener(new AddListener());
        this.view.addSubtractListener(new SubtractListener());
        this.view.addMultiplyListener(new MultiplyListener());
        this.view.addDivideListener(new DivideListener());
        this.view.addDeriveListener(new DeriveListener());
        this.view.addIntegrateListener(new IntegrateListener());
        this.view.addClearListener(new ClearListener());
    }

    class AddListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String polynomA = view.getUserInputA();
            String polynomB = view.getUserInputB();

            if (!InputValidation.isValid(polynomA)) {
                view.showError("Polynomial A does not obey the format!");
                return;
            }
            if (!InputValidation.isValid(polynomB)) {
                view.showError("Polynomial B does not obey the format!");
                return;
            }

            calculator.setPolynomA(InputValidation.buildPolynomial(polynomA));
            calculator.setPolynomB(InputValidation.buildPolynomial(polynomB));
            calculator.add();

            view.setResultA(calculator.getPolynomR()[0].toString());
        }
    }

    class SubtractListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String polynomA = view.getUserInputA();
            String polynomB = view.getUserInputB();

            if (!InputValidation.isValid(polynomA)) {
                view.showError("Polynomial A does not obey the format!");
                return;
            }
            if (!InputValidation.isValid(polynomB)) {
                view.showError("Polynomial B does not obey the format!");
                return;
            }

            calculator.setPolynomA(InputValidation.buildPolynomial(polynomA));
            calculator.setPolynomB(InputValidation.buildPolynomial(polynomB));
            calculator.subtract();

            view.setResultA(calculator.getPolynomR()[0].toString());
        }
    }

    class MultiplyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String polynomA = view.getUserInputA();
            String polynomB = view.getUserInputB();

            if (!InputValidation.isValid(polynomA)) {
                view.showError("Polynomial A does not obey the format!");
                return;
            }
            if (!InputValidation.isValid(polynomB)) {
                view.showError("Polynomial B does not obey the format!");
                return;
            }

            calculator.setPolynomA(InputValidation.buildPolynomial(polynomA));
            calculator.setPolynomB(InputValidation.buildPolynomial(polynomB));
            calculator.multiply();

            view.setResultA(calculator.getPolynomR()[0].toString());
        }
    }

    class DivideListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String polynomA = view.getUserInputA();
            String polynomB = view.getUserInputB();

            if (!InputValidation.isValid(polynomA)) {
                view.showError("Polynomial A does not obey the format!");
                return;
            }
            if (!InputValidation.isValid(polynomB)) {
                view.showError("Polynomial B does not obey the format!");
                return;
            }
            if (InputValidation.buildPolynomial(polynomA).getDegree() < InputValidation.buildPolynomial(polynomB).getDegree()) {
                view.showError("Dividend's degree is less than divisor's");
                return;
            }

            calculator.setPolynomA(InputValidation.buildPolynomial(polynomA));
            calculator.setPolynomB(InputValidation.buildPolynomial(polynomB));
            calculator.divide();

            view.setResultA("Quotient = " + calculator.getPolynomR()[0].toString() + "   Remainder = " + calculator.getPolynomR()[1].toString());
        }
    }

    class DeriveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String polynom = view.getUserInputC();

            if (!InputValidation.isValid(polynom)) {
                view.showError("Polynomial does not obey the format!");
                return;
            }

            calculator.setPolynomA(InputValidation.buildPolynomial(polynom));
            calculator.derive();

            view.setResultB(calculator.getPolynomR()[0].toString());
        }
    }

    class IntegrateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String polynom = view.getUserInputC();

            if (!InputValidation.isValid(polynom)) {
                view.showError("Polynomial does not obey the format!");
                return;
            }

            calculator.setPolynomA(InputValidation.buildPolynomial(polynom));
            calculator.integrate();

            view.setResultB(calculator.getPolynomR()[0].toString());
        }
    }

    class ClearListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.reset();
        }
    }
}
