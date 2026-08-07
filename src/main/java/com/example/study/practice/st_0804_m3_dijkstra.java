package com.example.study.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/*
 * ============================================================
 *  배달 (최단 경로)   [유형: 다익스트라 (Dijkstra Algorithm)]   (난이도: Medium)
 * ============================================================
 *
 * N개의 마을이 있고 각 마을은 양방향 도로로 연결되어 있습니다.
 * 1번 마을에서 출발하여 음식 배달을 하려고 합니다.
 * 각 도로를 지나가는 데 걸리는 시간(가중치)이 주어질 때,
 * 1번 마을에서 출발하여 "배달 시간이 K 이하"인 마을의 개수를 구하세요.
 *
 * road 정보: [a, b, c] (a번 마을과 b번 마을 사이 c 시간이 걸리는 양방향 도로)
 *
 * 예) N = 5, road = [[1,2,1],[2,3,3],[5,2,2],[1,4,2],[5,3,1],[5,4,2]], K = 3
 *     - 1번에서 각 마을까지의 최단 거리:
 *       1번: 0, 2번: 1, 3번: 4(1->2->3), 4번: 2, 5번: 3(1->2->5)
 *     - K=3 이하인 마을: {1, 2, 4, 5} -> 총 4개
 *     -> 정답: 4
 *
 * ------------------------------------------------------------
 * [이 유형의 핵심 개념]
 *
 * 다익스트라(Dijkstra) 최단 경로 알고리즘:
 * "한 시작점에서 다른 모든 지점까지의 최단 거리"를 구하는 대표적인 그래프 알고리즘입니다. (모든 간선 가중치 >= 0)
 *
 * 1. 그래프 인접 리스트 구축:
 *    List<Node>[] graph = new ArrayList[N + 1];
 *
 * 2. 최단 거리 배열 dist[] 초기화:
 *    Arrays.fill(dist, Integer.MAX_VALUE);
 *    dist[1] = 0; // 시작점 1번 마을 거리는 0
 *
 * 3. 우선순위 큐(PriorityQueue) 활용 탐색:
 *    - (마을 번호, 현재까지 최단거리) 객체를 담는 PQ를 만들어 최단 거리 노드를 먼저 방문합니다.
 *    - `if (current.dist > dist[current.to]) continue;` 노이즈 스킵!
 *    - 인접한 마을을 거쳐가는 거리가 기존 dist[next]보다 짧으면 dist[next]를 갱신하고 PQ에 삽입.
 *
 * 4. 결과 집계:
 *    dist[i] <= K 인 i의 개수를 반환.
 */
public class st_0804_m3_dijkstra {

    static class Node implements Comparable<Node> {
        int to;
        int weight;

        public Node(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    public static int delivery(int N, int[][] road, int K) {
        List<List<Node>> graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] r : road) {
            int u = r[0];
            int v = r[1];
            int w = r[2];
            graph.get(u).add(new Node(v, w));
            graph.get(v).add(new Node(u, w)); // 양방향 도로
        }

        int[] dist = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[1] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(1, 0));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();

            if (curr.weight > dist[curr.to]) {
                continue;
            }

            for (Node next : graph.get(curr.to)) {
                if (dist[next.to] > dist[curr.to] + next.weight) {
                    dist[next.to] = dist[curr.to] + next.weight;
                    pq.offer(new Node(next.to, dist[next.to]));
                }
            }
        }

        int count = 0;
        for (int i = 1; i <= N; i++) {
            if (dist[i] <= K) {
                count++;
            }
        }
        return count;
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    public static void main(String[] args) {
        System.out.println("===== [Dijkstra / 최단 경로] 배달 =====");

        check(5, new int[][] {
                { 1, 2, 1 }, { 2, 3, 3 }, { 5, 2, 2 },
                { 1, 4, 2 }, { 5, 3, 1 }, { 5, 4, 2 }
        }, 3, 4);

        check(6, new int[][] {
                { 1, 2, 1 }, { 1, 3, 2 }, { 2, 3, 2 },
                { 3, 4, 3 }, { 3, 5, 2 }, { 3, 5, 3 }, { 5, 6, 1 }
        }, 4, 4);
    }

    static void check(int N, int[][] road, int K, int expected) {
        int actual = delivery(N, road, K);
        if (actual == expected) {
            System.out.println("[PASS] 결과: " + actual);
        } else {
            System.out.println("[FAIL] 예상값: " + expected + ", 실제값: " + actual);
        }
    }
}
