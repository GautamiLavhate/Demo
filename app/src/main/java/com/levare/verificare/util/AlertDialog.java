package com.levare.verificare.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.levare.verificare.R;

public class AlertDialog {

    private Context mContext;

    public interface IAlertListener{
        void onClick(Object o);
    }

    private IAlertListener mIAlertListener;

    public void setListener(IAlertListener mIAlertListener){
        this.mIAlertListener=mIAlertListener;
    }

    public AlertDialog(Context mContext){
        this.mContext=mContext;

    }

    public void showDialog( String msg1, String msg2) {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom);

        TextView message1 = dialog.findViewById(R.id.diMessage1);
        message1.setVisibility(View.VISIBLE);
        message1.setText(msg1);

        TextView message2 = dialog.findViewById(R.id.diMessage2);
        message2.setVisibility(View.VISIBLE);
        message2.setText(msg2);

        Button dialogBtn_okay = dialog.findViewById(R.id.mbtnOK);
        dialogBtn_okay.setVisibility(View.VISIBLE);
        dialogBtn_okay.setText("Ok");
        dialogBtn_okay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mIAlertListener!=null){
                    mIAlertListener.onClick("click");
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public Dialog showProgressDialog(){

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.lay_progress_dialog);
        return dialog;
    }
}
