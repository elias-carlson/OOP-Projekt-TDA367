package mindchess.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import mindchess.model.Ply;
import mindchess.model.Square;
import mindchess.model.pieces.IPiece;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * PlyController is responsible for keeping track of all the moves/plies that have happened in a game so that multiple games can be played
 * at the same time
 */
public class PlyController extends AnchorPane {
    private final Ply ply;
    private final ImageHandlerUtil imageHandlerUtil;

    @FXML
    private Label labelPlyNumber;
    @FXML
    private Label labelMovedFrom;
    @FXML
    private Label labelMovedTo;
    @FXML
    private Label labelPlayer;
    @FXML
    private ImageView imagePiece;

    public PlyController(Ply ply, int plyNum, ImageHandlerUtil imageHandlerUtil) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/plyView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.ply = ply;
        this.imageHandlerUtil = imageHandlerUtil;
        this.imagePiece.setImage(imageHandlerUtil.createPieceImage(ply.getMovedPiece().getPieceType(), ply.getMovedPiece().getColor()));
        this.labelPlyNumber.setText(String.format("#%d", plyNum));
        this.labelMovedFrom.setText(String.format("%s%s", translateXCoordinate(ply.getMovedFrom().getX()), translateYCoordinate(ply.getMovedFrom().getY())));
        this.labelMovedTo.setText(String.format("%s%s", translateXCoordinate(ply.getMovedTo().getX()), translateYCoordinate(ply.getMovedTo().getY())));
        this.labelPlayer.setText(String.format("%s", ply.getPlayerName()));
    }

    public List<ImageView> generateBoardImages(boolean performMove) {
        List<ImageView> imageViewList = new ArrayList<>();

        for (Map.Entry<Square, IPiece> entry : ply.getBoardSnapshot().entrySet()) {
            ImageView imageView = imageHandlerUtil.fetchPieceImageView(entry.getKey(), entry.getValue().getPieceType(), entry.getValue().getColor(), 40);

            imageViewList.add(imageView);

            if (entry.getValue().equals(ply.getMovedPiece())) {

                if (performMove) {
                    imageHandlerUtil.addTranslateTransition(imageView, ply.getMovedFrom(), ply.getMovedTo(), 40, 400);
                } else {
                    imageView.setX(ply.getMovedFrom().getX() * 40);
                    imageView.setY(ply.getMovedFrom().getY() * 40);
                }

                if (ply.getTakenPiece() != null) {
                    ImageView attackedImageView = imageHandlerUtil.fetchPieceImageView(ply.getMovedTo(), ply.getTakenPiece().getPieceType(), ply.getTakenPiece().getColor(), 40);
                    imageHandlerUtil.addScaleTransition(attackedImageView, 400, false);
                    imageViewList.add(attackedImageView);
                }
            }
        }

        return imageViewList;
    }

    private String translateXCoordinate(int x) {
        String[] translation = {"a", "b", "c", "d", "e", "f", "g", "h"};
        return translation[x];
    }

    private String translateYCoordinate(int y) {
        String[] translation = {"8", "7", "6", "5", "4", "3", "2", "1"};
        return translation[y];
    }
}
