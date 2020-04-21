package com.sienrgitec.painaniprov.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sienrgitec.painaniprov.R;

public class HomeActivity extends AppCompatActivity {


    private Button btnProductos;
    private Button btnPedXSurtir;
    private Button btnHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        btnProductos = (Button)  findViewById(R.id.btnProductos);
        btnPedXSurtir = (Button)  findViewById(R.id.btnPedXSustir);
        btnHistorico = (Button)  findViewById(R.id.btnHistorico);

        btnProductos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MenuProducto();

            }
        });

        btnPedXSurtir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MenuXSurtir();

            }
        });

        btnHistorico.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MenuHistorico();

            }
        });




    }

    private void MenuHistorico() {

    }

    private void MenuXSurtir() {
    }

    private void MenuProducto() {
        startActivity(new Intent(HomeActivity.this, ArticulosActivity.class));

    }
}

