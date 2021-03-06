package com.sienrgitec.painaniprov.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.sienrgitec.painaniprov.model.ctDomicilio;
import com.sienrgitec.painaniprov.model.ctProveedor;
import com.sienrgitec.painaniprov.model.ctUsuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText txtUsuario;
    private EditText txtPassword;
    private Button btnAceptar;
    private TextView tvRecuperaPW;

    private Globales globales;

    private static RequestQueue mRequestQueue;
    private String url = globales.URL;

    private List<ctUsuario> listUsuario;
    private List<ctProveedor> listProveedor;
    private  List<ctDomicilio> listDomicilio;

    private String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        tvRecuperaPW = (TextView) findViewById(R.id.tvRecuperaPW);

        tvRecuperaPW.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Recupera contraseña- Captura nombre de usuario");

                final EditText password = new EditText(MainActivity.this);

                password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(password);


                builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        email = password.getText().toString();

                        if(password.getText().toString().isEmpty()){
                            MuestraMensaje("Error", "Se debe capturar el nombre de usuario");
                            return;
                        } else{
                            RecuperaPW();
                        }
                    }
                });
                builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ValidaUsuario();
            }
        });


    }

    private void ValidaUsuario() {

        final String vcUsuario = txtUsuario.getText().toString();
        final String vcPassword = txtPassword.getText().toString();

        if (txtUsuario.getText().toString().isEmpty()) {
            AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
            myBuild.setMessage("No se capturo el nombre de usuario");
            myBuild.setTitle(Html.fromHtml("<font color ='#FF0000'> ERROR </font>"));
            myBuild.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();


                }
            });
            AlertDialog dialog = myBuild.create();
            dialog.show();
            return;
        }


        if (txtPassword.getText().toString().isEmpty()) {
            AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
            myBuild.setMessage("No se capturo el password del usuario");
            myBuild.setTitle(Html.fromHtml("<font color ='#FF0000'> ERROR </font>"));
            myBuild.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    // btnEntrar.setEnabled(true);

                }
            });
            AlertDialog dialog = myBuild.create();
            dialog.show();
            return;
        }

        getmRequestQueue();
        String urlParams = String.format(url + "PwordProv?ipcUsuario=%1$s&ipcPassword=%2$s&ipcPersona=%3$s", vcUsuario,vcPassword, "Proveedor");

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
                            JSONObject ds_ctUsuario = respuesta.getJSONObject("tt_ctUsuario");
                            JSONObject ds_ctProveedor = respuesta.getJSONObject("tt_ctProveedor");
                            JSONObject ds_ctDomicilio = respuesta.getJSONObject("tt_ctDomicilio");


                            JSONArray tt_ctUsuario = ds_ctUsuario.getJSONArray("tt_ctUsuario");
                            JSONArray tt_ctProveedor = ds_ctProveedor.getJSONArray("tt_ctProveedor");
                            JSONArray tt_ctDomicilio = ds_ctDomicilio.getJSONArray("tt_ctDomicilio");



                            listUsuario = Arrays.asList(new Gson().fromJson(tt_ctUsuario.toString(), ctUsuario[].class));
                            listProveedor = Arrays.asList(new Gson().fromJson(tt_ctProveedor.toString(), ctProveedor[].class));
                            listDomicilio = Arrays.asList(new Gson().fromJson(tt_ctDomicilio.toString(), ctDomicilio[].class));

                            if (! listUsuario.isEmpty()) {
                                globales.g_ctUsuario = listUsuario.get(0);
                            }
                            if (! listProveedor.isEmpty()){
                                globales.g_ctProveedor = listProveedor.get(0);

                            }

                            if (!listDomicilio.isEmpty()) {
                                globales.g_ctDomicilio = listDomicilio.get(0);
                            }


                            if (Error == true) {

                                MuestraMensaje("Error", Mensaje);
                                return;

                            } else {

                                if (globales.g_ctProveedor == null) {
                                    MuestraMensaje("Error", "no se encontro datos del proveedor");
                                    return;
                                }

                                if (globales.g_ctUsuario == null) {
                                    MuestraMensaje("Error", "No se encontraron datos del usuario");
                                    return;

                                }

                               /*imb if (!globales.g_ctUsuario.getcPassword().equals(vcPassword)) {
                                    MuestraMensaje("Error", "el password es incorrecto");
                                    return;
                                }*/

                               startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                finish();

                            }


                        } catch (JSONException e) {

                            AlertDialog.Builder myBuild = new AlertDialog.Builder(MainActivity.this);
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
                        AlertDialog.Builder myBuild = new AlertDialog.Builder(MainActivity.this);
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
                params.put("ipcUsuario", vcUsuario);
                params.put("ipcPassword", vcPassword);


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

    private void RecuperaPW(){

        getmRequestQueue();

        String urlParams = String.format(url + "recuperapw?ipcEmail=%1$s&ipcPersona=%2$s", email, "Proveedor" );

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
                            JSONObject ds_ctUsuario = respuesta.getJSONObject("tt_ctUsuario");

                            JSONArray tt_ctUsuario  = ds_ctUsuario.getJSONArray("tt_ctUsuario");

                            listUsuario = Arrays.asList(new Gson().fromJson(tt_ctUsuario.toString(), ctUsuario[].class));
                            if (! listUsuario.isEmpty()) {
                                globales.g_ctUsuario = listUsuario.get(0);
                            }


                            if (Error == true) {
                                tvRecuperaPW.setEnabled(true);
                                MuestraMensaje("Error", Mensaje);
                                return;

                            } else {

                                tvRecuperaPW.setEnabled(true);

                                MuestraMensaje("Aviso", "Tu contraseña es " + globales.g_ctUsuario.getcPassword());
                            }
                        } catch (JSONException e) {
                            tvRecuperaPW.setEnabled(true);
                            androidx.appcompat.app.AlertDialog.Builder myBuild = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
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
                        tvRecuperaPW.setEnabled(true);
                        // TODO: Handle error
                        Log.i("Error Respuesta", error.toString());
                        androidx.appcompat.app.AlertDialog.Builder myBuild = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
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
                params.put("ipcEmail", email);
                params.put("ipcPersona", "proveedor");


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
        AlertDialog.Builder myBuild = new AlertDialog.Builder(MainActivity.this);
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
