/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Marcin
 */
public class DBConnection {

    public static Connection connection = null;

    public static Connection getCurrentConnection()
    {
        if(connection != null)
        {
            return connection;
        }
        else
        {
            try
            {
                try
                {
                    try
                    {
                        Class.forName("com.mysql.jdbc.Driver").newInstance();

                        try
                        {
                            connection = DriverManager.getConnection("jdbc:mysql://localhost/eventsmanager?user=root&password=");
                        }
                        catch(SQLException e)
                        {
                            System.out.println("SQLException");
                        }
                    }
                    catch(IllegalAccessException e)
                    {
                        System.out.println("IllegalAccessException");
                    }
                }
                catch(InstantiationException e)
                {
                    System.out.println("InstantiationException");
                }
            }
            catch(ClassNotFoundException e)
            {
                System.out.println("ClassNotFoundException");
            }
        }
        
        return connection;
    }
}
