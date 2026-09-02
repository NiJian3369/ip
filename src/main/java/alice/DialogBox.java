package alice;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * A single speech bubble in the conversation: a display picture next to a
 * label of text. User messages are right-aligned with the picture on the
 * right; {@link #flip()} mirrors the layout for Alice's replies so they
 * read as coming from the left.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DialogBox.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            // The FXML file is bundled as a resource, so this should only
            // happen if it is accidentally deleted or renamed.
            throw new IllegalStateException("Could not load DialogBox.fxml", e);
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        // Clip the square display picture into a circle, so it reads as an avatar.
        double radius = displayPicture.getFitWidth() / 2;
        displayPicture.setClip(new Circle(radius, radius, radius));
    }

    /**
     * Creates a dialog box for a message the user typed.
     *
     * @param text the user's message.
     * @param img the user's display picture.
     * @return a dialog box styled for the user (aligned to the right).
     */
    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox dialogBox = new DialogBox(text, img);
        dialogBox.dialog.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white;"
                + " -fx-background-radius: 12; -fx-padding: 8 12 8 12;");
        return dialogBox;
    }

    /**
     * Creates a dialog box for one of Alice's replies.
     *
     * @param text Alice's reply.
     * @param img Alice's display picture.
     * @return a dialog box styled for Alice (aligned to the left).
     */
    public static DialogBox getAliceDialog(String text, Image img) {
        DialogBox dialogBox = new DialogBox(text, img);
        dialogBox.dialog.setStyle("-fx-background-color: #e6e6e6; -fx-text-fill: black;"
                + " -fx-background-radius: 12; -fx-padding: 8 12 8 12;");
        dialogBox.flip();
        return dialogBox;
    }

    /**
     * Flips this dialog box so the display picture is on the left and the
     * text is on the right, instead of the default (user) layout.
     */
    private void flip() {
        ObservableList<Node> children = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(children);
        this.getChildren().setAll(children);
        this.setAlignment(Pos.TOP_LEFT);
    }
}
