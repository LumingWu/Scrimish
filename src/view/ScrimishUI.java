package view;

import control.ScrimishManager;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScrimishUI {

    private final Stage _primaryStage;
    private final ScrimishManager _manager;
    private final double _width;
    private final double _height;

    private final StackPane root;
    private BorderPane gameOverScreen;
    private BorderPane inGameScreen;
    private BorderPane menuScreen;
    private BorderPane ruleScreen;

    private BorderPane currentScreen;

    private Label menuaudio;
    private Label ingameaudio;
    private Label gameoveraudio;
    private boolean updatingaudio;

    private HBox redCards;
    private HBox blueCards;

    private ScrimishColors cardSourceColor;
    private int cardSourcePile;
    private ScrimishColors cardDestinationColor;
    private int cardDestinationPile;
    private boolean cardNotClicked;

    public ScrimishUI(Stage primaryStage, double width, double height) {
        _primaryStage = primaryStage;
        _width = width;
        _height = height;

        root = new StackPane();

        /* Initialize panes */
        initializePanes();
        updatingaudio = true;
        cardNotClicked = true;
        /* Connect classes */
        _manager = new ScrimishManager(this);
        resetBoard();
    }

    private void initializePanes() {
        initializeMenuScreen();
        initializeHelpScreen();
        initializeInGameScreen();
        initializeGameOverScreen();
    }

    private void initializeMenuScreen() {
        /* Create the screen */
        menuScreen = new BorderPane();

        /* Create a vertical pane to store buttons */
        VBox menu_list = new VBox();

        /* Set vertical pane alignment IMPORTANT */
        menu_list.setAlignment(Pos.CENTER);

        /* Set spacing for the vertical pane */
        menu_list.setSpacing(5);

        /* Create logo */
        ImageView logo_image = new ImageView(new Image("resource/img/logo.png"));
        Label logo = new Label();
        logo.setGraphic(logo_image);

        /* Create buttons */
        Button enter_button = new Button("Enter");
        Button rule_button = new Button("Rule");
        Button exit_button = new Button("Exit");

        /* Resizing buttons */
        setButtonSize(enter_button, rule_button, exit_button);

        /* Set handlers for buttons */
        enter_button.setOnAction((ActionEvent event) -> {
            _manager.handleEnterButtonClicked();
        });
        rule_button.setOnAction((ActionEvent event) -> {
            _manager.handleRuleButtonClicked();
        });
        exit_button.setOnAction((ActionEvent event) -> {
            _manager.handleExitButtonClicked();
        });

        /* Initialize audio label */
        ImageView volume_max_image = new ImageView(new Image("resource/img/volume-max.png"));
        ImageView volume_mute_image = new ImageView(new Image("resource/img/volume-mute.png"));
        volume_max_image.setFitWidth(35);
        volume_max_image.setFitHeight(35);
        volume_mute_image.setFitWidth(35);
        volume_mute_image.setFitHeight(35);
        menuaudio = new Label();
        menuaudio.setGraphic(volume_max_image);
        /* Set its special spacing */
        menuaudio.setPadding(new Insets(50, 0, 0, 0));

        /* Set handler for audio_image */
        menuaudio.setOnMouseClicked((MouseEvent event) -> {
            if (menuaudio.getGraphic() == volume_max_image) {
                menuaudio.setGraphic(volume_mute_image);
            } else {
                menuaudio.setGraphic(volume_max_image);
            }
            if (updatingaudio) {
                updatingaudio = false;
                Event.fireEvent(gameoveraudio, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY,
                        1, false, false, false, false, false, false, false, false, false, false, null));
                Event.fireEvent(ingameaudio, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY,
                        1, false, false, false, false, false, false, false, false, false, false, null));
                updatingaudio = true;
            }
            _manager.handleAudioButtonClicked();
        });

        /*Add the logo and buttons to the vertical pane */
        menu_list.getChildren().addAll(logo, enter_button, rule_button, exit_button, menuaudio);

        /* Place the vertical pane to the center of the screen */
        menuScreen.setCenter(menu_list);

        /* Add this inital screen to the pane */
        root.getChildren().add(menuScreen);
        currentScreen = menuScreen;
    }

    private void initializeInGameScreen() {
        inGameScreen = new BorderPane();

        /* Create cheat to test game over screen */
        inGameScreen.setOnKeyReleased((KeyEvent event) -> {
            if (event.isControlDown()) {
                switch (event.getCode()) {
                    case B:
                        _manager.handleVictory(ScrimishColors.BLUE);
                        break;
                    case R:
                        _manager.handleVictory(ScrimishColors.RED);
                        break;
                    default:

                }
            }
        });

        /* Set up in_game menu */
        VBox ingameMenu = new VBox();
        ingameMenu.setAlignment(Pos.CENTER);
        ingameMenu.setSpacing(5);

        Button reset_button = new Button("Reset");
        Button menu_button = new Button("Menu");

        reset_button.setOnMouseClicked((MouseEvent event) -> {
            _manager.handleRestartButtonClicked();
        });

        menu_button.setOnMouseClicked((MouseEvent event) -> {
            _manager.handleMenuButtonClicked();
        });

        setButtonSize(reset_button, menu_button);

        /* Set audio and handler */
        ImageView volume_max_image = new ImageView(new Image("resource/img/volume-max.png"));
        ImageView volume_mute_image = new ImageView(new Image("resource/img/volume-mute.png"));
        volume_max_image.setFitWidth(35);
        volume_max_image.setFitHeight(35);
        volume_mute_image.setFitWidth(35);
        volume_mute_image.setFitHeight(35);
        ingameaudio = new Label();
        ingameaudio.setGraphic(volume_max_image);
        ingameaudio.setOnMouseClicked((MouseEvent event) -> {
            if (ingameaudio.getGraphic() == volume_max_image) {
                ingameaudio.setGraphic(volume_mute_image);
            } else {
                ingameaudio.setGraphic(volume_max_image);
            }
            if (updatingaudio) {
                updatingaudio = false;
                Event.fireEvent(gameoveraudio, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY,
                        1, false, false, false, false, false, false, false, false, false, false, null));
                Event.fireEvent(menuaudio, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY,
                        1, false, false, false, false, false, false, false, false, false, false, null));
                updatingaudio = true;
            }
            _manager.handleAudioButtonClicked();
        });
        /* Add all components to ingame menu */
        ingameMenu.getChildren().addAll(reset_button, menu_button, ingameaudio);

        BorderPane board = new BorderPane();
        blueCards = new HBox();
        blueCards.setAlignment(Pos.CENTER);
        blueCards.setSpacing(5);
        for (int i = 0; i < 6; i++) {
            Label newPile = new Label();
            newPile.setPrefSize(100, 200);
            newPile.setStyle("-fx-border-width: 2px;-fx-border-color: blue;");
            blueCards.getChildren().add(newPile);
        }
        redCards = new HBox();
        redCards.setAlignment(Pos.CENTER);
        redCards.setSpacing(5);
        for (int i = 0; i < 6; i++) {
            Label newPile = new Label();
            newPile.setPrefSize(100, 200);
            newPile.setStyle("-fx-border-width: 2px;-fx-border-color: red;");
            redCards.getChildren().add(newPile);
        }

        board.setTop(blueCards);
        board.setBottom(redCards);
        BorderPane.setMargin(board, new Insets(10, 0, 10, 150));

        /* Set componenets in the screen */
        inGameScreen.setCenter(board);
        inGameScreen.setRight(ingameMenu);
        /* Set it invisible initially*/
        inGameScreen.setVisible(false);
        root.getChildren().add(inGameScreen);
    }

    private void initializeGameOverScreen() {
        gameOverScreen = new BorderPane();

        /* Created vertical pane */
        VBox game_over_list = new VBox();
        /* Set vertical pane alignment IMPORTANT */
        game_over_list.setAlignment(Pos.CENTER);
        /* Set spacing for the vertical pane */
        game_over_list.setSpacing(5);

        /* Create winner announcement */
        HBox announcement = new HBox();
        /* Set horizontal pane alignment IMPORTANT */
        announcement.setAlignment(Pos.CENTER);
        /* Create texts */
        Label winner = new Label();
        Label won = new Label(" Won!");
        /* Style texts */
        winner.setStyle("-fx-font-size: 69px;");
        won.setStyle("-fx-font-size: 69px;");
        /* Add labels to annoucement */
        announcement.getChildren().addAll(winner, won);

        /* Create buttons */
        Button menu_button = new Button("Menu");
        Button restart_button = new Button("Restart");
        Button exit_button = new Button("Exit");

        /* Set button handlers */
        menu_button.setOnAction((ActionEvent event) -> {
            _manager.handleMenuButtonClicked();
        });
        restart_button.setOnAction((ActionEvent event) -> {
            _manager.handleRestartButtonClicked();
        });
        exit_button.setOnAction((ActionEvent event) -> {
            _manager.handleExitButtonClicked();
        });

        /* Resize buttons */
        setButtonSize(menu_button, restart_button, exit_button);

        /* Set audio and handler */
        ImageView volume_max_image = new ImageView(new Image("resource/img/volume-max.png"));
        ImageView volume_mute_image = new ImageView(new Image("resource/img/volume-mute.png"));
        volume_max_image.setFitWidth(35);
        volume_max_image.setFitHeight(35);
        volume_mute_image.setFitWidth(35);
        volume_mute_image.setFitHeight(35);
        gameoveraudio = new Label();
        gameoveraudio.setPadding(new Insets(10, 0, 0, 0));
        gameoveraudio.setGraphic(volume_max_image);
        gameoveraudio.setOnMouseClicked((MouseEvent event) -> {
            if (gameoveraudio.getGraphic() == volume_max_image) {
                gameoveraudio.setGraphic(volume_mute_image);
            } else {
                gameoveraudio.setGraphic(volume_max_image);
            }
            if (updatingaudio) {
                updatingaudio = false;
                Event.fireEvent(menuaudio, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY,
                        1, false, false, false, false, false, false, false, false, false, false, null));
                Event.fireEvent(ingameaudio, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY,
                        1, false, false, false, false, false, false, false, false, false, false, null));
                updatingaudio = true;
            }
            _manager.handleAudioButtonClicked();
        });

        game_over_list.getChildren().addAll(announcement, menu_button, restart_button, exit_button, gameoveraudio);

        gameOverScreen.setCenter(game_over_list);
        /* Set it invisible initially */
        gameOverScreen.setVisible(false);
        root.getChildren().add(gameOverScreen);
    }

    private void initializeHelpScreen() {
        ruleScreen = new BorderPane();

        /* Make dark background */
        ruleScreen.setStyle("-fx-background-color: rgba(0,0,0,0.8);");

        /* Set handler for screen */
        ruleScreen.setOnMouseClicked((MouseEvent event) -> {
            _manager.handleRuleScreenClicked();
        });

        /* Create a horizontal pane to store pictures */
        HBox image_list = new HBox();

        /* Set horizontal pane alignment IMPORTANT */
        image_list.setAlignment(Pos.CENTER);

        /* Set spacing for the horizontal pane */
        image_list.setSpacing(160);

        /* Get image from local */
        ImageView rule1 = new ImageView(new Image("resource/img/rule1.jpg"));
        ImageView rule2 = new ImageView(new Image("resource/img/rule2.jpg"));

        /* Resize images */
        rule1.setPreserveRatio(true);
        rule2.setPreserveRatio(true);
        rule1.setFitHeight(_height);
        rule2.setFitHeight(_height);

        /* Add images to the image list */
        image_list.getChildren().addAll(rule1, rule2);

        /* Place the image list at the center of the screen */
        ruleScreen.setCenter(image_list);
        /* Set it invisible initially */
        ruleScreen.setVisible(false);
        root.getChildren().add(ruleScreen);
    }

    public void switchScreen(ScrimishScreens screen) {
        switch (screen) {
            case MENU_SCREEN:
                screenTransition(currentScreen, menuScreen);
                currentScreen = menuScreen;
                break;
            case IN_GAME_SCREEN:
                screenTransition(currentScreen, inGameScreen);
                currentScreen = inGameScreen;
                inGameScreen.requestFocus();
                break;
            case GAME_OVER_SCREEN:
                screenTransition(currentScreen, gameOverScreen);
                currentScreen = gameOverScreen;
                break;
            case RULE_SCREEN:
                screenTransition(currentScreen, ruleScreen);
                currentScreen = ruleScreen;
                break;
            default:

        }
    }

    private void screenTransition(BorderPane a, BorderPane b) {
        a.setVisible(false);
        b.setVisible(true);
    }

    private void setButtonSize(Button... buttons) {
        for (Button button : buttons) {
            button.setPrefSize(150, 50);
        }
    }

    public void resetBoard() {
        /* Clean the 6 labels' images*/
        for (int i = 0; i < 6; i++) {
            /* Clean and set handler */
            ((Label) blueCards.getChildren().get(i)).setGraphic(null);
            ((Label) blueCards.getChildren().get(i)).setOnMouseClicked((MouseEvent e) -> {
                if(e.getClickCount() >= 2 && blueCards.getChildren().indexOf(e.getSource()) == 5){
                    _manager.rotateBlueCard();
                    displayCard();
                }
                else if(cardNotClicked){
                    cardSourceColor = ScrimishColors.BLUE;
                    cardSourcePile = blueCards.getChildren().indexOf(e.getSource());
                    cardNotClicked = false;
                    _manager.playCardSound();
                }
                else{
                    cardDestinationColor = ScrimishColors.BLUE;
                    cardDestinationPile = blueCards.getChildren().indexOf(e.getSource());
                    _manager.cardFight(cardSourceColor, cardSourcePile, cardDestinationColor, cardDestinationPile);
                    cardNotClicked = true;
                    displayCard();
                }
            });
            ((Label) redCards.getChildren().get(i)).setGraphic(null);
            ((Label) redCards.getChildren().get(i)).setOnMouseClicked((MouseEvent e) -> {
                if(e.getClickCount() >= 2 && redCards.getChildren().indexOf(e.getSource()) == 5){
                    _manager.rotateRedCard();
                    displayCard();
                }
                else if(cardNotClicked){
                    cardSourceColor = ScrimishColors.RED;
                    cardSourcePile = redCards.getChildren().indexOf(e.getSource());
                    cardNotClicked = false;
                    _manager.playCardSound();
                }
                else{
                    cardDestinationColor = ScrimishColors.RED;
                    cardDestinationPile = redCards.getChildren().indexOf(e.getSource());
                    _manager.cardFight(cardSourceColor, cardSourcePile, cardDestinationColor, cardDestinationPile);
                    cardNotClicked = true;
                    displayCard();
                }
            });
        }
        _manager.resetCardBoard();
        displayCard();
    }
    
    public void displayCard(){
        for(int i = 0; i < 6; i++){
            String blueCard = _manager.getBlueCard(i);
            if(blueCard == null){
                ((Label) blueCards.getChildren().get(i)).setGraphic(null);
            }
            else{
                ((Label) blueCards.getChildren().get(i)).setGraphic(createImageView(blueCard));
            }
            String redCard = _manager.getRedCard(i);
            if(redCard == null){
                ((Label) redCards.getChildren().get(i)).setGraphic(null);
            }
            else{
                ((Label) redCards.getChildren().get(i)).setGraphic(createImageView(redCard));
            }
        }
    }
    
    private ImageView createImageView(String name){
        switch(name){
            case "archerBlue":
                return new ImageView(new Image("resource/img/archerBlue.jpg", 100, 200, true, true));
            case "archerRed":
                return new ImageView(new Image("resource/img/archerRed.jpg", 100, 200, true, true));
            case "crownBlue":
                return new ImageView(new Image("resource/img/crownBlue.jpg", 100, 200, true, true));
            case "crownRed":
                return new ImageView(new Image("resource/img/crownRed.jpg", 100, 200, true, true));
            case "daggerBlue":
                return new ImageView(new Image("resource/img/daggerBlue.jpg", 100, 200, true, true));
            case "daggerRed":
                return new ImageView(new Image("resource/img/daggerRed.jpg", 100, 200, true, true));
            case "halberdBlue":
                return new ImageView(new Image("resource/img/halberdBlue.jpg", 100, 200, true, true));
            case "halberdRed":
                return new ImageView(new Image("resource/img/halberdRed.jpg", 100, 200, true, true));
            case "longSwordBlue":
                return new ImageView(new Image("resource/img/longSwordBlue.jpg", 100, 200, true, true));
            case "longSwordRed":
                return new ImageView(new Image("resource/img/longSwordRed.jpg", 100, 200, true, true));
            case "morningStarBlue":
                return new ImageView(new Image("resource/img/morningStarBlue.jpg", 100, 200, true, true));
            case "morningStarRed":
                return new ImageView(new Image("resource/img/morningStarRed.jpg", 100, 200, true, true));
            case "shieldBlue":
                return new ImageView(new Image("resource/img/shieldBlue.jpg", 100, 200, true, true));
            case "shieldRed":
                return new ImageView(new Image("resource/img/shieldRed.jpg", 100, 200, true, true));
            case "swordBlue":
                return new ImageView(new Image("resource/img/swordBlue.jpg", 100, 200, true, true));
            case "swordRed":
                return new ImageView(new Image("resource/img/swordRed.jpg", 100, 200, true, true));
            case "warAxeBlue":
                return new ImageView(new Image("resource/img/warAxeBlue.jpg", 100, 200, true, true));
            case "warAxeRed":
                return new ImageView(new Image("resource/img/warAxeRed.jpg", 100, 200, true, true));
            default:
                return null;
        }
    }

    public enum ScrimishScreens {
        MENU_SCREEN, IN_GAME_SCREEN, GAME_OVER_SCREEN, RULE_SCREEN
    }

    public enum ScrimishColors {
        BLUE, RED
    }

    public Stage getPrimaryStage() {
        return _primaryStage;
    }

    public StackPane getRoot() {
        return root;
    }

    public BorderPane getMenuScreen() {
        return menuScreen;
    }

    public BorderPane getRuleScreen() {
        return ruleScreen;
    }

    public BorderPane getInGameScreen() {
        return inGameScreen;
    }

    public BorderPane getGameOverScreen() {
        return gameOverScreen;
    }

}
