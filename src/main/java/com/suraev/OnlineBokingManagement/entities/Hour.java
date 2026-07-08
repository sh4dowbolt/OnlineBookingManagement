package com.suraev.OnlineBokingManagement.entities;

import java.util.ArrayList;
import java.util.List;

public enum Hour {
    Twelve,
    Thirteen,
    Fourteen,
    Fifteen,
    Sixteen,
    Seventeen,
    Eighteen,
    Nineteen,
    Twenty

    public List<Hour>  getAllHours() {
        List<Hour> hours = new ArrayList<Hour>();
        hours.add(Twelve);
        hours.add(Thirteen);
        hours.add(Fourteen);
        hours.add(Fifteen);

    }
}
