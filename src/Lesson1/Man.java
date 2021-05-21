package Lesson1;

public class Man implements IJumpRun{

    private String name;
    private float maxDistance;
    private float maxHeight;
    private JumpAndRun jar;

    public Man(String name) {
        this.name = name;
        jar = new JumpAndRun(this);
        this.maxDistance = 5000f;
        this.maxHeight = 1.5f;
    }

    public Man (String name, float maxDistance, float maxHeight) {
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
        jar.run(this.name);
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
