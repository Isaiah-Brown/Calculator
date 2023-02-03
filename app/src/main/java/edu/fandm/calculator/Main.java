package edu.fandm.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import java.util.ArrayList;

import java.util.Stack;
import java.util.PriorityQueue;


public class Main extends AppCompatActivity {

    public ArrayList<String> equation = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInputButtons();
    }

    public void updateTextView(ArrayList<String> list) {

        TextView tv = findViewById(R.id.formula);
        if (list.isEmpty()) {
            tv.setText(tv.getHint().toString());
        } else {
            StringBuilder s = new StringBuilder();
            for (String input : list) {
                s.append(input);
            }
            tv.setText(s.toString());
        }
        printArray(equation);
    }

    protected void setInputButtons() {

        Button b1 = findViewById(R.id.button1);
        b1.setOnClickListener(view -> handleNumberButton(b1));

        Button b2 = findViewById(R.id.button2);
        b2.setOnClickListener(view -> handleNumberButton(b2));

        Button b3 = findViewById(R.id.button3);
        b3.setOnClickListener(view -> handleNumberButton(b3));

        Button b4 = findViewById(R.id.button4);
        b4.setOnClickListener(view -> handleNumberButton(b4));

        Button b5 = findViewById(R.id.button5);
        b5.setOnClickListener(view -> handleNumberButton(b5));

        Button b6 = findViewById(R.id.button6);
        b6.setOnClickListener(view -> handleNumberButton(b6));

        Button b7 = findViewById(R.id.button7);
        b7.setOnClickListener(view -> handleNumberButton(b7));

        Button b8 = findViewById(R.id.button8);
        b8.setOnClickListener(view -> handleNumberButton(b8));

        Button b9 = findViewById(R.id.button9);
        b9.setOnClickListener(view -> handleNumberButton(b9));

        Button b0 = findViewById(R.id.button0);
        b0.setOnClickListener(view -> handleNumberButton(b0));

        Button bDecimal = findViewById(R.id.buttonDecimal);
        bDecimal.setOnClickListener(view -> handleNumberButton(bDecimal));

        Button bLeftP = findViewById(R.id.buttonLeftP);
        bLeftP.setOnClickListener(view -> handleOperatorButton(bLeftP));

        Button bRightP = findViewById(R.id.buttonRightP);
        bRightP.setOnClickListener(view -> handleOperatorButton(bRightP));

        Button bAdd = findViewById(R.id.opAdd);
        bAdd.setOnClickListener(view -> handleOperatorButton(bAdd));

        Button bSubtract = findViewById(R.id.opSubtract);
        bSubtract.setOnClickListener(view -> handleOperatorButton(bSubtract));

        Button bMultiply = findViewById(R.id.opMultiply);
        bMultiply.setOnClickListener(view -> handleOperatorButton(bMultiply));

        Button bDivide = findViewById(R.id.opDivide);
        bDivide.setOnClickListener(view -> handleOperatorButton(bDivide));

        Button bSquare = findViewById(R.id.opSquare);
        bSquare.setOnClickListener(view -> handleOperatorButton(bSquare));

        Button bClear = findViewById(R.id.clear);
        bClear.setOnClickListener(view -> handleClearButton()); //https://stackoverflow.com/questions/57137935/anonymous-new-view-onclicklistener-can-be-replaced-with-lambda

        Button bUndo = findViewById(R.id.undo);
        bUndo.setOnClickListener(view -> handleUndoButton());

        Button bEnter = findViewById(R.id.enter);
        bEnter.setOnClickListener(view -> handleEnterButton());



    }

    protected void handleNumberButton(Button b) {
        String operators = "()+-*/^";
        String s = b.getText().toString();
        if(equation.isEmpty() || operators.contains(equation.get(equation.size()-1))) {
            equation.add(s);
        } else {
            String lastNum = equation.get(equation.size() -1);
            if (s.equals(".")) {
                if (lastNum.contains(".")) {    //There is already a decimal in the number, do not add another
                    return;
                }
            }
            equation.remove(equation.size() -1);
            equation.add(lastNum + s);
        }
        updateTextView(equation);
    }

    protected void handleOperatorButton(Button b) {

        ErrorCheck ec = new ErrorCheck();

        String operator = b.getText().toString();
        boolean passes = ec.checkOperatorInput(operator, equation);

        if(passes) {
            equation.add(operator);
            updateTextView(equation);
        }
    }

    protected void handleClearButton(){
        equation.clear();
        updateTextView(equation);
    }

    protected void handleUndoButton(){
        if(!equation.isEmpty()) {
            equation.remove(equation.size() - 1);
        }
        updateTextView(equation);
    }

    protected void handleEnterButton(){
        ErrorCheck ec = new ErrorCheck();
        boolean passes = ec.checkEnterButton(equation);
        if(passes) {
            shuntingYard();
        }
    }

    protected void shuntingYard(){
        Stack<String> stack = new Stack<>();
        ArrayList<String> tmp = new ArrayList<>();
        equation.add(")");
        stack.push("(");

        String operators = "()+-*/^";

        printArray(equation);
        for (String item : equation) {
            printArray(tmp);
            printStack(stack);
            if (operators.contains(item)) {
                if (item.equals("(")) {
                    stack.push("(");

                } else if (item.equals(")")) {
                    while(!stack.peek().equals("(")) {
                        tmp.add(stack.pop());
                    }
                    stack.pop();
                } else {
                    while (weight(item) >= weight(stack.peek())) {
                        tmp.add(stack.pop());
                    }
                    stack.push(item);
                }
            } else {
                tmp.add(item);
            }
        }
        updateTextView(tmp);
        printArray(tmp);

        //PriorityQueue<String> queue = new PriorityQueue<>();



        for(String item : tmp) {
            if (operators.contains(item)) {
                double num2 = Double.parseDouble(stack.pop());
                double num1 = Double.parseDouble(stack.pop());
                double ans;
                switch(item) {
                    case "+":
                        ans = num1 + num2;
                        break;
                    case "-":
                        ans = num1 - num2;
                        break;
                    case "*":
                        ans = num1 * num2;
                        break;
                    case "/":
                        ans = num1 / num2;
                        break;
                    case "^":
                        ans = Math.pow(num1, num2);
                        break;
                    default:
                        ans = 0.0;
                }
                System.out.println(String.valueOf(ans));
                stack.push(String.valueOf(ans));
            } else {
                stack.push(item);
            }
        }

        String ans = stack.pop();
        equation.clear();
        equation.add(ans);
        updateTextView(equation);


    }

    protected Integer weight(String s) {
        if(s.equals("(")) {
            return 4;
        } else if (s.equals("^")) {
            return 1;
        } else if ((s.equals("*") || s.equals("/"))) {
            return 2;
        } else {
            return 3;
        }
    }

    public void printArray(ArrayList<String> a) {
        StringBuilder s = new StringBuilder();

        for(String item : a) {
            s.append(item);
            System.out.println(item + " ");
        }
        System.out.println("Array :" + s);
    }

    public void printStack(Stack<String> s) {
        StringBuilder str = new StringBuilder();
        Stack<String> tmp = new Stack<>();

        while(!s.empty()) {
            String strTmp = s.pop();
            str.append(strTmp);
            tmp.push(strTmp);
        }
        System.out.println("Stack: " + s);

        while(!tmp.empty()) {
            s.push(tmp.pop());
        }
    }
}