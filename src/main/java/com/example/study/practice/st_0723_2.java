package com.example.study.practice;

public class st_0723_2 {
    static int[] question = { 1, 2, 3 };
    static int count = 2;
    static boolean[] visited = new boolean[question.length];
    static int arr[] = new int[count];

    public static void main(String[] args) {
        test(0);
    }

    static void test(int depth) {
        // depth가 count와 같아지면, 즉 count개의 숫자를 선택했으면 출력
        if (depth == count) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i]);
            }
            System.out.println();
            return;
        }

        for (int i = 0; i < question.length; i++) {
            if (visited[i]) {
                continue;
            }
            arr[depth] = question[i];
            // 현재 숫자를 사용했다고 표시
            visited[i] = true;
            test(depth + 1);
            // 현재 숫자를 사용하지 않았다고 표시
            visited[i] = false;
        }
        System.out.println();
        return;

    }

}
