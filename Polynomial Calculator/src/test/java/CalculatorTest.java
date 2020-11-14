import input.InputValidation;
import model.calculator.Calculator;
import model.polynom.Polynom;
import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.TestCase.assertEquals;

public class CalculatorTest {

    Calculator calculator;

    public CalculatorTest() {
        calculator = new Calculator();
        calculator.setPolynomA(InputValidation.buildPolynomial("x^3-4x^2+2x+5"));
        calculator.setPolynomB(InputValidation.buildPolynomial("x-2"));
    }

    @Test
    public void testAdd() {
        calculator.add();

        assertEquals(calculator.getPolynomR()[0].toString(),"x^3-4x^2+3x+3");
    }

    @Test
    public void testSubtract() {
        calculator.subtract();

        assertEquals(calculator.getPolynomR()[0].toString(),"x^3-4x^2+x+7");
    }

    @Test
    public void testMultiply() {
        calculator.multiply();

        assertEquals(calculator.getPolynomR()[0].toString(),"x^4-6x^3+10x^2+x-10");
    }

    @Test
    public void testDerive() {
        calculator.derive();

        assertEquals(calculator.getPolynomR()[0].toString(),"3x^2-8x+2");
    }

    @Test
    public void testIntegrate() {
        calculator.integrate();

        assertEquals(calculator.getPolynomR()[0].toString(),"0.25x^4-1.33x^3+x^2+5x");
    }

    @Test
    public void testDivide() {
        calculator.divide();

        assertEquals(calculator.getPolynomR()[0].toString(),"x^2-2x-2");
        assertEquals(calculator.getPolynomR()[1].toString(),"1");
    }

}
