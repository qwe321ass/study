package com.example.study.practice;

import java.util.PriorityQueue;

/*
 * ============================================================
 *  더 맵게   [유형: 우선순위 큐 / Heap]   (난이도: Medium)
 * ============================================================
 *
 * 모든 음식의 스코빌 지수를 K 이상으로 만들고 싶습니다.
 * 스코빌 지수가 가장 낮은 두 개의 음식을 아래와 같이 특별한 방법으로 섞어 새로운 음식을 만듭니다.
 *
 *   섞은 음식의 스코빌 지수 = 가장 맵지 않은 음식 + (두 번째로 맵지 않은 음식 * 2)
 *
 * 모든 음식의 스코빌 지수가 K 이상이 될 때까지 섞는 최소 횟수를 구하세요.
 * 만약 모든 음식의 스코빌 지수를 K 이상으로 만들 수 없는 경우 -1을 반환합니다.
 *
 * 예) scoville = [1, 2, 3, 9, 10, 12], K = 7
 *     1회 섞기: 1 + (2 * 2) = 5  -> [3, 5, 9, 10, 12]
 *     2회 섞기: 3 + (5 * 2) = 13 -> [9, 10, 12, 13] (모두 7 이상 완료!)
 *     -> 정답: 2
 *
 * ------------------------------------------------------------
 * [이 유형의 핵심 개념]
 *
 * 최소 힙 (Min-Heap / PriorityQueue):
 * - 매 순간 "가장 작은 값 2개"를 빠르게 추출(poll)해야 합니다.
 * - 일반 리스트로 정렬하면 O(N^2 log N)으로 시간 초과가 나지만,
 *   자바의 `PriorityQueue`를 쓰면 O(N log N) 만에 해결 가능합니다.
 *
 * ------------------------------------------------------------
 * [힌트]
 *
 * PriorityQueue<Integer> pq = new PriorityQueue<>();
 * for (int s : scoville) pq.offer(s);
 *
 * int count = 0;
 * while (pq.peek() < K) {
 *     if (pq.size() < 2) return -1; // 2개를 꺼낼 수 없는데 최솟값이 K 미만이면 불가능!
 *     int first = pq.poll();
 *     int second = pq.poll();
 *     pq.offer(first + second * 2);
 *     count++;
 * }
 */
public class st_0804_m2_heap {

    public static int scoville(int[] scoville, int K) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int s : scoville) {
            pq.offer(s);
        }

        int count = 0;

        while (pq.peek() < K) {
            if (pq.size() < 2) {
                return -1; // 2개를 꺼낼 수 없는데 최솟값이 K보다 작으면 불가능
            }

            int first = pq.poll();
            int second = pq.poll();
            int mixed = first + (second * 2);

            pq.offer(mixed);
            count++;
        }

        return count;
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    public static void main(String[] args) {
        System.out.println("===== [Heap / 우선순위 큐] 더 맵게 =====");
        check(new int[] { 1, 2, 3, 9, 10, 12 }, 7, 2);
        check(new int[] { 0, 0, 0, 0 }, 1, -1);
        check(new int[] { 10, 12, 15 }, 5, 0);
    }

    static void check(int[] scoville, int K, int expected) {
        int actual = scoville(scoville, K);
        if (actual == expected) {
            System.out.println("[PASS] 결과: " + actual);
        } else {
            System.out.println("[FAIL] 예상값: " + expected + ", 실제값: " + actual);
        }
    }
}
