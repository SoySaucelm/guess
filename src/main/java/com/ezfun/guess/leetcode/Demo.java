package com.ezfun.guess.leetcode;

import java.util.Arrays;

/**
 * @author SoySauce
 * @date 2020/6/1
 */
public class Demo {

   /* 1109. 航班预订统计
    这里有 n 个航班，它们分别从 1 到 n 进行编号。

    我们这儿有一份航班预订表，表中第 i 条预订记录 bookings[i] = [i, j, k] 意味着我们在从 i 到 j 的每个航班上预订了 k 个座位。

    请你返回一个长度为 n 的数组 answer，按航班编号顺序返回每个航班上预订的座位数。
    示例：
    输入：bookings = [[1,2,10],[2,3,20],[2,5,25]], n = 5
    输出：[10,55,45,25,25]
    提示：
            1 <= bookings.length <= 20000
            1 <= bookings[i][0] <= bookings[i][1] <= n <= 20000
            1 <= bookings[i][2] <= 10000

     1      2
     10             -10
            2       3
            20                -20
            2       3         4         5
            25

    10      55      45        25        25
            */


    static class Solution {
        static int[] corpFlightBookings(int[][] bookings, int n) {
            int[] result = new int[n];
            for (int[] booking : bookings) {
                for (int j = booking[0]; j <= booking[1]; j++) {
                    result[j - 1] += booking[2];
                }
            }
            return result;
        }

        public static void main(String[] args) {
            int[][] req = {{1, 2, 10}, {2, 3, 20}, {2, 5, 25}};
            int[] res = corpFlightBookings(req, 5);
            System.out.println(Arrays.toString(res));
        }
    }
}
