package edu.fandm.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    public ArrayAdapter<String> aa;
    public ArrayList<String> history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent i = getIntent();
        history = i.getStringArrayListExtra("hist");

        if(i != null) {
            ListView lv = (ListView) findViewById(R.id.lv);
            this.aa = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, history);
            lv.setAdapter(aa);
        }
    }
}