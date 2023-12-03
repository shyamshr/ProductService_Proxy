package com.example.productservice_proxy;

import org.junit.jupiter.api.Test;

import java.util.Random;

public class RandomTest {
    @Test
    public void randomTest() { //flaky test
        Random random = new Random();
        int a = random.nextInt(10);
        assert(a < 5);
    }
}
