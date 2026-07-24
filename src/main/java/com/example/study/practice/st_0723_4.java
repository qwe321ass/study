package com.example.study.practice;

public class st_0723_4 {

    /*
     * 문제: 부분합 만들기
     * 
     * 숫자 배열이 있습니다.
     * 
     * int[] numbers = {1, 2, 3, 4, 5};
     * 
     * 이 숫자들 중에서 중복 없이 숫자를 선택해서 합이 7이 되는 모든 경우를 출력하세요.
     * 
     * 조건
     * 숫자는 한 번만 사용할 수 있습니다.
     * 숫자를 선택하는 순서는 상관없습니다.
     * 선택한 숫자의 합이 7이 되면 출력합니다.
     * 합이 7보다 커지면 더 이상 탐색하지 않습니다.
     * 아무 숫자도 선택하지 않은 경우는 제외합니다.
     */

    static int[] numbers = { 1, 4, 3, 2, 5 };
    // static int count = 3;
    static boolean[] visited = new boolean[numbers.length];
    static int arr[] = new int[numbers.length];
    static int targetSum = 7;

    public static void main(String[] args) {
        test(0, 0);
    }

    // depth : 현재까지 선택한 숫자의 개수
    // start : 다음 선택할 숫자의 시작 인덱스
    static void test(int depth, int start) {

        int sum = 0;
        for (int i = 0; i < depth; i++) {
            sum += arr[i];
        }
        if (sum == targetSum) {
            for (int j = 0; j < depth; j++) {
                System.out.print(arr[j] + " ");
            }
            System.out.println();
            return;
        }
        if (sum > targetSum) {
            return;
        }

        for(int i = start; i < numbers.length; i++) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            arr[depth] = numbers[i];
            test(depth + 1, i + 1);
            visited[i] = false;
        }

    }
}