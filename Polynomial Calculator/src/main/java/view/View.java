package view;

import model.calculator.Calculator;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * View class
 *
 * -generates the graphical interface
 * -creates the fields and buttons needed for the interaction with the user
 * -implements the methods that give the basic functionality
 */

public class View extends JFrame {

        private Calculator calculator;

        private JTextField userInputA = new JTextField(20);
        private JTextField userInputB = new JTextField(20);
        private JTextField userInputC = new JTextField(20);
        private JTextField resultA = new JTextField(40);
        private JTextField resultB = new JTextField(30);
        private JButton addBtn = new JButton("ADD");
        private JButton subtractBtn = new JButton("SUBTRACT");
        private JButton multiplyBtn = new JButton("MULTIPLY");
        private JButton divideBtn = new JButton("DIVIDE");
        private JButton deriveBtn = new JButton("DERIVE");
        private JButton integrateBtn = new JButton("INTEGRATE");
        private JButton clearBtn = new JButton("Clear");


        public View(Calculator calculator) {
            this.calculator = calculator;

            this.resultA.setEditable(false);
            this.resultB.setEditable(false);

            JPanel content = new JPanel();
            JPanel row1 = new JPanel();
            JPanel row2 = new JPanel();
            JPanel row3 = new JPanel();
            JPanel row4 = new JPanel();
            JPanel row5 = new JPanel();
            JPanel row6 = new JPanel();
            JPanel row7 = new JPanel();
            JPanel inter = new JPanel();


            row1.setLayout(new FlowLayout());
            row1.add(new JLabel("PolynomialA:"));
            row1.add(userInputA);
            row1.add(new JLabel("PolynomialB:"));
            row1.add(userInputB);

            row2.setLayout(new FlowLayout());
            row2.add(new JLabel("Result:"));
            row2.add(resultA);

            row3.setLayout(new FlowLayout());
            row3.add(addBtn);
            row3.add(subtractBtn);
            row3.add(multiplyBtn);
            row3.add(divideBtn);

            row4.setLayout(new FlowLayout());
            row4.add(new JLabel("Polynomial:"));
            row4.add(userInputC);

            row5.setLayout(new FlowLayout());
            row5.add(new JLabel("Result:"));
            row5.add(resultB);

            row6.setLayout(new FlowLayout());
            row6.add(deriveBtn);
            row6.add(integrateBtn);

            row7.setLayout(new FlowLayout());
            row7.add(clearBtn);

            inter.setLayout(new GridLayout(3,1));
            JSeparator separator1 = new JSeparator();
            JSeparator separator2 = new JSeparator();
            JSeparator separator3 = new JSeparator();
            separator1.setOrientation(SwingConstants.HORIZONTAL);
            separator1.setBackground(Color.BLUE);
            separator2.setOrientation(SwingConstants.HORIZONTAL);
            separator2.setBackground(Color.BLUE);
            separator3.setOrientation(SwingConstants.HORIZONTAL);
            separator3.setBackground(Color.BLUE);
            inter.add(separator1);
            inter.add(separator2);
            inter.add(separator3);


            content.setLayout(new GridLayout(8,1));
            content.add(row1);
            content.add(row2);
            content.add(row3);
            content.add(inter);
            content.add(row4);
            content.add(row5);
            content.add(row6);
            content.add(row7);

            this.setContentPane(content);
            this.pack();

            this.setTitle("Polynomial Calculator");
            this.setSize(600,300);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(true);
        }

        public void reset() {
            userInputA.setText("");
            userInputB.setText("");
            userInputC.setText("");
            resultA.setText("");
            resultB.setText("");
        }

        public String getUserInputA() {
            return userInputA.getText();
        }

        public String getUserInputB() {
            return userInputB.getText();
        }

        public String getUserInputC() {
            return userInputC.getText();
        }

        public void setResultA(String text) {
            resultA.setText(text);
        }

        public void setResultB(String text) {
            resultB.setText(text);
        }

        public void showError(String error) {
            JOptionPane.showMessageDialog(this, error);
        }

        public void addAddListener(ActionListener actionListener) {
            addBtn.addActionListener(actionListener);
        }

        public void addSubtractListener(ActionListener actionListener) {
            subtractBtn.addActionListener(actionListener);
        }

        public void addMultiplyListener(ActionListener actionListener) {
            multiplyBtn.addActionListener(actionListener);
        }

        public void addDivideListener(ActionListener actionListener) {
            divideBtn.addActionListener(actionListener);
        }

        public void addDeriveListener(ActionListener actionListener) {
            deriveBtn.addActionListener(actionListener);
        }

        public void addIntegrateListener(ActionListener actionListener) {
            integrateBtn.addActionListener(actionListener);
        }

        public void addClearListener(ActionListener actionListener) {
            clearBtn.addActionListener(actionListener);
        }
}
