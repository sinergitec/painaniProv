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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.sienrgitec.painaniprov.model.ctCategoriaProv;
import com.sienrgitec.painaniprov.model.ctMarca;
import com.sienrgitec.painaniprov.model.ctSubCategoriaProv;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private Spinner  spMarca;
    private Spinner  spCategoria;
    private Spinner  spSubCategoria;

    private List<ctMarca> listctMarca;
    private List<ctCategoriaProv> listctCategoriaProv;
    private List<ctSubCategoriaProv> listctSubCategoriaProv;
    private ctArtProveedor objArtProveedor = new ctArtProveedor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_producto);


        Log.d("New" , "entro");
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




        /*Carga Marcas*/

        listctMarca            = new ArrayList<ctMarca>();
        listctCategoriaProv    = new ArrayList<ctCategoriaProv>();
        listctSubCategoriaProv = new ArrayList<ctSubCategoriaProv>();

        listctMarca          = globales.g_ctMarca;
        listctCategoriaProv  = globales.g_ctCategoriaProv;

        spMarca        = (Spinner) findViewById(R.id.spMarca);
        spCategoria    = (Spinner) findViewById(R.id.spCategoria);
        spSubCategoria = (Spinner) findViewById(R.id.spSubCategoria);


        ArrayAdapter<ctMarca> adapterMarca = new ArrayAdapter<ctMarca>(this, android.R.layout.simple_spinner_dropdown_item, listctMarca);
        ArrayAdapter<ctCategoriaProv> adapterCategoria = new ArrayAdapter<ctCategoriaProv>(this, android.R.layout.simple_spinner_dropdown_item, listctCategoriaProv);
        final ArrayAdapter<ctSubCategoriaProv> adapterSubCategoria = new ArrayAdapter<ctSubCategoriaProv>(this, android.R.layout.simple_spinner_dropdown_item, listctSubCategoriaProv);


        adapterMarca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarca.setAdapter(adapterMarca);


        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(adapterCategoria);



        adapterSubCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubCategoria.setAdapter(adapterSubCategoria);


       spMarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ctMarca objMarca = (ctMarca) parent.getSelectedItem();

                Log.i("Marca" , objMarca.getiMarca().toString()  + " " + objMarca.getcMarca()  );

                objArtProveedor.setiMarca(objMarca.getiMarca());
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ctCategoriaProv objCategoria = (ctCategoriaProv) parent.getSelectedItem();

                Log.i("Categoria" , objCategoria.getiCategoria().toString()  + " " + objCategoria.getcCategoria()  );

                objArtProveedor.setiCategoria(objCategoria.getiCategoria());


               /* listctSubCategoriaProv.clear();
                for (ctSubCategoriaProv obj : globales.g_ctSubCategoriaProv){

                    if (obj.getiCategoria().equals(objCategoria.getiCategoria())) {
                        listctSubCategoriaProv.add(obj);

                    }

                }




                adapterSubCategoria.notifyDataSetChanged();



*/

                //llena sub categoria
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spSubCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ctSubCategoriaProv objSubCategoria = (ctSubCategoriaProv) parent.getSelectedItem();

                Log.i("SubCategoria" , objSubCategoria.getiCategoria().toString()  + " " +  objSubCategoria.getiSubCategoria().toString()  + " " + objSubCategoria.getcSubCategoria()  );

                objArtProveedor.setiSubCategoria(objSubCategoria.getiSubCategoria());



                //llena sub categoria
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    public void guardaArticulo() {

        Double precioVta = Double.parseDouble(txtPrecio.getText().toString());


        Log.i("precio" , precioVta.toString());

        ArrayList<ctArtProveedor> arrayArtProveedor = new ArrayList<ctArtProveedor>();

        arrayArtProveedor.isEmpty();


        objArtProveedor.setiProveedor(globales.g_ctProveedor.getiProveedor());
        objArtProveedor.setiDomicilio(globales.g_ctDomicilio.getiDomicilio());
        objArtProveedor.setiArticulo(0);
        objArtProveedor.setcArticulo(txtCveArticulo.getText().toString());
        objArtProveedor.setcAplicaciones("");
        objArtProveedor.setcPresentacion(txtPresentacion.getText().toString());
        objArtProveedor.setcDescripcion(txtDescripcion.getText().toString());
        objArtProveedor.setiImpuesto(1);
        objArtProveedor.setiClasificacion(0);
        objArtProveedor.setiSubClasificacion(0);
        objArtProveedor.setlActivo(true);
        objArtProveedor.setbImagen("");
        objArtProveedor.setDtCreado("");
        objArtProveedor.setDtModificado("");
        objArtProveedor.setcUsuCrea(globales.g_ctUsuario.getcUsuario());
        objArtProveedor.setcUsuModifica("");
        objArtProveedor.setDePrecioVtaPza(precioVta);
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


                            String Mensaje = respuesta.getString("opcMensaje");
                            Boolean Error = respuesta.getBoolean("oplError");



                            if (Error == true) {

                                MuestraMensaje("Error", Mensaje);
                                return;

                            }else{

                                startActivity(new Intent(NewProductoActivity.this, ArticulosActivity.class));
                                finish();

                            }



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
