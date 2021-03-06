package com.sienrgitec.painaniprov.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.sienrgitec.painaniprov.R;
import com.sienrgitec.painaniprov.adapter.opPedDetAdapter;
import com.sienrgitec.painaniprov.config.Globales;
import com.sienrgitec.painaniprov.model.opPedidoDet;
import com.sienrgitec.painaniprov.model.opPedidoProveedor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pedidodetbanActivity extends AppCompatActivity {


    public Globales globales;

    private static RequestQueue mRequestQueue;
    private String url = globales.URL;

    private ListView lvDetalle;
    private opPedDetAdapter adapter;
    private List<opPedidoDet> lista_detalle;
    private opPedidoProveedor pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidodetalle);


        Intent intent = getIntent();


        if (!intent.hasExtra("pedido")) {

            AlertDialog.Builder alert = new AlertDialog.Builder(pedidodetbanActivity.this);
            alert.setTitle(Html.fromHtml("<font color ='#3b83bd'> Informacion </font>"));
            alert.setMessage("No se encontro en parametro articulo");
            alert.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    dialog.cancel();
                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();
        }


        pedido = (opPedidoProveedor) intent.getSerializableExtra("pedido");


        Log.i("direc", pedido.toString());



        lvDetalle = (ListView) findViewById(R.id.lvDetalle);
        lista_detalle = new ArrayList<opPedidoDet>();

        getDetallePedido(pedido.getiPedido(),pedido.getiPedidoProv()) ;
    }


    public void getDetallePedido (int ipiPedido ,int ipiPedProv){

        getmRequestQueue();

        // String urlParams = String.format(url + "/vtCargaOrden?ipcCveCia=%1$s&ipiFolio=%2$s", globales.vgCompania, viFolioSusp);

        String urlParams = String.format(url + "opPedidoDet?ipiPedido=%1$s&ipiPedProv=%2$s", ipiPedido, ipiPedProv);


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
                            JSONObject ds_opPedidoDet = respuesta.getJSONObject("tt_opPedidoDet");

                            JSONArray tt_opPedidoDet  = ds_opPedidoDet.getJSONArray("tt_opPedidoDet");

                            lista_detalle = Arrays.asList(new Gson().fromJson(tt_opPedidoDet.toString(), opPedidoDet[].class));



                            ArrayList<opPedidoDet> arrayDetalle = new ArrayList<opPedidoDet>(lista_detalle);

                            adapter = new opPedDetAdapter(pedidodetbanActivity.this, (ArrayList<opPedidoDet>) arrayDetalle);

                            for (opPedidoDet obj : arrayDetalle ){
                                Log.i("arraylist" , obj.getcDescripcion());
                            }

                            lvDetalle.setAdapter(adapter);

                            /**lvDetalle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    opPedidoDet objctArtProv = new opPedidoDet();

                                    objctArtProv = (opPedidoDet) adapter.getItem(position);

                                    Log.i("item" , objctArtProv.getiPedido().toString());



                                }
                            });**/



                        } catch (JSONException e) {

                            AlertDialog.Builder myBuild = new AlertDialog.Builder(pedidodetbanActivity.this);
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
                        AlertDialog.Builder myBuild = new AlertDialog.Builder(pedidodetbanActivity.this);
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
        AlertDialog.Builder myBuild = new AlertDialog.Builder(pedidodetbanActivity.this);
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
