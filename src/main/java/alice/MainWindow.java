package alice;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI window (see {@code view/MainWindow.fxml}).
 *
 * <p>Wires the text field and send button to {@link Alice#getResponse}, and
 * renders the conversation as a scrolling column of {@link DialogBox}es.
 */
public class MainWindow {
    private static final Image USER_IMAGE = new Image(MainWindow.class.getResourceAsStream("/images/DaUser.png"));
    private static final Image ALICE_IMAGE = new Image(MainWindow.class.getResourceAsStream("/images/DaAlice.png"));

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Alice alice;

    /**
     * Keeps the scroll pane pinned to the bottom as new dialog boxes are
     * added, so the latest message is always visible. Called automatically
     * by the FXML loader after the annotated fields above are injected.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Supplies the chatbot instance this window talks to, and shows its
     * greeting as the first message in the conversation.
     *
     * @param alice the chatbot instance backing this GUI.
     */
    public void setAlice(Alice alice) {
        this.alice = alice;
        dialogContainer.getChildren().add(DialogBox.getAliceDialog(alice.getGreeting(), ALICE_IMAGE));
    }

    /**
     * Handles the user submitting a command, either by pressing Enter in
     * the text field or clicking the send button. Appends both the user's
     * input and Alice's reply as dialog boxes, then closes the window
     * shortly after a "bye" command so the user can read the goodbye
     * message first.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.isBlank()) {
            return;
        }

        String response = alice.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, USER_IMAGE),
                DialogBox.getAliceDialog(response, ALICE_IMAGE)
        );
        userInput.clear();

        if (alice.isExit(input)) {
            userInput.setDisable(true);
            sendButton.setDisable(true);
            PauseTransition closeDelay = new PauseTransition(Duration.seconds(1.5));
            closeDelay.setOnFinished(event -> Platform.exit());
            closeDelay.play();
        }
    }
}
