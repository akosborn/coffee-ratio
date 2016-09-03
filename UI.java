import javafx.animation.AnimationTimer;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class UI extends Application
{
    TextField coffeeField, waterField;
    Calculations calculate;
    WaterTextFieldHandler waterHandler = new WaterTextFieldHandler();
    CoffeeTextFieldHandler coffeeHandler = new CoffeeTextFieldHandler();
    Label timerLabel;
    Timeline timeline;
    private static final Integer STARTTIME = 270;
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);

    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Sir Coffee");

        Label coffeeLabel = new Label("Coffee (g)");
        coffeeField = new TextField();
        coffeeField.setPrefColumnCount(10);
        coffeeField.textProperty().addListener(coffeeHandler);
        VBox coffeeBox = new VBox();
        coffeeBox.setAlignment(Pos.TOP_CENTER);
        coffeeBox.getChildren().addAll(coffeeLabel,coffeeField);

        Label ratioOptionsLabel = new Label("Ratio");
        ComboBox<String> ratioOptions = new ComboBox<String>();
        ratioOptions.getItems().addAll(
                "1:8 (cold brew)",
                "1:12",
                "1:16"
        );
        ratioOptions.getSelectionModel().selectedItemProperty().addListener(new ComboBoxEventHandler());

        timerLabel = new Label("0:00");
        timerLabel.setStyle("-fx-font-size: 2em");
        timerLabel.textProperty().bind(timeSeconds.asString());
        Button startTimer = new Button("Start Brewing");
        startTimer.setOnAction(new TimerLabelHandler());

        VBox ratioBox = new VBox();
        ratioBox.getChildren().addAll(ratioOptionsLabel, ratioOptions, startTimer, timerLabel);
        ratioBox.setAlignment(Pos.TOP_CENTER);

        Label waterLabel = new Label("Water (mL)");
        waterField = new TextField();
        waterField.setPrefColumnCount(10);
        waterField.textProperty().addListener(waterHandler);
        VBox waterBox = new VBox();
        waterBox.setAlignment(Pos.TOP_CENTER);
        waterBox.getChildren().addAll(waterLabel, waterField);

        HBox hbox = new HBox(5);
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().addAll(coffeeBox, ratioBox, waterBox);
        calculate = new Calculations();

        primaryStage.setScene(new Scene(hbox, 500, 300));
        primaryStage.show();
    }

    public class CoffeeTextFieldHandler implements ChangeListener<String>
    {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
        {
            String entry = coffeeField.getText();

            if(entry.length() >= 1)
            {
                try
                {
                    Double entryAsDouble = Double.parseDouble(entry);
                    waterField.textProperty().removeListener(waterHandler);
                    waterField.setText(calculate.coffeeToWater(entryAsDouble));
                    waterField.textProperty().addListener(waterHandler);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }else
            {
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
            if( (observable.getValue()).equals("1:8 (cold brew)") )
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

    // http://asgteach.com/2011/10/javafx-animation-and-binding-simple-countdown-timer-2/
    public class TimerLabelHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent e)
        {
            if (timeline != null)
            {
                timeline.stop();
            }
            timeSeconds.set(STARTTIME);
            timeline = new Timeline();
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(STARTTIME+1),
                            new KeyValue(timeSeconds, 0)));
            timeline.playFromStart();
        }
    }

    public class WaterTextFieldHandler implements ChangeListener<String>
    {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
        {
            String entry = waterField.getText();
            if (entry.length() >= 1)
            {
                try
                {
                    Double entryAsDouble = Double.parseDouble(entry);
                    coffeeField.textProperty().removeListener(coffeeHandler);
                    coffeeField.setText(calculate.waterToCoffee(entryAsDouble));
                    coffeeField.textProperty().addListener(coffeeHandler);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }else
            {
                coffeeField.textProperty().removeListener(coffeeHandler);
                coffeeField.setText("");
                coffeeField.textProperty().addListener(coffeeHandler);
            }
        }
    }
}
