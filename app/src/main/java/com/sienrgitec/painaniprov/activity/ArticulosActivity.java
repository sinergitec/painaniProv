package com.sienrgitec.painaniprov.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
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
import com.sienrgitec.painaniprov.adapter.ArtProveedorAdapter;
import com.sienrgitec.painaniprov.config.Globales;
import com.sienrgitec.painaniprov.model.ctArtProveedor;
import com.sienrgitec.painaniprov.model.ctCategoriaProv;
import com.sienrgitec.painaniprov.model.ctMarca;
import com.sienrgitec.painaniprov.model.ctSubCategoriaProv;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticulosActivity extends AppCompatActivity {

    public Globales globales;

    private static RequestQueue mRequestQueue;
    private String url = globales.URL;

    private ListView lvArticulo;
    private ArtProveedorAdapter adapter;
    private List<ctArtProveedor> listaCtArtProveedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulos);


        lvArticulo = (ListView) findViewById(R.id.lvArticulos);
        listaCtArtProveedor = new ArrayList<ctArtProveedor>();




        cargaArticulos(globales.g_ctProveedor.getiProveedor());


    }

    public void cargaArticulos(int ipiProveedor ){

        getmRequestQueue();
        String urlParams = String.format(url + "ctArtProveedor?ipiProveedor=" + ipiProveedor);


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
                            JSONObject ds_ctArtProveedor = respuesta.getJSONObject("tt_ctArtProveedor");

                            JSONArray tt_ctArtProveedor  = ds_ctArtProveedor.getJSONArray("tt_ctArtProveedor");

                            listaCtArtProveedor = Arrays.asList(new Gson().fromJson(tt_ctArtProveedor.toString(), ctArtProveedor[].class));

                            ArrayList<ctArtProveedor> ArrayCtArtProveedor = new ArrayList<ctArtProveedor>(listaCtArtProveedor);

                            adapter = new ArtProveedorAdapter(ArticulosActivity.this, (ArrayList<ctArtProveedor>) ArrayCtArtProveedor);
                            lvArticulo.setAdapter(adapter);

                            lvArticulo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    ctArtProveedor objctArtProv = new ctArtProveedor();

                                    objctArtProv = (ctArtProveedor) adapter.getItem(position);

                                    Log.i("item" , objctArtProv.getcDescripcion());

                                    startActivity(new Intent(ArticulosActivity.this, updateProductoActivity.class));
                                    finish();

                                }
                            });



                        } catch (JSONException e) {

                            AlertDialog.Builder myBuild = new AlertDialog.Builder(ArticulosActivity.this);
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
                        AlertDialog.Builder myBuild = new AlertDialog.Builder(ArticulosActivity.this);
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


    public void cargaCategorias(int ipiProveedor){
        getmRequestQueue();
        String urlParams = String.format(url + "ctCategoriaProv?ipiProveedor=" + ipiProveedor);


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


                            JSONObject ds_tt_ctCategoriaProv   = respuesta.getJSONObject("tt_ctCategoriaProv");
                            JSONObject ds_tt_ctSubCategoriaProv = respuesta.getJSONObject("tt_ctSubCategoriaProv");
                            JSONObject ds_tt_ctMarca            = respuesta.getJSONObject("tt_ctMarca");


                            JSONArray tt_ctCategoriaProv     = ds_tt_ctCategoriaProv.getJSONArray("tt_ctCategoriaProv");
                            JSONArray tt_ctSubCategoriaProv  = ds_tt_ctSubCategoriaProv.getJSONArray("tt_ctSubCategoriaProv");
                            JSONArray tt_ctMarca             = ds_tt_ctMarca.getJSONArray("tt_ctMarca");

                            globales.g_ctCategoriaProv = Arrays.asList(new Gson().fromJson(tt_ctCategoriaProv.toString(), ctCategoriaProv[].class));
                            globales.g_ctSubCategoriaProv = Arrays.asList(new Gson().fromJson(tt_ctSubCategoriaProv.toString(), ctSubCategoriaProv[].class));
                            globales.g_ctMarca = Arrays.asList(new Gson().fromJson(tt_ctMarca.toString(), ctMarca[].class));



                            Log.i("marca", String.valueOf(globales.g_ctMarca.size()));






                        } catch (JSONException e) {

                            AlertDialog.Builder myBuild = new AlertDialog.Builder(ArticulosActivity.this);
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
                        AlertDialog.Builder myBuild = new AlertDialog.Builder(ArticulosActivity.this);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mnuNuevo:
                Intent iMenu = new Intent(getApplicationContext(), NewProductoActivity.class);
                startActivity(iMenu);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void MuestraMensaje(String vcTitulo,  String vcMensaje){
        AlertDialog.Builder myBuild = new AlertDialog.Builder(ArticulosActivity.this);
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
