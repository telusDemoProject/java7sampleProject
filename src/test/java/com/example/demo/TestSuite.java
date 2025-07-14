package com.example.demo;

import com.example.demo.service.FileServiceTest;
import com.example.demo.service.UserServiceTest;
import com.example.demo.util.JwtUtilTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Complete Application Test Suite")
@SelectClasses({
    UserServiceTest.class,
    FileServiceTest.class,
    JwtUtilTest.class,
    com.example.demo.controller.UserControllerTest.class,
    com.example.demo.controller.FileControllerTest.class,
    com.example.demo.controller.AuthControllerTest.class
})
class TestSuite {
}