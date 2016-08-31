package org.guess.sysmaintenance.databasebackup;

import org.guess.core.utils.BackUpMySQL;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by rguess on 2015/1/9.
 */
//@Component
public class DateBaseBackUpScheduled {

//    @Scheduled(cron = "0/5 * * * * ?")
    public void backUp() {
        BackUpMySQL.backlinux();
    }
}
