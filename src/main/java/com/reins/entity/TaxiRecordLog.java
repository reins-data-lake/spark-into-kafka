package com.reins.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
public class TaxiRecordLog {
    private Long taxiId;
    private String tripId;
    private Long timestamp;
    private Double longtitude;
    private Double latitude;
    private Double speed;

    public TaxiRecordLog(Long taxiId, String tripId, Long timestamp, Double longtitude, Double latitude, Double speed) {
        this.taxiId = taxiId;
        this.tripId = tripId;
        this.timestamp = timestamp;
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.speed = speed;
    }

    public static TaxiRecordLog parseLines(String logString) {
        return null;
    }

    @Override
    public String toString() {
        return String.format("taxiId:%l,tripId:%s,timestamp:%l,longtitude:%f,latitude:%f,speed:%f",
                taxiId,
                tripId,
                timestamp,
                longtitude,
                latitude,
                speed);
    }
}
