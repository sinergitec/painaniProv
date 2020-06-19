package com.sienrgitec.painaniprov.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.sienrgitec.painaniprov.R;
import com.sienrgitec.painaniprov.config.Globales;
import com.sienrgitec.painaniprov.model.ctUsuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ConsultaPagos extends AppCompatActivity {
    private Globales globales;
    private String url = globales.URL;

    private TextView tvFecha, tvAtendidosT, tvIB, tvAporta, tvMontoProp, tvMontoNeto;


    private static RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_pagos);

        tvFecha      = (TextView) findViewById(R.id.textFec);
        tvAtendidosT = (TextView) findViewById(R.id.textAtendidosT);
        tvIB         = (TextView) findViewById(R.id.tvIb);
        tvAporta     = (TextView) findViewById(R.id.tvAporta);
        tvMontoProp  = (TextView) findViewById(R.id.tvMontoProp);
        tvMontoNeto  = (TextView) findViewById(R.id.tvMontoNeto);

        BuscaPagosProv();
    }


    public void BuscaPagosProv(){
        getmRequestQueue();

        String urlParams = String.format(url + "cpPagosProveedor?ipiProveedor=%1$s", globales.g_ctProveedor.getiProveedor());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urlParams, null, new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject respuesta = response.getJSONObject("response");
                            Log.i("respuesta--->", respuesta.toString());

                            Boolean Error = respuesta.getBoolean("oplError");
                            String Mensaje = respuesta.getString("opcError");
                            JSONObject ds_cpPagosEnc = respuesta.getJSONObject("tt_cpPagosProveedor");
                            JSONObject ds_cpPagosDet = respuesta.getJSONObject("tt_cpPagosProveedorDet");

                            JSONArray tt_opPagosProveedor  = ds_cpPagosEnc.getJSONArray("tt_cpPagosProveedor");
                            JSONArray tt_opPagosProveedorDet  = ds_cpPagosDet.getJSONArray("tt_cpPagosProveedorDet");



                            if (Error == true) {

                                MuestraMensaje("Error", Mensaje);
                                return;

                            } else {


                            }
                        } catch (JSONException e) {
                            androidx.appcompat.app.AlertDialog.Builder myBuild = new androidx.appcompat.app.AlertDialog.Builder(ConsultaPagos.this);
                            myBuild.setMessage("Error en la conversión de Datos. Vuelva a Intentar. " + e);
                            myBuild.setTitle(Html.fromHtml("<font color ='#FF0000'> ERROR CONVERSION </font>"));
                            myBuild.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            });
                            androidx.appcompat.app.AlertDialog dialog = myBuild.create();
                            dialog.show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("Error Respuesta", error.toString());
                        androidx.appcompat.app.AlertDialog.Builder myBuild = new androidx.appcompat.app.AlertDialog.Builder(ConsultaPagos.this);
                        myBuild.setMessage("No se pudo conectar con el servidor. Vuelva a Intentar. " + error.toString());
                        myBuild.setTitle(Html.fromHtml("<font color ='#FF0000'> ERROR RESPUESTA </font>"));
                        myBuild.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        androidx.appcompat.app.AlertDialog dialog = myBuild.create();
                        dialog.show();
                    }
                }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("ipiProveedor", globales.g_ctProveedor.getiProveedor().toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        // Access the RequestQueue through your singleton class.
        mRequestQueue.add(jsonObjectRequest);

    }

    public void MuestraMensaje(String vcTitulo, String vcMensaje) {
        AlertDialog.Builder myBuild = new AlertDialog.Builder(ConsultaPagos.this);
        myBuild.setMessage(vcMensaje);
        myBuild.setTitle(Html.fromHtml("<font color ='#FF0000'>" + vcTitulo + "</font>"));
        myBuild.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                return;

            }
        });
        AlertDialog dialog = myBuild.create();
        dialog.show();
        return;

    }

    public void getmRequestQueue() {
        try {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(getApplicationContext());
                //your code
            }
        } catch (Exception e) {
            Log.d("Volley", e.toString());
        }
    }
}