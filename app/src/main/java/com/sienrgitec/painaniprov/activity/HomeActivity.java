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
import android.widget.Button;
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
import com.sienrgitec.painaniprov.model.ctDomicilio;
import com.sienrgitec.painaniprov.model.ctProveedor;
import com.sienrgitec.painaniprov.model.ctUsuario;
import com.sienrgitec.painaniprov.model.opDispProveedor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {


    private  Button btnBandeja;
    private Button btnProductos;
    private Button btnPedXSurtir;
    private Button btnHistorico;
    private TextView txtProveedor;
    private Button btnIniOpe;
    private Button btnPedXEntregar;

    private  TextView txtActivo;


    public Globales globales;

    private List<opDispProveedor> opDispProveedorList;


    private static RequestQueue mRequestQueue;
    private String url = globales.URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        btnProductos = (Button)  findViewById(R.id.btnProductos);
        btnPedXSurtir = (Button)  findViewById(R.id.btnPedXSustir);
        btnBandeja = (Button)  findViewById(R.id.btnBandeja);
        btnHistorico = (Button)  findViewById(R.id.btnHistorico);
        btnIniOpe = (Button) findViewById(R.id.btnIniOpe);
        btnPedXEntregar =(Button) findViewById(R.id.btnPedXEntregar);

        txtProveedor =(TextView) findViewById(R.id.txtProveedor);
        txtActivo    =(TextView) findViewById(R.id.txtActivo);

        txtProveedor.setText(globales.g_ctProveedor.getcNegocio());





        btnIniOpe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InicioOpe(globales.g_ctProveedor.getiProveedor() ,globales.g_ctDomicilio.getiDomicilio());

            }
        });

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

        btnPedXEntregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MenuXEntregar();


            }
        });


        getDisponible(globales.g_ctProveedor.getiProveedor(),globales.g_ctDomicilio.getiDomicilio());



    }

    private void MenuHistorico() {
        startActivity(new Intent(HomeActivity.this, pedidohistoricoActivity.class));

    }

    private void MenuXSurtir() {
        startActivity(new Intent(HomeActivity.this, pedidoxsurtirActivity.class));


    }
    private void MenuXEntregar(){
        startActivity(new Intent(HomeActivity.this , perdidoxentregarActivity.class ));
    }

    private void MenuProducto() {
        startActivity(new Intent(HomeActivity.this, ArticulosActivity.class));

    }

    private void MenuBandeja() {
        startActivity(new Intent(HomeActivity.this , pedidobandejaActivity.class) );

    }


    private  void InicioOpe(int ipiProveedor ,int ipiDomicilio){
        startActivity(new Intent(HomeActivity.this , OperacionesActivity.class) );


    }

    public void MuestraMensaje(String vcTitulo,  String vcMensaje){
        AlertDialog.Builder myBuild = new AlertDialog.Builder(HomeActivity.this);
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


    private void getDisponible(int ipiProveedor , int ipiDomicilio) {


        Log.i("home", "ctProveedor");
        getmRequestQueue();
        String urlParams = String.format(url + "ctProveedor?ipiProveedor=%1$s&ipiDomicilio=%2$s", ipiProveedor,ipiDomicilio);

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
                            JSONObject ds_opDispProveedor = respuesta.getJSONObject("tt_opDispProveedor");

                             JSONArray tt_opDispProveedor = ds_opDispProveedor.getJSONArray("tt_opDispProveedor");






                            if (Error == true) {

                                MuestraMensaje("Error", Mensaje);
                                return;

                            } else {


                                opDispProveedorList = Arrays.asList(new Gson().fromJson(tt_opDispProveedor.toString(), opDispProveedor[].class));

                                if (! opDispProveedorList.isEmpty()) {
                                    globales.g_opDispProveedor = opDispProveedorList.get(0);


                                    if (globales.g_opDispProveedor.getDtCheckIn().equals(0) ||
                                        globales.g_opDispProveedor.getDtCheckIn() == null){
                                        txtActivo.setText("Activo");

                                    }else {
                                        txtActivo.setText("Cierre de Opreaciones");
                                    }
                                }else {
                                    txtActivo.setText("Inactivo");
                                }



                            }


                        } catch (JSONException e) {

                            android.app.AlertDialog.Builder myBuild = new android.app.AlertDialog.Builder(HomeActivity.this);
                            myBuild.setMessage("Error en la conversión de Datos. Vuelva a Intentar. " + e);
                            myBuild.setTitle(Html.fromHtml("<font color ='#FF0000'> ERROR CONVERSION </font>"));
                            myBuild.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            });
                            android.app.AlertDialog dialog = myBuild.create();
                            dialog.show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // TODO: Handle error
                        Log.i("Error Respuesta", error.toString());
                        android.app.AlertDialog.Builder myBuild = new android.app.AlertDialog.Builder(HomeActivity.this);
                        myBuild.setMessage("No se pudo conectar con el servidor. Vuelva a Intentar. " + error.toString());
                        myBuild.setTitle(Html.fromHtml("<font color ='#FF0000'> ERROR RESPUESTA </font>"));
                        myBuild.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        android.app.AlertDialog dialog = myBuild.create();
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

