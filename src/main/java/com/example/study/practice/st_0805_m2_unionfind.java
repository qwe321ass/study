package com.example.study.practice;

/*
 * ============================================================
 *  섬 연결하기   [유형: 유니온 파인드 / 크루스칼 (Kruskal)]   (난이도: Medium)
 * ============================================================
 *
 * n개의 섬 사이에 다리를 건설하는 비용 costs가 주어질 때,
 * 모든 섬이 서로 통행 가능하도록 만들기 위한 "최소 다리 건설 비용"을 구하세요.
 *
 * costs = [[from, to, cost], ...]
 *
 * 예) n = 4, costs = [[0,1,1],[0,2,2],[1,2,5],[1,3,1],[2,3,8]]
 *     - 간선 정렬: (0-1: 1), (1-3: 1), (0-2: 2), (1-2: 5), (2-3: 8)
 *     - (0-1:1) 연결, (1-3:1) 연결, (0-2:2) 연결 -> 모든 섬 연결 완료!
 *     -> 정답: 4 (1 + 1 + 2)
 *
 * ------------------------------------------------------------
 * [이 유형의 핵심 개념]
 *
 * 최소 신장 트리 (MST, Minimum Spanning Tree) & Kruskal 알고리즘:
 * 모든 노드를 최소 비용으로 연결하는 그리디 알고리즘입니다.
 *
 * 1. 간선 비용 오름차순 정렬:
 *    `Arrays.sort(costs, (a, b) -> Integer.compare(a[2], b[2]));`
 *
 * 2. 유니온 파인드 (Union-Find / Disjoint Set):
 *    - `find(x)`: 노드 x가 속한 집합의 대표(루트 노드)를 찾습니다. (경로 압축 Path Compression)
 *    - `union(a, b)`: 노드 a와 b가 속한 집합을 하나로 합칩니다.
 *
 * 3. 사이클(Cycle) 형성 방지:
 *    - 간선을 가중치가 작은 순서대로 확인하며, 두 노드의 루트가 다를 때(`find(u) != find(v)`)만
 *      간선을 채택하고 union 연산을 수행합니다.
 *    - 루트가 같으면 이미 연결되어 사이클이 생기므로 무시합니다.
 *
 * ------------------------------------------------------------
 * [자주 하는 실수]
 * - find 메서드에서 경로 압축(`parent[x] = find(parent[x])`)을 빠뜨려 시간 초과 발생
 * - union 연산 시 parent 배열 갱신 오류
 */
public class st_0805_m2_unionfind {

    public static int minCostConnect(int n, int[][] costs) {
        // TODO: 직접 구현해 보세요!
        // 힌트:
        // 1. costs 배열을 cost 기준 오름차순 정렬 (Arrays.sort)
        // 2. int[] parent = new int[n]; 부모 배열 초기화 (parent[i] = i)
        // 3. find(x)와 union(a, b) 메서드 작성 (경로 압축 find 포함)
        // 4. costs 순회하며 find(u) != find(v)일 때 union(u, v) 하고 totalCost에 비용 누적

        return 0;
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    public static void main(String[] args) {
        System.out.println("===== [Union-Find / 크루스칼] 섬 연결하기 =====");

        check(4, new int[][] {
                { 0, 1, 1 }, { 0, 2, 2 }, { 1, 2, 5 }, { 1, 3, 1 }, { 2, 3, 8 }
        }, 4);

        check(5, new int[][] {
                { 0, 1, 5 }, { 1, 2, 3 }, { 2, 3, 1 }, { 3, 4, 2 }, { 0, 4, 4 }
        }, 10);
    }

    static void check(int n, int[][] costs, int expected) {
        int actual = minCostConnect(n, costs);
        if (actual == expected) {
            System.out.println("[PASS] 결과: " + actual);
        } else {
            System.out.println("[FAIL] 예상값: " + expected + ", 실제값: " + actual);
        }
    }
}
