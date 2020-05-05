package com.sienrgitec.painaniprov.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sienrgitec.painaniprov.R;
import com.sienrgitec.painaniprov.config.Globales;

public class HomeActivity extends AppCompatActivity {


    private  Button btnBandeja;
    private Button btnProductos;
    private Button btnPedXSurtir;
    private Button btnHistorico;
    private TextView txtProveedor;

    public Globales globales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        btnProductos = (Button)  findViewById(R.id.btnProductos);
        btnPedXSurtir = (Button)  findViewById(R.id.btnPedXSustir);
        btnBandeja = (Button)  findViewById(R.id.btnBandeja);
        btnHistorico = (Button)  findViewById(R.id.btnHistorico);

        txtProveedor =(TextView) findViewById(R.id.txtProveedor);

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


        btnBandeja.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MenuBandeja();

            }
        });

        txtProveedor.setText(globales.g_ctProveedor.getcRazonS());


    }

    private void MenuHistorico() {

    }

    private void MenuXSurtir() {


    }

    private void MenuProducto() {
        startActivity(new Intent(HomeActivity.this, ArticulosActivity.class));

    }

    private void MenuBandeja() {
        startActivity(new Intent(HomeActivity.this , pedidobandejaActivity.class) );

    }



}

