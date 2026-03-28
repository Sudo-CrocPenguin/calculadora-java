package main;

import java.util.Scanner;

import controller.CalculadoraController;
import model.Operacion;
import service.Division;
import service.Multiplicacion;
import service.Potencias;
import service.Raices;
import service.Resta;
import service.Suma;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        CalculadoraController controller = new CalculadoraController();

        Operacion op;

        double numero1, numero2, resultado;
        int selector;

        do {

            System.out.println("Escriba la operacion que desea hacer:");
            System.out.println("1. sumar");
            System.out.println("2. restar");
            System.out.println("3. multiplicar");
            System.out.println("4. dividir");
            System.out.println("5. potencia");
            System.out.println("6. raíz");

            selector = sc.nextInt();

            switch (selector) {
                case 1:

                    System.out.println("escribe el primer numero");
                    numero1 = sc.nextDouble();
                    System.out.println("escribe el segundo numero");
                    numero2 = sc.nextDouble();

                    op = new Suma(numero1, numero2);
                    resultado = controller.calcular(op);

                    System.out.println("Resultado: " + resultado);

                    break;

                case 2:

                    System.out.println("escribe el primer numero");
                    numero1 = sc.nextDouble();
                    System.out.println("escribe el segundo numero");
                    numero2 = sc.nextDouble();

                    op = new Resta(numero1, numero2);
                    resultado = controller.calcular(op);

                    System.out.println("Resultado: " + resultado);

                    break;
                case 3:

                    System.out.println("escribe el primer numero");
                    numero1 = sc.nextDouble();
                    System.out.println("escribe el segundo numero");
                    numero2 = sc.nextDouble();

                    op = new Multiplicacion(numero1, numero2);
                    resultado = controller.calcular(op);

                    System.out.println("Resultado: " + resultado);

                    break;
                case 4:

                    System.out.println("escribe el primer numero");
                    numero1 = sc.nextDouble();
                    System.out.println("escribe el segundo numero");
                    numero2 = sc.nextDouble();

                    op = new Division(numero1, numero2);
                    resultado = controller.calcular(op);

                    System.out.println("Resultado: " + resultado);

                    break;
                case 5:

                    System.out.println("escribe el numero");
                    numero1 = sc.nextDouble();
                    System.out.println("escribe la potencia");
                    numero2 = sc.nextDouble();

                    op = new Potencias(numero1, numero2);
                    resultado = controller.calcular(op);

                    System.out.println("Resultado: " + resultado);

                    break;
                case 6:

                    System.out.println("escribe el numero");
                    numero1 = sc.nextDouble();

                    op = new Raices(numero1);
                    resultado = controller.calcular(op);

                    System.out.println("Resultado: " + resultado);

                    break;

                default:
                    break;
            }

        } while (true);

    }
}
