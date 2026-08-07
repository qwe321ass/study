package com.example.study.practice;

/*
 * ============================================================
 *  N-Queen   [유형: 백트래킹 (Backtracking / DFS)]   (난이도: Medium ~ Hard)
 * ============================================================
 *
 * N x N 크기의 체스판 위에 N개의 퀸을 서로 공격할 수 없게 놓는 방법의 수를 구하세요.
 * (퀸은 가로, 세로, 대각선 방향으로 칸 수에 제한 없이 이동할 수 있습니다.)
 *
 * 예) N = 4 ➔ 정답: 2
 * 예) N = 8 ➔ 정답: 92
 *
 * ------------------------------------------------------------
 * [이 유형의 핵심 개념]
 *
 * 백트래킹 (가지치기 / Pruning):
 * 모든 유효한 경우의 수를 탐색하되, 조건에 맞지 않는 경로는 더 이상 깊이 탐색하지 않고
 * 즉시 되돌아오는(Backtrack) 최적화 기법입니다.
 *
 * 1. 상태 표현 (1차원 배열):
 *    `int[] cols = new int[N];`
 *    `cols[row] = col;` (row번째 행의 퀸 위치는 col번째 열)
 *    -> 1차원 배열만으로 N개 행의 퀸 위치를 완벽하게 표현할 수 있습니다!
 *
 * 2. 공격 유효성 검사 (isPromising):
 *    새로운 행 `row`에 퀸을 놓을 때, 이전에 놓았던 0 ~ `row-1` 행의 퀸들과 비교합니다.
 *    - 같은 열(column)에 있는지: `cols[i] == cols[row]`
 *    - 같은 대각선(diagonal)에 있는지: `Math.abs(cols[row] - cols[i]) == row - i`
 *    (행의 차이와 열의 차이가 같으면 같은 대각선 상에 위치함)
 *
 * 3. 기저 조건 (Base Case):
 *    `row == N` 이 되면 N개의 퀸을 모두 무사히 배치 완료한 것이므로 방법의 수 `count++` !
 *
 * ------------------------------------------------------------
 * [자주 하는 실수]
 * - 2차원 배열(N x N) 전체를 매번 복사/생성하여 메모리와 시간을 낭비하는 경우
 * - 대각선 조건 판별 시 `Math.abs()` 절대값을 빠뜨리는 경우
 */
public class st_0805_m1_backtracking {

    public static int nQueen(int n) {
        // TODO: 직접 구현해 보세요!
        // 힌트:
        // 1. int[] cols = new int[n]; (cols[row] = col 의미)
        // 2. dfs(row, n) 재귀함수를 통해 0행부터 n-1행까지 퀸 배치 탐색
        // 3. isPromising(row) 메서드로 이전에 놓은 퀸들과 (같은 열 or 같은 대각선) 여부 검사 후 가지치기!
        // 4. row == n 도달 시 count++ 반환

        return 0;
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    public static void main(String[] args) {
        System.out.println("===== [백트래킹] N-Queen =====");

        check(4, 2);
        check(8, 92);
        check(1, 1);
    }

    static void check(int n, int expected) {
        int actual = nQueen(n);
        if (actual == expected) {
            System.out.println("[PASS] 결과: " + actual);
        } else {
            System.out.println("[FAIL] 예상값: " + expected + ", 실제값: " + actual);
        }
    }
}
