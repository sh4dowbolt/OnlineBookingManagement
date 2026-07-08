package com.suraev.OnlineBokingManagement.entities;

import java.math.BigDecimal;
import java.time.Duration;

public class Service {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Duration duration;
    private Photo photo;
    private Category category;
}
