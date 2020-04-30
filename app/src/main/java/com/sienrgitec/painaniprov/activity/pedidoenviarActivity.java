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
import android.view.View;
import android.widget.AdapterView;
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
import com.sienrgitec.painaniprov.adapter.opPedProvAdapter;
import com.sienrgitec.painaniprov.config.Globales;
import com.sienrgitec.painaniprov.model.opPedidoProveedor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pedidoenviarActivity extends AppCompatActivity {


    public Globales globales;

    private static RequestQueue mRequestQueue;
    private String url = globales.URL;

    private ListView lvPedProv;
    private opPedProvAdapter adapter;
    private List<opPedidoProveedor> lista_pedprov;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidosenviar);


        lvPedProv = (ListView) findViewById(R.id.lvPedidos);
        lista_pedprov = new ArrayList<opPedidoProveedor>();

        getPedidos(11);
    }


    public void getPedidos (int ipiProveedor){
        getmRequestQueue();

        // String urlParams = String.format(url + "/vtCargaOrden?ipcCveCia=%1$s&ipiFolio=%2$s", globales.vgCompania, viFolioSusp);

        String urlParams = String.format(url + "opPedidoProveedor?ipiProveedor=%1$s&ipcCuales=%2$s", ipiProveedor, "TODOS");


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

                            adapter = new opPedProvAdapter(pedidoenviarActivity.this, (ArrayList<opPedidoProveedor>) arrayPedProv);
                            lvPedProv.setAdapter(adapter);

                            lvPedProv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    opPedidoProveedor objctArtProv = new opPedidoProveedor();

                                    objctArtProv = (opPedidoProveedor) adapter.getItem(position);

                                    Log.i("item" , objctArtProv.getiPedido().toString());

                                    startActivity(new Intent(pedidoenviarActivity.this, pedidodetalleActivity.class));
                                    finish();




                                }
                            });



                        } catch (JSONException e) {

                            AlertDialog.Builder myBuild = new AlertDialog.Builder(pedidoenviarActivity.this);
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
                        AlertDialog.Builder myBuild = new AlertDialog.Builder(pedidoenviarActivity.this);
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
        AlertDialog.Builder myBuild = new AlertDialog.Builder(pedidoenviarActivity.this);
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
