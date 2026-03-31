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
    private String expresion = "";

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
                    expresion += texto;
                    pantalla.setText(expresion);

                } else if (texto.equals("C")) {
                    expresion = "";
                    pantalla.clear();

                } else if (texto.equals("=")) {
                    try {
                        String res = evaluarExpresion(expresion);
                        pantalla.setText(res);
                        expresion = res;
                    } catch (Exception ex) {
                        pantalla.setText("Error");
                        expresion = "";
                    }

                } else if (texto.equals("√")) {
                    try {
                        String res = controller.calcular("√", expresion, "");
                        pantalla.setText(res);
                        expresion = res;
                    } catch (Exception ex) {
                        pantalla.setText("Error");
                        expresion = "";
                    }

                } else {
                    expresion += texto;
                    pantalla.setText(expresion);
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

        // CSS
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

        // TECLADO
        scene.setOnKeyPressed(event -> {

            String tecla = event.getText();

            if (tecla.matches("[0-9.]")) {
                expresion += tecla;
                pantalla.setText(expresion);

            } else {
                switch (event.getCode()) {

                    case ADD:
                    case PLUS:
                        expresion += "+";
                        break;

                    case SUBTRACT:
                    case MINUS:
                        expresion += "-";
                        break;

                    case MULTIPLY:
                        expresion += "*";
                        break;

                    case DIVIDE:
                        expresion += "/";
                        break;

                    case ENTER:
                        String res = evaluarExpresion(expresion);
                        pantalla.setText(res);
                        expresion = res;
                        return;

                    case BACK_SPACE:
                        if (!expresion.isEmpty()) {
                            expresion = expresion.substring(0, expresion.length() - 1);
                        }
                        break;

                    case ESCAPE:
                        expresion = "";
                        break;

                    default:
                        return;
                }

                pantalla.setText(expresion);
            }
        });

        stage.setTitle("Calculadora techrunner");
        stage.setScene(scene);
        stage.show();

        // IMPORTANTE para teclado
        pantalla.requestFocus();
    }

    // MÉTODO PARA CALCULAR
    private String evaluarExpresion(String exp) {

        try {

            if (exp.contains("+")) {

                String[] partes = exp.split("\\+");
                return controller.calcular("+", partes[0], partes[1]);

            } else if (exp.contains("-")) {

                String[] partes = exp.split("-");
                return controller.calcular("-", partes[0], partes[1]);

            } else if (exp.contains("*")) {

                String[] partes = exp.split("\\*");
                return controller.calcular("*", partes[0], partes[1]);

            } else if (exp.contains("/")) {

                String[] partes = exp.split("/");
                return controller.calcular("/", partes[0], partes[1]);

            } else if (exp.contains("^")) {

                String[] partes = exp.split("\\^");
                return controller.calcular("^", partes[0], partes[1]);

            }

        } catch (Exception e) {
            return "Error";
        }

        return "Error";
    }

    public static void main(String[] args) {
        launch();
    }
}