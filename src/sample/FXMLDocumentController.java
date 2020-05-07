package sample;


import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Map;

import javafx.fxml.FXML;

import java.util.HashMap;
import java.io.FileReader;
import java.nio.file.Path;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

import javafx.scene.shape.*;
import javafx.util.Duration;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.geometry.Insets;

import java.io.BufferedReader;

import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

import java.util.ResourceBundle;

import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.beans.binding.Bindings;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.animation.FillTransition;
import javafx.scene.text.TextAlignment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.animation.ParallelTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.stage.FileChooser.ExtensionFilter;

import java.util.concurrent.atomic.AtomicBoolean;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLDocumentController implements Initializable {

    String t;

    @FXML
    TableView<MyDataType> table = new TableView<>();
    @FXML
    private TableColumn<MyDataType, String> wordss;
    @FXML
    private TableColumn<MyDataType, Integer> frequent;
    @FXML
    private TextArea filename;
    @FXML
    private TextField tf;
    @FXML
    Rectangle rect;
    @FXML
    Circle circ;
    @FXML
    Label tgLabel, cs, ft, ft1, fn, fq, ef, or;
    @FXML
    AnchorPane acPane;
    @FXML
    ToggleGroup tg = new ToggleGroup();
    @FXML
    ToggleGroup tg_f = new ToggleGroup();
    @FXML
    RadioButton txt, csv, md, unique_f, all_f;
    Boolean bg = false;

    ObservableList<MyDataType> list = FXCollections.observableArrayList();
    ObjectProperty<MyDataType> criticalPerson = new SimpleObjectProperty<>();

    public void popUP() {

        // Play default sound
        final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");

        if (runnable != null) {
            runnable.run();
        }

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);

        Pane dialogVbox = new Pane();
        Text text = new Text("Invalid File Input\n" +
                "Please Enter a correct file with correct path!");
        text.setTextAlignment(TextAlignment.CENTER);
        Button button = new Button("Ok");
        Button button1 = new Button("Cancel");
        button.setLayoutX(250);
        button.setLayoutY(110);
        button1.setLayoutX(190);
        button1.setLayoutY(110);
        text.setLayoutX(40);
        text.setLayoutY(60);
        dialogVbox.getChildren().addAll(text, button1, button);

        Scene dialogScene = new Scene(dialogVbox, 300, 150);
        dialog.setTitle("Invalid Path Warning");
        dialog.setScene(dialogScene);
        dialogVbox.setStyle("-fx-background-color:#808079");
        dialog.show();
        button.setOnMouseClicked(mouseEvent -> {
            dialog.close();
        });
        button1.setOnMouseClicked(mouseEvent -> {
            dialog.close();
        });
    }

    public void setCustom(Boolean s, Label... l) {
        for (Label label : l) {
            if (s)
                label.setBackground(new Background(new BackgroundFill(Color.rgb(216, 191, 216), new CornerRadii(10), Insets.EMPTY)));
            else
                label.setBackground(new Background(new BackgroundFill(Color.rgb(249, 231, 159), new CornerRadii(10), Insets.EMPTY)));
        }
    }

    public void setCustomR(Boolean s, RadioButton... r) {
        for (RadioButton radioButton : r) {
            if (s)
                radioButton.setBackground(new Background(new BackgroundFill(Color.rgb(216, 191, 216), new CornerRadii(10), Insets.EMPTY)));
            else
                radioButton.setBackground(new Background(new BackgroundFill(Color.rgb(249, 231, 159), new CornerRadii(10), Insets.EMPTY)));
        }
    }

    public void peekCentre() {
        try {
            if (bg) {
                acPane.setStyle("-fx-background-color:BLACK");
                setCustom(true, cs, ef, ft, ft1, fn, fq, or);
                setCustomR(true, txt, csv, md, unique_f, all_f);
                filename.setStyle("-fx-control-inner-background:#808080");
                table.setStyle("-fx-control-inner-background:#808080");
                tf.setStyle("-fx-control-inner-background:#808080");
            } else {
                acPane.setStyle("-fx-background-color:#808080");
                setCustom(false, cs, ef, ft, ft1, fn, fq, or);
                setCustomR(false, txt, csv, md, unique_f, all_f);
                filename.setStyle("-fx-control-inner-background:BLACK");
                table.setStyle("-fx-control-inner-background:BLACK");
                tf.setStyle("-fx-control-inner-background:BLACK");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tgLabel.setTextFill(Color.BLACK);
        tgLabel.setTextAlignment(TextAlignment.CENTER);
        if (!bg)
            tgLabel.setText("         LIGHT");
        else
            tgLabel.setText("  DARK");
    }

    public static class MyDataType {
        private final int intValue;
        private final String stringValue;

        MyDataType(String stringValue, int intValue) {
            this.intValue = intValue;
            this.stringValue = stringValue;
        }

        public int getIntValue() {
            return intValue;
        }

        public String getStringValue() {
            return stringValue;
        }
    }

    String path = "\0";

    @FXML
    public void goButtonOnAction(ActionEvent event) {
        RadioButton sl = (RadioButton) tg.getSelectedToggle();
        t = sl.getText();
        path = tf.getText();
        if (path.equals("") || !path.contains(":\\")) {
            popUP();
        } else {
            int pos = path.lastIndexOf('\\');
            System.out.println("Not in if");
            filename.setText("\nFileName : " + path.substring(pos + 1, path.length()) + "\nLocation of File : " + path);
        }
    }

    @FXML
    public void Button1Action(ActionEvent event) {
        // Initialize File Chooser
        FileChooser fc = new FileChooser();

        // Get Selected Radio Button Text to find proper file extension
        RadioButton sl = (RadioButton) tg.getSelectedToggle();
        t = sl.getText();

        // Call proper file extension
        if (t.contains("txt"))
            fc.getExtensionFilters().addAll(new ExtensionFilter("Text file", "*.txt"));
        else if (t.contains("csv"))
            fc.getExtensionFilters().addAll(new ExtensionFilter("CSV file", "*.csv"));
        else if (t.contains("md"))
            fc.getExtensionFilters().addAll(new ExtensionFilter("ReadMe file", "*.md"));

        File file = fc.showOpenDialog(null);

        if (file == null) {
            popUP();
        } else {
            filename.setText("\nFileName : " + file.getName() + "\nLocation of File : " + file.getAbsolutePath());
            path = file.getAbsolutePath();
        }
    }

    @FXML
    public void Button2Action(ActionEvent event) throws IOException {

        if (path.equals("") || path.equals("\0")) {
            popUP();

        }
        else{Path pathing = Paths.get(path);
        Map<String, Integer> wordfound = new HashMap<>();

        BufferedReader reader = new BufferedReader(new FileReader(pathing.toFile()));
        String line = reader.readLine();
        while (line != null) {
            if (!line.trim().equals("")) {
                String[] words = line.split("[\\W]+");
                for (String word : words) {
                    if (word != null && !word.trim().equals("")) {
                        String processed = word.toLowerCase();
                        processed = processed.replace(",", " ");

                        if (wordfound.containsKey(processed)) {
                            wordfound.put(processed, wordfound.get(processed) + 1);
                        } else {
                            wordfound.put(processed, 1);
                        }
                    }
                }
            }
            line = reader.readLine();
        }

        list.clear();
        RadioButton p_f = (RadioButton) tg_f.getSelectedToggle();
        var flag = new AtomicBoolean(true);
        if (p_f.getText().toLowerCase().contains("unique"))
            flag.set(false);

        for (Map.Entry<String, Integer> entry : wordfound.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            MyDataType ob = new MyDataType(key, value);
            if (flag.get())
                list.add(ob);
            else if (value == 1)
                list.add(ob);
        }

        if (!flag.get())
            list.add(new MyDataType("Total  Unique Words ", list.size()));
        else
            list.add(new MyDataType("Total  Words ", list.size()));
        criticalPerson.set(table.getItems().get(list.size() - 1));}
    }

    // ios style toggle switch

    private class ToggleSwitch {
        private BooleanProperty sOn = new SimpleBooleanProperty(false);
        private TranslateTransition translateAnimation = new TranslateTransition(Duration.seconds(.25));
        private FillTransition fillAnimation = new FillTransition(Duration.seconds(.25));
        private FillTransition fillAnimation1 = new FillTransition(Duration.seconds(.25));
        private ParallelTransition animation = new ParallelTransition(translateAnimation, fillAnimation, fillAnimation1);

        public BooleanProperty sOnProperty() {
            return sOn;
        }

        public ToggleSwitch() {
            //Rectangle rectangle = new Rectangle(0, 0, 70, 28);
            rect.setArcHeight(50);
            rect.setArcWidth(30);
            rect.setFill(Color.rgb(253, 184, 19));
            rect.setStroke(Color.GRAY);
            tgLabel.setTextFill(Color.BLACK);
            tgLabel.setTextAlignment(TextAlignment.CENTER);
            circ.setFill(Color.web("#17202A"));
            circ.setStroke(Color.GRAY);
            if (!bg) {
                tgLabel.setText("         LIGHT");
            } else {
                tgLabel.setText("  DARK");
            }

            translateAnimation.setNode(circ);
            fillAnimation.setShape(rect);
            fillAnimation1.setShape(circ);

            sOn.addListener((obs, oldState, newState) -> {
                boolean isOn = newState;
                translateAnimation.setToX(isOn ? 70.0 - 28.0 : 0);
                fillAnimation.setFromValue(isOn ? Color.rgb(253, 184, 19) : Color.LIGHTGREEN);
                fillAnimation.setToValue(isOn ? Color.LIGHTGREEN : Color.rgb(253, 184, 19));
                fillAnimation1.setFromValue(isOn ? Color.web("#17202A") : Color.web("#F9EBEA"));
                fillAnimation1.setToValue(isOn ? Color.web("#F9EBEA") : Color.web("#17202A"));
                animation.play();
            });
            circ.setOnMouseClicked(event -> {
                sOn.set(!sOn.get());
                bg = !bg;
                peekCentre();
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Initialize toggleSwitch
        FXMLDocumentController.ToggleSwitch toggleSwitch = new ToggleSwitch();
        peekCentre();

        // Making Only one radio button selectable
        txt.setToggleGroup(tg);
        csv.setToggleGroup(tg);
        md.setToggleGroup(tg);
        txt.setSelected(true);

        // Making only one radio button selectable
        unique_f.setToggleGroup(tg_f);
        all_f.setToggleGroup(tg_f);
        all_f.setSelected(true);

        wordss.setCellValueFactory(new PropertyValueFactory<>("stringValue"));
        table.setRowFactory(tv -> {
            TableRow<MyDataType> row = new TableRow<>();
            BooleanBinding critical = row.itemProperty().isEqualTo(criticalPerson).and(row.itemProperty().isNotNull());
            row.styleProperty().bind(Bindings.when(critical)
                    .then("-fx-background-color: BLUE ;")
                    .otherwise(""));
            return row;
        });
        frequent.setCellValueFactory(new PropertyValueFactory<>("intValue"));
        table.setItems(list);
    }
}
