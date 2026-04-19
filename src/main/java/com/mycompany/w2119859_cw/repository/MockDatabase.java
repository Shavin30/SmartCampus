package com.mycompany.w2119859_cw.repository;

import com.mycompany.w2119859_cw.model.Room;
import com.mycompany.w2119859_cw.model.Sensor;
import com.mycompany.w2119859_cw.model.SensorReading;
import java.util.ArrayList;
import java.util.List;

public class MockDatabase {
    public static final List<Room> ROOMS = new ArrayList<>();
    public static final List<Sensor> SENSORS = new ArrayList<>();
    public static final List<SensorReading> READINGS = new ArrayList<>();

    static {
        // Create Room first
        Room room1 = new Room("LIB-301", "Library Quiet Study", 50);
        ROOMS.add(room1);

        // Create Sensor and link to Room ID
        Sensor sensor1 = new Sensor("TEMP-001", "Temperature", "OFFLINE", "LIB-301");
        SENSORS.add(sensor1);
        
        // Add Sensor ID to the Room's list
        room1.getSensorIds().add("TEMP-001");
    }
}