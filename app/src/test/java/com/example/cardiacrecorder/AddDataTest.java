package com.example.cardiacrecorder;

import static org.junit.Assert.*;
import org.junit.Test;

public class AddDataTest {
    String curr_date = "06/07/2023";
    String curr_time = "23:53";

    /**
     * Testing addData method
     */
    @Test
    public void testAddData() {
        addData dataList = new addData();
        Measurement data1 = new Measurement(130, 78, curr_date, curr_time, 88, "Seems fine");
        dataList.addData(data1);
        assertEquals(1, dataList.getData().size());

        Measurement data2 = new Measurement(122, 71, curr_date, curr_time, 98, "Heart rate is not fine");
        dataList.addData(data2);
        assertEquals(2, dataList.getData().size());

        assertTrue(dataList.getData().contains(data1));
        assertTrue(dataList.getData().contains(data2));
    }

    /**
     * Testing deleteData method
     */
    @Test
    public void testDeleteData() {
        addData dataList = new addData();
        Measurement data1 = new Measurement(130, 78, curr_date, curr_time, 88, "Seems fine");
        dataList.addData(data1);
        assertEquals(1, dataList.getData().size());

        Measurement data2 = new Measurement(122, 71, curr_date, curr_time, 98, "Heart rate is not fine");
        dataList.addData(data2);
        assertEquals(2, dataList.getData().size());

        assertTrue(dataList.getData().contains(data1));
        assertTrue(dataList.getData().contains(data2));

        dataList.deleteData(data1);
        assertEquals(1, dataList.getData().size());
        assertFalse(dataList.getData().contains(data1));

        dataList.deleteData(data2);
        assertEquals(0, dataList.getData().size());
        assertFalse(dataList.getData().contains(data2));
    }

    /**
     * Testing addData method for exceptions
     */
    @Test
    public void testAddRecordException() {
        addData dataList = new addData();
        Measurement data1 = new Measurement(130, 78, curr_date, curr_time, 88, "Seems fine");
        dataList.addData(data1);

        assertThrows(IllegalArgumentException.class, () -> dataList.addData(data1));
    }

    /**
     * Testing deleteData method for exceptions
     */
    @Test
    public void testDeleteRecordException() {
        addData dataList = new addData();
        Measurement data1 = new Measurement(130, 78, curr_date, curr_time, 88, "Seems fine");
        dataList.addData(data1);

        dataList.deleteData(data1);

        assertThrows(IllegalArgumentException.class, () -> dataList.deleteData(data1));
    }

    /**
     * Testing editData method
     */
    @Test
    public void testEditData() {
        addData dataList = new addData();
        Measurement data1 = new Measurement(130, 78, curr_date, curr_time, 88, "Seems fine");
        Measurement newData = new Measurement(140, 80, curr_date, curr_time, 90, "Doing well");

        dataList.addData(data1);
        assertEquals(1, dataList.getData().size());

        dataList.editData(data1, newData);

        assertEquals(1, dataList.getData().size());
        assertFalse(dataList.getData().contains(data1));
        assertTrue(dataList.getData().contains(newData));
    }
}
