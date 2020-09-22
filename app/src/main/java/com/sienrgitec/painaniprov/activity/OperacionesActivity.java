package com.sienrgitec.painaniprov.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.sienrgitec.painaniprov.R;
import com.sienrgitec.painaniprov.config.Globales;


public class OperacionesActivity extends AppCompatActivity {


    public Globales globales;
    private Button btnIniOpe;
    private Button btnFinOpe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaciones);


        btnIniOpe = (Button) findViewById(R.id.btnIniOpe);
        btnFinOpe = (Button) findViewById(R.id.btnFinOpe);


        btnIniOpe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(OperacionesActivity.this , InicioOpeActivity.class ));


            }
        });

        btnFinOpe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(OperacionesActivity.this , FinOpeActivity.class ));


            }
        });


    }
}

