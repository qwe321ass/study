package com.example.study.practice;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import javax.management.Query;

/*
 * ============================================================
 *  [카카오 2021 인턴십 기출] 거리두기 확인하기
 *  유형: BFS / 2차원 격자 탐색 및 조건 구현
 *  난이도: 프로그래머스 Lv 2 ~ 3 (카카오 인턴 대표 문제)
 * ============================================================
 *
 * [문제 설명]
 * 대기실은 5x5 크기의 격자 형태입니다.
 * 대기실 표시는 다음과 같습니다:
 *   - 'P': 응시자가 앉아있는 자리
 *   - 'O': 빈 테이블
 *   - 'X': 파티션(벽)
 *
 * 거리두기 수칙:
 * 1. 응시자(P) 간의 맨해튼 거리가 2 이하로 앉으면 안 됩니다.
 *    (맨해튼 거리 = |r1 - r2| + |c1 - c2|)
 * 2. 단, 응시자 사이에 파티션('X')으로 막혀 있는 경우는 예외로 허용됩니다.
 *
 * 5개의 대기실(places) 정보가 주어질 때, 각 대기실별로 거리두기를 잘 지키고 있으면 1,
 * 한 명이라도 위반했으면 0을 담아 크기 5의 배열로 반환하세요.
 *
 * 예) 대기실 1개 모습:
 *     P O O O P
 *     O X X O X
 *     O P X P X
 *     O O X O X
 *     P O O O P
 *
 * ------------------------------------------------------------
 * [이 유형의 핵심 개념]
 *
 * 대기실의 크기가 5x5로 매우 작습니다!
 * 대기실의 모든 'P' (응시자 위치)에서 시작하여 **거리(distance) 2 이하**인 곳만 BFS 탐색을 진행하면 됩니다.
 *
 * 1. 'P'를 발견할 때마다 해당 위치에서 BFS를 시작합니다.
 * 2. BFS 탐색 중:
 *    - 자기 자신(거리 0)은 넘어가고,
 *    - 거리 1 또는 2 이내에서 다른 'P'를 만나면 ➔ 거리두기 위반! (false 반환)
 *    - 파티션('X')을 만나면 ➔ 더 이상 그 방향으로 전진할 필요 없음 (큐에 안 넣음)
 *    - 빈 테이블('O')을 만나면 ➔ 계속 탐색 진행 (거리 + 1 해서 큐에 넣음)
 *    - 거리가 2에 도달하면 ➔ 더 먼 거리는 탐색할 필요 없음 (큐에 안 넣음)
 *
 * ------------------------------------------------------------
 * [힌트]
 *
 * int[] answer = new int[places.length];
 * for (int i = 0; i < places.length; i++) {
 *     answer[i] = checkRoom(places[i]) ? 1 : 0;
 * }
 * return answer;
 *
 * // 하나의 대기실(5x5)을 검사하는 메서드
 * boolean checkRoom(String[] room) {
 *     for (int r = 0; r < 5; r++) {
 *         for (int c = 0; c < 5; c++) {
 *             if (room[r].charAt(c) == 'P') {
 *                 if (!bfs(room, r, c)) return false; // 한 곳이라도 위반하면 false
 *             }
 *         }
 *     }
 *     return true;
 * }
 *
 * ------------------------------------------------------------
 * [자주 하는 실수]
 *
 * - 파티션('X') 너머에 있는 'P'도 맨해튼 거리만 계산해서 위반이라고 판단하는 실수
 * - 각 'P'에서 BFS를 시작할 때 매번 visited 배열을 새로 초기화해 줘야 함
 */
public class st_0803_kakao2_dist {

    public static int[] solution(String[][] places) {
        int[] answer = new int[places.length];
        for (int i = 0; i < places.length; i++) {
            answer[i] = checkRoom(places[i]) ? 1 : 0;
        }
        return answer;
    }

    static boolean checkRoom(String[] room) {
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                if (room[r].charAt(c) == 'P') {
                    if (!bfs(room, r, c))
                        return false;
                }
            }
        }
        return true;
    }

    static boolean bfs(String[] room, int startR, int startC) {
        boolean[][] visited = new boolean[5][5];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[] { startR, startC, 0 });
        visited[startR][startC] = true;

        int[] dr = { -1, 1, 0, 0 };
        int[] dc = { 0, 0, -1, 1 };

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];
            int dist = current[2];

            if (dist >= 2)
                continue;

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nr >= 0 && nr < 5 && nc >= 0 && nc < 5 && !visited[nr][nc]) {
                    if (room[nr].charAt(nc) == 'X')
                        continue;
                    if (room[nr].charAt(nc) == 'P')
                        return false;

                    visited[nr][nc] = true;
                    queue.offer(new int[] { nr, nc, dist + 1 });
                }
            }
        }
        return true;
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    public static void main(String[] args) {
        System.out.println("===== [카카오 2021 인턴십] 거리두기 확인하기 =====");

        String[][] places = {
                { "POOOP", "OXXOX", "OPXPX", "OOXOX", "POXXP" },
                { "POOPX", "OXPXP", "PXXXO", "OXXXO", "OOOPP" },
                { "PXOPX", "OXOXP", "OXPOX", "OXXOP", "PXPOX" },
                { "OOOXX", "XOOOX", "OOOXX", "OXOOX", "OOOOO" },
                { "PXPXP", "XPXPX", "PXPXP", "XPXPX", "PXPXP" }
        };

        check(places, new int[] { 1, 0, 1, 1, 1 });
    }

    static void check(String[][] places, int[] expected) {
        int[] actual = solution(places);
        if (Arrays.equals(actual, expected)) {
            System.out.println("[PASS] 결과: " + Arrays.toString(actual));
        } else {
            System.out.println("[FAIL] 예상값: " + Arrays.toString(expected) + ", 실제값: " + Arrays.toString(actual));
        }
    }
}
