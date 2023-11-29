package controllers;

import game.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static game.MultipleChoiceGame.option;

public class GameAventure implements Initializable {
    @FXML
    private ImageView player;
    @FXML
    private AnchorPane scene;
    @FXML
    private Button buttonA;
    @FXML
    private Button buttonB;
    @FXML
    private Button buttonC;
    @FXML
    private Button buttonD;
    @FXML
    private Text HPText;
    @FXML
    private Text pointText;
    @FXML
    private Button exitButton;

    @FXML
    private Text questionText;
    @FXML
    private Text content;

    private WordChoiceGame wordGame = new WordChoiceGame();     // Su dung game nay by TVDH
    private MeaningChoiceGame meaningGame = new MeaningChoiceGame();     // Su dung game nay by TVDH
    private String correctAnswer;
    private ArrayList<ImageView> listBox = new ArrayList<>();
    private final Image boxImage = new Image(getClass().getResourceAsStream("/icon/box.png"));
    private final Image idleImage = new Image(getClass().getResourceAsStream("/icon/Pink_Monster_Idle_4.png"));
    private final Image hurtImage = new Image(getClass().getResourceAsStream("/icon/Pink_Monster_Hurt_4.png"));
    private final Image dieImage = new Image(getClass().getResourceAsStream("/icon/Pink_Monster_Death_8.png"));
    private boolean isHurt = false;
    private boolean isDie = false;

