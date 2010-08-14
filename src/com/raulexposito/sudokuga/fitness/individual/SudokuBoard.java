package com.raulexposito.sudokuga.fitness.individual;

import com.raulexposito.sudokuga.exception.BuilderException;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SudokuBoard {

     private static final Log log = LogFactory.getLog( SudokuBoard.class );

    // constantes
    public static final String CELL_SEPARATOR = ";";
    public static final String SEPARATOR = "  ";
    public static final String NEW_LINE = "\n\n";
    public static final int NULL_VALUE = 0;

    // variables relativas al tablero
    private Square[][] square;
    private int[][] values;
    private int dimension;

    // valores calculados
    private int dimensionCuadrado;
    private int dimensionCubo;
    private int dimensionHipercubo;

    // --------------------------------------------------------
    //
    // CONSTRUCTORES
    //
    // --------------------------------------------------------

    public SudokuBoard (int dimension, final String cellValues) throws BuilderException {
        createSudokuBoard(dimension, this.createCellValues(cellValues));
    }

    public SudokuBoard (int dimension, int[] cellValues) throws BuilderException {
        createSudokuBoard(dimension, cellValues);
    }

    // --------------------------------------------------------
    //
    // HERRAMIENTAS AUXILIARES
    //
    // --------------------------------------------------------

    private void createSudokuBoard (int dimension, int[] cellValues) throws BuilderException {

        this.dimension = dimension;
        this.dimensionCuadrado = (int) Math.pow(dimension, 2);
        this.dimensionCubo = (int) Math.pow(dimension, 3);
        this.dimensionHipercubo = amountOfNumbersInBoard (dimension);

        validateCellValues(cellValues);

        square = new Square[dimension][dimension];
        
        int[][] recoveredSquare = new int[dimensionCuadrado][dimensionCuadrado];
        int squareNumber = 0;

        // PASO 1:
        // Se parte el tablero en tantas filas como dimensiones haya, es decir,
        // si el tablero es de 2 dimensiones se partirá en 2 filas, si es de 3
        // dimensiones en 3 filas, etc. Estos puntos indican donde empieza y
        // donde termina cada fila
        // ---------------------------------------------------------------------

        int inicioCorte = 0;
        int finCorte = dimensionCubo;

        while (inicioCorte < cellValues.length) {

            int[] fila = recoverFila (cellValues, inicioCorte, finCorte);

            // PASO 2:
            // Hay que sacar los cuadrados de cada una de las filas. Esto tiene
            // una dificultad, y es que en un sudoku de 2x2 no hay cuadrados
            // enmedio, pero en uno de 3x3 hay un cuadrado en medio y en uno
            // de 4x4 hay dos.
            // Esto quiere decir que hay que hacer "saltos" para pasar a la
            // siguiente fila del cuadrado dentro de la fila del tablero. De
            // este modo, en un sudoku de 2x2 hay que saltar 2 valores (2*1),
            // en uno de 3x3 6 valores (3*2), en uno de 4*4 12 (4*3), etc
            // -----------------------------------------------------------------

            int inicio = 0;
            int posicionEnCuadrado = 0;

            // se calcula la longitud de los saltos
            int longitudSalto = dimensionCuadrado;

            // se recuperan los cuadrados de la fila del sudoku
            for (int numeroCuadrados = 0; numeroCuadrados < dimension; numeroCuadrados ++) {

                posicionEnCuadrado = 0;

                // se copian los valores que se pueda en cada salto
                for (int saltos = 0; saltos < dimension; saltos++) {
                    inicio = numeroCuadrados * dimension + saltos * longitudSalto;

                    // se inserta una fila en el cuadrado
                    for (int i = 0; i < dimension; i++) {
                        recoveredSquare[squareNumber][posicionEnCuadrado] = fila[inicio + i];
                        posicionEnCuadrado++;
                    }
                }
                
                // se incrementa el numero de cuadrados recuperados
                squareNumber ++;
            }

            // PASO 3:
            // Se actualizan los valores para poder hacer otro corte y poder
            // recuperar mas cuadrados
            // -----------------------------------------------------------------

            inicioCorte += dimensionCubo;
            finCorte += dimensionCubo;
        }

        // PASO 4:
        // Se generan los cuadrados a partir de los valores sacados del sudoku
        // ---------------------------------------------------------------------

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                square[i][j] = new Square(dimension, recoveredSquare[i * dimension + j]);
            }
        }

        // PASO 5:
        // Se almacenan los datos del sudoku
        // ---------------------------------------------------------------------

        values = new int[dimensionCuadrado][dimensionCuadrado];
        for (int i = 0; i < (int) Math.pow (dimension, 2); i++) {
            for (int j = 0; j < (int) Math.pow (dimension, 2); j++) {
                values[i][j] = cellValues[i * dimensionCuadrado + j];
            }
        }

        log.debug ("created a new sudoku: \n\n" + this);
    }

    /**
     * Recupera una fila de cuadrados de un sudoku
     * @param cellValues el contenido del sudoku
     * @param inicio punto de inicio de la fila de cuadrados
     * @param fin punto de fin de la fila de cuadrados
     * @return una fila de cuadrados de un sudoku
     */
    private int[] recoverFila (int[] cellValues, int inicio, int fin) {
        int[] result = new int [fin - inicio];
        int position = 0;
        for (int i = inicio; i < fin; i++) {
            result[position] = cellValues[i];
            position++;
        }
        return result;
    }

    // --------------------------------------------------------
    //
    // VALIDADORES
    //
    // --------------------------------------------------------

    private int[] createCellValues (final String cellValues) throws BuilderException {
        final String[] stringValues = cellValues.split(CELL_SEPARATOR);
        int[] intValues = new int[stringValues.length];

        // se realiza la conversion de tipos a la vez que se comprueba que el sudoku no contenga caracteres que no le corresponden
        for (int i = 0; i < stringValues.length; i++) {
            try {
                intValues[i] = Integer.parseInt(stringValues[i]);
            } catch (NumberFormatException nfe) {
                final String message = "En el sudoku hay caracteres que no son numeros";
                log.warn (message, nfe);
                throw new BuilderException(message, nfe);
            }
        }

        return intValues;
    }

    private void validateCellValues (int[] cellValues) throws BuilderException {

        // se valida que el numero de celdas sea el correcto
        if (cellValues.length != dimensionHipercubo) {
            final String message = "En el sudoku hay " + cellValues.length + " celdas cuando deberia haber " + dimensionHipercubo;
            log.warn (message);
            throw new BuilderException(message);
        }

        // se valida que no haya numeros que no correspondan al tablero
        for (int i = 0; i < cellValues.length; i++) {
            if (cellValues[i] > dimensionCuadrado) {
                final String message = "El numero mas alto que se puede poner en un sudoku de dimension " + dimension + " es " + dimensionCuadrado +
                        ", pero se ha encontrado el numero " + cellValues[i];
                log.warn (message);
                throw new BuilderException(message);
            }

            if (cellValues[i] < NULL_VALUE) {
                final String message = "El numero mas bajo que se puede poner en un sudoku es " + NULL_VALUE +
                        ", pero se ha encontrado el numero " + cellValues[i];
                log.warn (message);
                throw new BuilderException(message);
            }
        }
    }

    // --------------------------------------------------------
    //
    // RECUPERADORES DE INFORMACION
    //
    // --------------------------------------------------------

    /**
     * Devuelve los elementos de una fila concreta
     * @param fila numero de la fila comenzando por la izquierda
     * @return elementos de la fila seleccionada
     */
    public int[] getLine (int fila) {
        return values[fila];
    }

    /**
     * Devuelve los elementos de una columna concreta
     * @param columna numero de la columna comenzando por arriba
     * @return elementos de la columna seleccionada
     */
    public int[] getColumn (int columna) {
        int[] columnValues = new int[dimensionCuadrado];
        for (int i = 0; i < dimensionCuadrado; i++) {
            columnValues [i] = values[i][columna];
        }
        return columnValues;
    }

    /**
     * Devuelve el numero de valores no repetidos en cada uno de los cuadrados
     * del sudoku
     * @return numero de valores no repetidos en cada uno de los cuadrados del sudoku
     */
    public int calculateNonDuplicatedValuesInSquares () {
        int nonDuplicadedValues = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                nonDuplicadedValues += square[i][j].calculateNonDuplicatedValues();
            }
        }
        log.debug ("the sudoku has " + nonDuplicadedValues + " (of " + dimensionHipercubo + ") non duplicated values in the squares");
        return nonDuplicadedValues;
    }

    /**
     * Devuelve el numero de elementos unicos que hay en el sudoku
     * @return el numero de elementos unicos que hay en el sudoku
     */
    public int calculateNonDuplicatedValuesInLines () {
        int nonDuplicadedValues = 0;
        final Set<Integer> uniqueValues = new HashSet<Integer>();
        for (int lineNumber = 0; lineNumber < dimensionCuadrado; lineNumber++) {
            uniqueValues.clear();
            int[] line = this.getLine(lineNumber);

            for (int i = 0; i < dimensionCuadrado; i ++) {
                uniqueValues.add(Integer.valueOf(line[i]));
            }
            nonDuplicadedValues += uniqueValues.size();
        }
        log.debug ("the sudoku has " + nonDuplicadedValues + " (of " + dimensionHipercubo + ") non duplicated values in the lines");
        return nonDuplicadedValues;
    }

    /**
     * Devuelve el numero de elementos unicos que hay en el sudoku
     * @return el numero de elementos unicos que hay en el sudoku
     */
    public int calculateNonDuplicatedValuesInColumns () {
        int nonDuplicadedValues = 0;
        final Set<Integer> uniqueValues = new HashSet<Integer>();
        for (int columnNumber = 0; columnNumber < dimensionCuadrado; columnNumber++) {
            uniqueValues.clear();
            int[] column = this.getColumn(columnNumber);

            for (int i = 0; i < dimensionCuadrado; i ++) {
                uniqueValues.add(Integer.valueOf(column[i]));
            }
            nonDuplicadedValues += uniqueValues.size();
        }
        log.debug ("the sudoku has " + nonDuplicadedValues + " (of " + dimensionHipercubo + ") non duplicated values in the columns");
        return nonDuplicadedValues;
    }

    /**
     * Indica si este sudoku puede resolver un sudoku que no está completo a
     * modo de plantilla.
     * Lo que asegura es que si en la posicion [1,2] de la plantilla hay un 3
     * y nuestro sudoku ha mutado y ha puesto en esa misma posición un 5, el
     * sudoku se marque como invalido ya que no esta resolviendo la plantilla
     * que debe resolver
     * @param template plantilla de sudoku que debe ser resuelta
     * @return si nuestro sudoku respeta las restricciones de esa plantilla
     */
    public boolean canSolveSudokuTemplate (final SudokuBoard template) {
        boolean canSolve = true;
        int [][] templateValues = template.values;        
        for (int i = 0; i < dimensionCuadrado; i++) {
            for (int j = 0; j < dimensionCuadrado; j++) {

                if (templateValues[i][j] != NULL_VALUE) {
                    if (templateValues[i][j] != values[i][j]) {
                        canSolve = false;
                        log.warn ("the sudoku don't match with the template!");
                    }
                }
            }
        }
        return canSolve;
    }

    /**
     * Devuelve el numero de casillas que hay en un sudoku de las dimensiones
     * indicadas
     * @param dimensiones del sudoku
     * @return numero de casillas que hay en el sudoku
     */
    public static int amountOfNumbersInBoard (int dimension) {
        return (int) Math.pow (dimension, 4);
    }

    /**
     * Permite la visualizacion del sudoku
     * @return
     */
    @Override
    public String toString () {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dimension * dimension; i ++) {
            for (int j = 0; j < dimension * dimension; j ++) {
                sb.append(values[i][j] + SEPARATOR);
            }
            sb.append(NEW_LINE);
        }
        return sb.toString();
    }
}
