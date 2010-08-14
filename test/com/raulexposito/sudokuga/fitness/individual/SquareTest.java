package com.raulexposito.sudokuga.fitness.individual;

import org.junit.Test;
import static org.junit.Assert.*;

public class SquareTest {

    private static final int[] SQUARE_3X3 = {
            1, 2, 3,
            4, 5, 6,
            7, 8, 9
    };

    private static final int[] SQUARE_3X3_1_VALUE = {
            1, 1, 1,
            1, 1, 1,
            1, 1, 1
    };

    private static final int[] SQUARE_3X3_3_VALUES = {
            1, 2, 3,
            3, 1, 2,
            2, 3, 1
    };

    @Test
    public void testCalculateDuplicatedValues() {
        final Square square9 = new Square(3, SQUARE_3X3);
        if (square9.calculateNonDuplicatedValues() != 9) {
            fail ("deberia tener 9 numeros diferentes");
        }

        final Square square1 = new Square(3, SQUARE_3X3_1_VALUE);
        if (square1.calculateNonDuplicatedValues() != 1) {
            fail ("deberia tener 1 numeros diferentes");
        }

        final Square square3 = new Square(3, SQUARE_3X3_3_VALUES);
        if (square3.calculateNonDuplicatedValues() != 3) {
            fail ("deberia tener 3 numeros diferentes");
        }
    }
}