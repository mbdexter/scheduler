/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knapsack;

import database.DBConnection;
import java.lang.String;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Marcin
 */
public class Event {
    
    public int id;
    public int user_id;
    public String name;
    public String address;
    public int event_length;
    public String registration_end;
    
    public Event(int id, int user_id, String name, String address, int event_length, String registration_end)
    {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.address = address;
        this.event_length = event_length;
        this.registration_end = registration_end;
    }
    
    static ArrayList<Event> getEvents()
    {
        ArrayList<Event> events = new ArrayList();
        
        try
        {
            Connection con = DBConnection.getCurrentConnection();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM events");

            while(rs.next())
            {
                events.add(
                    new Event(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getInt("event_length"),
                        rs.getString("registration_end")
                    )
                );
            }
        }
        catch(SQLException e)
        {
            System.out.println("SQLException");
        }
        
        return events;
    }
}
