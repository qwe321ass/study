package com.example.study.practice;

public class st_0723_3 {

    /*
     * 문제: N과 M (순열 + 조건)
     * 
     * 숫자 1, 2, 3, 4, 5가 있습니다.
     * 
     * 이 중에서 3개를 골라 순서 있게 나열하세요.
     * 
     * 단, 서로 이웃한 두 숫자의 합이 5보다 작거나 같아야 합니다.
     * 
     * 조건
     * 1. 중복 사용 금지
     * 2. 숫자 3개를 선택
     * 3. 선택한 순서가 다르면 다른 경우
     * 4. 연속된 두 숫자의 합은 5 이하
     */

    static int[] question = { 1, 2, 3, 4, 5 };
    static int count = 3;
    static boolean[] visited = new boolean[question.length];
    static int arr[] = new int[count];

    public static void main(String[] args) {
        test(0);
    }

    static void test(int depth) {
        // 조건에 맞는 arr 출력
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
            if (depth > 0 && arr[depth - 1] + question[i] > 5) {
                continue;
            } else {
                arr[depth] = question[i];
                visited[i] = true;
                test(depth + 1);
            }
            visited[i] = false;

        }

    }

}
