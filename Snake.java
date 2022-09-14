package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";

    private List<GameObject> snakeParts = new ArrayList<>();
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT; // хранит направление движения змейки

    public Snake(int x, int y) {
        snakeParts.add(new GameObject(x, y)); // новый объект
        snakeParts.add(new GameObject(x + 1, y));
        snakeParts.add(new GameObject(x + 2, y));
    }


    public void draw(Game game) {
        int colorIndex = 0; // Индекс цвета со списка
        ArrayList<Color> colorArr = new ArrayList<>(); // список цветов
        colorArr.add(Color.DARKBLUE); // Добавление цветов в список
        colorArr.add(Color.BLUE);
        colorArr.add(Color.AQUA);
        colorArr.add(Color.BLUE);
        int size = 80; // Максимальный размер (Будет голова)
        int div = (size-25)/snakeParts.size(); // Установка разницы относительно от размера змейки. Чем больше значение мы отнимаем от size (в данном случае 25) тем больше будет размер змейки

        //Color color = isAlive ? Color.BLACK : Color.RED;
        for (int i = 0; i < snakeParts.size(); i++) {
            GameObject part = snakeParts.get(i);
            String sign = (i != 0) ? BODY_SIGN : HEAD_SIGN;
            game.setCellValueEx(part.x, part.y, Color.NONE, sign, isAlive ? sign == HEAD_SIGN ? Color.BLACK : colorArr.get(colorIndex) : Color.RED, size); //
            size -= div; // получаем размер следущей клетки
            colorIndex++; // Перемещаемся на следущий цвет
            colorIndex = colorIndex == colorArr.size() -1 ? 0 : colorIndex; // проверка что бы не выйти за рамки списка
        }

    }

    public GameObject createNewHead(){
        GameObject oldHead = snakeParts.get(0); // создание нового элмента рядом с головой
        if (direction == Direction.LEFT) {
            return new GameObject(oldHead.x - 1, oldHead.y);
        } else if (direction == Direction.RIGHT) {
            return new GameObject(oldHead.x + 1, oldHead.y);
        } else if (direction == Direction.UP) {
            return new GameObject(oldHead.x, oldHead.y - 1);
        } else {
            return new GameObject(oldHead.x, oldHead.y + 1);
        }
    }

    public void removeTail(){
        snakeParts.remove(snakeParts.size() - 1); // удаляет последний элемент из списка
    }

    public void move(Apple apple){
        GameObject newHead = createNewHead();
        if (newHead.x >= SnakeGame.WIDTH || newHead.x < 0 || newHead.y >= SnakeGame.HEIGHT || newHead.y < 0){
            isAlive = false;
            return;
        }

        if (checkCollision(newHead)) { // если было столкновение
            isAlive = false;
            return;
        }

        snakeParts.add(0, newHead); // добавление нового сегмента змеи в список snakeParts


        if (newHead.x == apple.x && newHead.y == apple.y){
            apple.isAlive = false;
        }
        else {
            removeTail(); // удалить хвост
        }


    }

    public void setDirection(Direction direction) {
        if ((this.direction == Direction.LEFT || this.direction == Direction.RIGHT) && snakeParts.get(0).x == snakeParts.get(1).x) {
            return;
        }
        if ((this.direction == Direction.UP || this.direction == Direction.DOWN) && snakeParts.get(0).y == snakeParts.get(1).y) {
            return;
        }

        if (direction == Direction.UP && this.direction == Direction.DOWN) {
            return;
        } else if (direction == Direction.LEFT && this.direction == Direction.RIGHT) {
            return;
        } else if (direction == Direction.RIGHT && this.direction == Direction.LEFT) {
            return;
        } else if (direction == Direction.DOWN && this.direction == Direction.UP) {
            return;
        }

        this.direction = direction;
    }

    public  boolean checkCollision(GameObject object) { // проверка, не съела ли сама себя
        //GameObject part = new sna

        for (GameObject part : snakeParts) {
            if (object.x == part.x && object.y == part.y) {
                return true;
            }
        }
        return false;

    }

    public int getLength(){// кол-во сигментов
       return snakeParts.size();
    }
}
