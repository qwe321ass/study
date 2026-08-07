package com.example.study.practice;

import java.util.Arrays;

/*
 * ============================================================
 *  줄 세우기   [유형: 위상 정렬 (Topological Sort)]   (난이도: Medium ~ Hard)
 * ============================================================
 *
 * 1번부터 N번까지 N명의 학생이 있습니다.
 * 몇몇 학생들의 키를 비교한 결과(A번 학생이 B번 학생보다 먼저 서야 함)가
 * 2차원 배열 prerequisites = [[A, B], ...] 로 주어집니다.
 *
 * 학생들을 순서대로 줄을 세운 결과를 배열에 담아 반환하세요.
 * (답이 여러 가지인 경우 임의의 올바른 순서 하나를 반환하면 됩니다.)
 *
 * 예) N = 3, prerequisites = [[1, 3], [2, 3]]
 *     - 1번 ➔ 3번, 2번 ➔ 3번 (1번과 2번이 3번보다 먼저 서야 함)
 *     - 가능한 줄 세우기: [1, 2, 3] 또는 [2, 1, 3]
 *
 * ------------------------------------------------------------
 * [이 유형의 핵심 개념]
 *
 * 위상 정렬 (Topological Sort):
 * 순서가 정해져 있는 작업(방향 그래프)에서 선후 관계를 위배하지 않도록 전체 노드를 정렬하는 알고리즘입니다.
 * (DAG: Directed Acyclic Graph 에서만 가능)
 *
 * 1. 진입 차수 (Indegree):
 *    - 특정 노드로 들어오는 간선의 개수입니다.
 *    - `indegree[B]++` (A ➔ B 간선이 있을 때)
 *
 * 2. 큐(Queue)를 이용한 알고리즘 절차:
 *    a. 진입 차수가 0인 모든 노드를 큐에 넣습니다. (선행 조건이 없는 작업부터 시작)
 *    b. 큐가 빌 때까지 다음을 반복합니다:
 *       - 큐에서 노드 u를 꺼내 결과 순서에 추가합니다.
 *       - u와 연결된 모든 노드 v의 진입 차수를 1 감소시킵니다 (`indegree[v]--`).
 *       - 감소 결과 `indegree[v] == 0` 이 되면 노드 v를 큐에 넣습니다.
 *
 * ------------------------------------------------------------
 * [자주 하는 실수]
 * - 진입 차수 감소 시 `indegree[v]--` 후 0 체크 시점 오류
 * - 1-indexed (1번~N번 노드)와 0-indexed 번호 혼용 오류
 */
public class st_0805_m3_topological {

    public static int[] lineUp(int n, int[][] prerequisites) {
        // TODO: 직접 구현해 보세요!
        // 힌트:
        // 1. List<List<Integer>> graph 와 int[] indegree 배열 생성
        // 2. prerequisites 정보를 순회하며 graph에 간선 추가 및 indegree[v]++
        // 3. Queue<Integer> queue 생성 후 indegree가 0인 노드 모두 offer
        // 4. queue가 빌 때까지 poll하며 결과 배열에 넣고, 이웃 노드의 indegree 1 차감 후 0되면 queue에 offer

        return new int[n];
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    public static void main(String[] args) {
        System.out.println("===== [위상 정렬] 줄 세우기 =====");

        check(3, new int[][] { { 1, 3 }, { 2, 3 } });
        check(4, new int[][] { { 1, 2 }, { 3, 1 }, { 4, 2 } });
    }

    static void check(int n, int[][] prerequisites) {
        int[] actual = lineUp(n, prerequisites);
        if (isValidTopologicalSort(n, prerequisites, actual)) {
            System.out.println("[PASS] 결과: " + Arrays.toString(actual));
        } else {
            System.out.println("[FAIL] 잘못된 순서: " + Arrays.toString(actual));
        }
    }

    private static boolean isValidTopologicalSort(int n, int[][] prerequisites, int[] result) {
        if (result.length != n) return false;
        int[] pos = new int[n + 1];
        for (int i = 0; i < n; i++) {
            pos[result[i]] = i;
        }

        for (int[] p : prerequisites) {
            int u = p[0];
            int v = p[1];
            if (pos[u] >= pos[v]) {
                return false;
            }
        }
        return true;
    }
}
