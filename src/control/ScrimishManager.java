package control;

import java.io.File;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import view.ScrimishUI;
import view.ScrimishUI.ScrimishColors;

public class ScrimishManager {
    
    private final ScrimishUI _ui;
    
    private MediaPlayer music;
    private AudioClip buttonSound;
    private AudioClip victorySound;
    private AudioClip cardSound;
    
    public ScrimishManager(ScrimishUI ui){
        _ui = ui;
        
        initializeAudio();
    }
    
    private void initializeAudio(){
        playMusic();
        buttonSound = new AudioClip(new File("src/resource/music/ButtonSound - Sunnyside.aiff").toURI().toString());
        victorySound = new AudioClip(new File("src/resource/music/powerup-success-Gabrielaraujo.wav").toURI().toString());
        cardSound = new AudioClip(new File("src/resource/music/card-flip-f4ngy.wav").toURI().toString());
    }
    
    private void playMusic(){
        music = new MediaPlayer(new Media(new File("src/resource/music/SDS - Hiroyuki Sawano.mp3").toURI().toString()));
        music.setVolume(0.1);
        music.setOnEndOfMedia(() -> {
            music.stop();
            music.play();
        });
        music.play();
    }
    
    private void playButtonClickedSound(){
        buttonSound.play();
    }
    
    private void playVictorySound(){
        victorySound.play();
    }
    
    public void playCardSound(){
        cardSound.play();
    }
    
    public void handleEnterButtonClicked(){
        playButtonClickedSound();
        _ui.switchScreen(ScrimishUI.ScrimishScreens.IN_GAME_SCREEN);
    }
    
    public void handleRuleButtonClicked(){
        playButtonClickedSound();
        _ui.switchScreen(ScrimishUI.ScrimishScreens.RULE_SCREEN);
    }
    
    public void handleExitButtonClicked(){
        playButtonClickedSound();
        _ui.getPrimaryStage().close();
    }
    
    public void handleRuleScreenClicked(){
        _ui.switchScreen(ScrimishUI.ScrimishScreens.MENU_SCREEN);
    }
    
    public void handleVictory(ScrimishUI.ScrimishColors color){
        Label winner = (Label)((HBox)((VBox)_ui.getGameOverScreen().getCenter()).getChildren().get(0)).getChildren().get(0);
        switch(color){
            case BLUE:
                winner.setText("Blue");
                winner.setTextFill(Paint.valueOf("blue"));
                break;
            case RED:
                winner.setText("Red");
                winner.setTextFill(Paint.valueOf("red"));
                break;
            default:
                
        }
        _ui.switchScreen(ScrimishUI.ScrimishScreens.GAME_OVER_SCREEN);
        playVictorySound();
    }
    
    public void handleMenuButtonClicked(){
        _ui.switchScreen(ScrimishUI.ScrimishScreens.MENU_SCREEN);
    }
    
    public void handleResetButtonClicked(){
        _ui.resetBoard();
    }
    
    public void handleRestartButtonClicked(){
        _ui.resetBoard();
        _ui.switchScreen(ScrimishUI.ScrimishScreens.IN_GAME_SCREEN);
    }
    
    /**
     * This function should clean all the cards for both players and redistribute cards that
     * the crown card should be at the bottom of one of the piles.
     * This function should be called by the GUI and it should call GUI function
     * to display the new set of cards.
     */
    public void resetCardBoard(){
        for(int i = 0; i < 5; i++){
            Label bluecard = new Label("Card" + i);
            bluecard.setPrefSize(100, 200);
            bluecard.setStyle("-fx-background-color: blue;");
            _ui.addCard(bluecard, ScrimishColors.BLUE, i);
            Label redcard = new Label("Card" + i);
            redcard.setPrefSize(100, 200);
            redcard.setStyle("-fx-background-color: red;");
            _ui.addCard(redcard, ScrimishColors.RED, i);
        }
    }
    
    /**
     * This function should determine the result of the card fight.
     * Three possible result:
     * (1) Attacking card is discard.
     * (2) Defending card is discard.
     * (3) Both cards are discard.
     * (4) No card gets discard, announce winner through GUI function.
     * According to the result, the view of GUI has to be changed by GUI function
     * and the Manager should change its own data too.
     * @param sourceColor
     * @param sourcePile
     * @param destinColor
     * @param destinPile 
     */
    public void cardFight(ScrimishColors sourceColor, int sourcePile, ScrimishColors destinColor, int destinPile){
        _ui.removeCard(destinColor, destinPile);
        playCardSound();
        handleVictory(sourceColor);
    }
    
    public void handleAudioButtonClicked(){
        if(music.isMute()){
            /* Unmute */
            music.setMute(false);
        }
        else{
            /* Mute */
            music.setMute(true);
        }
    }
    
}
