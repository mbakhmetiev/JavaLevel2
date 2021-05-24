package Lesson1;

public class Robot implements IJumpRun{

    private String name;
    private float maxDistance;
    private float maxHeight;
    private JumpAndRun jar;

    public Robot(String name) {
        this.name = name;
        jar = new JumpAndRun(this);
        maxDistance = 15000;
        maxHeight = 2.5f;
    }

    public Robot (String name, int maxDistance, float maxHeight) {
        this.name = name;
        this.maxDistance = maxDistance;
        this.maxHeight = maxHeight;
        jar = new JumpAndRun(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        jar.run( this.name);
    }

    @Override
    public void jump() {
        jar.jump(this.name);
    }

    @Override
    public void runTask(float distance) {
        jar.runTask(this.name, this.maxDistance, distance);
    }

    @Override
    public void jumpTask(float height) {
        jar.jumpTask(this.name, this.maxHeight, height );
    }

}
