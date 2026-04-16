package com.mycompany.w2119859_cw.repository;

import com.mycompany.w2119859_cw.model.Room;
import java.util.ArrayList;
import java.util.List;

public class MockDatabase {
    // These static lists act as our database tables
    public static final List<Room> ROOMS = new ArrayList<>();

    static {
        // Sample data for initial testing
        ROOMS.add(new Room("LIB-301", "Library Quiet Study", 50));
        ROOMS.add(new Room("ENG-101", "Engineering Lab", 30));
    }
}