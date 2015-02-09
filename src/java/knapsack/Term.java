/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package knapsack;

import database.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Marcin
 */
public class Term extends Range {
    
    protected int value;

    public Term(int low, int high, int value){
        super(low, high);
        this.value = value;
    }
    
    public Term(Range range, int value)
    {
        super(range.getLow().value, range.getHigh().value);
        this.value = value;
    }
    
    public Range intersection(Term t)
    {
        Range r1, r2;
        
        r1 = new Range(low.value, high.value);
        r2 = new Range(t.low.value, t.high.value);
        
        return r1.intersection(r2);
    }
    
    public int getValue()
    {
        return this.value;
    }
    
    static ArrayList<Term> getUserTerms(int $userId, int $eventId)
    {
        ArrayList<Term> terms = new ArrayList();
        
        try
        {
            Connection con = DBConnection.getCurrentConnection();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT * "
                + "FROM terms"
            );

            while(rs.next())
            {
                terms.add(
                    new Term(
                        rs.getInt("begin_time"),
                        rs.getInt("end_time"),
                        rs.getInt("weight")
                    )
                );
            }
        }
        catch(SQLException e)
        {
            System.out.println("SQLException");
        }
        
        return terms;
    }
}