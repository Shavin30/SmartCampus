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
        Room room1 = new Room("LIB-301", "Library Quiet Study", 50);
        ROOMS.add(room1);

        Sensor sensor1 = new Sensor("TEMP-001", "Temperature", "ACTIVE", "LIB-301");
        SENSORS.add(sensor1);
        
        room1.getSensorIds().add("TEMP-001");
    }
}