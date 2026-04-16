package com.mycompany.w2119859_cw.repository;

import com.mycompany.w2119859_cw.model.Room;
import com.mycompany.w2119859_cw.model.Sensor;
import com.mycompany.w2119859_cw.model.SensorReading;
import java.util.ArrayList;
import java.util.List;

public class MockDatabase {

    // These static lists act as our database tables
    public static final List<Room> ROOMS = new ArrayList<>();

    public static final List<Sensor> SENSORS = new ArrayList<>();
    public static final List<SensorReading> READINGS = new ArrayList<>();

    static {
        // Initial Room
        ROOMS.add(new Room("LIB-301", "Library Quiet Study", 50));

        // Initial Sensor linked to LIB-301
        Sensor tempSensor = new Sensor("TEMP-001", "Temperature", "ACTIVE", "LIB-301");
        SENSORS.add(tempSensor);

        // Link the sensor ID back to the room's sensor list
        ROOMS.get(0).getSensorIds().add("TEMP-001");
    }
}
