/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knapsack;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import database.DBConnection;
import static knapsack.Knapsack.users;

/**
 *
 * @author Marcin
 */
public class DatabaseManager {
    
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
    
    static ArrayList<Term>[] getUsersTerms(int eventId)
    {
        int count = 0;
        users = (ArrayList<Term>[])new ArrayList[count];

        try
        {
            Connection con = DBConnection.getCurrentConnection();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM events WHERE id = " + eventId);

            while(rs.next())
            {
                System.out.println(rs.getString("name"));
            }
        }
        catch(SQLException e)
        {
            System.out.println("SQLException");
        }
        
        return users;
    }
    
}
