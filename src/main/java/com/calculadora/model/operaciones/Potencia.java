package com.calculadora.model.operaciones;

public class Potencia implements Operacion {

    @Override
    public double calcular(double a, double b) {

        return Math.pow(a, b);

    }
}