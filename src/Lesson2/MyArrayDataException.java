
package Lesson2;

public class MyArrayDataException extends NumberFormatException {


    public MyArrayDataException(int[] ints) {
        super(String.format("Элемет [%d][%d] не является числом", ints[0], ints[1]));
    }
}
