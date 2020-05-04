package sample;


import java.io.File;
import java.net.URL;
import java.util.Map;

import javafx.fxml.FXML;

import java.util.HashMap;
import java.io.FileReader;
import java.nio.file.Path;

import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

import javafx.scene.control.*;

import java.io.BufferedReader;
import java.util.ResourceBundle;

import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class FXMLDocumentController implements Initializable {

    String t;
    Stage stage;

    @FXML
    TableView<MyDataType> table = new TableView<>();
    @FXML
    private TableColumn<MyDataType, String> wordss;
    @FXML
    private TableColumn<MyDataType, Integer> frequent;
    @FXML
    private TextArea filename;

    @FXML
    ToggleGroup tg = new ToggleGroup();
    @FXML
    RadioButton txt, csv, epub, md, pdf, doc;

    ObservableList<MyDataType> list = FXCollections.observableArrayList();
    ObjectProperty<MyDataType> criticalPerson = new SimpleObjectProperty<>();

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
    int total = 0;
    int remain = 0;

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
        else if (t.contains("pdf"))
            fc.getExtensionFilters().addAll(new ExtensionFilter("PDF file", "*.pdf"));
        else if (t.contains("csv"))
            fc.getExtensionFilters().addAll(new ExtensionFilter("CSV file", "*.csv"));
        else if (t.contains("docx"))
            fc.getExtensionFilters().addAll(new ExtensionFilter("Word file", "*.docx"));
        else if (t.contains("md"))
            fc.getExtensionFilters().addAll(new ExtensionFilter("ReadMe file", "*.md"));
        else if (t.contains("epub"))
            fc.getExtensionFilters().addAll(new ExtensionFilter("Epub file", "*.epub"));


        File file = fc.showOpenDialog(null);

        if (file == null) {
            System.out.println("File is invalid");
        } else {
            filename.setText("FileName : " + file.getName() + "\n\nLocation of File :" + file.getAbsolutePath());
            path = file.getAbsolutePath();
        }
    }

    @FXML
    public void Button2Action(ActionEvent event) throws IOException {
        Path pathing = Paths.get(path);
        System.out.println(path);

        Map<String, Integer> wordfound = new HashMap<>();
        total = 0;

        if (t.contains("txt") || t.contains("csb") || t.contains("md")) {
            System.out.println("In first if");
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
                                total++;
                            }
                        }
                    }
                }
                line = reader.readLine();
            }
        } else if (t.contains("pdf")) {
            PDDocument pdf = PDDocument.load(new File(path));
            if(pdf.getNumberOfPages()==104){
                System.out.println("Success");
            }
            if(!pdf.isEncrypted()){
                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(pdf);
                System.out.println(text);
            }
        }
        list.clear();
        wordfound.forEach((key, value) -> {
            MyDataType ob = new MyDataType(key, value);
            list.add(ob);
        });

        list.add(new MyDataType("Total  Unique Words ", total));
        criticalPerson.set(table.getItems().get(total + remain));
    }

    @FXML
    public void CloseWindowOnAction(ActionEvent event) {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Making Only one radio button selectable
        txt.setToggleGroup(tg);
        csv.setToggleGroup(tg);
        pdf.setToggleGroup(tg);
        epub.setToggleGroup(tg);
        md.setToggleGroup(tg);
        doc.setToggleGroup(tg);
        txt.setSelected(true);

        wordss.setCellValueFactory(new PropertyValueFactory<>("stringValue"));
        table.setRowFactory(tv -> {
            TableRow<MyDataType> row = new TableRow<>();
            BooleanBinding critical = row.itemProperty().isEqualTo(criticalPerson).and(row.itemProperty().isNotNull());
            row.styleProperty().bind(Bindings.when(critical)
                    .then("-fx-background-color: yellow ;")
                    .otherwise(""));
            return row;
        });
        frequent.setCellValueFactory(new PropertyValueFactory<>("intValue"));
        table.setItems(list);
    }
}
