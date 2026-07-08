package com.suraev.OnlineBokingManagement.entities;

import java.util.Date;
import java.util.List;

public class Schedule {
    private Long id;
    private BranchService branchService;
    private Date workingDate;
    private List<DaySchedule> daySchedules;
}
