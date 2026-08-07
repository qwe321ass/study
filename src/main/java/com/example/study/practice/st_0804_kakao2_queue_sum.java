package com.example.study.practice;

import java.util.ArrayDeque;
import java.util.Queue;

/*
 * ============================================================
 *  [카카오 2022 인턴십] 두 큐 합 같게 만들기
 *  유형: 투 포인터(Two Pointers) / 큐(Queue) / 그리디(Greedy)
 *  난이도: 프로그래머스 Lv 2
 * ============================================================
 *
 * [문제 설명]
 * 길이가 같은 두 개의 큐 queue1, queue2가 주어집니다.
 * 하나의 큐에서 pop하여 다른 큐에 insert하는 작업을 통해, 두 큐의 원소 합을 같게 만들고자 합니다.
 * 이때 필요한 최소 작업 횟수(pop 1회 + insert 1회 = 1회)를 구하세요.
 * 두 큐의 합을 같게 만드는 것이 불가능한 경우 -1을 반환합니다.
 *
 * 예) queue1 = [3, 2, 7, 2], queue2 = [4, 6, 5, 1]
 *     - 합: q1 = 14, q2 = 16 (전체 합 = 30, 목표 합 = 15)
 *     - 1회: q2에서 4 추출 후 q1에 삽입 -> q1=[3,2,7,2,4](합 18), q2=[6,5,1](합 12)
 *     - 2회: q1에서 3 추출 후 q2에 삽입 -> q1=[2,7,2,4](합 15), q2=[6,5,1,3](합 15)
 *     - 정답: 2
 *
 * ------------------------------------------------------------
 * [이 유형의 핵심 개념]
 *
 * 1. 탐욕적 선택 (Greedy Strategy):
 *    - 원소의 합이 더 큰 큐에서 원소를 꺼내(poll) 합이 더 작은 큐로 넣어(offer)줍니다.
 *    - 이 규칙을 반복하면 항상 최소 작업 횟수로 목표에 도달합니다.
 *
 * 2. 오버플로우 방지 (long 타입 필수):
 *    - 큐 원소의 합이 int 범위를 초과할 수 있으므로 반드시 `long` 타입으로 관리합니다.
 *
 * 3. 종료 조건 (무한 루프 방지):
 *    - 두 큐의 원소가 원위치로 다시 돌아오려면 약 4 * N 번의 이동이 필요합니다.
 *    - 따라서 작업 횟수가 `4 * N` (또는 `3 * N`)을 초과할 때까지 합을 맞추지 못하면 -1을 반환합니다.
 *
 * ------------------------------------------------------------
 * [힌트]
 *
 * long sum1 = 0, sum2 = 0;
 * for (int num : queue1) sum1 += num;
 * for (int num : queue2) sum2 += num;
 *
 * long target = (sum1 + sum2) / 2;
 * if ((sum1 + sum2) % 2 != 0) return -1; // 총합이 홀수면 2등분 불가능!
 */
public class st_0804_kakao2_queue_sum {

    public static int solution(int[] queue1, int[] queue2) {
        Queue<Long> q1 = new ArrayDeque<>();
        Queue<Long> q2 = new ArrayDeque<>();

        long sum1 = 0;
        long sum2 = 0;

        for (int val : queue1) {
            q1.offer((long) val);
            sum1 += val;
        }

        for (int val : queue2) {
            q2.offer((long) val);
            sum2 += val;
        }

        if ((sum1 + sum2) % 2 != 0) {
            return -1; // 총합이 홀수면 정확히 절반으로 나눌 수 없음
        }

        int count = 0;
        int maxOps = queue1.length * 4; // 이동 한계선 설정

        while (sum1 != sum2) {
            if (count > maxOps) {
                return -1;
            }

            if (sum1 > sum2) {
                long val = q1.poll();
                sum1 -= val;
                sum2 += val;
                q2.offer(val);
            } else {
                long val = q2.poll();
                sum2 -= val;
                sum1 += val;
                q1.offer(val);
            }
            count++;
        }

        return count;
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    public static void main(String[] args) {
        System.out.println("===== [카카오 2022 인턴십] 두 큐 합 같게 만들기 =====");

        check(new int[] { 3, 2, 7, 2 }, new int[] { 4, 6, 5, 1 }, 2);
        check(new int[] { 1, 2, 1, 2 }, new int[] { 1, 10, 1, 2 }, 7);
        check(new int[] { 1, 1 }, new int[] { 1, 5 }, -1);
    }

    static void check(int[] queue1, int[] queue2, int expected) {
        int actual = solution(queue1, queue2);
        if (actual == expected) {
            System.out.println("[PASS] 결과: " + actual);
        } else {
            System.out.println("[FAIL] 예상값: " + expected + ", 실제값: " + actual);
        }
    }
}
