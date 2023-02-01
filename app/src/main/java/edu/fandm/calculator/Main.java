package edu.fandm.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;


public class Main extends AppCompatActivity {

    public ArrayList<String> equation = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInputButtons();
    }

    protected void setInputButtons() {
        Button b1 = findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {handleInputButton(b1);}});

        Button b2 = findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {handleInputButton(b2);}});

        Button b3 = findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {handleInputButton(b3);}});

        Button b4 = findViewById(R.id.button4);
        b4.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {handleInputButton(b4);}});

        Button b5 = findViewById(R.id.button5);
        b5.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {handleInputButton(b5);}});

        Button b6 = findViewById(R.id.button6);
        b6.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {handleInputButton(b6);}});

        Button b7 = findViewById(R.id.button7);
        b7.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {handleInputButton(b7);}});

        Button b8 = findViewById(R.id.button8);
        b8.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {handleInputButton(b8);}});

        Button b9 = findViewById(R.id.button9);
        b9.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {handleInputButton(b9);}});

        Button b0 = findViewById(R.id.button0);
        b0.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {handleInputButton(b0);}});

        Button bDecimal = findViewById(R.id.buttonDecimal);
        bDecimal.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {handleInputButton(bDecimal);}});

        Button bLeftP = findViewById(R.id.buttonLeftP);
        bLeftP.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {handleInputButton(bLeftP);}});

        Button bRightP = findViewById(R.id.buttonRightP);
        bRightP.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {handleInputButton(bRightP);}});

        Button bAdd = findViewById(R.id.opAdd);
        bAdd.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {handleInputButton(bAdd);}});

        Button bSubtract = findViewById(R.id.opSubtract);
        bSubtract.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {handleInputButton(bSubtract);}});

        Button bMultiply = findViewById(R.id.opMultiply);
        bMultiply.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {handleInputButton(bMultiply);}});

        Button bDivide = findViewById(R.id.opDivide);
        bDivide.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {handleInputButton(bDivide);}});

        Button bSquare = findViewById(R.id.opSquare);
        bSquare.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {handleInputButton(bSquare);}});

    }


    protected void handleInputButton(Button b) {
        String s = b.getText().toString();
        equation.add(s);
        updateForumla();
    }

    protected void updateForumla() {
        StringBuilder s = new StringBuilder();
        for(String input: equation) {
            s.append(input);
        }
        TextView tv = findViewById(R.id.formula);
        tv.setText(s.toString());
    }
}