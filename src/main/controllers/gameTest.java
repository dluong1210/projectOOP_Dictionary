package controllers;

import game.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static game.MultipleChoiceGame.option;

public class gameTest implements Initializable {
    @FXML
    private ImageView player;
    @FXML
    private AnchorPane scene;

    private WordChoiceGame game = new WordChoiceGame();     // Su dung game nay by TVDH

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

        game.initGame();            // Chuan bi choi game


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

        if (!isQuest) controllerKey();
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
        game.getPrepared();  // tao cau hoi va cac lua chon moi
        String question = game.giveQuestion();
        String choiceA = game.giveChoice(0);
        String choiceB = game.giveChoice(1);
        String choiceC = game.giveChoice(2);
        String choiceD = game.giveChoice(3);

        // SetText dong nay
    }

    public void getResult() {
        String playerInput = "";   // can 1 method lay input tra ve duoi dang "A" / "B" / "C" / "D"

        boolean result = playerInput.equals(option[game.getCorrectChoice()]);

        // Co result thi xu li controller tiep
    }



    public void changeBox() {
        for (int i = 0; i < listBox.size(); i++) {
            if (map[(int) ((listBox.get(i).getY() - 40) / 64)][(int) ((listBox.get(i).getX() - 436) / 64)] == 3) {
                listBox.get(i).setViewport(new Rectangle2D(48, 0, 48, 48));
            }
        }
    }

}
