/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package knapsack;

import java.util.*;

/**
 *
 * @author Marcin
 */
public class Range {
    
    public RangePoint low;
    public RangePoint high;

    public Range(int low, int high)
    {
        this.low = new RangePoint(low);
        this.high = new RangePoint(high);
    }

    public boolean contains(int number)
    {
        return (number >= low.getValue() && number <= high.getValue());
    }
    
    public Range intersection(Range t)
    {
        int thisId;
        int otherId;
        
        RangePoint points[] = new RangePoint[4];
        Range result = null;
        
        if(low.getValue() != t.getLow().getValue())
        {
            thisId = 2;
            otherId = 1;
        }
        else
        {
            thisId = 1;
            otherId = 2;
        }
        
        low.setGroupId(thisId);
        high.setGroupId(thisId);
        t.low.setGroupId(otherId);
        t.high.setGroupId(otherId);
        
        points[0] = low;
        points[1] = high;
        points[2] = t.getLow();
        points[3] = t.getHigh();
        
        Arrays.sort(points);
        
        if(points[0].getGroupId() != points[1].getGroupId())
        {
            result = new Range(points[1].value, points[2].getValue());
        }
        
        return result;
    }
    
    public int rangeSize()
    {
        return high.value - low.value;
    }
    
    public RangePoint getLow()
    {
        return low;
    }
    
    public RangePoint getHigh()
    {
        return high;
    }
    
}
