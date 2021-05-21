package Lesson1;

public class Wall extends Obstacle{

    private float heigth;

    public Wall(float heigth) {
        this.heigth = heigth;
    }

    @Override
    public float getSize() {
        return this.heigth;
    }
}
