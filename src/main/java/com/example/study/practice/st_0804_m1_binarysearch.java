package com.example.study.practice;

import java.util.Arrays;

/*
 * ============================================================
 *  입국심사   [유형: 이분 탐색 / 매개변수 탐색]   (난이도: Medium ~ Hard)
 * ============================================================
 *
 * n명의 사람이 입국심사를 받기 위해 줄을 서서 기다리고 있습니다.
 * 각 심사관이 한 명을 심사하는 데 걸리는 시간이 담긴 배열 times가 주어질 때,
 * 모든 사람이 심사를 받는 데 걸리는 "최소 시간"을 구하세요.
 *
 * 예) n = 6, times = [7, 10]
 *     - 28분 동안 7분 심사관은 4명, 10분 심사관은 2명을 심사하여 총 6명 처리 가능
 *     -> 정답: 28
 *
 * ------------------------------------------------------------
 * [이 유형의 핵심 개념]
 *
 * 매개변수 탐색 (Parametric Search):
 * "최소 시간을 구하라"는 최적화 문제를 "시간 T 안에 n명 이상을 심사할 수 있는가?"라는
 * 결정 문제(Yes/No)로 바꾸어 이분 탐색(Binary Search)을 적용합니다.
 *
 * 1. 탐색 범위 설정 (left, right):
 *    - left = 1 (최소 1분)
 *    - right = (long) 가장 느린 심사관 시간 * n
 *
 * 2. mid 시간 동안 처리할 수 있는 사람 수 계산:
 *    long total = 0;
 *    for (int time : times) {
 *        total += mid / time;
 *    }
 *
 * 3. 범위 축소 조건:
 *    - total >= n : mid 시간 내에 충분히 심사 가능함!
 *                   시간을 더 줄일 수 있는지 확인 (answer = mid, right = mid - 1)
 *    - total < n  : n명을 심사하기에 시간이 부족함!
 *                   시간을 늘려야 함 (left = mid + 1)
 *
 * ------------------------------------------------------------
 * [자주 하는 실수]
 *
 * - 데이터 타입 오버플로우:
 *   n(최대 10억), time(최대 10억)인 경우 right는 최대 10^18에 달하므로
 *   left, right, mid, total 변수는 반드시 `long` 타입을 사용해야 합니다.
 */
public class st_0804_m1_binarysearch {

    public static long minTime(int n, int[] times) {
        Arrays.sort(times);

        long left = 1;
        long right = (long) times[times.length - 1] * n;
        long answer = right;

        while (left <= right) {
            long mid = left + (right - left) / 2;

            long totalPeople = 0;
            for (int time : times) {
                totalPeople += mid / time;
            }

            if (totalPeople >= n) {
                answer = mid;       // n명 이상 심사 가능하므로 답 후보에 저장
                right = mid - 1;    // 더 적은 시간으로도 가능한지 확인
            } else {
                left = mid + 1;     // n명 심사에 시간이 부족하므로 시간 증가
            }
        }

        return answer;
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    public static void main(String[] args) {
        System.out.println("===== [이분 탐색] 입국심사 =====");

        check(6, new int[] { 7, 10 }, 28L);
        check(10, new int[] { 1, 5 }, 9L);
        check(3, new int[] { 1, 2, 3 }, 2L);
    }

    static void check(int n, int[] times, long expected) {
        long actual = minTime(n, times);
        if (actual == expected) {
            System.out.println("[PASS] 결과: " + actual);
        } else {
            System.out.println("[FAIL] 예상값: " + expected + ", 실제값: " + actual);
        }
    }
}
