package logic;

import java.text.SimpleDateFormat;

public class TgtLoadSchedule {

    private SimpleDateFormat sched_start = new SimpleDateFormat("dd.MM.yyyy hh:mm");
    private SimpleDateFormat sched_end = new SimpleDateFormat("dd.MM.yyyy hh:mm");
    private SimpleDateFormat sched_time = new SimpleDateFormat("hh:mm");
    private Integer sched_day_of_month;
    private Integer sched_day_of_week;

    public TgtLoadSchedule(){

    }
}
