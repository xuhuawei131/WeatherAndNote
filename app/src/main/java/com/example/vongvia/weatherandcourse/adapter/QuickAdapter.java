package com.example.vongvia.weatherandcourse.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.vongvia.weatherandcourse.R;
import com.example.vongvia.weatherandcourse.utils.SmoothCheckBox;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import test.greenDAO.bean.Duty;

/**
 * Created by castl on 2016/5/20.
 */
public class QuickAdapter extends BaseQuickAdapter<Duty> {


    public QuickAdapter(Context context, List<Duty> dataSize) {
        super(context, R.layout.item_duty, dataSize);

    }

    @Override
    protected void convert(BaseViewHolder helper, Duty item) {

        SmoothCheckBox scb=helper.getView(R.id.scb);
        if (item.getStatus()) //默认显示已完成
            scb.setChecked(true);
        else
            scb.setChecked(false);

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1=item.getDate();
        String t1=format.format(d1);
        helper.setText(R.id.item_title, item.getTitle())
                .setText(R.id.item_info,"详情："+ item.getInfo())
                .setText(R.id.item_date,"始于："+ t1)
                .setOnClickListener(R.id.scb, new OnItemChildClickListener());

    }


}