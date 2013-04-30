package com.raulexposito.sudokuga.fitness.individual;

import java.util.HashSet;
import java.util.Set;

/**
 * Representa un cuadrado de un sudoku. El un cuadrado no puede haber elementos
 * repetidos y su union forma un tablero
 */
public class Square {

    private int[][] values;
    private int dimension;

    /**
     * Costruye un cuadrado de unas dimensiones y unos valores recibidos por
     * parametro
     * @param dimension numero de elementos por cada fila y columna
     * @param values valores a insertar en el cuadrado
     */
    public Square (int dimension, int[] values) {        
        this.dimension = dimension;
        this.values = new int[dimension][dimension];

        for (int i = 0; i < dimension; i ++) {
            for (int j = 0; j < dimension; j ++) {
                this.values[i][j] = values[i * dimension + j];
            }
        }
    }

    /**
     * Devuelve el numero de elementos unicos que hay en el cuadrado
     * @return el numero de elementos unicos que hay en el cuadrado
     */
    public int calculateNonDuplicatedValues () {
        final Set<Integer> uniqueValues = new HashSet<Integer>();

       for (int i = 0; i < dimension; i ++) {
            for (int j = 0; j < dimension; j ++) {
                uniqueValues.add(Integer.valueOf(values[i][j]));
            }
        }
        return uniqueValues.size();
    }
}
