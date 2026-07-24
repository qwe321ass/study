package com.example.study.practice;

/**
 * st_0723_1
 */

/*
 * 1, 2, 3 세 숫자가 있습니다.
 * 
 * 이 세 숫자를 중복 없이 한 번씩 사용해서 만들 수 있는 모든 순서를 출력하세요.
 */
public class st_0723_1 {

    static int[] num = {1, 2, 3,4,5,6,7};
    static int[] arr = new int[num.length];
    static boolean[] visited = new boolean[num.length];

    //depth : 현재까지 선택한 숫자의 개수 
    public static void main(String[] args) {
        backtracking(0);
    }

    static void backtracking(int depth) {

      //  System.out.println("depth: " + depth);
        // 모든 자리를 채웠다면 출력
        if (depth == num.length) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i]);
            }

            System.out.println();
            return;
        }

        // 어떤 숫자를 넣을지 선택
        for (int i = 0; i < num.length; i++) {
            // 이미 사용한 숫자라면 넘어감
            if (visited[i]) {
                continue;
            }

            // 현재 자리에 숫자 넣기
            arr[depth] = num[i];

            // 숫자 사용 처리
            visited[i] = true;

            // 다음 자리 채우러 가기
            backtracking(depth + 1);

            visited[i] = false;
        }
    }
}