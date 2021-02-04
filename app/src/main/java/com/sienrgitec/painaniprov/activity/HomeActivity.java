package com.sienrgitec.painaniprov.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.sienrgitec.painaniprov.adapter.opPedProvBanAdapter;
import com.sienrgitec.painaniprov.config.Globales;
import com.sienrgitec.painaniprov.model.opDispProveedor;
import com.sienrgitec.painaniprov.model.opPedidoProveedor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {


    private  Button btnBandeja;
    private Button btnProductos;
    private Button btnPedXSurtir;
    private Button btnHistorico;
    private TextView txtProveedor;
    private Button btnIniOpe;
    private Button btnPedXEntregar;

    private List<opPedidoProveedor> lista_pedprov;

    private  TextView txtActivo;


    public Globales globales;

    private List<opDispProveedor> opDispProveedorList;


    private static RequestQueue mRequestQueue;
    private String url = globales.URL;

    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;

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


       llamar();
    }

    private void MenuHistorico() {
        startActivity(new Intent(HomeActivity.this, pedidohistoricoActivity.class));
    //    startActivity(new Intent(HomeActivity.this, ConsultaPedActivity.class));

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



    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }


    private void createNotification( int  pedidos){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle("Notificacion ITAKAT");
        builder.setContentText("Tienes  " + pedidos +  " Pedidos en la bandeja" );
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
       builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }



    private void esperar(){

            //createNotification();

           // createNotificationChannel();


    }




    Timer timer = new Timer();
    final Handler handler = new Handler();

    public void llamar(){

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                AsyncTask mytask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {

                        new Handler (Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                                    // esperar();
                                getPedidos(globales.g_ctProveedor.getiProveedor() ,globales.g_ctDomicilio.getiDomicilio());

                            }
                        });

                        return null;
                    }
                };
                mytask.execute();
            }
        };
        timer.schedule(task,0,10000);
    }




    public void getPedidos (int ipiProveedor , int ipiDomicilio){
        getmRequestQueue();

        // String urlParams = String.format(url + "/vtCargaOrden?ipcCveCia=%1$s&ipiFolio=%2$s", globales.vgCompania, viFolioSusp);

        String urlParams = String.format(url + "opPedidoProveedor?ipiProveedor=%1$s&ipiDomicilio=%2$s&ipcCuales=%3$s", ipiProveedor, ipiDomicilio, "NUEVO");


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

                            if (lista_pedprov.size() > 0){
                                createNotification(lista_pedprov.size());
                                createNotificationChannel();
                            }

/*


*/

                        } catch (JSONException e) {

                            AlertDialog.Builder myBuild = new AlertDialog.Builder(HomeActivity.this);
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
                        AlertDialog.Builder myBuild = new AlertDialog.Builder(HomeActivity.this);
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
}

