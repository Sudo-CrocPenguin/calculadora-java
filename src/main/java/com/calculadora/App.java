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

    @Override
    public void start(Stage stage) {

        TextField num1 = new TextField();
        num1.setPromptText("Número 1");

        TextField num2 = new TextField();
        num2.setPromptText("Número 2");

        TextField resultado = new TextField();
        resultado.setPromptText("Resultado");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(num1, 0, 0);
        grid.add(num2, 1, 0);
        grid.add(resultado, 0, 1, 2, 1);

        String[] ops = { "+", "-", "*", "/", "^", "√" };

        int col = 0;
        for (String op : ops) {
            Button btn = new Button(op);

            btn.setOnAction(e -> {
                String res = controller.calcular(op, num1.getText(), num2.getText());
                resultado.setText(res);
            });

            grid.add(btn, col++, 2);
        }

        Scene scene = new Scene(grid, 300, 200);

        stage.setTitle("Calculadora");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}