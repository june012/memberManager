package org.guess.facility;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by wan.peng on 2016/10/13.
 */
public class CalendarUtils {
    public static String getWeek(Date date){
        String[] weekOfDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar calendar = Calendar.getInstance();
        if(date != null){ calendar.setTime(date); }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return weekOfDays[w];
    }
}
