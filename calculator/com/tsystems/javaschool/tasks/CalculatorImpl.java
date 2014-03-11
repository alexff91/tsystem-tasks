package com.tsystems.javaschool.tasks;

import java.math.BigDecimal;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandr
 * Date: 15.01.13
 * Time: 22:17
 */
public class CalculatorImpl implements Calculator {

    public CalculatorImpl() {
    }

    @Override
    public String evaluate(String param) {
        try {

            return calculateInfix(param);
        } catch (Exception e) {

            return null;
        }
    }

    /**
     * Method realises the algorithm to convert mathematical  expression from infix-notation
     * to reverse polish notation where at first written operands and then operators by order
     * of their priority. The name of algorithm is Sort Station. Spaces between operands and
     * operators consists for future parsing.
     *
     * @param s input string in infix-notation form
     * @return output string in reverse polish notation form
     */
    String sortStation(String s) {
        //result string
        String result = "";
        //stack for operators
        Stack<Character> stackO = new Stack<Character>();
        try {
            //for each character in input string s
            for (int i = 0; i < s.length(); i++) {
                String acc = s.substring(i, i + 1);
                char curChar = acc.charAt(0);
                //if char is digit or dot add it to result string
                if (acc.matches("[0-9]*\\.*")) result += acc;
                    //if open bracket occurs push it to stack
                else if (curChar == '(') stackO.push(curChar);
                    //if close bracket occurs pop operators to result string
                else if (curChar == ')') {
                    boolean openBrace = true;
                    while (openBrace) {
                        char popChar = stackO.pop();
                        //if occurs open bracket pop it from stack, but not add to result
                        if (popChar != '(')
                            //if not open bracket add to result string with spaces
                            result += " " + popChar + " ";
                        else {
                            //if after open bracket occurs any function char add it to result string
                            if (!stackO.empty() && stackO.peek().toString().matches("[\\Q*/+-\\E]")) {
                                // result += " " + stackO.pop() + " ";
                                openBrace = false;
                            } else {
                                openBrace = false;
                            }


                        }

                    }
                }
                //if operation1 priority lower than operator2 on top of stack add it to result
                else if (acc.matches("[\\Q*/+\\E]")) {
                    while (!stackO.empty() && getPriority(stackO.peek()) >= getPriority(curChar)
                            && !stackO.peek().equals('(')) {
                        result += " " + stackO.pop() + " ";


                    }
                    stackO.push(curChar);
                    result += " ";
                }
                //if occurs minus change the sign of digit and add +, if only not open brace in pop or its a
                //begin index
                else if (acc.charAt(0) == '-' && s.charAt(i + 1) != '(') {

                    while (!stackO.empty() && getPriority(stackO.peek()) >= getPriority(curChar)
                            && !stackO.peek().equals('(')) {
                        result += " " + stackO.pop() + " ";


                    }
                    if (!stackO.isEmpty() && stackO.peek() != '(' && i != '0')
                        stackO.push('+');
                    result += " -";
                    if (stackO.isEmpty() && i != 0) stackO.push('+');
                }//if minus before bracket add minus to stack
                else {
                    while (!stackO.empty() && getPriority(stackO.peek()) >= getPriority(curChar)
                            && !stackO.peek().equals('(')) {
                        result += " " + stackO.pop() + " ";
                    }
                    stackO.push(curChar);
                    result += " ";
                }
            }
            //add all other operators
            while (!stackO.isEmpty()) result += " " + stackO.pop() + " ";
            return result;
        } catch (Exception e) {
            return null;

        }
    }


    /**
     * Method returns the priority of operation +,-,*,/
     *
     * @param c
     * @return priority of operation from 1 to 3
     */
    static int getPriority(char c) {
        switch (c) {
            case '*':
            case '/':
                return 2;

            case '+':
            case '-':
                return 1;

            default:
                return 3;
        }

    }

    /**
     * Method realises the algorithm of calculating expression from in
     * reverse polish notation(rpn) where at first written operands and then operators by order
     * of their priority. Method puts operands to stack and when any operation occurs
     * it do this operation. At the end result will be on the top of stack.
     *
     * @param s input string in infix notation form
     * @return output string is result rounded by 4 digits after dot
     */
    public String calculateInfix(String s) {
        try {
            //stack of operands
            Stack<Double> stack = new Stack<Double>();
            //get rpn
            String backPolish = sortStation(s);
            //parse rpn by spaces
            StringTokenizer tokenizer = new StringTokenizer(backPolish, " ");
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                //if any number add to stack
                if (token.matches("\\-*[0-9]+\\.*[0-9]*")) {
                    stack.push(new Double(token));
                } else {
                    //if operation occurs do this operation
                    Double op2 = stack.pop();
                    Double op1 = stack.empty() ? 0 : stack.pop();
                    if (token.equals("+")) {
                        stack.push(op1 + op2);
                    } else if (token.equals("*")) {
                        stack.push(op1 * op2);
                    } else if (token.equals("/")) {
                        stack.push(op1 / op2);
                    } else if (token.equals("-")) {
                        stack.push(op1 - op2);
                    }
                }
            }
            //rounding by scaling factor 4
            BigDecimal result = BigDecimal.valueOf(stack.pop());
            result = result.setScale(4, BigDecimal.ROUND_HALF_DOWN);
            return result.toString();
        } catch (Exception e) {
            return null;

        }
    }


    public static void main(String[] args) {

        Calculator c = new CalculatorImpl();
        System.out.println(c.evaluate("45-(7*9+6)-5/6-(45+89)")); // Результат:  -158.8333

        c = new CalculatorImpl();
        System.out.println(c.evaluate("7*6/2+8")); // Результат: 29
        c = new CalculatorImpl();
        System.out.println(c.evaluate("-12)1//(")); // Результат: null

    }
}
