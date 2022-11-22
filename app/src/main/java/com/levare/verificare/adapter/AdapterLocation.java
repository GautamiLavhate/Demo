package com.levare.verificare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.levare.verificare.R;
import com.levare.verificare.model.Location;

import java.util.ArrayList;

public class AdapterLocation extends BaseAdapter {

    Context context;
    private LayoutInflater inflater;
    public ArrayList<Location.LocationDetails> locationDetails;

    public AdapterLocation(Context context,ArrayList<Location.LocationDetails> locationDetails) {
        this.locationDetails=locationDetails;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return locationDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return locationDetails;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context=parent.getContext();
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.location_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtplantLocation0.setText(locationDetails.get(position).getName());
        holder.txtplantLocation1.setText(locationDetails.get(position).getPlant_name());
        return convertView;
    }

    class ViewHolder {
        private TextView txtplantLocation0, txtplantLocation1;


        public ViewHolder(View view) {
            txtplantLocation0 = view.findViewById(R.id.txtplantLocation0);
            txtplantLocation1 = view.findViewById(R.id.txtplantLocation1);
        }
    }
}
