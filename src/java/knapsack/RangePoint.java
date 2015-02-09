/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package knapsack;

/**
 *
 * @author Marcin
 */
public class RangePoint implements Comparable<RangePoint> {
    
    public int value;
    private int groupId;
    
    public RangePoint(int value)
    {
        this.value = value;
    }
    
    public int getValue()
    {
        return value;
    }
    
    public void setGroupId(int id)
    {
        groupId = id;
    }
    
    public int getGroupId()
    {
        return groupId;
    }

    @Override
    public int compareTo(RangePoint o)
    {
        int result = value - o.value;
        if (result != 0)
            return result;
        else
            return groupId - o.groupId;
    }
    
}
