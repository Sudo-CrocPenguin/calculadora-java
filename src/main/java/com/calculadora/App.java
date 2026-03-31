package com.calculadora;

import com.calculadora.controller.CalculadoraController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {

    private CalculadoraController controller = new CalculadoraController();

    private String numero1 = "";
    private String numero2 = "";
    private String operacion = "";

    @Override
    public void start(Stage stage) {

        TextField pantalla = new TextField();
        pantalla.setEditable(false);
        pantalla.setPrefHeight(50);

        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(5);

        grid.add(pantalla, 0, 0, 4, 1);

        String[] botones = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "√", "^", "C"
        };

        int fila = 1;
        int col = 0;

        for (String texto : botones) {

            Button btn = new Button(texto);
            btn.setPrefSize(60, 60);

            btn.setOnAction(e -> {

                if (texto.matches("[0-9.]")) {
                    // números
                    if (operacion.isEmpty()) {
                        numero1 += texto;
                        pantalla.setText(numero1);
                    } else {
                        numero2 += texto;
                        pantalla.setText(numero2);
                    }

                } else if (texto.equals("C")) {
                    numero1 = "";
                    numero2 = "";
                    operacion = "";
                    pantalla.clear();

                } else if (texto.equals("=")) {
                    String res = controller.calcular(operacion, numero1, numero2);
                    pantalla.setText(res);

                    numero1 = res;
                    numero2 = "";
                    operacion = "";

                } else if (texto.equals("√")) {
                    String res = controller.calcular("√", numero1, "");
                    pantalla.setText(res);
                    numero1 = res;

                } else {
                    // operación
                    operacion = texto;
                }
            });

            grid.add(btn, col, fila);
            col++;

            if (col == 4) {
                col = 0;
                fila++;
            }
        }

        Scene scene = new Scene(grid, 260, 350);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

        stage.setTitle("Calculadora Pro");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}