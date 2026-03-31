package com.calculadora.model.operaciones;

public class Raiz implements Operacion {

    @Override
    public double calcular(double a, double b) {

        return Math.sqrt(a);

    }
}