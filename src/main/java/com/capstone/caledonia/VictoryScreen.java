package com.capstone.caledonia;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class VictoryScreen extends AnchorPane {

    @FXML private ImageView background;
    @FXML private Text victoryMessage;
    @FXML private Button quit;
    @FXML private Button playAgain;


    VictoryScreen(String message)throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("VictoryScreen.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
        victoryMessage.textProperty().set(message);
    }

    VictoryScreenViewModel viewModel = new VictoryScreenViewModel();

    @FXML
    protected void handleQuitButtonClick(){
        Platform.exit();
    }
    @FXML
    protected void handlePlayAgainClick()throws Exception{
        viewModel.restartGame();
        BattleScreen initialBattle = new BattleScreen();
        getScene().setRoot(initialBattle);
    }

}
