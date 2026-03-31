package com.calculadora.model.operaciones;

public class Resta implements Operacion {

    @Override
    public double calcular(double a, double b) {

        return a - b;

    }

}
