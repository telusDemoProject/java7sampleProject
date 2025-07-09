package com.example.demo;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.example.demo.service.FileServiceTest;
import com.example.demo.service.UserServiceSimpleTest;
import com.example.demo.util.JwtUtilSimpleTest;

@Suite
@SelectClasses({
        UserServiceSimpleTest.class,
        FileServiceTest.class,
        JwtUtilSimpleTest.class
})
public class TestSuite {
}