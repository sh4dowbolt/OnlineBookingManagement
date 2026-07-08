package com.suraev.OnlineBokingManagement.entities;

import java.util.List;

public class DaySchedule {
    private Long id;
    private Master master;
    private Schedule schedule;
    private List<HourPerDay> hourPerDay;
    public DaySchedule() {

    }
}
