package com.capstone.caledonia;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;

import java.util.ArrayList;

import static javafx.beans.binding.Bindings.bindBidirectional;

public class BattleScreen extends AnchorPane{
    @FXML
    private Button mapButton;
    @FXML
    private Button deckButton;
    @FXML
    private Button quitButton;
    @FXML
    private ImageView playerSprite;
    @FXML
    private ProgressBar playerHealth;
    @FXML
    private ProgressBar enemyHealth;
    @FXML
    private HBox enemyBox;
    @FXML
    private ImageView enemySprite;
    @FXML
    private HBox cardBox;
    @FXML
    private Button confirmButton;
    @FXML
    private ImageView background;
    @FXML
    private Text deckCount;
    @FXML
    private Text discardCount;
    @FXML
    private Text handCount;
    @FXML
    private Text energy;

    private final BattleScreenViewModel viewModel = new BattleScreenViewModel();
    BattleScreen()throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BattleScreen.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
        bindViewModel();
        cardBox.getChildren().addAll(generateHandImageViews());
    }

    private void bindViewModel(){
        playerHealth.progressProperty().bindBidirectional(viewModel.playerHealthProperty());
        enemyHealth.progressProperty().bindBidirectional(viewModel.enemyHealthProperty());
        playerSprite.imageProperty().bindBidirectional(viewModel.playerSpriteProperty());
        enemySprite.imageProperty().bindBidirectional(viewModel.enemySpriteProperty());
        Bindings.bindBidirectional(handCount.textProperty(), viewModel.handCountProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(deckCount.textProperty(), viewModel.deckCountProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(discardCount.textProperty(), viewModel.discardCountProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(
                energy.textProperty(),
                viewModel.energyProperty(),
                new NumberStringConverter());
    }

    @FXML
    protected void onQuitButtonClick(){
        Platform.exit();
    }
    private ArrayList<ImageView> generateHandImageViews(){
        ArrayList<ImageView> result = new ArrayList<>();
        int i = 0;
        for (Image cardImage: viewModel.generateHandImages()){
            ImageView cardDisplay = new ImageView(cardImage);
            cardDisplay.setId(String.valueOf(i));
            cardDisplay.setOnMouseClicked(event -> {
                try {
                    handleCardClick(event);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            i++;
            result.add(cardDisplay);
        }
        return result;
    }

    private void handleCardClick(Event event) throws Exception{
        String imageID = ((ImageView)event.getSource()).getId();
        if(!viewModel.useCard(Integer.parseInt(imageID))){
        rebuildHand();} else {
                VictoryScreen vicScreen = new VictoryScreen("Congratulations!");
                getScene().setRoot(vicScreen);
        }

    }
    @FXML
    private void handleConfirmClick()throws Exception{
        if(viewModel.endTurn()){
            VictoryScreen vicScreen = new VictoryScreen("Defeat!");
            getScene().setRoot(vicScreen);
        } else {
            rebuildHand();
        }

    }
    public void rebuildHand(){
        cardBox.getChildren().clear();
        cardBox.getChildren().addAll(generateHandImageViews());
    }
}
