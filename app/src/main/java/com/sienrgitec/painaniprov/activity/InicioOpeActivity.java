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

public class InicioOpeActivity extends AppCompatActivity {


    public Globales globales;

    private static RequestQueue mRequestQueue;
    private String url = globales.URL;

    private RadioGroup rbComunidad;
    private List<ctComisiones> listaComunidad;
    private EditText txtOtraComunidad;

    private RadioGroup rbTitlani;
    private List<ctComisiones> listaTitlani;
    private EditText txtOtraTitlani;


    private Button btnInicio;


    public Integer viComision = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        rbComunidad   = (RadioGroup) findViewById(R.id.rbComunidad);
        txtOtraComunidad        = (EditText)   findViewById(R.id.txtOtraComunidad);

        rbTitlani   = (RadioGroup) findViewById(R.id.rbTitlatli);
        txtOtraTitlani        = (EditText)   findViewById(R.id.txtOtratitlani);

        btnInicio = (Button) findViewById(R.id.btnAceptar);


        btnInicio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                inicio();
            }
        });


        getComisionesComunidad();
        getComisionesTitlani();
    }


    public void inicio(){


        double comision ,propina;

        comision = Double.parseDouble(txtOtraComunidad.getText().toString());

        propina = Double.parseDouble(txtOtraTitlani.getText().toString());


        Log.i("comision",  String.valueOf(comision));
        Log.i("propina", String.valueOf(propina));





        ArrayList<opDispProveedor> lista = new ArrayList<opDispProveedor>();

        opDispProveedor objDispProveedor= new opDispProveedor();

        objDispProveedor.setiProveedor(globales.g_ctProveedor.getiProveedor());
        objDispProveedor.setiUnidad(globales.g_ctProveedor.getiUnidad());
        objDispProveedor.setDtFecha(null);
        objDispProveedor.setiDomicilio(globales.g_ctDomicilio.getiDomicilio());
        objDispProveedor.setiComision(viComision);
        objDispProveedor.setDeComision(comision);
        objDispProveedor.setDePropina(propina);
        objDispProveedor.setDtCheckIn(null);
        objDispProveedor.setiCheckIn(0);
        objDispProveedor.setDtCheckOut(null);
        objDispProveedor.setiCheckOut(0);
        objDispProveedor.setId(null);


        lista.add(objDispProveedor);

        JSONObject jsonBody = new JSONObject();
        JSONObject jsonParams = new JSONObject();
        JSONObject jsonDataSet = new JSONObject();

        final Gson gson = new Gson();

        String JS_opDispProveedor = gson.toJson(
                lista,
                new TypeToken<ArrayList<opDispProveedor>>() {
                }.getType());



        try {
            JSONArray opDispProvArray  = new JSONArray(JS_opDispProveedor);


            jsonDataSet.put("tt_opDispProveedor",  opDispProvArray);
            jsonParams.put("ds_opDispProveedor", jsonDataSet);


            jsonBody.put("request", jsonParams);


            Log.i("request", jsonBody.toString());

        } catch (JSONException e) {
            e.printStackTrace();

            MuestraMensaje("Error", e.getMessage());

        }


        getmRequestQueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url + "operacionProveedor/", jsonBody, new Response.Listener<JSONObject>() {
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
                                MuestraMensaje("Aviso" , "Inicio de Opreciones Creado");


                            }

                        } catch (JSONException e) {
                            Log.i("Error JSONExcepcion", e.getMessage());
                            MuestraMensaje("Error" ,  e.getMessage());




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



    public void getComisionesComunidad (){
        getmRequestQueue();




        String urlParams = String.format(url + "ctComisiones?ipiUnidad=%1$s&ipiPersona=%2$s", globales.g_ctProveedor.getiUnidad() , 4 );

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



                            listaComunidad = Arrays.asList(new Gson().fromJson(tt_ctComisiones.toString(), ctComisiones[].class));

                            CreaBotonesComunidad();


                        } catch (JSONException e) {

                            AlertDialog.Builder myBuild = new AlertDialog.Builder(InicioOpeActivity.this);
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
                        AlertDialog.Builder myBuild = new AlertDialog.Builder(InicioOpeActivity.this);
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



    public void CreaBotonesComunidad(){




        Log.i("Botones","entro" + listaComunidad.size());

        final int esmelada = Color.parseColor("#05F7D9");
        final int blanco = Color.parseColor("#FFFFFF");



        int vxMod = 0, vyMod = 0, vCuantosMod = 0;
        for(final ctComisiones objComisiones: listaComunidad){

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
                        txtOtraComunidad.setEnabled(true);
                        txtOtraComunidad.setBackgroundColor(esmelada);
                    }else{
                        txtOtraComunidad.setEnabled(false);
                        txtOtraComunidad.setBackgroundColor(blanco);
                    }

                    txtOtraComunidad.setText(objComisiones.getDeValor().toString());


                }
            });
            rbComunidad.addView(rdbtn);
        }




    }


    /*titlani*/

    public void getComisionesTitlani (){
        getmRequestQueue();



        Log.i("comision" , globales.g_ctProveedor.getiUnidad().toString() );

        String urlParams = String.format(url + "ctComisiones?ipiUnidad=%1$s&ipiPersona=%2$s", globales.g_ctProveedor.getiUnidad() , 3  );


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



                            listaTitlani = Arrays.asList(new Gson().fromJson(tt_ctComisiones.toString(), ctComisiones[].class));

                            CreaBotonesTitlani();


                        } catch (JSONException e) {

                            AlertDialog.Builder myBuild = new AlertDialog.Builder(InicioOpeActivity.this);
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
                        AlertDialog.Builder myBuild = new AlertDialog.Builder(InicioOpeActivity.this);
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



    public void CreaBotonesTitlani(){




        Log.i("Botones","entro" + listaTitlani.size());

        final int esmelada = Color.parseColor("#05F7D9");
        final int blanco = Color.parseColor("#FFFFFF");



        int vxMod = 0, vyMod = 0, vCuantosMod = 0;
        for(final ctComisiones objComisiones: listaTitlani){

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

                   // viComision = objComisiones.getiComision();

                    if(objComisiones.getcComision().equals("OTRO")){
                        txtOtraTitlani.setEnabled(true);
                        txtOtraTitlani.setBackgroundColor(esmelada);
                    }else{
                        txtOtraTitlani.setEnabled(false);
                        txtOtraTitlani.setBackgroundColor(blanco);
                    }

                    txtOtraTitlani.setText(objComisiones.getDeValor().toString());


                }
            });
            rbTitlani.addView(rdbtn);
        }




    }

    /*titlani*/

    public void MuestraMensaje(String vcTitulo,  String vcMensaje){
        AlertDialog.Builder myBuild = new AlertDialog.Builder(InicioOpeActivity.this);
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