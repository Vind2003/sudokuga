package com.raulexposito.sudokuga.fitness;

import com.raulexposito.sudokuga.exception.BuilderException;
import com.raulexposito.sudokuga.fitness.individual.SudokuBoard;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class NumberSituationFitnessFunction extends FitnessFunction {

    private static final Log log = LogFactory.getLog(NumberSituationFitnessFunction.class);
    private static final double INVALID = 0;
    private SudokuBoard sudokuProblem;
    private int dimension;

    public NumberSituationFitnessFunction(int dimension, final int[] sudokuProblem) throws BuilderException {
        this.dimension = dimension;
        this.sudokuProblem = new SudokuBoard(dimension, sudokuProblem);
    }

    @Override
    protected double evaluate(IChromosome cromosoma) {
        SudokuBoard individual;
        double fitness = INVALID;

        try {
            individual = createSudokuFromChromosome(dimension, cromosoma);
        } catch (BuilderException be) {
            log.error("The sudoku cannot be build", be);
            return INVALID;
        }

        if (individual.canSolveSudokuTemplate(sudokuProblem)) {
//            fitness += individual.calculateNonDuplicatedValuesInSquares();
            fitness += individual.calculateNonDuplicatedValuesInLines();
            fitness += individual.calculateNonDuplicatedValuesInColumns();
        }
        return fitness;
    }

    public static SudokuBoard createSudokuFromChromosome(int dimension, final IChromosome cromosoma) throws BuilderException {

        int amountOfNumbersInBoard = SudokuBoard.amountOfNumbersInBoard(dimension);
        int[] genes = new int[amountOfNumbersInBoard];

        for (int i = 0; i < amountOfNumbersInBoard; i++) {
            genes[i] = ((Integer) cromosoma.getGene(i).getAllele()).intValue();
        }

        return new SudokuBoard(dimension, genes);
    }
}
