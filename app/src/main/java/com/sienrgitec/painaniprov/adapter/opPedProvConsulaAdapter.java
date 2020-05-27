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

public class opPedProvConsulaAdapter extends BaseAdapter {


    public Globales globales;
    private Context context;
    private ArrayList<opPedidoProveedor> lista;

    public opPedProvConsulaAdapter(Context context, ArrayList<opPedidoProveedor> lista) {
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
            convertView = layoutInflater.inflate(R.layout.com_consulta, null);

        }


        TextView textPedido = (TextView)   convertView.findViewById(R.id.textPedido);
        TextView  textFecha = (TextView)   convertView.findViewById(R.id.textFecha);
        TextView  textxtPza   = (TextView)  convertView.findViewById(R.id.textPza);
        TextView  textTotal   = (TextView) convertView.findViewById(R.id.textTotal) ;
        TextView  textHora   = (TextView) convertView.findViewById(R.id.textHora) ;
        Button btnDetalle = (Button) convertView.findViewById(R.id.btnDetalle);

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


        return convertView;
    }

    private void MuestraDetalle(opPedidoProveedor pedido) {


       Intent intent  = new Intent(context, pedidodetbanActivity.class);
       intent.putExtra("pedido",pedido);
        context.startActivity(intent);
    }




}
