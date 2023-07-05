package com.example.cardiacrecorder;
import java.util.ArrayList;
import java.util.List;


public class addData {
    public List<Measurement> records = new ArrayList<>(); //a list of type "Medical Report Model" is declared

    /**
     * this method is used to add any new data
     * if data already exists,it will throw an exception
     * @param data a new record
     */
    public void addData(Measurement data)
    {
        if(records.contains(data))
        {
            throw new IllegalArgumentException();
        }
        records.add(data);
    }

    /**
     * this method returns an instance of sorted record list
     * sort is based on first attribute by default
     * @return a list of data
     */

    public List<Measurement> getData()
    {
        return records;
    }

    public Measurement getData(int x)
    {
        return records.get(x);
    }

    /**
     * this method is used for deleting a particular data
     * if the data doesnt exist,it will throw an exception
     * @param data a data that need to be deleted
     */
    public void deleteData(Measurement data)
    {
        List<Measurement> dataList = records;
        if(dataList.contains(data)){
            records.remove(data);
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * this method returns the size of list
     * @return int
     */
    public int countData()
    {
        return records.size();
    }
}
