package com.sienrgitec.painaniprov.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sienrgitec.painaniprov.R;
import com.sienrgitec.painaniprov.config.Globales;
import com.sienrgitec.painaniprov.model.ctComisiones;
import com.sienrgitec.painaniprov.model.opDispProveedor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinOpeActivity extends AppCompatActivity {


    public Globales globales;

    private static RequestQueue mRequestQueue;
    private String url = globales.URL;

    private RadioGroup mRgAllButtons;


    private List<ctComisiones> listaComision;


    private Button btnFin;

    private EditText etAporta;


    public Integer viComision = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin);

        mRgAllButtons   = (RadioGroup) findViewById(R.id.rb);

        etAporta        = (EditText)   findViewById(R.id.etOtraComision);

        btnFin = (Button) findViewById(R.id.btnAceptar);



       btnFin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                fin();
            }
        });

        getComisiones();
    }


    public void fin(){


        double vAportacion;

        vAportacion = Double.parseDouble(etAporta.getText().toString());

        ArrayList<opDispProveedor> lista = new ArrayList<opDispProveedor>();


        opDispProveedor objDispProveedor= new opDispProveedor();
        objDispProveedor.setiProveedor(globales.g_ctProveedor.getiProveedor());
        objDispProveedor.setiUnidad(globales.g_ctProveedor.getiUnidad());
        objDispProveedor.setDtFecha(null);
        objDispProveedor.setiDomicilio(globales.g_ctDomicilio.getiDomicilio());
        objDispProveedor.setDePropina(vAportacion);
        objDispProveedor.setDtCheckIn(null);
        objDispProveedor.setiCheckIn(0);
        objDispProveedor.setDtCheckOut(null);
        objDispProveedor.setiCheckOut(0);
        objDispProveedor.setId(null);
        objDispProveedor.setiComision(viComision);

        lista.add(objDispProveedor);


        JSONObject jsonBody = new JSONObject();
        JSONObject jsonParams = new JSONObject();
        JSONObject jsonDataSet = new JSONObject();

        final Gson gson = new Gson();

        String JS_opDispProv = gson.toJson(
                lista,
                new TypeToken<ArrayList<opDispProveedor>>() {
                }.getType());



        try {
            JSONArray opDispProvArray  = new JSONArray(JS_opDispProv);


            jsonDataSet.put("tt_opDispProveedor",  opDispProvArray);
            jsonParams.put("ds_opDispProveedor", jsonDataSet);


            jsonBody.put("request", jsonParams);


            Log.i("Response", jsonBody.toString());

        } catch (JSONException e) {
            e.printStackTrace();

            MuestraMensaje("Error", e.getMessage());

        }


        getmRequestQueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url + "operacionProveedor/", jsonBody, new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject respuesta = response.getJSONObject("response");
                            Log.i("respuesta resrt->", "mensaje: " + respuesta.toString());

                            Boolean Error = respuesta.getBoolean("oplError");
                            String Mensaje = respuesta.getString("opcMensaje");

                            //JSONObject ds_opdispPainani   = respuesta.getJSONObject("tt_opDispPainani");
                            //JSONArray tt_opDispPainani = ds_opdispPainani.getJSONArray("tt_opDispPainani");
                            //globales.g_opDispPList       = Arrays.asList(new Gson().fromJson(tt_opDispPainani.toString(), opDispPainani[].class));



                            if (Error == true) {

                                MuestraMensaje("Error" , Mensaje);


                            } else {
                                MuestraMensaje("Aviso" , "Fin de Opreciones Creado");


                            }

                        } catch (JSONException e) {
                            Log.i("Error JSONExcepcion", e.getMessage());


                            Log.i("Error JSONExcepcion", e.getMessage());

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("Error", error.toString());

                        MuestraMensaje("Error", error.toString());

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        mRequestQueue.add(jsonObjectRequest);

    }


    public void getComisiones (){
        getmRequestQueue();


        String urlParams = String.format(url + "ctComisiones?ipiPersona=%1$s", 4);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urlParams, null, new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject respuesta = response.getJSONObject("response");
                            Log.i("respuesta--->", respuesta.toString());


                            String Mensaje = respuesta.getString("opcError");
                            Boolean Error = respuesta.getBoolean("oplError");
                            JSONObject ds_opPedidoProveedor = respuesta.getJSONObject("tt_ctComisiones");

                            JSONArray tt_ctComisiones  = ds_opPedidoProveedor.getJSONArray("tt_ctComisiones");



                            listaComision = Arrays.asList(new Gson().fromJson(tt_ctComisiones.toString(), ctComisiones[].class));

                            CreaBotonesCom();


                        } catch (JSONException e) {

                            AlertDialog.Builder myBuild = new AlertDialog.Builder(FinOpeActivity.this);
                            myBuild.setMessage("Error en la conversión de Datos. Vuelva a Intentar. " + e);
                            myBuild.setTitle(Html.fromHtml("<font color ='#FF0000'> ERROR CONVERSION </font>"));
                            myBuild.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            });
                            AlertDialog dialog = myBuild.create();
                            dialog.show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // TODO: Handle error
                        Log.i("Error Respuesta", error.toString());
                        AlertDialog.Builder myBuild = new AlertDialog.Builder(FinOpeActivity.this);
                        myBuild.setMessage("No se pudo conectar con el servidor. Vuelva a Intentar. " + error.toString());
                        myBuild.setTitle(Html.fromHtml("<font color ='#FF0000'> ERROR RESPUESTA </font>"));
                        myBuild.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        AlertDialog dialog = myBuild.create();
                        dialog.show();
                    }
                }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();



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



    public void CreaBotonesCom(){




        Log.i("Botones","entro" + listaComision.size());

        final int esmelada = Color.parseColor("#05F7D9");
        final int blanco = Color.parseColor("#FFFFFF");



        int vxMod = 0, vyMod = 0, vCuantosMod = 0;
        for(final ctComisiones objComisiones: listaComision){

            Log.i("for" , objComisiones.getDeValor().toString());
            vCuantosMod = vCuantosMod + 1;
            RadioButton rdbtn = new RadioButton(this);
            if(objComisiones.getcComision().equals("OTRO")){
                rdbtn.setText("Otro");
            }else {
                rdbtn.setText(objComisiones.getDeValor() + "%");
            }

            Drawable d = getResources().getDrawable(R.drawable.radiob);
            rdbtn.setBackgroundDrawable(d);

            rdbtn.setWidth(300);
            rdbtn.setHeight(80);
            rdbtn.setX(vxMod);
            rdbtn.setY(vyMod);
            vyMod = vyMod + 25;

            rdbtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    viComision = objComisiones.getiComision();

                    if(objComisiones.getcComision().equals("OTRO")){
                        etAporta.setEnabled(true);
                        etAporta.setBackgroundColor(esmelada);
                    }else{
                        etAporta.setEnabled(false);
                        etAporta.setBackgroundColor(blanco);
                    }

                    etAporta.setText(objComisiones.getDeValor().toString());


                }
            });
            mRgAllButtons.addView(rdbtn);
        }




    }

    public void MuestraMensaje(String vcTitulo,  String vcMensaje){
        AlertDialog.Builder myBuild = new AlertDialog.Builder(FinOpeActivity.this);
        myBuild.setMessage(vcMensaje);
        myBuild.setTitle(Html.fromHtml("<font color ='#FF0000'>" + vcTitulo +"</font>"));
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
    public void getmRequestQueue(){
        try{
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(getApplicationContext());
                //your code
            }
        }catch(Exception e){
            Log.d("Volley",e.toString());
        }
    }
}