package com.example.study.practice;

import java.util.LinkedList;
import java.util.Queue;

public class st_0723_6 {

    /*
     * 문제: 미로에서 최단 거리 찾기
     * 
     * 다음과 같은 미로가 있습니다.
     * 
     * S 1 1 0 1
     * 0 0 1 0 1
     * 1 1 1 0 1
     * 1 0 0 0 1
     * 1 1 1 1 T
     * S : 시작점
     * T : 도착점
     * 1 : 이동 가능
     * 0 : 이동 불가능
     * 
     * 상하좌우로만 이동할 수 있습니다.
     * 
     * 목표
     * 
     * S에서 T까지 가는 최단 이동 횟수를 구하세요.
     * 
     * 예상 결과: 8
     */
    public static void main(String[] args) {
        String[] maze = {
                "S1101",
                "00101",
                "11101",
                "10001",
                "1111T"
        };

        int[] startPoint = findStart(maze);
        int shortestDistance = findShortestDistance(maze, startPoint[0], startPoint[1]);
        System.out.println("최단 이동 횟수: " + shortestDistance);
    }

    static int[] findStart(String[] maze) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length(); j++) {
                if (maze[i].charAt(j) == 'S') {
                    return new int[] { i, j };
                }
            }
        }
        return null;
    }

    static int findShortestDistance(String[] maze, int startRow, int startCol) {
        if (maze == null || maze.length == 0) {
            return -1; // 미로가 비어있거나 null인 경우
        }
        // 미로 크기
        int rows = maze.length;
        int cols = maze[0].length();

        // 이동 방향 (상, 하, 좌, 우)
        int[][] distions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] { startRow, startCol });
        visited[startRow][startCol] = true;
        int distance = 0;

        int nowRows = 0;
        int nowCols = 0;
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            nowRows = current[0];
            nowCols = current[1];

            if (maze[nowRows].charAt(nowCols) == 'T') {
                return distance;
            }
            for (int[] direction : distions) {
                // 다음 위치 계산
                int newRow = nowRows + direction[0];
                int newCol = nowCols + direction[1];

                // 기본적으로 미로를 벗어나지 않는지 확인하고, 이동 가능한 위치인지 확인
                if(newRow>=0 && newRow<rows && newCol>=0 && newCol<cols ){
                    if(!visited[newRow][newCol] && (maze[newRow].charAt(newCol) == '1' || maze[newRow].charAt(newCol) == 'T')) {
                        // 이동완료
                        queue.add(new int[] { newRow, newCol });
                        visited[newRow][newCol] = true;
                        distance++;
                    }

                }
            }

        }

        return -1; // 도착점에 도달할 수 없는 경우
    }

}