package com.example.study.practice;

/*
 * ============================================================
 *  최대 매출 구간   [유형: 슬라이딩 윈도우 (Sliding Window)]   (난이도: Medium)
 * ============================================================
 *
 * N일 동안의 일별 매출 기록이 담긴 정수 배열 sales와 연속된 일수 k가 주어집니다.
 * 연속된 k일 동안의 매출 합이 가장 큰 금액을 반환하세요.
 *
 * 단, N의 크기가 매우 클 수 있으므로 O(N) 시간 복잡도로 구현해야 합니다.
 *
 * 예) sales = [10, 20, 30, 20, 5, 40, 30], k = 3
 *     - [10, 20, 30] = 60
 *     - [20, 30, 20] = 70
 *     - [30, 20, 5]  = 55
 *     - [20, 5, 40]  = 65
 *     - [5, 40, 30]  = 75 (최대)
 *     -> 정답: 75
 *
 * 예) sales = [1, 2, 3, 4, 5], k = 2
 *     - [4, 5] = 9 (최대)
 *     -> 정답: 9
 *
 * ------------------------------------------------------------
 * [이 유형의 핵심 개념]
 *
 * 슬라이딩 윈도우 (Sliding Window):
 * 고정된 크기 k의 창문(Window)을 오른쪽으로 한 칸씩 미끄러지듯 이동하면서
 * 구간 내의 값들을 효율적으로 계산하는 기법입니다.
 *
 * 1. 초기 윈도우 계산 (0 ~ k-1 인덱스):
 *    첫번째 k개 원소의 합을 구하여 `sum`과 `maxSales`의 초기값으로 둡니다.
 *
 * 2. 윈도우 이동 (O(1) 갱신):
 *    i = k 부터 sales.length - 1 까지 순회하며:
 *    - 새 원소 추가: `sum += sales[i]`
 *    - 이전 창문의 첫 원소 제거: `sum -= sales[i - k]`
 *    - `maxSales = Math.max(maxSales, sum)` 갱신
 *
 * 3. 시간 복잡도:
 *    매 구간마다 2중 루프로 k개를 다시 더하지 않기 때문에 O(N * K)가 아닌 O(N) 만에 해결 가능합니다!
 *
 * ------------------------------------------------------------
 * [자주 하는 실수]
 * - 매 구간마다 k번씩 다시 sum을 구하여 O(N * K)로 시간 초과가 발생하는 경우
 * - 배열의 길이(N)가 k보다 작은 예외 상황 처리 누락
 * - i - k 인덱스 차감 시 Off-by-one 에러 (인덱스 범위 착오)
 */
public class st_0807_m3_slidingwindow {

    public static int maxSales(int[] sales, int k) {
        // TODO: 직접 구현해 보세요!
        // 힌트:
        // 1. if (sales == null || sales.length < k) return 0;
        // 2. 0부터 k-1까지 초기 sum 계산
        // 3. max = sum 초기화
        // 4. for (int i = k; i < sales.length; i++) {
        //        sum += sales[i] - sales[i - k];
        //        max = Math.max(max, sum);
        //    }
        // 5. return max;

        return 0;
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    public static void main(String[] args) {
        System.out.println("===== [슬라이딩 윈도우] 최대 매출 구간 =====");

        check(new int[]{10, 20, 30, 20, 5, 40, 30}, 3, 75);
        check(new int[]{1, 2, 3, 4, 5}, 2, 9);
        check(new int[]{12, 15, 11, 20, 25, 10, 20}, 4, 71);
    }

    static void check(int[] sales, int k, int expected) {
        int actual = maxSales(sales, k);
        if (actual == expected) {
            System.out.println("[PASS] 결과: " + actual);
        } else {
            System.out.println("[FAIL] 예상값: " + expected + ", 실제값: " + actual);
        }
    }
}
