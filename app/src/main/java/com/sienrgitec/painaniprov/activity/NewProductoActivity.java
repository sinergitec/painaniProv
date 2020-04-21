package com.sienrgitec.painaniprov.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import com.sienrgitec.painaniprov.model.ctArtProveedor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewProductoActivity extends AppCompatActivity {

    public Globales globales;
    private static RequestQueue mRequestQueue;
    private String url = globales.URL;

    private Button btnCrear;
    private EditText txtCveArticulo;
    private EditText txtDescripcion;
    private EditText txtPresentacion;
    private EditText txtPrecio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_producto);

        btnCrear = (Button) findViewById(R.id.btnCrear);
        txtCveArticulo = (EditText) findViewById(R.id.txtCveArticulo);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        txtPresentacion = (EditText) findViewById(R.id.txtPresentacion);
        txtPrecio = (EditText) findViewById(R.id.txtPrecio);


        btnCrear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                guardaArticulo();
            }
        });


    }


    public void guardaArticulo() {

        Double precioVta = Double.parseDouble(txtPrecio.getText().toString());

        ArrayList<ctArtProveedor> arrayArtProveedor = new ArrayList<ctArtProveedor>();


        ctArtProveedor objArtProveedor = new ctArtProveedor();

        objArtProveedor.setiProveedor(1);
        objArtProveedor.setiArticulo(0);
        objArtProveedor.setcArticulo(txtCveArticulo.getText().toString());
        objArtProveedor.setcAplicaciones("");
        objArtProveedor.setcPresentacion(txtPresentacion.getText().toString());
        objArtProveedor.setcDescripcion(txtDescripcion.getText().toString());
        objArtProveedor.setiImpuesto(0);
        objArtProveedor.setiCategoria(0);
        objArtProveedor.setiSubCategoria(0);
        objArtProveedor.setiClasificacion(0);
        objArtProveedor.setiSubClasificacion(0);
        objArtProveedor.setiMarca(0);
        objArtProveedor.setlActivo(true);
        objArtProveedor.setbImagen("");
        objArtProveedor.setDtCreado("");
        objArtProveedor.setDtModificado("");
        objArtProveedor.setcUsuCrea("");
        objArtProveedor.setcUsuModifica("");
        objArtProveedor.setDePrecioVta(precioVta);
        objArtProveedor.setId("");

        arrayArtProveedor.add(objArtProveedor);






        JSONArray jArrayctArtProv = null;
        JSONObject jsonTTctArtProv = new JSONObject();
        JSONObject jsonDSctArtProv = new JSONObject();
        JSONObject jsonBody = new JSONObject();

        Gson gson = new Gson();

        String JS_ArtProveedor = gson.toJson(
                arrayArtProveedor,
                new TypeToken<ArrayList<ctArtProveedor>>() {
                }.getType());


        try {
            jArrayctArtProv = new JSONArray(JS_ArtProveedor);

            jsonTTctArtProv.put("tt_Nuevos", jArrayctArtProv);
            jsonDSctArtProv.put("ds_ctArtProveedor", jsonTTctArtProv);
            jsonBody.put("request", jsonDSctArtProv);




        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.i("Response", jsonBody.toString());


        getmRequestQueue();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url + "ctArtProveedor/", jsonBody, new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject respuesta = response.getJSONObject("response");
                            Log.i("respuesta",  respuesta.toString());



                        } catch (JSONException e) {
                            Log.i("Error JSONExcepcion", e.getMessage());


                            Log.i("Error JSONExcepcion", e.getMessage());
                            MuestraMensaje("Error", "Error Conversión de Datos." + "\n " + e.getMessage());

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



    public void MuestraMensaje(String vcTitulo, String vcMensaje){
        AlertDialog.Builder myBuild = new AlertDialog.Builder(NewProductoActivity.this);
        myBuild.setMessage(vcMensaje);
        myBuild.setTitle(Html.fromHtml("<font color ='#FF0000'>" + vcTitulo +"</font>"));
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
