package com.sienrgitec.painaniprov.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
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
import com.sienrgitec.painaniprov.model.ctComisiones;
import com.sienrgitec.painaniprov.model.ctPainani;
import com.sienrgitec.painaniprov.model.opPedidoProveedor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class titlaniActivity extends AppCompatActivity {

    private TextView nombre, telefono;
    private ImageView foto, back, home;

    public Globales globales;

    private static RequestQueue mRequestQueue;
    private String url = globales.URL;

    private opPedidoProveedor pedido;

    private List<ctPainani> ctPainaniList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repartidorctivity);


        nombre = findViewById(R.id.nombreT);
        telefono = findViewById(R.id.telT);
        foto = findViewById(R.id.imageView11);




        Intent intent = getIntent();


        if (!intent.hasExtra("pedido")) {

            AlertDialog.Builder alert = new AlertDialog.Builder(titlaniActivity.this);
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

        getTitlani(pedido.getiPedido());

    }

    public void getTitlani(int ipiPedido){
        getmRequestQueue();


        String urlParams = String.format(url + "ctPainaniProv?ipiPedido=%1$s", ipiPedido);


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
                            JSONObject ds_ctPainani = respuesta.getJSONObject("tt_ctPainani");

                            JSONArray tt_ctPainal  = ds_ctPainani.getJSONArray("tt_ctPainani");



                            ctPainaniList = Arrays.asList(new Gson().fromJson(tt_ctPainal.toString(), ctPainani[].class));


                           if ( ctPainaniList.isEmpty()){
                               return;
                           }

                            String nombreP   = ctPainaniList.get(0).getcNombre();
                            String apellidoP = ctPainaniList.get(0).getcApellidoPat();
                            String apellidoM = ctPainaniList.get(0).getcApellidoMat();

                            nombre.setText(nombreP + " " + apellidoP + " " + apellidoM);
                            telefono.setText(ctPainaniList.get(0).getcWhattsApp());

                            if(ctPainaniList.get(0).getbImagen() != null){
                                byte[] decodedString = Base64.decode(ctPainaniList.get(0).getbImagen(), Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                foto.setImageBitmap(decodedByte);
                            }



                            } catch (JSONException e) {

                                AlertDialog.Builder myBuild = new AlertDialog.Builder(titlaniActivity.this);
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
                            AlertDialog.Builder myBuild = new AlertDialog.Builder(titlaniActivity.this);
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