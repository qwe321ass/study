import java.util.*;

public class st_0727_1 {

    /*
     * 문제: 가장 긴 증가 부분 수열(LIS)
     *
     * 정수 배열 nums가 주어졌을 때,
     * 증가하는 부분 수열의 길이 중 가장 긴 값을 구하세요.
     *
     * 조건:
     * - 부분 수열은 원래 순서를 유지해야 합니다.
     * - 예시: nums = [10, 9, 2, 5, 3, 7, 101, 18]
     * -> 정답: 4
     *
     * 힌트:
     * - DP(동적 계획법)로 해결할 수 있습니다.
     */

    public static void main(String[] args) {
        int[] nums = { 10, 9, 2, 5, 3, 7, 101, 18 };
        int result = longestIncreasingSubsequence(nums);
        System.out.println("가장 긴 증가 부분 수열 길이: " + result);
    }

    static int longestIncreasingSubsequence(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] dp = new int[nums.length];
        int result = 0;

        for (int i = 0; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            result = Math.max(result, dp[i]);
        }

        return result;
    }

}