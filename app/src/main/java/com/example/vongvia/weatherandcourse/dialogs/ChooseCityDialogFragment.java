package com.example.vongvia.weatherandcourse.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.vongvia.weatherandcourse.R;

/**
 * Created by castl on 2016/5/19.
 */
public class ChooseCityDialogFragment extends DialogFragment {

    private EditText mCityname;


    public interface CityInputListener {
        void onCityInputComplete(String cityname);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_cityname, null);
        mCityname = (EditText) view.findViewById(R.id.id_txt_cityname);


        builder.setView(view)
                // Add action buttons
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //先检测输入城市是否合法
                                CityInputListener listener = (CityInputListener) getActivity();
                                listener.onCityInputComplete(mCityname.getText().toString());
                            }

                        }).setNegativeButton("取消", null);
        return builder.create();
    }
}
