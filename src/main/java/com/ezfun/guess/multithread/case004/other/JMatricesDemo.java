package com.ezfun.guess.multithread.case004.other;

import org.apache.mahout.math.Matrix;

import java.util.concurrent.RecursiveTask;

/**
 * @author SoySauce
 * @date 2019/10/25
 */
public class JMatricesDemo extends RecursiveTask<Matrix> {
    Matrix m1;
    Matrix m2;
    String pos;

    public JMatricesDemo(Matrix m1, Matrix m2, String pos) {
        this.m1 = m1;
        this.m2 = m2;
        this.pos = pos;
    }

    @Override
    protected Matrix compute() {
//        if (m1.rows())
        return null;
    }
}
