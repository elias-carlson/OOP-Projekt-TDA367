package chess.controller;

import chess.Observer;
import chess.TimerObserver;
import chess.model.ChessFacade;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * ChessController handles the chess board
 */
public class ChessController implements Initializable, Observer, TimerObserver {
    private ChessFacade model = ChessFacade.getInstance();

    private Parent menuParent;
    private Scene scene;

    private ImageHandler imageHandler = new ImageHandler();
    private List<ImageView> pieceImages;
    private List<ImageView> legalMoveImages = imageHandler.fetchLegalMoveImages();

    @FXML
    Button btnBack;
    @FXML
    private Label player1Name;
    @FXML
    private Label player2Name;
    @FXML
    private Label player1Timer;
    @FXML
    private Label player2Timer;
    @FXML
    private ImageView chessBoardImage;
    @FXML
    private AnchorPane chessBoardContainer;

    double squareDimension = 75;
    double chessboardContainerX;
    double chessboardContainerY;

    /**
     * Switches to the menu/startscreen scene
     *
     * @param event Button click
     */
    @FXML
    void goToMenu(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void createMenuScene(Parent menuParent) {
        this.menuParent = menuParent;
        this.scene = new Scene(menuParent);
    }

    public Scene getMenuScene() {
        return scene;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model.addObserver(this);
        updateSquareDimensions();
        chessboardContainerX = chessBoardContainer.getLayoutX();
        chessboardContainerY = chessBoardContainer.getLayoutY();

        pieceImages = imageHandler.fetchPieceImages();
        drawPieces();
    }

    /**
     * Gives initial values to JavaFX elements and starts timers
     */
    void init() {
        player1Name.setText(model.getPlayerWhite().getName());
        player2Name.setText(model.getPlayerBlack().getName());
        model.getPlayerWhite().getTimer().addObserver(this);
        model.getPlayerBlack().getTimer().addObserver(this);
        model.getCurrentGame().initTimers();
    }

    /**
     * Gets the dimensions of the squares from the current size of the chessboard
     */
    private void updateSquareDimensions() {
        squareDimension = chessBoardImage.getFitHeight() / 8;
        imageHandler.setSquareDimension(squareDimension);
    }

    /**
     * Sends to the board that it has been clicked on
     *
     * @param event board clicked
     */
    @FXML
    public void handleClick(MouseEvent event) {
        model.handleBoardClick(translateX(event.getSceneX()), translateY(event.getSceneY()));
    }

    /**
     * Translate input x coordinate into a square index x
     *
     * @param x coordinate of click
     * @return
     */
    private int translateX(double x) {
        for (int i = 0; i < 8; i++) {
            if ((i * squareDimension + chessboardContainerX <= x && x <= chessboardContainerX + squareDimension * (i + 1))) {
                return i;
            }
        }
        throw new IllegalArgumentException("Outside board");
    }

    /**
     * Translate input y coordinate into a square index y
     *
     * @param y coordinate of click
     * @return
     */
    private int translateY(double y) {
        for (int i = 0; i < 8; i++) {
            if ((i * squareDimension + chessboardContainerY <= y && y <= chessboardContainerY + squareDimension * (i + 1))) {
                return i;
            }
        }
        throw new IllegalArgumentException("Outside board");
    }

    /**
     * Draws all images from the list of pieceImages from the board, and the possible legal moves
     */
    public void drawPieces() {
        for (ImageView pieceImage : pieceImages) {
            chessBoardContainer.getChildren().remove(pieceImage);
        }

        //Clears the list of old piece locations/images and updates it with the new one
        imageHandler.getPieceImages().clear();
        pieceImages = imageHandler.fetchPieceImages();

        for (ImageView pieceImage : pieceImages) {
            chessBoardContainer.getChildren().add(pieceImage);
            chessBoardContainer.getChildren().get(chessBoardContainer.getChildren().indexOf(pieceImage)).setMouseTransparent(true);
        }

        for (ImageView imageView : legalMoveImages) {
            chessBoardContainer.getChildren().remove(imageView);
        }

        legalMoveImages = imageHandler.fetchLegalMoveImages();

        for (ImageView imageView : legalMoveImages) {
            chessBoardContainer.getChildren().add(imageView);
            chessBoardContainer.getChildren().get(chessBoardContainer.getChildren().indexOf(imageView)).setMouseTransparent(true);
        }
    }

    /**
     * Draws pieces when called by the observable, implemented by observer interface
     */
    @Override
    public void onAction() {
        imageHandler.fetchPieceImages();
        imageHandler.updateImageCoordinates();
        drawPieces();
    }

    private void timer1Start() {
        model.getPlayerWhite().getTimer().startTimer();
    }

    private void timer1Stop() {
        model.getPlayerWhite().getTimer().stopTimer();
    }

    private void timer1Pause() {
        model.getPlayerWhite().getTimer().setActive(false);
    }

    private void timer1Unpause() {
        model.getPlayerWhite().getTimer().setActive(true);
    }

    private void timer2Start() {
        model.getPlayerBlack().getTimer().startTimer();
    }

    private void timer2Stop() {
        model.getPlayerBlack().getTimer().stopTimer();
    }

    private void timer2Pause() {
        model.getPlayerBlack().getTimer().setActive(false);
    }

    private void timer2Unpause() {
        model.getPlayerBlack().getTimer().setActive(true);
    }


    /**
     * Fetches the times for each timer from the model when called and updates the labels
     */
    public void updateTimer() {
        Platform.runLater(() -> player1Timer.setText(formatTime(model.getPlayerWhite().getTimer().getTime())));
        Platform.runLater(() -> player2Timer.setText(formatTime(model.getPlayerBlack().getTimer().getTime())));
    }

    /**
     * Takes an input integer seconds and formats it into minutes and seconds xx:xx as a String
     * appends zeroes when needed to maintain the 4 digit structure
     *
     * @param seconds
     * @return the formatted time
     */
    private String formatTime(int seconds) {
        String sec = seconds % 60 >= 10 ? "" + (seconds % 3600) % 60 : "0" + (seconds % 3600) % 60;
        String min = seconds >= 600 ? "" + (seconds % 3600) / 60 : "0" + (seconds % 3600) / 60;
        return min + ":" + sec;
    }

    //Game
    public void updateImageHandler() {
        imageHandler.initTest();
    }
}
