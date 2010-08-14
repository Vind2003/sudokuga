package com.raulexposito.sudokuga.fitness.individual;

import com.raulexposito.sudokuga.exception.BuilderException;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

public class SudokuBoardTest {

    private static final String PIPE = SudokuBoard.CELL_SEPARATOR;

    private static final String WELL_FORMED_SIZE_SUDOKU =
            "1" + PIPE + "2" + PIPE + "3" + PIPE + "4" + PIPE +
            "1" + PIPE + "2" + PIPE + "3" + PIPE + "4" + PIPE +
            "1" + PIPE + "2" + PIPE + "3" + PIPE + "4" + PIPE +
            "1" + PIPE + "2" + PIPE + "3" + PIPE + "4";

    private static final String BAD_FORMED_SIZE_SUDOKU =
            "1" + PIPE + "2" + PIPE + "3" + PIPE + "4" + PIPE +
            "1" + PIPE + "2" + PIPE + "3" + PIPE + "4" + PIPE +
            "1" + PIPE + "2" + PIPE + "3" + PIPE + "4" + PIPE +
            "1" + PIPE + "2" + PIPE + "3";

    private static final String BAD_FORMED_INVALID_CHARACTERS_SUDOKU =
            "1" + PIPE + "2" + PIPE + "3" + PIPE + "4" + PIPE +
            "1" + PIPE + "2" + PIPE + "!" + PIPE + "4" + PIPE +
            "1" + PIPE + ":" + PIPE + "3" + PIPE + "4" + PIPE +
            "1" + PIPE + "2" + PIPE + "3" + PIPE + "@";

    private static final String BAD_FORMED_VALUES_TOO_HIGH_SUDOKU =
            "6" + PIPE + "2" + PIPE + "3" + PIPE + "42" + PIPE +
            "7" + PIPE + "2" + PIPE + "35" + PIPE + "4" + PIPE +
            "8" + PIPE + "12" + PIPE + "3" + PIPE + "4" + PIPE +
            "1" + PIPE + "2" + PIPE + "3" + PIPE + "4";

    private static final String BAD_FORMED_VALUES_TOO_LOW_SUDOKU =
            "1" + PIPE + "2" + PIPE + "3" + PIPE + "4" + PIPE +
            "1" + PIPE + "2" + PIPE + "-3" + PIPE + "-4" + PIPE +
            "1" + PIPE + "-2" + PIPE + "-3" + PIPE + "4" + PIPE +
            "1" + PIPE + "2" + PIPE + "3" + PIPE + "-4";

    private static final int[] SUDOKU_4X4 = {
            1, 2, 3, 4,
            1, 2, 3, 4,
            3, 3, 1, 1,
            4, 4, 2, 2
    };

    private static final int[] SUDOKU_4X4_1AND2 = {
            1, 1, 1, 1,
            2, 2, 2, 2,
            1, 1, 1, 1,
            2, 2, 2, 2
    };

    private static final int[] TEMPLATE_SUDOKU_4X4 = {
            0, 2, 0, 4,
            1, 0, 3, 0,
            0, 0, 0, 1,
            4, 0, 0, 2
    };

    @Test(expected=BuilderException.class)
    public void BadFormedSizeStringSudokuBoardTest() throws BuilderException {
        new SudokuBoard(2, BAD_FORMED_SIZE_SUDOKU);
    }

    @Test(expected=BuilderException.class)
    public void BadFormedInvalidCharactersStringSudokuBoardTest() throws BuilderException {
        new SudokuBoard(2, BAD_FORMED_INVALID_CHARACTERS_SUDOKU);
    }

    @Test(expected=BuilderException.class)
    public void BadFormedValuesTooHighSudokuBoardTest() throws BuilderException {
        new SudokuBoard(2, BAD_FORMED_VALUES_TOO_HIGH_SUDOKU);
    }

    @Test(expected=BuilderException.class)
    public void BadFormedValuesTooLowSudokuBoardTest() throws BuilderException {
        new SudokuBoard(2, BAD_FORMED_VALUES_TOO_LOW_SUDOKU);
    }

    @Test
    public void WellFormedSudokuBoardTest() throws BuilderException {
        new SudokuBoard(2, WELL_FORMED_SIZE_SUDOKU);
    }

    @Test
    public void testGetLine() throws BuilderException {
        final SudokuBoard sudokuBoard = new SudokuBoard(2, SUDOKU_4X4);
        int[] line0 = {1,2,3,4};
        int[] line1 = {1,2,3,4};
        int[] line2 = {3,3,1,1};
        int[] line3 = {4,4,2,2};

        if (!Arrays.equals(sudokuBoard.getLine(0), line0)) {
            fail ("Se ha recuperado " + Arrays.toString(sudokuBoard.getLine(0)) + " en vez de " + Arrays.toString(line0));
        }

        if (!Arrays.equals(sudokuBoard.getLine(1), line1)) {
            fail ("Se ha recuperado " + Arrays.toString(sudokuBoard.getLine(1)) + " en vez de " + Arrays.toString(line1));
        }

        if (!Arrays.equals(sudokuBoard.getLine(2), line2)) {
            fail ("Se ha recuperado " + Arrays.toString(sudokuBoard.getLine(2)) + " en vez de " + Arrays.toString(line2));
        }

        if (!Arrays.equals(sudokuBoard.getLine(3), line3)) {
            fail ("Se ha recuperado " + Arrays.toString(sudokuBoard.getLine(3)) + " en vez de " + Arrays.toString(line3));
        }
    }

