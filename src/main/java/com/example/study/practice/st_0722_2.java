package com.example.study.practice;

import java.util.LinkedList;
import java.util.Queue;

public class st_0722_2 {
    public static void main(String[] args) {
        int n = 5;
        int m = 6;
        String[] maze = {
                "S11110",
                "010010",
                "011110",
                "000010",
                "11111T"
        };

        int[][] dist = new int[n][m];
        boolean[][] visited = new boolean[n][m];
        Queue<int[]> queue = new LinkedList<>();

        int[] start = findStart(maze, n, m);
        queue.add(start);
        // 시작점 방문처리
        visited[start[0]][start[1]] = true;

        // 상하 좌우 이동 노가다 줄이기
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};


        // BFS 탐색
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int x = cur[0];
            int y = cur[1];
            //현재 위치 기준으로 셋팅 x y 좌표

            if (maze[x].charAt(y) == 'T') {
                System.out.println(dist[x][y]);
                return;
            }

            // 한 위치에서 가능한 이동 방향을 모두 확인 최대 4방향 queue에 넣고 방문처리
            for (int[] d : dirs) {
                // 다음 위치 계산
                int nx = x + d[0];
                int ny = y + d[1];

                // 다음 위치가 미로 내부인지 확인 1) 다음 위치가 미로안인지 n 이 최대 가로 m 이 최대 세로인지 확인
                if (nx >= 0 && nx < n && ny >= 0 && ny < m) {
                    // 다음위치 값 1이면 가능 0 이면 바다여서 불가능
                    char cell = maze[nx].charAt(ny);
                    // 방문하지 않았고, 이동할 수 있는 위치라면 큐에 추가하고 방문처리
                    if (!visited[nx][ny] && (cell == '1' || cell == 'T' || cell == 'S')) {
                        visited[nx][ny] = true;
                        dist[nx][ny] = dist[x][y] + 1;
                        queue.add(new int[]{nx, ny});
                    }
                }
            }
        }

        System.out.println(-1);
    }

    private static int[] findStart(String[] maze, int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (maze[i].charAt(j) == 'S') {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{0, 0};
    }
}