package controllers;

import game.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import static game.MultipleChoiceGame.option;

public class gameTest implements Initializable {
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
//    @FXML
//    private ;


    private WordChoiceGame wordGame = new WordChoiceGame();     // Su dung game nay by TVDH
    private MeaningChoiceGame meaningGame = new MeaningChoiceGame();     // Su dung game nay by TVDH

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
        openQuest();

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
                }
                index = 0;
            } else if (index >= 8) {
                player.setVisible(false);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
//        Platform.runLater(()->{
//            if (!isQuest) controllerKey();
//        });

    }

    public void controllerKey() {
        scene.setOnKeyPressed( e -> {
            System.out.println(e.getCode());
            if (isQuest) {
                if (e.getCode().equals(KeyCode.A)) {
                    isHurt = true;
                    index = 0;
                    player.setImage(hurtImage); // Hiệu ứng khi trả lời sai
                    isQuest = false;
                }
                else if (e.getCode().equals(KeyCode.B)) {
                    isDie = true;
                    index = 0;
                    player.setImage(dieImage); // Hiệu ứng khi chết
                    isQuest = false;
                }
                if (e.getCode().equals(KeyCode.C)) {
                    //
                    isQuest = false;
                }
                if (e.getCode().equals(KeyCode.D)) {
                    //
                    isQuest = false;
                }
            }
            if (isQuest) return;
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
                System.out.println("quest");
            }
        });
    }

    public void openQuest() {
        // Hiện câu hỏi lên
        //
        int randomGame = (int) (Math.random() * 2);
        wordGame.getPrepared();  // tao cau hoi va cac lua chon moi
        meaningGame.getPrepared();
        String question;
        String choiceA;
        String choiceB;
        String choiceC;
        String choiceD;
        if (randomGame == 0) {
            question = "Which word has the following meaning? " + wordGame.giveQuestion().substring(14, wordGame.giveQuestion().length());
            choiceA = wordGame.giveChoice(0);
            choiceB = wordGame.giveChoice(1);
            choiceC = wordGame.giveChoice(2);
            choiceD = wordGame.giveChoice(3);
        } else {
            question = "Which is the meaning of the following word? " + meaningGame.giveQuestion().substring(14, meaningGame.giveQuestion().length());
            choiceA = meaningGame.giveChoice(0);
            choiceB = meaningGame.giveChoice(1);
            choiceC = meaningGame.giveChoice(2);
            choiceD = meaningGame.giveChoice(3);
        }
        System.out.println(question);
        System.out.println(choiceA);
        System.out.println(choiceB);
        System.out.println(choiceC);
        System.out.println(choiceD);

        // SetText dong nay
        buttonA.setText(choiceA);
        buttonB.setText(choiceB);
        buttonC.setText(choiceC);
        buttonD.setText(choiceD);

    }

    public void getResult() {
        String playerInput = "";   // can 1 method lay input tra ve duoi dang "A" / "B" / "C" / "D"

        boolean result = playerInput.equals(option[wordGame.getCorrectChoice()]);
        if (result) {
            increasePoint();
        } else {
            decreaseHP();
        }
        // Co result thi xu li controller tiep
    }

    public void increasePoint() {
        point += 1;
        // cap nhat point o giao dien
        // bat dau vong choi moi
    }

    public void decreaseHP() {
        HP -= 1;
        if (HP == 0) {
            // endgame
        } else {
            // bat dau vong choi moi
        }
    }


    public void changeBox() {
        for (int i = 0; i < listBox.size(); i++) {
            if (map[(int) ((listBox.get(i).getY() - 40) / 64)][(int) ((listBox.get(i).getX() - 436) / 64)] == 3) {
                listBox.get(i).setViewport(new Rectangle2D(48, 0, 48, 48));
            }
        }
    }

}
