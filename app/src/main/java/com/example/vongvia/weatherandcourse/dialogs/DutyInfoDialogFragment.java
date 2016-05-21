package com.example.vongvia.weatherandcourse.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.vongvia.weatherandcourse.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import test.greenDAO.bean.Duty;

/**
 * Created by castl on 2016/5/21.
 */
public class DutyInfoDialogFragment extends DialogFragment {

    private TextView duty_info_title;
    private TextView duty_info_type;
    private TextView duty_info_content;
    private TextView duty_info_date;
    private TextView duty_info_state;

    public static final String DUTY = "DUTY";
    private Duty myduty;

    public DutyInfoDialogFragment newInstance(Duty duty) {
        Bundle args = new Bundle();
        args.putParcelable(DUTY, duty);
        DutyInfoDialogFragment infoFragment = new DutyInfoDialogFragment();
        infoFragment.setArguments(args);
        return infoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myduty = getArguments().getParcelable(DUTY);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_dutyinfo, null);
        duty_info_title = (TextView) view.findViewById(R.id.duty_info_title);
        duty_info_type = (TextView) view.findViewById(R.id.duty_info_type);
        duty_info_content = (TextView) view.findViewById(R.id.duty_info_content);
        duty_info_date = (TextView) view.findViewById(R.id.duty_info_date);
        duty_info_state = (TextView) view.findViewById(R.id.duty_info_state);


        duty_info_title.setText(myduty.getTitle());
        duty_info_type.setText(myduty.getType());
        duty_info_content.setText(myduty.getInfo());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = myduty.getDate();
        String t1 = format.format(d1);


        duty_info_date.setText(t1);
        String state = myduty.getStatus() ? "已完成" : "未完成";
        duty_info_state.setText(state);


        builder.setView(view);
        return builder.create();
    }


}
