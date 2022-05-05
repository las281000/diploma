package com.example.doctracermobile;

import org.junit.Before;

public class ProjectDataValidatorTest {
    private String v8;
    private String v10;
    private String v12;
    private String v13;
    private String v15;
    private String vLet12;
    private String vLet10;
    private String ogrnLet;

    private String inn12Reply;
    private String inn10Reply;
    private String ogrnReply;

    private String name;
    private String nameCap;

    @Before
    public void setUp(){
        v8 = "12345678"; //8 символов
        v10 = "1234567890"; //10 символов
        v12 = "123456789012"; //12 символов
        v13 = "1234567890123"; //13 символов
        v15 = "123456789012345"; //15 символов
        vLet12 = "123456789a";// ИНН (10) содержит букву
        vLet10 = "12345678901b"; //ИНН (12) содержит букву
        ogrnLet = "123456789012c"; //ОГРН (13) содержит букву

        inn12Reply = "ИНН для данной ОФП должен состоять из 12 цифр!";
        inn10Reply = "ИНН для данной ОФП должен состоять из 10 цифр!";
        ogrnReply = "ОГРН должен состоять из 13 цифр!";

        name = "авилон";
        nameCap = "Авилон";
    }


//////////////////////////////////// PE Tests (12 signs) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    /*@Test
    public void shortINN_PE(){ //короткий
        Assert.assertEquals(inn12Reply, CompanyDataValidator.innCheck(v10, CompanyDataValidator.PE));
    }

    @Test
    public void longINN_PE(){ //длинный
        Assert.assertEquals(inn12Reply, CompanyDataValidator.innCheck(v13, CompanyDataValidator.PE));
    }

    @Test
    public void normalINN_PE(){ //правильный
        Assert.assertEquals(null, CompanyDataValidator.innCheck(v12, CompanyDataValidator.PE));
    }


//////////////////////////////////// LE Tests (10 signs) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    @Test
    public void shortINN_LE(){ //короткий
        Assert.assertEquals(inn10Reply, CompanyDataValidator.innCheck(v8, CompanyDataValidator.LE));
    }

    @Test
    public void longINN_LE(){ //длинный
        Assert.assertEquals(inn10Reply, CompanyDataValidator.innCheck(v12, CompanyDataValidator.LE));
    }

    @Test
    public void normalINN_LE(){ //нормальный
        Assert.assertEquals(null, CompanyDataValidator.innCheck(v10, CompanyDataValidator.LE));
    }

////////////////////////////////// LETTERS TEST \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    @Test
    public void letINN_PE(){ //
        Assert.assertEquals(inn12Reply, CompanyDataValidator.innCheck(vLet12, CompanyDataValidator.PE));
    }

    @Test
    public void letINN_LE(){ //
        Assert.assertEquals(inn10Reply, CompanyDataValidator.innCheck(vLet10, CompanyDataValidator.LE));
    }

    @Test
    public void letOGRN(){ //
        Assert.assertEquals(ogrnReply, CompanyDataValidator.ogrnCheck(ogrnLet));
    }*/

//////////////////////////////// CAPITAL LETTER TEST \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/*
    @Test
    public void noCapLetter(){
        Assert.assertEquals(false, CompanyDataValidator.capitalLetterCheck(name));
    }

    @Test
    public void CapLetter(){
        Assert.assertEquals(true, CompanyDataValidator.capitalLetterCheck(nameCap));
    }*/

}
