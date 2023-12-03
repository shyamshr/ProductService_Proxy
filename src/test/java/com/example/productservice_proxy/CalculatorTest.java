package com.example.productservice_proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    @Test
    @DisplayName("Test add method 1+2=3")
    public void Test_WhenAddTwoInteger_ReturnInteger(){
        // Arrange
        Calculator calculator = new Calculator();
        // Act
        int result = calculator.add(1,2);
        // Assert
        assertEquals(3,result);
    }

    @Test
    @DisplayName("Test divide by zero throw exception")
    public void Test_WhenDivideByZero_ThenThrowsException(){
        // Arrange
        Calculator calculator = new Calculator();
        // Assert                               // Act
        assertThrows(ArithmeticException.class, () -> calculator.divide(1,0));

    }
    @Test
    @DisplayName("Test multiply two integers return integers")
    public void Test_WhenMultiplyTwoIntegers_ThenReturnInteger(){
        // Arrange
        Calculator calculator = new Calculator();
        // Act
        int result = calculator.multiply(3,2);
        // Assert
        assertEquals(6,result);

    }

}