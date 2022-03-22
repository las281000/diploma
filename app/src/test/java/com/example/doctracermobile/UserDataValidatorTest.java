package com.example.doctracermobile;

import com.example.doctracermobile.usecase.UserDataValidator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserDataValidatorTest {
    private String lenReply;
    private String symReply;
    private String numReply;

    @Before
    public void setUp(){
        lenReply = "Пароль должен содержать от 6 до 32 знаков.";
        symReply = "Запрещены: { } [ ] < > ( ) \u002F \\ ' \" . ; и пробелы.";
        numReply = "Номер телефона должен иметь форму +7 XXX XXX-XX-XX";
    }
//////////////////////// LENGTH TESTS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    @Test
    public void shortPassTest(){
        String password = "Test";
        Assert.assertEquals(lenReply, UserDataValidator.passwordCheck(password));
    }

    @Test
    public void longPassTest(){
        String password = "ThisIsNeededToGetTheWarningFromApplication"; //42 signs
        String expected = "Пароль должен содержать от 6 до 32 знаков.";
        Assert.assertEquals(lenReply, UserDataValidator.passwordCheck(password));
    }

//////////////////////////// FORBIDDEN CHARACTERS TESTS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    @Test
    public void bBracketTest(){
        String password = "For(Test)";
        Assert.assertEquals(symReply, UserDataValidator.passwordCheck(password));
    }

    @Test
    public void sBracketTest(){
        String password = "For[Test]";
        String expected = "Пароль НЕ МОЖЕТ содержать следующие символы: { } \\[ \\] < > ( ) \u002F \\ \' \" . ; и пробелы.";
        Assert.assertEquals(symReply, UserDataValidator.passwordCheck(password));
    }

    @Test
    public void fBracketTest(){
        String password = "For{Test}";
        Assert.assertEquals(symReply, UserDataValidator.passwordCheck(password));
    }

    @Test
    public void aBracketTest(){
        String password = "For<Test>";
        Assert.assertEquals(symReply, UserDataValidator.passwordCheck(password));
    }

    @Test
    public void slashTest(){
        String password = "For/Test";
        Assert.assertEquals(symReply, UserDataValidator.passwordCheck(password));
    }

    @Test
    public void backSlashTest(){
        String password = "For\\Test";
        Assert.assertEquals(symReply, UserDataValidator.passwordCheck(password));
    }

    @Test
    public void spaceTest(){
        String password = "For Test";
        Assert.assertEquals(symReply, UserDataValidator.passwordCheck(password));
    }

    @Test
    public void doubleQuoteTest(){
        String password = "For\"Test";
        Assert.assertEquals(symReply, UserDataValidator.passwordCheck(password));
    }

    @Test
    public void quoteTest(){
        String password = "For\'Test";
        Assert.assertEquals(symReply, UserDataValidator.passwordCheck(password));
    }

    @Test
    public void pointTest(){
        String password = "For.Test";
        Assert.assertEquals(symReply, UserDataValidator.passwordCheck(password));
    }

    @Test
    public void semicolonTest(){
        String password = "For;Test";
        Assert.assertEquals(symReply, UserDataValidator.passwordCheck(password));
    }

    @Test
    public void correctTest(){
        String password = "CorrectPassword";
        Assert.assertEquals(null, UserDataValidator.passwordCheck(password));
    }

    //////////////////////// PHONE NUM FORM TESTS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    @Test
    public void wrongNumTest(){
        String num = "89191033323";
        Assert.assertEquals(numReply, UserDataValidator.phoneCheck(num));
    }

    @Test
    public void correctNumTest(){
        String num = "+7 919 103-33-23";
        Assert.assertEquals(null, UserDataValidator.phoneCheck(num));
    }

}
