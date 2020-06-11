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
import android.widget.Spinner;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class viewProductoActivity extends AppCompatActivity {


    public Globales globales;
    private static RequestQueue mRequestQueue;
    private String url = globales.URL;


    private TextView txtCveArticulo;
    private TextView txtDescripcion;
    private TextView txtPresentacion;
    private TextView txtPrecio;

    private Spinner spMarca;
    private Spinner  spCategoria;
    private Spinner  spSubCategoria;

    private Button btnActualizar;

    private List<ctMarca> listctMarca;
    private List<ctCategoriaProv> listctCategoriaProv;
    private List<ctSubCategoriaProv> listctSubCategoriaProv;
    private ctArtProveedor objctArtProv = new ctArtProveedor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_producto);

        Intent i = getIntent();
         objctArtProv = (ctArtProveedor)i.getSerializableExtra("objctArtProv");

        Log.d("prv" , objctArtProv.getcArticulo());
        Log.d("marca" ,objctArtProv.getcMarca() +  objctArtProv.getiMarca());


        txtCveArticulo  = (TextView)  findViewById(R.id.txtCveArticulo);
        txtDescripcion  = (TextView)  findViewById(R.id.txtDescripcion);
        txtPresentacion = (TextView) findViewById(R.id.txtPresentacion);
        txtPrecio       = (TextView) findViewById(R.id.txtPrecio);
        btnActualizar  = (Button) findViewById(R.id.btnActualizar);




        txtCveArticulo.setText(objctArtProv.getcArticulo());
        txtDescripcion.setText(objctArtProv.getcDescripcion());
        txtPresentacion.setText(objctArtProv.getcPresentacion());
        txtPrecio.setText(objctArtProv.getDePrecioVtaPza().toString());

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
        spMarca.setSelection(listctMarca.indexOf(objctArtProv.getiMarca()));
        spMarca.setAdapter(adapterMarca);

        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(adapterCategoria);
        spCategoria.setSelection(listctCategoriaProv.indexOf(objctArtProv.getiCategoria()));



        adapterSubCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubCategoria.setAdapter(adapterSubCategoria);

        spMarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ctMarca objMarca = (ctMarca) parent.getSelectedItem();

                Log.i("Marca" , objMarca.getiMarca().toString()  + " " + objMarca.getcMarca()  );

             //   objArtProveedor.setiMarca(objMarca.getiMarca());
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ctCategoriaProv objCategoria = (ctCategoriaProv) parent.getSelectedItem();

                Log.i("Categoria" , objCategoria.getiCategoria().toString()  + " " + objCategoria.getcCategoria()  );

              //  objArtProveedor.setiCategoria(objCategoria.getiCategoria());


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

               // objArtProveedor.setiSubCategoria(objSubCategoria.getiSubCategoria());



                //llena sub categoria
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



       btnActualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                actualizaPedido();
            }
        });;



    }



    public void actualizaPedido(){
        Double precioVta = Double.parseDouble(txtPrecio.getText().toString());


        Log.i("precio" , precioVta.toString());

        ArrayList<ctArtProveedor> arrayArtProveedor = new ArrayList<ctArtProveedor>();

        arrayArtProveedor.isEmpty();



        objctArtProv.setcArticulo(txtCveArticulo.getText().toString());
        objctArtProv.setcPresentacion(txtPresentacion.getText().toString());
        objctArtProv.setcDescripcion(txtDescripcion.getText().toString());

       // objctArtProv.setcUsuCrea(globales.g_ctUsuario.getcUsuario());
        objctArtProv.setcUsuModifica(globales.g_ctUsuario.getcUsuario());
        objctArtProv.setDePrecioVtaPza(precioVta);

        arrayArtProveedor.add(objctArtProv);






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
                (Request.Method.PUT, url + "ctArtProveedor/", jsonBody, new Response.Listener<JSONObject>() {
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



                              //  startActivity(new Intent(viewProductoActivity.this, ArticulosActivity.class));
                               // finish();


                                Intent intent = new Intent(viewProductoActivity.this , ArticulosActivity.class);
                                startActivity(intent);
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
        AlertDialog.Builder myBuild = new AlertDialog.Builder(viewProductoActivity.this);
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
