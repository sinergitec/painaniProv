package com.sienrgitec.painaniprov.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.sienrgitec.painaniprov.R;
import com.sienrgitec.painaniprov.model.opPedidoProveedor;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class opPedProvAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<opPedidoProveedor> lista;

    public opPedProvAdapter(Context context, ArrayList<opPedidoProveedor> lista) {
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
            convertView = layoutInflater.inflate(R.layout.com_pedprov, null);

        }


        EditText  textPedido = (EditText)   convertView.findViewById(R.id.textPedido);
        EditText  textFecha = (EditText)   convertView.findViewById(R.id.textFecha);
        EditText  textxtPza   = (EditText)  convertView.findViewById(R.id.textPza);
        EditText  textTotal   = (EditText) convertView.findViewById(R.id.textTotal) ;
        EditText  textHora   = (EditText) convertView.findViewById(R.id.textHora) ;



        String FormatTotal = new DecimalFormat("0.00").format(lista.get(position).getDeImporte());
        SpannableString PrecioVta = new SpannableString(FormatTotal);



        textPedido.setText(lista.get(position).getiPedidoProv().toString());
        textFecha.setText(lista.get(position).getDtFecha());

        textxtPza.setText(lista.get(position).getDtFecha().toString());
        textTotal.setText(PrecioVta);

        textHora.setText(lista.get(position).getiHora().toString());





        return convertView;
    }


}
