package Lesson2;

public class MyArraySizeException extends ArrayIndexOutOfBoundsException{

    public MyArraySizeException() {
        super("Размерность массива не равна 4");
    }
}
