package com.example.vongvia.weatherandcourse.RxBus;

import test.greenDAO.bean.Duty;

/**
 * Created by castl on 2016/5/16.
 */
public class Event {

    public static class AddEvent {
        private Duty mduty;

        public AddEvent(Duty duty,String type) {
            this.mduty = duty;
        }

        public Duty getMduty() {
            return mduty;
        }

    }

    public static class TabEvent {
    }


}
