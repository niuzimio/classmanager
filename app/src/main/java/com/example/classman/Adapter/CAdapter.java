package com.example.classman.Adapter;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.classman.R;

public class CAdapter extends BaseAdapter {

    private List<String> names;
    private List<String> power;
    private List<String> tel;
    private List<String> cla;
    private LayoutInflater inflater;
    private Context context;

    public CAdapter(Context context, List<String> names , List<String> cla, List<String> power, List<String> tel){
        inflater = LayoutInflater.from(context);
        this.names = names;
        this.power=power;
        this.tel=tel;
        this.cla=cla;
        this.context = context;
    }


    public int getCount() {
        return names.size();
    }

    public Object getItem(int position) {
        return names.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup group) {
        ContantsHolder holder = new ContantsHolder();
        if(view==null){
            view = inflater.inflate(R.layout.cadpter, null);
            holder.contantsName = (TextView)view.findViewById(R.id.name_view);
            holder.contantsTel= (TextView)view.findViewById(R.id.tel_view);
            holder.contantsCla = (TextView)view.findViewById(R.id.class_view);
            holder.contantsPower = (TextView)view.findViewById(R.id.power_view);
            view.setTag(holder);
        }else{
            holder = (ContantsHolder)view.getTag();
        }
        holder.contantsName.setText(names.get(position));
        holder.contantsTel.setText(tel.get(position));
        holder.contantsCla.setText(cla.get(position));
        holder.contantsPower.setText(power.get(position));
        return view;
    }

    public class ContantsHolder
    {
        private TextView contantsName;
        private TextView contantsTel;
        private TextView contantsCla;
        private TextView contantsPower;
    }
}
