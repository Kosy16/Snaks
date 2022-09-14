package com.javarush.games.snake;


import com.javarush.engine.cell.*;
import javafx.scene.effect.ImageInput;

public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;
    private Apple apple;
    private boolean isGameStopped;
    private static final int GOAL = 20;
    private int score;

    private int turnDelay;

    public void initialize() {

        setScreenSize(WIDTH, HEIGHT);
        createGame();

    }
    public void onKeyPress(Key key) {
        if (key == Key.LEFT) {
            snake.setDirection(Direction.LEFT);
        } else if (key == Key.RIGHT) {
            snake.setDirection(Direction.RIGHT);
        } else if (key == Key.UP) {
            snake.setDirection(Direction.UP);
        } else if (key == Key.DOWN) {
            snake.setDirection(Direction.DOWN);
        }

        if ( key == Key.SPACE && isGameStopped == true){
            createGame();
        }
    }
    
    private void createGame() {
        score = 0; // счетчик очков
        setScore(score);
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        //apple = new Apple(5,5);
        createNewApple();
        isGameStopped = false;
        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);

    }

    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;

        showMessageDialog(Color.WHEAT, "GAME OVER", Color.BLACK, 100);

    }

    public void onTurn(int step) {
        snake.move(apple);
        if (apple.isAlive == false){
            score = score + 5;
            setScore(score);
            turnDelay = turnDelay - 10;
            setTurnTimer(turnDelay);
            createNewApple();
        }
        if(snake.isAlive == false) {
            gameOver();
        }
        if (snake.getLength() > GOAL){
        win();
        }
        drawScene();

    }

    private void drawScene() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++)
                setCellValueEx(x, y, Color.BURLYWOOD,"" );
        }
        snake.draw(this);
        apple.draw(this);

    }

    private void createNewApple() { // вызов метода пока кооординаты яблока и змеи совпадают
        Apple newApple;
        do {
            int x = getRandomNumber(WIDTH);
            int y = getRandomNumber(HEIGHT);
            newApple = new Apple(x, y);
        } while (snake.checkCollision(newApple));
        apple = newApple;
    }

private void win(){
        stopTurnTimer();
        isGameStopped = true;
    showMessageDialog(Color.WHEAT, "YOU WIN", Color.BLACK, 100);

}

}








