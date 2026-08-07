package com.example.study.practice;

import java.util.ArrayDeque;
import java.util.Queue;

/*
 * ============================================================
 *  미로 탈출 최단 거리   [유형: BFS]   (난이도: Medium)
 * ============================================================
 *
 * N x M 크기의 미로가 주어집니다.
 * 1은 이동할 수 있는 칸이고, 0은 이동할 수 없는 벽입니다.
 * (0, 0)에서 출발하여 (N-1, M-1) 위치로 이동할 때 지나야 하는 최소 칸 수를 구하세요.
 * (시작 칸과 끝 칸도 카운트에 포함합니다.)
 *
 * 예) N = 4, M = 6
 *     1 0 1 1 1 1
 *     1 0 1 0 1 0
 *     1 0 1 0 1 1
 *     1 1 1 0 1 1
 *     -> 정답: 15
 *
 * ------------------------------------------------------------
 * [이 유형의 핵심 개념]
 *
 * "격자에서의 최단 거리" 문제는 BFS(너비 우선 탐색)가 정석입니다.
 * BFS는 출발점에서 거리가 1인 모든 칸 -> 거리가 2인 모든 칸 순서로 동심원처럼 확산되므로,
 * 도착점에 가장 먼저 도달했을 때의 거리가 곧 최단 거리입니다.
 *
 * ------------------------------------------------------------
 * [힌트]
 *
 * 1. 위치와 거리(또는 좌표)를 담는 Queue 사용:
 *    Queue<int[]> queue = new ArrayDeque<>();
 *    queue.offer(new int[]{0, 0, 1}); // {row, col, distance}
 *
 * 2. 방문 처리 (visited 2차원 boolean 배열 또는 maze 원본 값 수정):
 *    visited[0][0] = true;
 *
 * 3. 사방 탐색 배열:
 *    int[] dr = {-1, 1, 0, 0};
 *    int[] dc = {0, 0, -1, 1};
 *
 * 4. 큐에서 하나씩 꺼내어 목표 지점(N-1, M-1)에 도착하면 distance 반환!
 *
 * ------------------------------------------------------------
 * [자주 하는 실수]
 *
 * - DFS를 사용하는 실수:
 *   DFS는 최단 거리를 보장하지 않으므로, 모든 경로를 탐색해 최소값을 찾아야 해서 시간 초과가 날 수 있습니다.
 *
 * - 방문 처리를 큐에서 "꺼낼 때" 하는 실수:
 *   중복된 좌표가 큐에 여러 번 들어가서 메모리 초과나 시간 초과가 발생합니다.
 *   반드시 큐에 "넣는 순간(offer)" 방문 처리 하세요!
 */
public class st_0803_m3_bfs {

    public static int minDistance(int[][] maze) {
        Queue<int[]> mazeQueue = new ArrayDeque<>();
        // 출발 지점 | 도착 지점 | 거리(시작점 포함)
        mazeQueue.offer(new int[] { 0, 0, 1 });

        // 방문 처리
        maze[0][0] = 0;

        // 행 좌표 변화량
        int[] dr = { -1, 1, 0, 0 };
        // 열 좌표 변화량
        int[] dc = { 0, 0, -1, 1 };

        // 큐에서 하나씩 꺼내어 목표 지점(N-1, M-1)에 도착하면 distance 반환!
        while (!mazeQueue.isEmpty()) {
            int[] current = mazeQueue.poll();
            int r = current[0];
            int c = current[1];
            int distance = current[2];

            // 목표 지점 도착
            if (r == maze.length - 1 && c == maze[0].length - 1) {
                return distance;
            }

            // 사방 탐색
            for (int i = 0; i < 4; i++) {
                // 이동후 좌표
                int nr = r + dr[i];
                int nc = c + dc[i];

                // 범위 체크
                if (nr >= 0 && nr < maze.length && nc >= 0 && nc < maze[0].length && maze[nr][nc] == 1) {
                    mazeQueue.offer(new int[] { nr, nc, distance + 1 });
                    maze[nr][nc] = 0;
                }
            }
        }
        return 0;
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    public static void main(String[] args) {
        System.out.println("===== minDistance (BFS Maze) =====");

        check(new int[][] {
                { 1, 0, 1, 1, 1, 1 },
                { 1, 0, 1, 0, 1, 0 },
                { 1, 0, 1, 0, 1, 1 },
                { 1, 1, 1, 0, 1, 1 }
        }, 15);

        check(new int[][] {
                { 1, 1, 1 },
                { 0, 1, 1 },
                { 0, 0, 1 }
        }, 5);

        check(new int[][] {
                { 1, 1 },
                { 1, 1 }
        }, 3);
    }

    static void check(int[][] maze, int expected) {
        int[][] copy = new int[maze.length][];
        for (int i = 0; i < maze.length; i++) {
            copy[i] = maze[i].clone();
        }

        int actual = minDistance(copy);
        if (actual == expected) {
            System.out.println("[PASS] 결과: " + actual);
        } else {
            System.out.println("[FAIL] 예상값: " + expected + ", 실제값: " + actual);
        }
    }
}
