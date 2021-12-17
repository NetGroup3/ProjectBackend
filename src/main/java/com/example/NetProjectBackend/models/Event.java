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
    private Timestamp creationTimestamp;
    private Timestamp eventTimestamp;
    private String title;
    private String description;
    private String imageId;
    private String status;
    private double locationLat;
    private double locationLon;

}
