package com.sienrgitec.painaniprov.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.sienrgitec.painaniprov.R;
import com.sienrgitec.painaniprov.model.opPedidoDet;

import java.util.ArrayList;

public class opPedDetAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<opPedidoDet> lista;

    public opPedDetAdapter(Context context, ArrayList<opPedidoDet> lista) {
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
            convertView = layoutInflater.inflate(R.layout.com_peddet, null);

        }


        EditText textNo          = (EditText)   convertView.findViewById(R.id.textNo);
        EditText textArticulo    = (EditText)   convertView.findViewById(R.id.textArticulo);
        EditText textCantidad    = (EditText)   convertView.findViewById(R.id.textCantidad);
        EditText textDescripcion = (EditText)   convertView.findViewById(R.id.textDescripcion);


        textNo.setText(lista.get(position).getiPartida().toString());
        textArticulo.setText(lista.get(position).getcArticulo());
        textCantidad.setText(lista.get(position).getDeCantidad().toString());
        textDescripcion.setText(lista.get(position).getcDescripcion());


        return convertView;
    }
}
