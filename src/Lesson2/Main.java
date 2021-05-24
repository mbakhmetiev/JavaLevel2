package Lesson2;

public class Main {
    public static void main(String[] args) {
        String[][] strArray1 = {{"x", "x", "x", "x"}, {"x", "x", "x", "x"}, {"x", "x", "x"}, {"x", "x", "x", "x"}, {"x"}};
        String[][] strArray2 = {{"1", "2", "3", "4"}, {"1", "2", "x", "x"}, {"x", "x", "x", "x"}, {"x", "x", "x", "x"}};
        String[][] strArray3 = {{"1", "2", "3", "4"}, {"1", "2", "3", "4"}, {"1", "2", "3", "4"}, {"1", "2", "3", "4"}};
        String[][][] strArrays = {strArray3, strArray2, strArray1};
        for (String[][] strArr : strArrays) {
            System.out.println("**************************************");
            try {
                checkArray(strArr);
            } catch (MyArrayDataException e) {
                System.out.println(e.getClass().getSimpleName());
                System.out.println(e.getMessage());
            }
        }
    }

    public static void checkArray(String[][] strArray) throws MyArrayDataException {
        boolean dimension4;
        int sum = 0;
        if (!(strArray.length == 4)) {
            throw new MyArraySizeException();
        } else {
            System.out.println("Размерность внешнего массива равна 4");
            for (int i = 0; i < strArray.length; i++) {
                if (!(strArray[i].length == 4)) {
                    throw new MyArraySizeException();
                } else {
                    System.out.println("Размерность внутреннего массива равна 4");
                    for (int j = 0; j < strArray[i].length; j++) {
                        try {
                            sum += Integer.parseInt(strArray[i][j]);
                        } catch (NumberFormatException e) {
                            throw new MyArrayDataException(new int[]{i,j});
                        }
                    }
                }
            }
            System.out.printf("Сумма элеметов массива равна %d%n", sum);
        }
    }
}