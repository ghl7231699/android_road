package com.example.data.array;

/**
 * 二维数组算法
 */
public class DoubleDimensionalArray {
    private static int[][] mtr = new int[][]{
            {1, 2, 14, 16},
            {3, 4, 9, 12},
            {5, 7, 10, 13},
            {6, 8, 11, 15}
    };

    private static int[][] matrix = new int[][]{
            {1, 0, 8},
            {3, 2, 9},
            {5, 6, 7},
    };

    public static void main(String[] args) {
        printSample(mtr);
        getValueFromMatrix(mtr, 4, 4, 5);

        printSample(matrix);
        shortestRoad(matrix, 3, 3);

        shortestRoad(matrix);


        int num = 1234;
        while (num != 0) {
            System.out.print(num % 10);
            num /= 10;

            System.out.println("最终结果为" + num);
        }
    }

    private static void printSample(int[][] matrix) {
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                System.out.print(anInt + "\t");
            }
            System.out.println();
        }
    }

    /**
     * 每行从左到右递增，每列从上到下递增的二维数组，找出是否包含某个值
     * <p>
     * 从二维数组的右上角开始进行比较，如果右上角的值比要找的数大，则删除整列，如果右上角的值比要找的数小，则去除整行
     * <p>
     * 比如说寻找数字 7
     * <p>
     * 第一步：跟mtr[0][3]的值9进行比较，小于该值，则该值所在的整列都要大于要找的数，所以下次寻找的话 可以不用考虑这列
     * <p>
     * 1	2	8	9	                   1    2   8
     * 2	4	9	12	                   2    4   9
     * 4	7	10	13	     ======》      4    7   10
     * 6	8	11	15                    6    8   11
     * <p>
     * 第二步：跟mtr[0,2]的值8相比，同第一步
     * <p>
     * 1	2	8		                   1	2
     * 2	4	9		                   2	4
     * 4	7	10		     ======》      4	7
     * 6	8	11                         6	8
     * <p>
     * 第三步：跟mtr[0,1]的值相比，该值小于7 则排除本行
     * <p>
     * 1	2
     * 2	4			                   2	4
     * 4	7			     ======》      4	7
     * 6	8	                          6	    8
     * <p>
     * 第四步：跟mtr[1,1]的值相比，该值小于7 则排除本行
     * <p>
     * 最后二位范围限制在了如下：
     * <p>
     * 4    7
     * 6    8
     * <p>
     * 继续以上的步骤，找到7
     *
     * @param matrix  二维数组
     * @param rows    行
     * @param columns 列
     */
    private static void getValueFromMatrix(int[][] matrix, int rows, int columns, int num) {
        boolean found = false;
        if (matrix != null && rows > 0 && columns > 0) {
            //确定第一个右上角的坐标
            int row = 0;
            int column = columns - 1;

            while (row < rows && column >= 0) {
                int temp = matrix[row][column];
                if (num == temp) {
                    found = true;
                    System.out.println("该值所在的位置 [" + row + "," + column + "]");
                    break;
                } else if (temp > num) {
                    --column;
                } else {
                    ++row;
                }
            }
        }
        if (!found) {
            System.out.println("没有找到这个值");
        }
    }

    /**
     * 矩阵的最短路径和
     * <p>
     * 动态规划算法
     * <p>
     * 给定一个矩阵m，从左上角开始每次只能向右或者向下走，最后到达右下角的位置，路径上所有的数字累加起来就是路径和，返回所有路径中最小的路径和。
     */
    private static void shortestRoad(int[][] matrix, int rows, int columns) {
        if (matrix != null) {
            int[][] dp = new int[matrix.length][matrix[0].length];
            dp[0][0] = matrix[0][0];
            for (int i = 1; i < matrix.length; i++) {
                dp[i][0] = dp[i - 1][0] + matrix[i][0];
                //第一列只能由上向下
                System.out.println("列------------->" + dp[i][0] + "\t");
            }
            for (int j = 1; j < matrix[0].length; j++) {
                dp[0][j] = dp[0][j - 1] + matrix[0][j];
                //第一行只能由左向右
                System.out.println("行-------------->" + dp[0][j] + "\t");
            }
            for (int i = 1; i < matrix.length; i++) {
                for (int j = 1; j < matrix[0].length; j++) {
                    int a = dp[i - 1][j];
                    int b = dp[i][j - 1];
                    System.out.println("a==\t" + a + "\tb==" + b);
                    dp[i][j] = Math.min(a, b) + matrix[i][j];
                    System.out.print("dp为 " + dp[i][j] + "\t" + "matrix为 " + matrix[i][j] + "\n");
                }
            }

            System.out.println("result is " + dp[matrix.length - 1][matrix[0].length - 1]);
        }
    }

    private static void shortestRoad(int[][] matrix) {
        if (matrix != null) {
            int[] dp = new int[matrix[0].length];
            dp[0] = matrix[0][0];

            for (int i = 1; i < dp.length; i++) {
                dp[i] = dp[i - 1] + matrix[0][i];
            }

            for (int i = 1; i < matrix.length; i++) {
                dp[0] = matrix[i][0] + dp[0];
                print(dp);
                for (int j = 1; j < dp.length; j++) {
                    dp[j] = Math.min(dp[j - 1], dp[j]) + matrix[i][j];
                    System.out.println("dp " + j + "\t" + dp[j]);
                }
            }

            print(dp);
            System.out.println("result is " + dp[dp.length - 1]);
        }
    }

    private static void print(int[] dp) {
        for (int i = 0; i < dp.length; i++) {
            System.out.print(dp[i] + "\t");
        }
    }
}
