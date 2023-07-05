package com.example.cardiacrecorder;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
public class addDataTest {
    String curr_date = "06/07/2023";
    String curr_time = "23 : 53";

    /**
     * testing addData method
     */
    @Test
    public void testAddData() {

        addData dataList = new addData();
        Measurement data1 = new Measurement(130, 78, "06/07/2023", "23:53", 88, "Seems fine");
        dataList.addData(data1);
        assertEquals(1, dataList.getData().size());

        Measurement data2 = new Measurement(122, 71, "06/07/2023", "23:53", 98, "Heart rate is not fine");
        dataList.addData(data2);
        assertEquals(2, dataList.getData().size());

        assertTrue(dataList.getData().contains(data1));
        assertTrue(dataList.getData().contains(data2));
    }

}