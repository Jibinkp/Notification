package com.example.nottification_checking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Thread_sample extends AppCompatActivity implements View.OnClickListener {
EditText ed;
Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_sample);
        ed=(EditText)findViewById(R.id.editTextTextPersonName);
        b1=(Button) findViewById(R.id.button2);
        b1.setOnClickListener(this);
    }
    private void runthread() {
        final String s1 = ed.getText().toString();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        b1.setText(s1);
                        runthread();
                    }
                });
            }
        }, 1000);

    }
    @Override
    public void onClick(View v) {
        runthread();
    }
}