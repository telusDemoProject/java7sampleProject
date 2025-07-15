package com.example.demo;

import com.example.demo.service.FileServiceTest;
import com.example.demo.service.UserServiceSimpleTest;
import com.example.demo.util.JwtUtilSimpleTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    UserServiceSimpleTest.class,
    FileServiceTest.class,
    JwtUtilSimpleTest.class
})
public class TestSuite {
}