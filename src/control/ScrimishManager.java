package control;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
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
    
    private ArrayList<LinkedList<String>> blueCards;
    private ArrayList<LinkedList<String>> redCards;
    
    public ScrimishManager(ScrimishUI ui){
        _ui = ui;
        blueCards = new ArrayList<LinkedList<String>>(6);
        redCards = new ArrayList<LinkedList<String>>(6);
        for(int i = 0; i < 6; i++){
            blueCards.add(new LinkedList<String>());
            redCards.add(new LinkedList<String>());
        }
        initializeAudio();
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
    
    /**
     * This function should clean all the cards for both players and redistribute cards that
     * the crown card should be at the bottom of one of the piles.
     * This function should be called by the GUI and it should call GUI function
     * to display the new set of cards.
     */
    public void resetCardBoard(){
        for(int i = 0; i < 6; i++){
            blueCards.get(i).clear();
            redCards.get(i).clear();
        }
        blueCards.get(5).add("archerBlue");
        redCards.get(5).add("archerRed");
        blueCards.get(5).add("crownBlue");
        redCards.get(5).add("crownRed");
        blueCards.get(5).add("daggerBlue");
        redCards.get(5).add("daggerRed");
        blueCards.get(5).add("halberdBlue");
        redCards.get(5).add("halberdRed");
        blueCards.get(5).add("longSwordBlue");
        redCards.get(5).add("longSwordRed");
        blueCards.get(5).add("morningStarBlue");
        redCards.get(5).add("morningStarRed");
        blueCards.get(5).add("shieldBlue");
        redCards.get(5).add("shieldRed");
        blueCards.get(5).add("swordBlue");
        redCards.get(5).add("swordRed");
        blueCards.get(5).add("warAxeBlue");
        redCards.get(5).add("warAxeRed");
    }
    
    public void rotateBlueCard(){
        blueCards.get(5).addFirst(blueCards.get(5).removeLast());
    }
    
    public void rotateRedCard(){
        redCards.get(5).addFirst(redCards.get(5).removeLast());
    }
    
    public String getBlueCard(int pile){
        if(blueCards.get(pile).isEmpty()){
            return null;
        }
        return blueCards.get(pile).getLast();
    }
    
    public String getRedCard(int pile){
        if(redCards.get(pile).isEmpty()){
            return null;
        }
        return redCards.get(pile).getLast();
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
        if(sourceColor == destinColor && sourcePile == 5){
            if(sourceColor == ScrimishColors.BLUE){
                blueCards.get(destinPile).addFirst(blueCards.get(sourcePile).removeLast());
            }
            else{
                redCards.get(destinPile).addFirst(redCards.get(sourcePile).removeLast());
            }
        }
        else{
            playCardSound();
            handleVictory(sourceColor);
        }
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
