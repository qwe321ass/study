package com.example.study.practice;

import java.util.HashMap;
import java.util.Map;

public class st_0724_2 {

    /*
     * 문제: 두 종류의 과일만 담기
     * 
     * 과일이 일렬로 놓여 있습니다.
     * 
     * String[] fruits = {
     * "APPLE", "BANANA", "APPLE", "ORANGE", "ORANGE", "BANANA", "BANANA"
     * };
     * 
     * 바구니에는 최대 2종류의 과일만 담을 수 있습니다.
     * 
     * 연속된 구간에서 과일을 담을 때,
     * 2종류 이하의 과일만 포함하면서 가장 많은 과일을 담을 수 있는 개수를 구하세요.
     * 
     * 예시
     * APPLE BANANA APPLE ORANGE ORANGE BANANA BANANA
     * 결과 4
     */
    public static void main(String[] args) {
        String[] fruits = {
                "APPLE", "BANANA", "APPLE", "ORANGE", "ORANGE", "BANANA", "BANANA"
        };
        int result = solution(fruits);

        System.out.print(result);
    }

    /*
     * 풀이: 투 포인터(슬라이딩 윈도우)
     *
     * st_0724_1 과 같은 기법이지만 방향이 반대다.
     * - st_0724_1 : 조건을 "만족"하면 while 로 줄이면서, 줄이기 직전에 답을 기록 (최소 구간)
     * - st_0724_2 : 조건을 "위반"하면 while 로 줄여서 되돌리고, 복구된 뒤에 답을 기록 (최대 구간)
     *
     * left, right 가 각각 최대 n번만 움직이므로 시간복잡도는 O(n).
     */
    public static int solution(String[] fruits) {
        // 윈도우 안에 각 과일이 몇 개 들어있는지 세는 용도.
        // basket.size() 가 곧 "바구니에 담긴 과일 종류 수" 가 된다.
        Map<String, Integer> basket = new HashMap<>();

        int left = 0; // 윈도우 시작 (포함)
        int max = 0;

        for (int right = 0; right < fruits.length; right++) {
            // 오른쪽 과일을 바구니에 담기
            String in = fruits[right];
            if (basket.containsKey(in)) {
                basket.put(in, basket.get(in) + 1);
            } else {
                basket.put(in, 1);
            }

            // 3종류가 되어버리면 2종류로 돌아올 때까지 왼쪽부터 빼낸다
            while (basket.size() > 2) {
                String out = fruits[left];
                basket.put(out, basket.get(out) - 1);
                // 개수가 0이 되면 그 종류가 바구니에서 완전히 빠진 것이므로 key 자체를 지운다.
                // (지우지 않으면 basket.size() 가 줄지 않아 while 이 끝나지 않는다)
                if (basket.get(out) == 0) {
                    basket.remove(out);
                }
                left++;
            }

            // 여기 도달하면 윈도우는 항상 2종류 이하 -> 지금 길이를 후보로 비교
            // 인덱스가 left ~ right 로 양쪽 다 포함이라 길이는 right - left + 1
            max = Math.max(max, right - left + 1);
        }

        return max;
    }
}