package listgengui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

import java.io.File;

public class Controller {
    @FXML private Label importLabel;
    @FXML private VBox rootControl;
    @FXML private TextField outputFileTextField;
    @FXML private Button executeButton;
    @FXML private Spinner<Integer> sheetNumber;
    @FXML private Spinner<Integer> rowIndex;
    @FXML private Spinner<Integer> columnIndex;

    public Controller() {}

    @FXML
    private void initialize() {}

    @FXML
    private void dragOverFile(DragEvent event) {
        if (event.getGestureSource() != rootControl
                && event.getDragboard().hasFiles()) {
            /* allow for both copying and moving, whatever user chooses */
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @FXML
    private void pasteFileUrl(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            File file = db.getFiles().get(0);
            importLabel.setText(file.toString());
            outputFileTextField.setText(file.getName());
            success = true;
        }
        /* let the source know whether the string was successfully
         * transferred and used */
        event.setDropCompleted(success);

        event.consume();
    }

    @FXML
    private void executeScript() {
        listgen.Main.execute(
                importLabel.getText(),
                outputFileTextField.getText(),
                sheetNumber.getValue() - 1,
                rowIndex.getValue() - 1,
                columnIndex.getValue() - 1
        );
    }
}
