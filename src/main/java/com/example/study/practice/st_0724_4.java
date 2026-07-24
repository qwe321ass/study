package com.example.study.practice;

import java.util.Arrays;

public class st_0724_4 {

    /*
     * 문제: 목표 무게를 넘기는 가장 짧은 구간
     *
     * 컨베이어 벨트에 상자가 일렬로 놓여 있고, 각 상자의 무게가 주어집니다.
     *
     * int[] weights = { 2, 3, 1, 2, 4, 3 };
     * int target = 7;
     *
     * 연속된 구간을 통째로 집어서 무게 합이 target 이상이 되게 하려고 합니다.
     * 조건을 만족하는 가장 짧은 구간의 길이를 구하세요.
     *
     * 예시
     * 2 3 1 2 4 3 / target = 7
     * -> 2 3 1 2 (합 8) 가능. 길이 4
     * -> 3 1 2 4 (합 10) 가능. 길이 4
     * -> 4 3 (합 7) 가능. 길이 2 <- 가장 짧음
     * 결과 2
     *
     * 함수
     * public static int solution(int[] weights, int target)
     *
     * 제한사항
     * 0 <= weights.length <= 100,000
     * 1 <= weights[i] <= 1,000 (모두 양수입니다)
     * 1 <= target <= 1,000,000,000
     * 조건을 만족하는 구간이 하나도 없으면 0 을 반환합니다.
     *
     * ---
     * 힌트
     *
     * 1. 지금까지는 윈도우 상태를 Map 으로 들고 있었지만, 이번엔 합(sum) 하나면 충분합니다.
     * right 를 넣을 때 sum += , left 를 뺄 때 sum -= 해주면 됩니다.
     *
     * 2. 방향은 st_0724_2, st_0724_3 이 아니라 st_0724_1 쪽입니다.
     * "최대"를 구할 때 : 위반하면 while 로 복구하고, while 밖에서 기록
     * "최소"를 구할 때 : 만족하면 while 로 줄이면서, 줄이기 직전에 기록 <- 이번엔 이쪽
     *
     * 3. 답이 없을 때 0 을 반환해야 하므로 minLength 초기값 처리에 주의하세요.
     *
     * ---
     * 생각해볼 것 (풀고 나서)
     *
     * 제한사항에 "모두 양수"가 왜 붙어 있을까요?
     * weights 에 음수가 섞이면 이 풀이가 왜 깨질까요?
     */
    public static void main(String[] args) {
        int[][] arrs = {
                { 2, 3, 1, 2, 4, 3 },
                { 1, 4, 4 },
                { 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 2, 3, 4, 5 },
                { 10 },
                { 1, 2, 3, 4, 5 },
                {},
                { 5, 1, 3, 5, 10, 7, 4, 9, 2, 8 },
                { 2, 3, 1, 2, 4, 3 },
        };
        int[] targets = { 7, 4, 11, 15, 10, 16, 5, 15, 1 };
        int[] expected = { 2, 1, 0, 5, 1, 0, 0, 2, 1 };

        for (int i = 0; i < arrs.length; i++) {
            int got = solution(arrs[i], targets[i]);
            System.out.println((got == expected[i] ? "PASS" : "FAIL")
                    + " " + Arrays.toString(arrs[i]) + " target=" + targets[i]
                    + " -> " + got + " (정답 " + expected[i] + ")");
        }
    }

    public static int solution(int[] weights, int target) {
        // 이 문제의 윈도우 상태는 합 하나면 충분하다. (개수 Map 불필요)
        int left = 0; // 윈도우 시작 (포함)
        int minLength = Integer.MAX_VALUE;
        int sum = 0;
        for (int right = 0; right < weights.length; right++) {
            sum += weights[right];

            // "최소" 구간이므로 조건을 만족하면 줄여본다.
            // 줄이기 '직전'에 현재 길이를 기록해야, 아직 target 이상인 구간의 길이가 반영된다.
            while (sum >= target) {
                minLength = Math.min(minLength, right - left + 1);
                sum -= weights[left];
                left++; // <- 이걸 빼먹으면 같은 값만 계속 빼서 left 가 안 움직인다
            }
        }
        // 한 번도 target 을 못 넘겼으면 초기값 그대로 -> 0 반환
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
}
