package Lesson1;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        IJumpRun[] men = {new Man("Джон"), new Man("Пол", 2900, 0.3f), new Man("Джордж", 4321, 3.1f)};
        IJumpRun[] robots = {new Robot("C-3PO"), new Robot("R2-D2", 17000, 2.7f), new Robot("R5-D4", 23000, 3.7f)};
        IJumpRun[] cats = {new Cat("Цезарь"), new Cat("Александр", 200, 0.1f), new Cat("Ганнибал", 312, .47f)};

        Wall[] walls = {new Wall(0.5f), new Wall(1.7f), new Wall(3f)};
        Track[] tracks = {new Track(300f), new Track(2800f), new Track(12000f)};

        IJumpRun[][] participants = {men, robots, cats};
        Obstacle[][] obstacles = {tracks, walls};

        System.out.println("***************************************");

        for (IJumpRun[] x : participants) {
            for (int i = 0; i < x.length; i++) {
                x[i].run();
                x[i].jump();
                for (int j = 0; j < obstacles[0].length ; j++) {
                    x[i].runTask(obstacles[0][j].getSize());
                    x[i].jumpTask(obstacles[1][j].getSize());
                }
                System.out.println("***************************************");
            }
        }
    }
}

