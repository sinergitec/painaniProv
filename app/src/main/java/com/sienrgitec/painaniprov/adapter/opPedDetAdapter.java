package com.sienrgitec.painaniprov.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sienrgitec.painaniprov.R;
import com.sienrgitec.painaniprov.model.opPedidoDet;

import java.text.DecimalFormat;
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



        TextView textNo          = (TextView)   convertView.findViewById(R.id.textNo);
        TextView textArticulo    = (TextView)   convertView.findViewById(R.id.textArticulo);
        TextView textCantidad    = (TextView)   convertView.findViewById(R.id.textCantidad);
        TextView textDescripcion = (TextView)   convertView.findViewById(R.id.textDescripcion);
        TextView textPrecio      = (TextView)   convertView.findViewById(R.id.textPrecio);
        TextView textImporte     = (TextView)   convertView.findViewById(R.id.textImporte);





         DecimalFormat FDPrecio = new DecimalFormat("00.00");
        DecimalFormat FDImporte = new DecimalFormat("00.00");

          String  Precio = FDPrecio.format(lista.get(position).getDePrecioVta());


           String Importe = FDImporte.format(lista.get(position).getDeImporte());









        textNo.setText(lista.get(position).getiPartida().toString());
        textArticulo.setText(lista.get(position).getcArticulo());
        textCantidad.setText(lista.get(position).getDeCantidad().toString());
        textDescripcion.setText(lista.get(position).getcDescripcion());
        textPrecio.setText(Precio);
        textImporte.setText(Importe);

        Log.i("adapter" , lista.get(position).getcDescripcion());

        return convertView;
    }
}
