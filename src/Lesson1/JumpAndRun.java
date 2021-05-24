package Lesson1;

public class JumpAndRun {
    private Object obj;

    public JumpAndRun(Object obj) {
        this.obj = obj;
    }

    public void run (String name) {
        System.out.println(obj.getClass().getSimpleName() + " по имени " + name + " бежит");
    }

    public void jump (String name) {
        System.out.println(obj.getClass().getSimpleName() + " по имени " + name + " прыгает");
    }

    public void runTask (String name, float maxDistance, float distance) {
        if (maxDistance >= distance) {
            System.out.println(obj.getClass().getSimpleName() + " по имени " + name + " смог пробежать дистанцию " + distance + " м");
        } else System.out.println(obj.getClass().getSimpleName() + " по имени " + name + " не смог пробежать дистанцию " + distance + " м");
    }
    public void jumpTask (String name, float maxHeigth, float height) {
        if (maxHeigth >= height) {
            System.out.println(obj.getClass().getSimpleName() + " по имени " + name + " смог перепрыгнуть барьер  " + height + " м");
        } else System.out.println(obj.getClass().getSimpleName() + " по имени " + name + " не смог перепрыгнуть барьер  " + height + " м");
    }
}
