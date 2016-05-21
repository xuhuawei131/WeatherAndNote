package com.example.vongvia.weatherandcourse.ui;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vongvia.weatherandcourse.Net.NetWork;
import com.example.vongvia.weatherandcourse.Net.Weather;
import com.example.vongvia.weatherandcourse.R;
import com.example.vongvia.weatherandcourse.RxBus.Event;
import com.example.vongvia.weatherandcourse.RxBus.RxBus;
import com.example.vongvia.weatherandcourse.adapter.ViewPagerAdapter;
import com.example.vongvia.weatherandcourse.dialogs.AboutDialogFragment;
import com.example.vongvia.weatherandcourse.dialogs.AddDialogFragment;
import com.example.vongvia.weatherandcourse.dialogs.ChooseCityDialogFragment;
import com.example.vongvia.weatherandcourse.fragments.DailyFragment;
import com.example.vongvia.weatherandcourse.utils.AppUtils;
import com.example.vongvia.weatherandcourse.utils.DbServices;
import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Date;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import test.greenDAO.bean.Duty;

public class MainActivity extends RxAppCompatActivity implements View.OnClickListener, ChooseCityDialogFragment.CityInputListener, AddDialogFragment.AddDutyInputListener {
    private static RxBus _rxBus;

    /**
     * 获取RxBus对象
     */
    public static RxBus getRxBusSingleton() {
        if (_rxBus == null) {
            _rxBus = new RxBus();
        }
        return _rxBus;
    }

    private TextView city;
    private TextView changecity, tv_about, scan_wholeweek;
    private ImageView iv_weather;
    private TextView date, weather, temperature, wind;
    private FrameLayout head_layout;
    private ImageView iv_bing_back;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsing_toolbar_layout;
    private TabLayout toolbar_tab;
    private AppBarLayout app_bar_layout;
    private ViewPager main_vp_container;
    private FloatingActionButton fab_add;

    private SharedPreferences preferences;  //修改城市时保存到本地
    private SharedPreferences.Editor sp_edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _rxBus = getRxBusSingleton();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        }


        initView();
        initData();
    }


    private void initView() {
        preferences = getSharedPreferences("weathercity", MODE_PRIVATE);
        sp_edit = preferences.edit();

        changecity = (TextView) findViewById(R.id.changecity);
        tv_about = (TextView) findViewById(R.id.tv_about);
        scan_wholeweek = (TextView) findViewById(R.id.tv_scan_all);
        changecity.setOnClickListener(this);
        tv_about.setOnClickListener(this);
        scan_wholeweek.setOnClickListener(this);


        fab_add = (FloatingActionButton) findViewById(R.id.fabBtn);

        city = (TextView) findViewById(R.id.city);
        iv_weather = (ImageView) findViewById(R.id.iv_weather);
        date = (TextView) findViewById(R.id.date);
        weather = (TextView) findViewById(R.id.weather);
        temperature = (TextView) findViewById(R.id.temperature);
        head_layout = (FrameLayout) findViewById(R.id.head_layout);
        wind = (TextView) findViewById(R.id.wind);
        iv_bing_back = (ImageView) findViewById(R.id.iv_bing_back);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCollapsing_toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        toolbar_tab = (TabLayout) findViewById(R.id.toolbar_tab);
        app_bar_layout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        main_vp_container = (ViewPager) findViewById(R.id.main_vp_container);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -head_layout.getHeight() / 2) {
                    mCollapsing_toolbar_layout.setTitle("天气备忘录");
                } else {
                    mCollapsing_toolbar_layout.setTitle(" ");
                }
            }
        });
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialogFragment adddialog = new AddDialogFragment();
                adddialog.show(getFragmentManager(), "addDialog");
            }
        });


        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        vpAdapter.addFragment(new DailyFragment().newInstance("学习"), "学习");
        vpAdapter.addFragment(new DailyFragment().newInstance("工作"), "工作");
        vpAdapter.addFragment(new DailyFragment().newInstance("运动"), "运动");
        vpAdapter.addFragment(new DailyFragment().newInstance("日常"), "日常");
        main_vp_container.setAdapter(vpAdapter);
        main_vp_container.setOffscreenPageLimit(4);  //设置4页缓存

        toolbar_tab.setupWithViewPager(main_vp_container);

    }

    private void initData() {
        if (AppUtils.back_url != null) {
            Picasso.with(this)
                    .load(AppUtils.back_url)
                    .centerCrop()
                    .fit()
                    .into(iv_bing_back);
        }
        if (AppUtils.today_weather != null) {
            showWeatherInfo();
        }

    }

    //显示天气信息
    private void showWeatherInfo() {
        //将天气信息填充
        weather.setText("天气：" + AppUtils.today_weather.getResult().getData().getRealtime().getWeather().getInfo());
        city.setText(AppUtils.today_weather.getResult().getData().getRealtime().getCity_name());
        date.setText("日期：" + AppUtils.today_weather.getResult().getData().getRealtime().getDate());
        wind.setText("风向：" + AppUtils.today_weather.getResult().getData().getRealtime().getWind().getDirect() + AppUtils.today_weather.getResult().getData().getRealtime().getWind().getPower());
        temperature.setText("温度：" + AppUtils.today_weather.getResult().getData().getRealtime().getWeather().getTemperature());

        //然后根据天气状况选择图片
        String weatherimg = "http://www.autumn-love.com/weathericon/day/" + AppUtils.today_weather.getResult().getData().getRealtime().getWeather().getImg() + ".png";

        Picasso.with(this)
                .load(weatherimg)
                .centerCrop()
                .fit()
                .into(iv_weather);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changecity:
                ChooseCityDialogFragment dialog = new ChooseCityDialogFragment();
                dialog.show(getFragmentManager(), "changecityDialog");
                break;

            case R.id.tv_about:
                AboutDialogFragment aboutdialog = new AboutDialogFragment();
                aboutdialog.show(getFragmentManager(), "aboutDialog");
                break;

            case R.id.tv_scan_all:

                Toast.makeText(MainActivity.this, "即将出炉", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onCityInputComplete(String cityname) {
        NetWork.getWeatherApi().getWeatherInfo(cityname, WelcomeActivity.API_KEY)
                .compose(this.<Weather>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Weather>() {
                    @Override
                    public void call(Weather weather) {
                        if (weather.getError_code() != 0) {
                            Toast.makeText(MainActivity.this, weather.getReason(), Toast.LENGTH_SHORT).show();
                        } else {
                            sp_edit.putString("city", weather.getResult().getData().getRealtime().getCity_name());
                            sp_edit.commit();
                            AppUtils.today_weather = weather;
                            showWeatherInfo();
                        }
                    }
                });
    }

    @Override
    public void onAddDutyInputComplete(String title, String type, String info) {

        if (title.trim().isEmpty()) {
            Toast.makeText(MainActivity.this, "标题不能为空！", Toast.LENGTH_SHORT).show();
        } else {

            Duty newduty = new Duty(null, title, info, type, false, new Date());
            DbServices.getInstance(this).saveNote(newduty);
            if (_rxBus.hasObservers()) {    //是否有观察者，有，则发送一个事件
                _rxBus.send(new Event.AddEvent(newduty,type));
            }
        }
    }


}
