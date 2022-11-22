package com.levare.verificare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.levare.verificare.R;
import com.levare.verificare.model.SubLocation;

import java.util.ArrayList;

public class AdapterSubLocation extends BaseAdapter {
    Context context;
    private LayoutInflater inflater;
    public ArrayList<SubLocation.SubLocationDetails> subLocationDetails;

    public AdapterSubLocation(Context context,ArrayList<SubLocation.SubLocationDetails> subLocationDetails) {
        this.subLocationDetails=subLocationDetails;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return subLocationDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return subLocationDetails;
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
            convertView = inflater.inflate(R.layout.sublocation_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtplantSubLocation0.setText(subLocationDetails.get(position).getName());
        holder.txtplantSubLocation1.setText(subLocationDetails.get(position).getPlant_name());
        holder.txtplantSubLocation2.setText(subLocationDetails.get(position).getLocation_name());
        return convertView;
    }

    class ViewHolder {
        private TextView txtplantSubLocation0, txtplantSubLocation1,txtplantSubLocation2;


        public ViewHolder(View view) {
            txtplantSubLocation0 = view.findViewById(R.id.txtplantSubLocation0);
            txtplantSubLocation1 = view.findViewById(R.id.txtplantSubLocation1);
            txtplantSubLocation2 = view.findViewById(R.id.txtplantSubLocation2);
        }
    }
}
