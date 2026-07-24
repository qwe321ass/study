package com.example.study.practice;

import java.util.LinkedList;
import java.util.Queue;

/**
 
## 🟢 문제 1. 미로 탈출

N × M 크기의 미로가 있습니다.

미로의 각 칸은 다음과 같이 표현됩니다.

* `0` : 이동할 수 있는 칸
* `1` : 벽이라서 이동할 수 없는 칸

당신은 **(0, 0)**에서 출발해서 **(N-1, M-1)**까지 이동해야 합니다.

한 번 이동할 때는 **상, 하, 좌, 우** 중 한 방향으로 인접한 칸으로만 이동할 수 있습니다.

이때 `(0, 0)`에서 `(N-1, M-1)`까지 이동하는 **최소 이동 횟수**를 구하세요.

### 입력

첫째 줄에 `N`, `M`이 주어집니다.

```text
N M
```

`2 ≤ N, M ≤ 100`

다음 N개의 줄에 미로가 주어집니다.

```text
0 0 1 0 0
0 0 1 0 0
0 0 0 0 1
0 1 1 1 1
0 0 0 0 0
```

### 출력

최소 이동 횟수를 출력하세요.

### 예제

입력

```text
5 5
00100
00100
00001
01111
00000
```

출력

```text
9
```


 */
public class st_0722_1 {

    public static void main(String[] args) {

        int result = 0;

        int n = 5;
        int m = 5;

        int goldX = n - 1;
        int goldY = m - 1;

        String[] maze = {
                "00100",
                "00100",
                "00001",
                "01111",
                "00000"
        };

        Queue<int[]> queue = new LinkedList<>();

        // 시작점 셋팅
        queue.add(new int[]{0, 0});

        boolean[][] visited = new boolean[n][m];
        int[][] distance = new int[n][m];
        visited[0][0] = true;
        distance[0][0] = 1;


        while(!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];

            if(x == goldX && y == goldY) {
                result = distance[x][y];
                break;
            }

            if(x > 0 && maze[x - 1].charAt(y) == '0' && !visited[x - 1][y]) {
                // 위로 이동해서 현재 위치 셋팅됨
                queue.add(new int[]{x - 1, y});
                // 이미 방문했는지 체크
                visited[x - 1][y] = true;
                distance[x - 1][y] = distance[x][y] + 1;
            }
            if(x < n - 1 && maze[x + 1].charAt(y) == '0' && !visited[x + 1][y]) {
                queue.add(new int[]{x + 1, y});
                visited[x + 1][y] = true;
                distance[x + 1][y] = distance[x][y] + 1;
            }
            if(y > 0 && maze[x].charAt(y - 1) == '0' && !visited[x][y - 1]) {
                queue.add(new int[]{x, y - 1});
                visited[x][y - 1] = true;
                distance[x][y - 1] = distance[x][y] + 1;
            }
            if(y < m - 1 && maze[x].charAt(y + 1) == '0' && !visited[x][y + 1]) {
                queue.add(new int[]{x, y + 1});
                visited[x][y + 1] = true;
                distance[x][y + 1] = distance[x][y] + 1;
            }
        }
        result = distance[goldX][goldY];
        System.out.println(result);
    }
}