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
            String valid = "(+-*^";
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

    public boolean checkEnterButton(ArrayList<String> equation) {
        if (equation.isEmpty()) {
            return false;
        }
        String operators = "+-*/^";
        String lastItem = equation.get(equation.size() - 1);
        if(operators.contains(lastItem)) {
            return false;
        }

        int leftCount = 0;
        int rightCount = 0;
        int opCount = 0;
        int numCount = 0;
        for(String item : equation) {
            if(item.equals("(")) {
                leftCount += 1;
            } else if (item.equals(")")) {
                rightCount += 1;
            } else if (operators.contains(item)) {
                opCount += 1;
            } else {
                numCount += 1;
            }
        }
        System.out.println("left: " + leftCount);
        System.out.println("right: " + rightCount);
        if (leftCount != rightCount) {
            return false;
        }
        if (opCount + 1 != numCount) {
            return false;
        }

        return true;
    }

}
