package com.mycompany.w2119859_cw.model;

public class Sensor implements BaseModel {

    private String id;
    private String type; // e.g., "Temperature", "CO2"
    private String status; // "ACTIVE", "MAINTENANCE", or "OFFLINE"
    private double currentValue;
    private String roomId;

    public Sensor() {
    }

    public Sensor(String id, String type, String status, String roomId) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.roomId = roomId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
