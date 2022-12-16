package Distributed.Matrix;

import java.io.Serializable;

/**
 * Serialized object class to send and receive the row and matrix object for multiplication
 * to and from client and server
 * */
public class SerializedObject_matrix implements Serializable {
    int [] row;
    int [][] matrix;
    int row_number;
    int [] results;
    public SerializedObject_matrix(int [] row, int [][]matrix, int row_number){
        this.row = row;
        this.matrix = matrix;
        this.row_number = row_number;
    }

    public SerializedObject_matrix(int [] results, int row_number){
        this.results = results;
        this.row_number = row_number;

    }
}
