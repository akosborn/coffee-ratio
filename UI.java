import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class UI extends Application
{
    TextField coffeeField, waterField;
    Calculations calculate;
    WaterTextFieldHandler waterHandler = new WaterTextFieldHandler();
    CoffeeTextFieldHandler coffeeHandler = new CoffeeTextFieldHandler();

    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Sir Coffee");
        coffeeField = new TextField();
        coffeeField.setPrefColumnCount(10);
        coffeeField.textProperty().addListener(coffeeHandler);

        ComboBox<String> ratioOptions = new ComboBox<String>();
        ratioOptions.getItems().addAll(
                "1:8 (cold brew)",
                "1:12",
                "1:16"
        );

        ratioOptions.getSelectionModel().selectedItemProperty().addListener(new ComboBoxEventHandler());

        waterField = new TextField();
        waterField.setPrefColumnCount(10);
        waterField.textProperty().addListener(waterHandler);

        HBox hbox = new HBox(5);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(coffeeField, ratioOptions, waterField);
        calculate = new Calculations();

        primaryStage.setScene(new Scene(hbox, 500, 300));
        primaryStage.show();
    }

    public class CoffeeTextFieldHandler implements ChangeListener<String>
    {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
        {
            // System.out.println("CoffeeTextFieldHandler fired.");

            String entry = coffeeField.getText();
            if(entry.length() >= 1)
            {
                try {
                    Double entryAsDouble = Double.parseDouble(entry);
                    waterField.textProperty().removeListener(waterHandler);
                    waterField.setText(calculate.coffeeToWater(entryAsDouble));
                    waterField.textProperty().addListener(waterHandler);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }else
            {
                // System.out.println("coffeeField lenth: 0");
                waterField.textProperty().removeListener(waterHandler);
                waterField.setText("");
                waterField.textProperty().addListener(waterHandler);
            }
        }
    }

    public class ComboBoxEventHandler implements ChangeListener<String>
    {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
        {
            // System.out.println("ComboBoxEventHandler fired.");

            if(observable.getValue().equals("1:8 (cold brew)"))
            {
                calculate.setSpecifiedRatio(8);
            }else if(observable.getValue().equals("1:12"))
            {
                calculate.setSpecifiedRatio(12);
            }else if(observable.getValue().equals("1:16"))
            {
                calculate.setSpecifiedRatio(16);
            }

            String value = coffeeField.getText();
            coffeeField.setText("");
            coffeeField.setText(value);
        }
    }

    public class WaterTextFieldHandler implements ChangeListener<String>
    {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
        {
            // System.out.println("WatertextFieldHandler fired.");

            String entry = waterField.getText();
            if (entry.length() >= 1)
            {
                try {
                    Double entryAsDouble = Double.parseDouble(entry);
                    // System.out.println(entryAsDouble);
                    coffeeField.textProperty().removeListener(coffeeHandler);
                    coffeeField.setText(calculate.waterToCoffee(entryAsDouble));
                    coffeeField.textProperty().addListener(coffeeHandler);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }else
            {
                // System.out.println("waterField length: 0");
                coffeeField.textProperty().removeListener(coffeeHandler);
                coffeeField.setText("");
                coffeeField.textProperty().addListener(coffeeHandler);
            }

        }
    }
}
