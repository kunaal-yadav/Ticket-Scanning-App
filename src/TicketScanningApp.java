import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.nio.file.Paths;
import javafx.scene.layout.Pane;

/**
 * A JavaFX application for scanning and validating tickets.
 * The application allows users to enter a ticket code, validate it,
 * and play sounds based on the validation result.
 *
 * @author Kunal Yadav
 *
 */
public class TicketScanningApp extends Application {
    private Table table; // Model for handling ticket data
    private TextField ticketInput; // Input field for entering ticket codes
    private Label resultLabel; // Label to display validation results
    private AudioClip validSound; // Sound played when a ticket is valid
    private AudioClip invalidSound; // Sound played when a ticket is invalid

    /**
     * Handles ticket validation when the validate button is clicked or ENTER key is pressed.
     * Checks if the ticket is valid, invalid, or a duplicate, and updates the result label
     * and plays the corresponding sound.
     *
     * @param e The ActionEvent triggered by the button click.
     */
    private void validateTicket(ActionEvent e) {
        String code = ticketInput.getText(); // Get the ticket code from input field
        String status = table.checkTicketStatus(code); // Get the ticket status for detailed response

        if (code.equalsIgnoreCase("reset")) {
            table.resetStatuses(); // Reset both purchase and entry statuses
            resultLabel.setText("All purchase and entry statuses have been reset.");
            ticketInput.clear();
            ticketInput.requestFocus(); // Set focus back to the input field
            return;
        }

        switch (status) {
            case "VALID":
                resultLabel.setStyle("-fx-text-fill: Green; ");
                resultLabel.setText(code + " is valid");
                validSound.play(); // Play valid sound
                break;
            case "NOT_PURCHASED":
                resultLabel.setStyle("-fx-text-fill: red; ");
                resultLabel.setText(code + " has not been purchased yet.");
                invalidSound.play(); // Play invalid sound
                break;
            case "DUPLICATE":
                resultLabel.setStyle("-fx-text-fill: red; ");
                resultLabel.setText(code + " has already been used.");
                invalidSound.play(); // Play invalid sound
                break;
            case "INVALID":
            default:
                resultLabel.setStyle("-fx-text-fill: red; ");
                resultLabel.setText(code + " is invalid.");
                invalidSound.play(); // Play invalid sound
                break;
        }
        ticketInput.clear(); // Clear the input field
        ticketInput.requestFocus(); // Set focus back to the input field
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene(root, 400, 225);
        stage.setTitle("Ticket scanning App");
        stage.setScene(scene);
        // TODO: Add your GUI-building code here

        //CSS for button
        String myStyle = "-fx-text-fill: white;";
        myStyle += "-fx-background-color: blue ; ";
        myStyle += "-fx-border-radius: 5; ";
        myStyle += "-fx-font-size: 17; ";
        myStyle += "-fx-border-color: blue";


        // 1. Create the model
        table = new Table("codes.txt"); // Initialize the table with the ticket file

        // Load sounds
        validSound = new AudioClip(Paths.get("valid.wav").toUri().toString()); // Load valid sound
        invalidSound = new AudioClip(Paths.get("invalid.wav").toUri().toString()); // Load invalid sound

        // 2. Create the GUI components
        // Instance Variables for View Components and Model
        Label headerLabel = new Label("Ticket Scanning App");
        ticketInput = new TextField(); // Input field for ticket code
        ticketInput.setPromptText("Enter ticket code"); // Placeholder text for the input field
        Button redeemButton = new Button("Redeem"); // Button to trigger ticket validation
        resultLabel = new Label(); // Label to display the result of ticket validation

        // 3. Add components to the root
        root.getChildren().addAll(headerLabel, ticketInput, redeemButton, resultLabel); // Add components to the pane

        // 4. Configure the components (colors, fonts, size, location)
        headerLabel.relocate(50, 20);
        headerLabel.setFont(new Font("System", 30));
        headerLabel.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");

        ticketInput.relocate(50, 80);
        ticketInput.setPrefWidth(130);
        ticketInput.setPrefHeight(40);
        ticketInput.requestFocus();

        redeemButton.relocate(200, 80);
        redeemButton.setPrefWidth(120);
        redeemButton.setPrefHeight(40);
        redeemButton.setStyle(myStyle);

        resultLabel.relocate(50, 140);
        resultLabel.setFont(new Font("System", 20));
        resultLabel.setPrefWidth(300);

        // 5. Add Event Handlers and do final setup
        redeemButton.setOnAction(this::validateTicket); // Handle validate button click

        // Make the validate button sensitive to ENTER key
        ticketInput.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                validateTicket(null); // Call validateTicket when ENTER key is pressed
            }
        });

        // Clear result label when text input is changed
        ticketInput.setOnMouseClicked((MouseEvent event) -> resultLabel.setText("")); // Clear result label on mouse click
        ticketInput.setOnKeyTyped((KeyEvent event) -> resultLabel.setText("")); // Clear result label on key typed


        // 6. Show the stage
        stage.show(); // Display the stage
    }

    /**
     * Main method to launch the JavaFX application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
