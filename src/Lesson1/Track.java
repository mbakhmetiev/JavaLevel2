package Lesson1;

public class Track extends Obstacle {

    private float distance;

    public Track(float distance) {this.distance = distance;}

    @Override
    public float getSize() {
        return this.distance;
    }
}
