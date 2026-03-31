package com.calculadora.model.operaciones;

public class Division implements Operacion {

    @Override
    public double calcular(double a, double b) {

        if (b == 0) {

            throw new ArithmeticException("No dividir por 0");

        }

        return a / b;

    }
}
