package edu.fandm.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//import android.view.View;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.Stack;
import java.util.PriorityQueue;


public class Main extends AppCompatActivity {

    public static final String TAG = "MAIN";
    public ArrayList<String> equation = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInputButtons();
        Log.d(TAG, "App started");
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

        Button bNegative = findViewById(R.id.negative);
        bNegative.setOnClickListener(view -> handleNegativeButton(bNegative));

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
        updateFormula();
    }

    protected void handleNegativeButton(Button b) {
        ErrorCheck ec = new ErrorCheck();
        String negation = "~";

        boolean passes = ec.checkNegativeInput(equation);
        if(passes) {
            equation.add(negation);
            updateFormula();
        }

    }

    protected void handleOperatorButton(Button b) {

        ErrorCheck ec = new ErrorCheck();

        String operator = b.getText().toString();
        boolean passes = ec.checkOperatorInput(operator, equation);

        if(passes) {
            equation.add(operator);
            updateFormula();
        }
    }

    protected void handleClearButton(){
        equation.clear();
        updateFormula();
    }

    protected void handleUndoButton(){
        if(!equation.isEmpty()) {
            equation.remove(equation.size() - 1);
            updateFormula();
        }

    }

    protected void handleEnterButton(){
        ErrorCheck ec = new ErrorCheck();
        boolean passes = ec.checkEnterButton(equation);
        if(passes) {
            shuntingYard();
        } else {
            Toast error = writeErrorMessage("Invalid Expression");
            error.show();
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

        printArray(tmp);

        //PriorityQueue<String> queue = new PriorityQueue<>();



        for(String item : tmp) {
            if (operators.contains(item)) {
                String s2 = stack.pop();
                String s1 = stack.pop();

                if (s2.charAt(0) == '~') {
                    s2 = "-" + s2.substring(1);
                }
                if (s1.charAt(0) == '~') {
                    s1 = "-" + s1.substring(1);
                }

                double num2 = Double.parseDouble(s2);
                double num1 = Double.parseDouble(s1);
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

        String lastTwo = ans.substring(ans.length() - 2);
        if (lastTwo.equals(".0")) {
            ans = ans.substring(0, ans.length() - 2);
        }
        equation.add(ans);
        updateFormula();


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

    public void updateFormula() {
        TextView tv = findViewById(R.id.formula);
        if (equation.isEmpty()) {
            tv.setText(tv.getHint().toString());
        } else {
            StringBuilder s = new StringBuilder();
            for (String input : equation) {
                if(input.charAt(0) == '~') {
                    System.out.println("negative num");
                    if (input.length() > 1) {
                        String tmp = "-" + input.substring(1);
                        s.append(tmp);
                    } else {
                        s.append("-");
                    }
                } else {
                    s.append(input);
                }
            }
            tv.setText(s.toString());
        }
        printArray(equation);
    }

    public Toast writeErrorMessage(String s){
        Toast error = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        ViewGroup group = (ViewGroup) error.getView();
        TextView messageTextView = (TextView) group.getChildAt(0); //        Toast error = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT); https://stackoverflow.com/questions/5274354/how-can-we-increase-the-font-size-in-toast
        messageTextView.setTextSize(25);
        messageTextView.setTextColor(this.getResources().getColor(R.color.red));  //https://stackoverflow.com/questions/4499208/android-setting-text-view-color-from-java-code
        error.setGravity(Gravity.CENTER, 0, 0);
        return error;

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