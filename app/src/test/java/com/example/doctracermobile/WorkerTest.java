package com.example.doctracermobile;
import com.example.doctracermobile.entity.Employee;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WorkerTest {
    private String expectedRes;
    private Employee alex = new Employee("Александра",
                                  "Лаухина",
                                 "Сергеевна",
                                  "Мобильный разработчик",
                                   "+79191033323",
                                  "las281000@gmail.com");

    @Before
    public void setUp(){
        expectedRes = "Лаухина А.С., мобильный разработчик";
    }

    @Test
    public void toStringTest(){
        Assert.assertEquals(expectedRes, alex.toString());
    }
}
