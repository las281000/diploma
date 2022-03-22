package com.example.doctracermobile;
import com.example.doctracermobile.entity.Worker;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WorkerTest {
    private String expectedRes;
    private Worker alex = new Worker("Александра",
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
