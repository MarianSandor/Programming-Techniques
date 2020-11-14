import controller.Controller;
import model.calculator.Calculator;
import model.calculator.PolynomialCalculator;
import input.InputValidation;
import model.polynom.Monomial;
import model.polynom.Polynom;
import view.View;

public class Main {
    public static void main(String[] args) {
        Polynom p1 = new Polynom();
        Polynom p2 = new Polynom();

        p1.addMonomial(new Monomial(4,4));
        p1.addMonomial(new Monomial(3,3));
        p1.addMonomial(new Monomial(0.5,1));
        p1.addMonomial(new Monomial(2,0));

        p2.addMonomial(new Monomial(5,3));
        p2.addMonomial(new Monomial(1,2));
        p2.addMonomial(new Monomial(4,1));
        p2.addMonomial(new Monomial(-7,0));

        p1.suppress();
        p2.suppress();

        System.out.println("P1 = " + p1);
        System.out.println("P2 = " + p2);

        System.out.println();

        System.out.println("P1 + P2 = " + PolynomialCalculator.add(p1,p2));
        System.out.println("P1 - P2 = " + PolynomialCalculator.subtract(p1,p2));

        System.out.println("P1 * P2 = " + PolynomialCalculator.multiply(p1,p2));

        System.out.println("P1' = " + PolynomialCalculator.derive(p1));
        System.out.println("P2' = " + PolynomialCalculator.derive(p2));

        System.out.println("S P1 dx= " + PolynomialCalculator.integrate(p1));
        System.out.println("S P2 dx= " + PolynomialCalculator.integrate(p2));

        Polynom p = p2;
        for (int i = 1; i <= 5; i++) {
            p = PolynomialCalculator.derive(p);
            System.out.println(i + " derivative of P = " + p);
        }

        Polynom p3 = new Polynom();
        Polynom p4 = new Polynom();

        p3.addMonomial(new Monomial(1,3));
        p3.addMonomial(new Monomial(-4,2));
        p3.addMonomial(new Monomial(2,1));
        p3.addMonomial(new Monomial(5,0));
       // p3.addMonomial(new Monomial(3,1));
        //p3.addMonomial(new Monomial(-2,0));

        p4.addMonomial(new Monomial(1,1));
        p4.addMonomial(new Monomial(-2,0));

        p3.suppress();
        p4.suppress();

        System.out.println("P3 = " + p3);
        System.out.println("P4 = " + p4);

        Polynom result[] = new Polynom[2];
        result =  PolynomialCalculator.divide(p3,p4);
        System.out.println("P3 / P4 => Q = " + result[0] + " R = " + result[1]);

        System.out.println("\n");

        System.out.println(InputValidation.isValid("x^2+x-1"));

        Polynom p5 = InputValidation.buildPolynomial("x^2+x-1");

        System.out.println(p5);

        Calculator calculator = new Calculator();
        View view = new View(calculator);
        Controller controller = new Controller(calculator, view);
    }
}
