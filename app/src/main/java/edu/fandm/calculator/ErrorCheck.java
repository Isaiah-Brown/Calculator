package edu.fandm.calculator;

import java.util.ArrayList;

public class ErrorCheck extends Main {

    public boolean checkOperatorInput(String operator, ArrayList<String> equation) {
        System.out.println("here");
        if (equation.isEmpty() && operator.equals("(")) {
            return true;
        }  else if(equation.isEmpty()) {
            System.out.println("Empty");
            return false;
        }


        String lastItem = equation.get(equation.size() - 1);
        String finalItem = lastItem.substring(lastItem.length() - 1);

        System.out.println("here");

        if(finalItem.equals(".") || finalItem.equals("#")) {
            return false;
        }
        if (operator.equals("(")) {
            String valid = "()+-*^";
            if (!valid.contains(lastItem)) {
                return false;
            }
        } else if (operator.equals(")")) {
            String notValid = "(+-*^";
            if (notValid.contains(lastItem)) {
                return false;
            }
        } else {
            String notValid = "(+-*^";
            System.out.println(lastItem);
            if (notValid.contains(lastItem)) {
                return false;
            }
        }
        return true;
    }
}
