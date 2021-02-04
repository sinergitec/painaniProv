package com.sienrgitec.painaniprov.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
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
import com.sienrgitec.painaniprov.adapter.opPedProvBanAdapter;
import com.sienrgitec.painaniprov.adapter.opPedProvConsulaAdapter;
import com.sienrgitec.painaniprov.config.Globales;
import com.sienrgitec.painaniprov.model.ctProveedor;
import com.sienrgitec.painaniprov.model.opPedidoProveedor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pedidohistoricoActivity extends AppCompatActivity  implements View.OnClickListener {

    public Globales globales;

    private static RequestQueue mRequestQueue;
    private String url = globales.URL;

    private ListView lvPedidos;
    private opPedProvConsulaAdapter adapter;
    private List<opPedidoProveedor> lista_pedprov;
    private ctProveedor proveedor;


    Button btnBuscar;
    EditText editFechaIni,editFechaFin , editTotal;
    private  int dia,mes,ano,hora,minutos;

    private  String FechaIni , FechaFin;

    static final int DATE_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidohistorico);






        // Create a new instance of DatePickerDialog and return it

        editFechaIni=(EditText) findViewById(R.id.editFechaIni);
        editFechaFin=(EditText) findViewById(R.id.editFechaFin);
        editTotal=(EditText) findViewById(R.id.editTotal);
        btnBuscar=(Button)findViewById(R.id.btnBuscar);


        final Calendar c= Calendar.getInstance();
        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH);
        ano=c.get(Calendar.YEAR);

        editFechaIni.setText(dia + "-" + (mes + 1) +"-"+ ano);

        editFechaFin.setText(dia + "-" + (mes + 1) +"-"+ ano);

        FechaIni= ano + "-" + String.format("%02d", (mes + 1)) +"-"+ String.format("%02d", dia);
        FechaFin= ano + "-" + String.format("%02d", (mes + 1))  +"-"+ String.format("%02d", dia);


        editFechaIni.setOnClickListener(this);

        editFechaFin.setOnClickListener(this);

        proveedor = globales.g_ctProveedor;

        lvPedidos = (ListView) findViewById(R.id.lvPedidos);
        lista_pedprov = new ArrayList<opPedidoProveedor>();

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPedidos( proveedor.getiProveedor(),  globales.g_ctDomicilio.getiDomicilio(), FechaIni ,FechaFin); //proveedor.getiProveedor());
            }
        });


    }




    public void getPedidos (int ipiProveedor , int ipiDomicilio , String FechaIni , String FechaFin){
        getmRequestQueue();


      //  String urlParams = String.format(url + "opPedidoProveedor?ipiProveedor=%1$s&ipcCuales=%2$s", ipiProveedor, "TODOS");
        String urlParams = String.format(url + "ProvHistPedidos?ipiProveedor=%1$s&ipiDomicilio=%2$s&ipiFechaIni=%3$s&ipiFechaFin=%4$s", ipiProveedor, ipiDomicilio, FechaIni, FechaFin);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urlParams, null, new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject respuesta = response.getJSONObject("response");
                            Log.i("respuesta--->", respuesta.toString());


                            String Mensaje = respuesta.getString("opcMensaje");
                            Boolean Error = respuesta.getBoolean("oplError");
                            JSONObject ds_opPedidoProveedor = respuesta.getJSONObject("tt_opPedidoProveedor");

                            JSONArray tt_opPedidoProveedor  = ds_opPedidoProveedor.getJSONArray("tt_opPedidoProveedor");

                            lista_pedprov = Arrays.asList(new Gson().fromJson(tt_opPedidoProveedor.toString(), opPedidoProveedor[].class));

                            ArrayList<opPedidoProveedor> arrayPedProv = new ArrayList<opPedidoProveedor>(lista_pedprov);

                            Collections.sort(arrayPedProv, new Comparator<opPedidoProveedor>() {

                                @Override
                                public int compare(opPedidoProveedor o1, opPedidoProveedor o2) {
                                    return new Integer(o2.getiPedido() ).compareTo(new Integer(o1.getiPedido()));
                                }
                            });


                            Double total =0.0 ;
                            for (opPedidoProveedor obj :lista_pedprov){
                                total = total + obj.getDeImporte().doubleValue();
                            }

                            Log.i("total",total.toString());

                            editTotal.setText(String.valueOf(total));


                            adapter = new opPedProvConsulaAdapter(pedidohistoricoActivity.this, (ArrayList<opPedidoProveedor>) arrayPedProv);
                            lvPedidos.setAdapter(adapter);


/*
                            lvPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    Log.i("click" ,"123");

                                    opPedidoProveedor objctArtProv = new opPedidoProveedor();

                                    objctArtProv = (opPedidoProveedor) adapter.getItem(position);

                                    Log.i("item" , objctArtProv.getiPedido().toString());

                                    startActivity(new Intent(pedidohistoricoActivity.this, pedidodetalleActivity.class));
                                    finish();




                                }
                            });

*/

                        } catch (JSONException e) {

                            AlertDialog.Builder myBuild = new AlertDialog.Builder(pedidohistoricoActivity.this);
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
                        AlertDialog.Builder myBuild = new AlertDialog.Builder(pedidohistoricoActivity.this);
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


    public void MuestraMensaje(String vcTitulo,  String vcMensaje){
        AlertDialog.Builder myBuild = new AlertDialog.Builder(pedidohistoricoActivity.this);
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






    @Override
    public void onClick(View v) {
        if(v==editFechaIni){

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    editFechaIni.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);




                    FechaIni= year + "-" +  String.format("%02d", (monthOfYear + 1)) +"-"+   String.format("%02d", dayOfMonth) ;
                }
            } ,ano,mes,dia);
            datePickerDialog.show();

        }else if(v==editFechaFin){
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    editFechaFin.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    FechaFin= year + "-" +  String.format("%02d", (monthOfYear + 1)) +"-"+   String.format("%02d", dayOfMonth) ;
                }
            } ,ano,mes,dia);
            datePickerDialog.show();
        }

    }





}
