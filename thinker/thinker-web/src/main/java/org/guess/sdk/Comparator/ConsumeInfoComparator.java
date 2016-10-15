package org.guess.sdk.Comparator;

import org.guess.sdk.dto.ConsumeInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by wan.peng on 2016/10/15.
 */
public class ConsumeInfoComparator implements Comparator<ConsumeInfo>{

    @Override
    public int compare(ConsumeInfo o1, ConsumeInfo o2) {
        DateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = dateFormat.parse(o1.getDate());
            d2 = dateFormat.parse(o2.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(d1.getTime()>d2.getTime()){
            return -1;
        }else if(d1.getTime()<d2.getTime()){
            return 1;
        }else{
            return 0;
        }
    }
}
