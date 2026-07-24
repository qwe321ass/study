package com.example.study.practice;

import java.util.Arrays;
import java.util.HashMap;

public class st_0724_3 {

    /*
     * 문제: 같은 상품은 최대 2개까지만
     *
     * 창고에 상품이 일렬로 쌓여 있습니다.
     *
     * String[] items = { "A", "A", "B", "A", "B", "B", "C" };
     *
     * 이 중 연속된 한 구간을 통째로 꺼내려고 합니다.
     * 단, 꺼낸 구간 안에 같은 상품이 3개 이상 들어 있으면 안 됩니다.
     * (= 어떤 상품이든 구간 안에서 최대 2개까지만 등장해야 합니다)
     *
     * 조건을 만족하는 가장 긴 구간의 길이를 구하세요.
     *
     * 예시
     * A A B A B B C
     * -> 1 ~ 4 구간(A B A B)은 A 2개, B 2개라서 가능. 길이 4
     * -> 3 ~ 6 구간(A B B C)도 가능. 길이 4
     * -> 2 ~ 5 구간(B A B B)은 B가 3개라서 불가능
     * 결과 4
     *
     * 함수
     * public static int solution(String[] items)
     *
     * 제한사항
     * 0 <= items.length <= 100,000
     * items[i] 는 영문 대문자로만 이루어져 있습니다.
     * 조건을 만족하는 구간이 없으면 0 을 반환합니다. (빈 배열인 경우뿐입니다)
     *
     * ---
     * 힌트
     *
     * st_0724_2 와 뼈대는 완전히 같습니다. 딱 한 군데만 다릅니다.
     *
     * st_0724_2 : 조건 위반 = 종류가 너무 많다 -> while (basket.size() > 2)
     * st_0724_3 : 조건 위반 = 하나가 너무 많다 -> while (???)
     *
     * "지금 막 넣은 상품의 개수"를 보면 됩니다.
     * 방금 오른쪽에서 넣은 상품 때문에 위반이 생긴 것이므로,
     * 다른 상품의 개수는 확인할 필요가 없습니다.
     */
    public static void main(String[] args) {
        String[][] cases = {
                { "A", "A", "B", "A", "B", "B", "C" },
                { "A", "A", "A" },
                { "A", "B", "C", "D" },
                { "A", "A", "B", "B", "A", "A" },
                { "A" },
                { "A", "B", "A", "B", "A", "B" },
                {},
        };
        int[] expected = { 4, 2, 4, 4, 1, 4, 0 };

        for (int i = 0; i < cases.length; i++) {
            int got = solution(cases[i]);
            System.out.println((got == expected[i] ? "PASS" : "FAIL")
                    + " " + Arrays.toString(cases[i])
                    + " -> " + got + " (정답 " + expected[i] + ")");
        }
    }
    // st_0724_2 와 뼈대는 동일하고, while 조건만 다르다.
    // st_0724_2 : 종류가 많은 게 위반 -> basket.size() > 2
    // st_0724_3 : 하나가 많은 게 위반 -> basket.get(in) > 2
    public static int solution(String[] items) {
        int left = 0; // 윈도우 시작 (포함)
        int max = 0;
        HashMap<String, Integer> basket = new HashMap<>();
        for (int right = 0; right < items.length; right++) {
            String in = items[right];
            if (basket.containsKey(in)) {
                basket.put(in, basket.get(in) + 1);
                basket.getOrDefault(basket.get(in), 0);
            } else {
                basket.put(in, 1);
            }

            // 방금 넣은 in 때문에 3개가 되었을 때만 위반이 생긴다.
            // 다른 상품은 직전까지 2개 이하였고 이번에 늘지 않았으므로 확인할 필요가 없다.
            // in 이 윈도우 안에 3개 있으니 left 를 당기다 보면 반드시 in 을 만나 개수가 줄어든다 -> 무한 루프 없음
            while (basket.get(in) > 2) {
                String out = items[left];
                basket.put(out, basket.get(out) - 1);
                // 개수가 0이 되면 map 에서 지워 크기를 불필요하게 키우지 않는다
                if (basket.get(out) == 0) {
                    basket.remove(out);
                }
                left++;
            }
            max = Math.max(max, right - left + 1);
        }
        return max;
    }
}