    private int[][] map = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 1, 2, 0, 1, 0, 1, 0, 0},
        {0, 1, 0, 1, 2, 1, 2, 0, 1, 0},
        {0, 1, 1, 1, 1, 0, 1, 1, 0, 0},
        {0, 0, 2, 0, 2, 1, 2, 1, 1, 0},
        {0, 1, 1, 1, 1, 0, 1, 0, 2, 0},
        {0, 1, 0, 2, 0, 1, 2, 1, 1, 0},
        {0, 2, 1, 1, 1, 1, 1, 0, 1, 0},
        {0, 1, 0, 1, 0, 1, 2, 1, -1, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    private boolean isQuest = false;
    private int currentX = 2;
    private int currentY = 1;
    private int index = 0;

    private int HP = 3;
    private int point = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        wordGame.initGame();            // Chuan bi choi game
        meaningGame.initGame();

        scene.requestFocus();
        for (int i = 1; i < map.length - 1; i++) {
            for (int j = 1; j < map[0].length - 1; j++) {
                if (map[i][j] < 2) continue;

                ImageView box = new ImageView(boxImage);
                if (map[i][j] == 2) {
                    box.setViewport(new Rectangle2D(0, 0, 48, 48));
                } else {
                    box.setViewport(new Rectangle2D(48, 0, 48, 48));
                }
                box.setX(436 + 64 * j);
                box.setY(40 + 64 * i);
                box.setFitWidth(64);
                box.setFitHeight(64);
                listBox.add(box);
                scene.getChildren().add(box);
            }
        }

        player.toFront();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            player.setViewport(new Rectangle2D(index * 32, 0, 32, 32));
            index++;
            if (index >= 4 && !isDie) {
                if (isHurt) {
                    isHurt = false;
                    player.setImage(idleImage);
                    if (HP == 0) {
                        // endgame
                        isDie = true;
                        index = 0;
                        player.setImage(dieImage); // Hiệu ứng khi chết
                    }
                }
                index = 0;
            } else if (index >= 8) {
                player.setVisible(false);
                buttonA.setText("Play again !?");
                buttonA.setVisible(true);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        controllerKey();
        controllerMouse();
        firstScene();
    }

    private void firstScene() {
        questionText.setVisible(true);
        setButtonVisible(false);
        HPText.setVisible(false);
        pointText.setVisible(false);
        content.setText("HOW TO PLAY:");
        questionText.setText("  - Move the OASIS to all the yellow boxes by using keyboard then answer the question in each box by click at the button.\n" +
                "  - You get 0 points at first, it will increase by 10 when you answer correctly.\n" +
                "  - You get 3 HP at first, it will decrease by 1 when you answer incorrectly and you gonna lose if your HP reach 0.\n");

    }

    public void controllerKey() {
        scene.setOnKeyPressed( e -> {
            System.out.println(e.getCode());
//            if (isQuest) {
//                if (e.getCode().equals(KeyCode.A)) {
//                    getResult("A");
//                }
//                else if (e.getCode().equals(KeyCode.B)) {
//                    getResult("B");
//                }
//                if (e.getCode().equals(KeyCode.C)) {
//                    getResult("C");
//                }
//                if (e.getCode().equals(KeyCode.D)) {
//                    getResult("D");
//                }
//            }
            if (isQuest || isHurt || isDie) return;
            if (e.getCode().equals(KeyCode.RIGHT)) {
                player.setScaleX(1);
                if (map[currentY][currentX + 1] != 0) {
                    player.setX(player.getX() + 64);
                    currentX++;
                }
            } else if (e.getCode().equals(KeyCode.LEFT)) {
                player.setScaleX(-1);
                if (map[currentY][currentX - 1] != 0) {
                    player.setX(player.getX() - 64);
                    currentX--;
                }
            } else if (e.getCode().equals(KeyCode.UP)) {
                if (map[currentY - 1][currentX] != 0) {
                    player.setY(player.getY() - 64);
                    currentY--;
                }
            } else if (e.getCode().equals(KeyCode.DOWN)) {
                if (map[currentY + 1][currentX] != 0) {
                    player.setY(player.getY() + 64);
                    currentY++;
                }
            }
            if (map[currentY][currentX] == 2) {
                map[currentY][currentX] = 3;
                changeBox();
                isQuest = true;
                openQuest();

            }
        });
    }

    public void controllerMouse() {
        buttonA.setOnAction(e -> {
            if (isDie || ((point / 10) + (3 - HP) == 11)) {
                resetGame();
                return;
            }
            getResult("A");

        });
        buttonB.setOnAction(e -> {
            getResult("B");

        });
        buttonC.setOnAction(e -> {
            getResult("C");

        });
        buttonD.setOnAction(e -> {
            getResult("D");

        });

        exitButton.setOnAction(e -> {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        });
    }

    public void openQuest() {
        int randomGame = (int) (Math.random() * 2);
        wordGame.getPrepared();  // tao cau hoi va cac lua chon moi
        meaningGame.getPrepared();
        String question;
        String choiceA;
        String choiceB;
        String choiceC;
        String choiceD;
        if (randomGame == 0) {
            content.setText("Which word has this meaning?");
            questionText.setText(wordGame.giveQuestion().substring(14, wordGame.giveQuestion().length()));
            question = wordGame.giveQuestion().substring(14, wordGame.giveQuestion().length());
            choiceA = wordGame.giveChoice(0);
            choiceB = wordGame.giveChoice(1);
            choiceC = wordGame.giveChoice(2);
            choiceD = wordGame.giveChoice(3);
            correctAnswer = option[wordGame.getCorrectChoice()];
        } else {
            content.setText("Which is the meaning of this word?");
            questionText.setText(meaningGame.giveQuestion().substring(14, meaningGame.giveQuestion().length()));
            question = meaningGame.giveQuestion().substring(14, meaningGame.giveQuestion().length());
            choiceA = meaningGame.giveChoice(0);
            choiceB = meaningGame.giveChoice(1);
            choiceC = meaningGame.giveChoice(2);
            choiceD = meaningGame.giveChoice(3);
            correctAnswer = option[meaningGame.getCorrectChoice()];
        }

        System.out.println(question);
        System.out.println(choiceA);
        System.out.println(choiceB);
        System.out.println(choiceC);
        System.out.println(choiceD);
        System.out.println(correctAnswer);

        buttonA.setText(choiceA);
        buttonB.setText(choiceB);
        buttonC.setText(choiceC);
        buttonD.setText(choiceD);

        content.setVisible(true);
        questionText.setVisible(true);
        HPText.setVisible(true);
        pointText.setVisible(true);
        setButtonVisible(true);
    }

    public void getResult(String playerInput) {
        boolean result = playerInput.equals(correctAnswer);
        if (result) {
            content.setText("Congratulation !!!");
            questionText.setText("CORRECT!");
            increasePoint();
        } else {
            content.setText("OASIS is so saddd TT");
            questionText.setText("INCORRECT!");
            decreaseHP();
        }
        isQuest = false;

        setButtonVisible(false);
    }

    private void setButtonVisible(boolean b) {
        buttonA.setVisible(b);
        buttonB.setVisible(b);
        buttonC.setVisible(b);
        buttonD.setVisible(b);
    }

    public void increasePoint() {
        point += 10;
        pointText.setText("Point: " + point);

        if ((point / 10) + (3 - HP) == 11) {
            content.setText("Congratulation. You Won !!!");
            questionText.setText("GAME OVER!\n Your Point: " + point);
            buttonA.setText("Play again !?");
            buttonA.setVisible(true);
        }
    }

    public void decreaseHP() {
        HP -= 1;
        isHurt = true;
        index = 0;
        player.setImage(hurtImage); // Hiệu ứng khi trả lời sai
        HPText.setText("HP: " + HP);

        if (HP == 0) {
            questionText.setText("GAME OVER!\n Your Point: " + point);
        }
    }


    public void changeBox() {
        for (int i = 0; i < listBox.size(); i++) {
            if (map[(int) ((listBox.get(i).getY() - 40) / 64)][(int) ((listBox.get(i).getX() - 436) / 64)] == 3) {
                listBox.get(i).setViewport(new Rectangle2D(48, 0, 48, 48));
            } else {
                listBox.get(i).setViewport(new Rectangle2D(0, 0, 48, 48));
            }
        }
    }

    public void resetGame() {
        player.setX(player.getX() - 64 * --currentX);
        player.setY(player.getY() - 64 * --currentY);

        index = 0;
        point = 0;
        HP = 3;
        currentX = 1;
        currentY = 1;
        isDie = false;

        player.setImage(idleImage);
        player.setVisible(true);
        pointText.setText("Point: 0");
        HPText.setText("HP: 0");

        for (int i = 1; i < map.length; i++) {
            for (int j = 1; j < map[0].length; j++) {
                if (map[i][j] == 3) {
                    map[i][j] = 2;
                }
            }
        }
        changeBox();
        firstScene();
    }

}
