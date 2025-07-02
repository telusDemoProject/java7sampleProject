package com.example.demo;

import com.example.demo.service.FileServiceTest;
import com.example.demo.service.UserServiceSimpleTest;
import com.example.demo.util.JwtUtilSimpleTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    UserServiceSimpleTest.class,
    FileServiceTest.class,
    JwtUtilSimpleTest.class
})
public class TestSuite {
}