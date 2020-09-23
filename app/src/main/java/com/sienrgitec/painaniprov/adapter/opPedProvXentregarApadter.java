package com.sienrgitec.painaniprov.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sienrgitec.painaniprov.R;
import com.sienrgitec.painaniprov.activity.EvaluaTitlani;
import com.sienrgitec.painaniprov.activity.pedidodetbanActivity;
import com.sienrgitec.painaniprov.config.Globales;
import com.sienrgitec.painaniprov.model.opPedidoProveedor;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class opPedProvXentregarApadter extends BaseAdapter {

    public Globales globales;
    private Context context;
    private ArrayList<opPedidoProveedor> lista;
    private static RequestQueue mRequestQueue;
    private String url = globales.URL;

    public opPedProvXentregarApadter(Context context, ArrayList<opPedidoProveedor> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
         return lista.size();
    }

    @Override
    public Object getItem(int position) {

            return lista.get(position);
    }

    @Override
    public long getItemId(int position) {

            return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.com_xentregar, null);

        }


        TextView textPedido = (TextView)   convertView.findViewById(R.id.textPedido);
        TextView  textFecha = (TextView)   convertView.findViewById(R.id.textFecha);
        TextView  textxtPza   = (TextView)  convertView.findViewById(R.id.textPza);
        TextView  textTotal   = (TextView) convertView.findViewById(R.id.textTotal) ;
        TextView  textHora   = (TextView) convertView.findViewById(R.id.textHora) ;
        Button btnDetalle = (Button) convertView.findViewById(R.id.btnDetalle);
        Button btnSurtir= (Button) convertView.findViewById(R.id.btnTerminar);

        int millis = lista.get(position).getiHora() / 1000;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String time = df.format(millis);




        String FormatTotal = new DecimalFormat("00.00").format(lista.get(position).getDeImporte());
        SpannableString PrecioVta = new SpannableString(FormatTotal);







        textPedido.setText(lista.get(position).getiPedido().toString());
        textFecha.setText(lista.get(position).getDtFecha());

        textxtPza.setText(lista.get(position).getDeTotalPzas().toString());

        textTotal.setText(PrecioVta);

        textHora.setText(time);



        final opPedidoProveedor obj =  lista.get(position);


        btnDetalle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MuestraDetalle(obj);

            }
        });

        btnSurtir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                xSurtir(obj);

            }
        });


        return convertView;
    }
    private void MuestraDetalle(opPedidoProveedor pedido) {


        Intent intent  = new Intent(context, pedidodetbanActivity.class);
        intent.putExtra("pedido",pedido);
        context.startActivity(intent);
    }



    private void xSurtir(final opPedidoProveedor pedido){



        this.notifyDataSetChanged();

        getmRequestQueue();

        // String urlParams = String.format(url + "/vtCargaOrden?ipcCveCia=%1$s&ipiFolio=%2$s", globales.vgCompania, viFolioSusp);

        String urlParams = String.format(url + "opPedidoProvActualiza?ipiPedido=%1$s&ipiPedProv=%2$s&ipcTipo=%3$s", pedido.getiPedido(), pedido.getiPedidoProv()  ,"XENTREGAR");

        Log.i("url",urlParams);

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


                            if (Error == true){
                                MuestraMensaje("Error" , Mensaje);

                            }else {
                                lista.remove(pedido);

                                notifyDataSetChanged();

                                MuestraMensaje("Informacion" , "Pedido Terminado" + " "  + pedido.getiPedido());

                                Intent intent  = new Intent(context, EvaluaTitlani.class);
                                intent.putExtra("pedido",pedido);
                                context.startActivity(intent);

                            }


/*
                            lvPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    Log.i("click" ,"123");

                                    opPedidoProveedor objctArtProv = new opPedidoProveedor();

                                    objctArtProv = (opPedidoProveedor) adapter.getItem(position);

                                    Log.i("item" , objctArtProv.getiPedido().toString());

                                    startActivity(new Intent(pedidobandejaActivity.this, pedidodetalleActivity.class));
                                    finish();




                                }
                            });

*/

                        } catch (JSONException e) {

                            AlertDialog.Builder myBuild = new AlertDialog.Builder(context);
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
                        AlertDialog.Builder myBuild = new AlertDialog.Builder(context);
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
        AlertDialog.Builder myBuild = new AlertDialog.Builder(context);
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
                mRequestQueue = Volley.newRequestQueue(context);
                //your code
            }
        }catch(Exception e){
            Log.d("Volley",e.toString());
        }
    }

}
