package com.example.vongvia.weatherandcourse.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.vongvia.weatherandcourse.R;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by castl on 2016/5/19.
 */
public class AddDialogFragment extends DialogFragment {

    private EditText et_title;
    private EditText et_info;

    private MaterialSpinner bp;

    //创建接口在Acitvity中调用
    public interface AddDutyInputListener {
        void onAddDutyInputComplete(String title, String type, String info);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String[] ITEMS = {"学习", "工作", "运动", "日常"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add, null);
        et_title = (EditText) view.findViewById(R.id.et_title);
        et_info = (EditText) view.findViewById(R.id.et_info);
        bp = (MaterialSpinner) view.findViewById(R.id.spinner);
        bp.setAdapter(adapter);

        builder.setView(view)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                AddDutyInputListener listener = (AddDutyInputListener) getActivity();
                                listener.onAddDutyInputComplete(et_title.getText().toString(), bp.getSelectedItem().toString(), et_info.getText().toString());
                            }

                        }).setNegativeButton("取消", null);
        return builder.create();
    }


}