    @Test
    public void testGetColumn() throws BuilderException {
        final SudokuBoard sudokuBoard = new SudokuBoard(2, SUDOKU_4X4);
        int[] column0 = {1,1,3,4};
        int[] column1 = {2,2,3,4};
        int[] column2 = {3,3,1,2};
        int[] column3 = {4,4,1,2};

        if (!Arrays.equals(sudokuBoard.getColumn(0), column0)) {
            fail ("Se ha recuperado " + Arrays.toString(sudokuBoard.getColumn(0)) + " en vez de " + Arrays.toString(column0));
        }

        if (!Arrays.equals(sudokuBoard.getColumn(1), column1)) {
            fail ("Se ha recuperado " + Arrays.toString(sudokuBoard.getColumn(1)) + " en vez de " + Arrays.toString(column1));
        }

        if (!Arrays.equals(sudokuBoard.getColumn(2), column2)) {
            fail ("Se ha recuperado " + Arrays.toString(sudokuBoard.getColumn(2)) + " en vez de " + Arrays.toString(column2));
        }

        if (!Arrays.equals(sudokuBoard.getColumn(3), column3)) {
            fail ("Se ha recuperado " + Arrays.toString(sudokuBoard.getColumn(3)) + " en vez de " + Arrays.toString(column3));
        }
    }

    @Test
    public void testCalculateNonDuplicatedValuesInLines() throws BuilderException {
        final SudokuBoard sudokuBoard = new SudokuBoard(2, SUDOKU_4X4);
        if (sudokuBoard.calculateNonDuplicatedValuesInLines() != 12) {
            fail ("Se han recuperado " + sudokuBoard.calculateNonDuplicatedValuesInLines() + " valores no duplicados en filas en vez de 12");
        }

        final SudokuBoard sudokuBoard1and2 = new SudokuBoard(2, SUDOKU_4X4_1AND2);
        if (sudokuBoard1and2.calculateNonDuplicatedValuesInLines() != 4) {
            fail ("Se han recuperado " + sudokuBoard1and2.calculateNonDuplicatedValuesInLines() + " valores no duplicados en filas en vez de 4");
        }
    }

    @Test
    public void testCalculateNonDuplicatedValuesInColumns() throws BuilderException {
        final SudokuBoard sudokuBoard = new SudokuBoard(2, SUDOKU_4X4);
        if (sudokuBoard.calculateNonDuplicatedValuesInColumns() != 12) {
            fail ("Se han recuperado " + sudokuBoard.calculateNonDuplicatedValuesInLines() + " valores no duplicados en columnas en vez de 12");
        }

        final SudokuBoard sudokuBoard1and2 = new SudokuBoard(2, SUDOKU_4X4_1AND2);
        if (sudokuBoard1and2.calculateNonDuplicatedValuesInColumns() != 8) {
            fail ("Se han recuperado " + sudokuBoard1and2.calculateNonDuplicatedValuesInLines() + " valores no duplicados en columnas en vez de 8");
        }
    }

    @Test
    public void canSolveSudokuTemplate() throws BuilderException {
        final SudokuBoard sudokuBoardTemplate = new SudokuBoard(2, TEMPLATE_SUDOKU_4X4);
        final SudokuBoard sudokuBoardFail = new SudokuBoard(2, SUDOKU_4X4_1AND2);
        final SudokuBoard sudokuBoardCorrect = new SudokuBoard(2, SUDOKU_4X4);

        if (!sudokuBoardCorrect.canSolveSudokuTemplate(sudokuBoardTemplate)) {
            fail ("Este sudoku deberia respetar las restricciones de la plantilla");
        }

        if (sudokuBoardFail.canSolveSudokuTemplate(sudokuBoardTemplate)) {
            fail ("Este sudoku no deberia respetar las restricciones de la plantilla");
        }
    }

    @Test
    public void testCalculateNonDuplicatedValuesInSquares() throws BuilderException {
        final SudokuBoard sudokuBoard = new SudokuBoard(2, SUDOKU_4X4);
        if (sudokuBoard.calculateNonDuplicatedValuesInSquares() != 8) {
            fail ("Se han recuperado " + sudokuBoard.calculateNonDuplicatedValuesInSquares() + " valores no duplicados en los cuadrados en vez de 8");
        }
    }
}