package com.sienrgitec.painaniprov.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sienrgitec.painaniprov.R;
import com.sienrgitec.painaniprov.model.ctArtProveedor;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ArtProveedorAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<ctArtProveedor> lista;


    public ArtProveedorAdapter(Context context, ArrayList<ctArtProveedor> lista) {
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
            convertView = layoutInflater.inflate(R.layout.com_art_proveedor, null);

        }

        TextView txtArticulo = (TextView) convertView.findViewById(R.id.txtArticulo);
        TextView txtDescripcion = (TextView) convertView.findViewById(R.id.txtDescripcion);
        TextView txtPrecioVta = (TextView) convertView.findViewById(R.id.txtPrecioVta);


        String FdePrecivta = new DecimalFormat("0.00").format(lista.get(position).getDePrecioVtaPza());
        SpannableString PrecioVta = new SpannableString(FdePrecivta);
        txtPrecioVta.setText(PrecioVta);


        txtArticulo.setText(lista.get(position).getcArticulo().toString());
        txtDescripcion.setText(lista.get(position).getcDescripcion().toString());
        return convertView;
    }





}
