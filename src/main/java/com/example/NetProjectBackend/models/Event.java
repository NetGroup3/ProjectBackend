package com.example.NetProjectBackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private int id;
    private Timestamp creation_timestamp;
    private Timestamp event_timestamp;
    private String title;
    private String description;
    private String image_id;
    private String status;
    private double location_lat;
    private double location_lon;

}
