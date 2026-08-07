package com.example.study.practice;

import java.util.Arrays;

/*
 * ============================================================
 *  회의실 배정   [유형: 그리디 / 정렬]   (난이도: Easy ~ Medium)
 * ============================================================
 *
 * 1개의 회의실이 있고, 이를 사용하고자 하는 N개의 회의에 대하여 회의실 사용표를 만들려고 합니다.
 * 각 회의 i에 대해 시작 시간과 끝나는 시간이 주어졌을 때,
 * 각 회의가 겹치지 않게 하면서 회의실을 사용할 수 있는 회의의 최대 개수를 구하세요.
 * (단, 회의가 끝나는 것과 동시에 다음 회의가 시작될 수 있으며, 시작 시간과 끝나는 시간이 같을 수도 있습니다.)
 *
 * 예) [[1, 4], [3, 5], [0, 6], [5, 7], [3, 8], [5, 9], [6, 10], [8, 11], [8, 12], [2, 13], [12, 14]]
 *     -> 정답: 4   (선택: [1, 4] -> [5, 7] -> [8, 11] -> [12, 14])
 *
 * ------------------------------------------------------------
 * [이 유형의 핵심 개념]
 *
 * 그리디(Greedy) 알고리즘은 "매 순간 가장 좋아보이는 선택"을 하는 방법입니다.
 * 회의실 배정 문제의 탐욕적 선택 기준(Greedy Choice):
 *   -> "가장 먼저 끝나는 회의를 선택하는 것"
 *
 * 이유: 회의가 일찍 끝날수록 뒤에 더 많은 회의를 시작할 수 있는 시간이 많이 남기 때문입니다.
 *
 * 정렬 기준:
 *   1. 끝나는 시간(End Time) 오름차순
 *   2. 만약 끝나는 시간이 같다면, 시작 시간(Start Time) 오름차순
 *
 * ------------------------------------------------------------
 * [힌트]
 *
 * 1. 2차원 배열 정렬 (Comparator 또는 Lambda)
 *    Arrays.sort(meetings, (a, b) -> {
 *        if (a[1] == b[1]) return Integer.compare(a[0], b[0]);
 *        return Integer.compare(a[1], b[1]);
 *    });
 *
 * 2. 회의 순회
 *    - 이전 회의가 끝난 시간(lastEndTime)을 기록합니다.
 *    - 다음 회의의 시작 시간이 lastEndTime 이상이면 -> 회의 선택! (count++, lastEndTime = 현재 회의 끝 시간)
 *
 * ------------------------------------------------------------
 * [자주 하는 실수]
 *
 * - 끝나는 시간만 같을 때 시작 시간 정렬을 누락하는 경우:
 *   예: (2, 2)와 (1, 2) 회의가 있을 때, (1, 2) -> (2, 2) 순서로 선택되어야 2개 모두 가능하지만
 *   (2, 2)가 먼저 오면 (1, 2)를 선택하지 못해 결과가 달라질 수 있습니다.
 */
public class st_0803_m1_greedy {

    public static int maxMeetings(int[][] meetings) {
        // 끝나는 시간 기준으로 오름차순 정렬
        Arrays.sort(meetings, (a, b) -> {
            // 시작시간 끝나는 시간 같으면 시작시간 기준 오름차순 정렬
            if (a[1] == b[1]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });

        // 회의 시간 순회
        int count = 0;
        int lastEndTime = Integer.MIN_VALUE;
        // 시작 시간이 이전 회의 끝나는 시간보다 크거나 같으면 회의 선택
        for (int[] meeting : meetings) {
            // 회의의 시작시간이 이전 회의의 끝나는 시간보다 크거나 같으면
            // 회의 선택
            // 회의의 끝나는 시간을 lastEndTime으로 갱신
            if (meeting[0] >= lastEndTime) {
                System.out.println(meeting[0] + " " + meeting[1]);

                count++;
                lastEndTime = meeting[1];
            }
        }
        return count;
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    public static void main(String[] args) {
        System.out.println("===== maxMeetings (Greedy / Sort) =====");

        check(new int[][] {
                { 1, 4 }, { 3, 5 }, { 0, 6 }, { 5, 7 }, { 3, 8 },
                { 5, 9 }, { 6, 10 }, { 8, 11 }, { 8, 12 }, { 2, 13 }, { 12, 14 }
        }, 4);

        check(new int[][] {
                { 1, 3 }, { 2, 4 }, { 3, 5 }
        }, 2);

        check(new int[][] {
                { 2, 2 }, { 1, 2 }
        }, 2);

        check(new int[][] {
                { 1, 10 }
        }, 1);

        check(new int[][] {
                { 1, 2 }, { 2, 3 }, { 3, 4 }, { 4, 5 }
        }, 4);
    }

    static void check(int[][] meetings, int expected) {
        int[][] copy = new int[meetings.length][];
        for (int i = 0; i < meetings.length; i++) {
            copy[i] = meetings[i].clone();
        }

        int actual = maxMeetings(copy);
        if (actual == expected) {
            System.out.println("[PASS] 결과: " + actual);
        } else {
            System.out.println("[FAIL] 예상값: " + expected + ", 실제값: " + actual);
        }
    }
}
