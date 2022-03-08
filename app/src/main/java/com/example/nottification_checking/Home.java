package com.example.nottification_checking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity implements View.OnClickListener {
Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        b1=(Button)findViewById(R.id.thread);
        b1.setOnClickListener(this);
        b2=(Button)findViewById(R.id.asyc);
        b2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==b1)
        {
//            startActivity(new Intent(getApplicationContext(),Thread_sample.class));



        }
        else if(v==b2)
        {
            startActivity(new Intent(getApplicationContext(),Thread_sample.class));
        }

    }
}