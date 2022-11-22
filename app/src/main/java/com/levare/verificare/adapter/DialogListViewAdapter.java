package com.levare.verificare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.levare.verificare.R;

import java.util.List;

public class DialogListViewAdapter extends BaseAdapter {

    public  interface  onClick{
        void onClickEvent(Object e,int pos);
    }
    onClick mOnClick;
    Context mContext;
    List<String > allItems;

    public DialogListViewAdapter(@NonNull Context context, @NonNull List<String > allItems,onClick mOnClick) {

        this.allItems=allItems;
        mContext=context;
        this.mOnClick=mOnClick;
    }

    @Override
    public int getCount() {
        return allItems.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.lay_dialog_listview_item, parent, false);
        }
        TextView textView=convertView.findViewById(R.id.tvTitle);
        LinearLayout linearLayout=convertView.findViewById(R.id.llLayout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClick!=null){
                    mOnClick.onClickEvent(allItems.get(position),position);
                }

            }
        });
        textView.setText(allItems.get(position));
        return convertView;
    }
}
