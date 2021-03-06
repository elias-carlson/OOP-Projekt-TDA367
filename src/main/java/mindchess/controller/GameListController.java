package mindchess.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Responsible for fetching information about a game and displaying that information it in the gameListView.fxml
 */
class GameListController extends AnchorPane {
    @FXML
    private Label player1NameLabel;
    @FXML
    private Label player2NameLabel;
    @FXML
    private Label gameStatusLabel;
    @FXML
    private Label gameIDLabel;

    GameListController(String player1Name, String player2Name, String gameStatus, int gameID) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gameListView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        player1NameLabel.setText(player1Name);
        player2NameLabel.setText(player2Name);
        gameStatusLabel.setText(gameStatus);
        gameIDLabel.setText("#" + gameID);
    }
}
