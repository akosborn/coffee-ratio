import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class UI extends Application
{
    String address;

    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Sir Coffee");
        TextField coffeeField = new TextField();
        coffeeField.setPrefColumnCount(10);
        ComboBox<String> ratioOptions = new ComboBox<String>();
        ratioOptions.getItems().addAll(
                "1:8 (cold brew)",
                "1:12",
                "1:16"
        );

        ratioOptions.getSelectionModel().selectedItemProperty().addListener(new ComboBoxEventHandler());


        TextField waterField = new TextField();
        waterField.setPrefColumnCount(10);

        HBox hbox = new HBox(5);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(coffeeField, ratioOptions, waterField);


        primaryStage.setScene(new Scene(hbox, 500, 300));
        primaryStage.show();
    }

    public class ComboBoxEventHandler implements ChangeListener<String>
    {
        @Override
        public void changed(ObservableValue<? extends String> t, String oldValue, String newValue)
        {
            System.out.print("Ratio set to ");
            if(t.getValue().equals("1:8 (cold brew)"))
            {
                System.out.println(newValue);
            }else if(t.getValue().equals("1:12"))
            {
                System.out.println(newValue);
            }else if(t.getValue().equals("1:16"))
            {
                System.out.println(newValue);
            }
        }
    }
}
