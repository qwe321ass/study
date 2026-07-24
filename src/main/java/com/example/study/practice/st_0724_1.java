package com.example.study.practice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class st_0724_1 {

    /*
     * ## 문제: 모든 종류의 상품을 포함하는 최소 구간
     * 쇼핑몰에 상품이 일렬로 진열되어 있습니다.
     * 각 상품은 문자열로 주어지며, 전체 상품 종류를 **최소 한 번씩 모두 포함하는 가장 짧은 연속 구간**을 찾아야 합니다.
     * 
     * 가장 짧은 구간이 여러 개라면 **시작 위치가 가장 작은 구간**을 반환하세요.
     * 
     * ### 예시
     * 
     * ```text
     * products = ["A", "B", "A", "C", "B", "D", "C", "A"]
     * ```
     * 
     * 전체 상품 종류는
     * 
     * ```text
     * A, B, C, D
     * ```
     * 
     * 입니다.
     * 
     * 모든 상품을 포함하는 구간 중
     * 
     * ```text
     * ["A", "C", "B", "D"] // 3 ~ 6
     * ["C", "B", "D", "C", "A"] // 4 ~ 8
     * ```
     * 
     * 등이 있지만 가장 짧은 구간은
     * 
     * ```text
     * 3 ~ 6
     * ```
     * 
     * 입니다.
     * 
     * 따라서
     * 
     * ```text
     * [3, 6]
     * ```
     * 
     * 을 반환합니다.
     * 
     * > 위치는 **1부터 시작**합니다.
     * 
     * ---
     * 
     * ## 함수
     * 
     * ```java
     * public int[] solution(String[] products)
     * ```
     * 
     * ### 제한사항
     * 
     * ```text
     * 1 ≤ products.length ≤ 100,000
     * 1 ≤ products[i].length() ≤ 20
     * products[i]는 영문 대문자로만 이루어져 있습니다.
     * ```
     * 
     * ### 테스트
     * 
     * #### 테스트 1
     * 
     * ```java
     * ["A", "B", "A", "C", "B", "D", "C", "A"]
     * ```
     * 
     * 결과:
     * 
     * ```java
     * [3, 6]
     * ```
     * 
     * #### 테스트 2
     * 
     * ```java
     * ["A", "A", "B", "B", "C", "A"]
     * ```
     * 
     * 결과:
     * 
     * ```java
     * [2, 5]
     * ```
     * 
     * #### 테스트 3
     * 
     * ```java
     * ["A", "B", "C"]
     * ```
     * 
     * 결과:
     * 
     * ```java
     * [1, 3]
     * ```
     * 
     * #### 테스트 4
     * 
     * ```java
     * ["A", "B", "A", "B", "C", "B", "A", "C"]
     * ```
     * 
     * 결과:
     * 
     * ```java
     * [3, 5]
     * ```
     */
    public static void main(String[] args) {
        String[] products = { "A", "B", "A", "C", "B", "D", "C", "A" };
        int[] result = solution(products);
        System.out.println("[" + result[0] + ", " + result[1] + "]");
    }

    /*
     * 풀이: 투 포인터(슬라이딩 윈도우)
     *
     * 1. 전체 상품 종류 개수(size)를 먼저 구한다.
     * 2. right 를 한 칸씩 늘리며 윈도우를 넓히다가,
     * 3. 윈도우가 모든 종류를 담으면(map.size() == size) 답 후보로 기록하고
     * left 를 당겨 더 짧게 만들 수 있는지 확인한다.
     *
     * left, right 가 각각 최대 n번만 움직이므로 시간복잡도는 O(n).
     */
    public static int[] solution(String[] products) {
        Set<String> distProducts = new HashSet<>();
        // 일단 중복 되지 않고 필요한 갯수 구하기
        for (int j = 0; j < products.length; j++) {
            distProducts.add(products[j]);
        }
        int size = distProducts.size();

        int left = 0; // 윈도우 시작 (0-based, 포함)
        int right = 0; // 윈도우 끝 (1-based, 즉 지금까지 담은 개수)
        int minLength = Integer.MAX_VALUE;
        int answerLeft = 0;
        int answerRight = 0;

        // 윈도우 안에 각 상품이 몇 개 들어있는지 세는 용도.
        // map.size() 가 곧 "윈도우 안의 상품 종류 수" 가 된다.
        Map<String, Integer> map = new HashMap<>();

        // 이동 수 구하기
        for (String product : products) {
            // 오른쪽으로 한 칸 확장 -> 윈도우는 products[left ... right-1], 길이는 right - left
            right++;
            if (map.containsKey(product)) {
                map.put(product, map.get(product) + 1);
            } else {
                map.put(product, 1);
            }

            // 모든 종류를 다 담은 상태 -> 왼쪽을 최대한 당겨서 구간을 줄여본다
            while (map.size() == size) {
                // 줄이기 전에 현재 구간을 답 후보로 기록.
                // 등호(<=)가 아닌 부등호(<)를 써야 길이가 같을 때 먼저 나온(시작이 더 작은) 구간이 유지된다.
                if (right - left < minLength) {
                    minLength = right - left;
                    answerLeft = left;
                    answerRight = right;
                }

                // 왼쪽 상품 하나를 윈도우 밖으로 밀어낸다
                String leftProduct = products[left];
                map.put(leftProduct, map.get(leftProduct) - 1);
                // 개수가 0이 되면 그 종류가 윈도우에서 완전히 빠진 것이므로 key 자체를 지운다.
                // (지우지 않으면 map.size() 가 줄지 않아 while 이 끝나지 않는다)
                if (map.get(leftProduct) == 0) {
                    map.remove(leftProduct);
                }
                left++;
            }

        }
        // 내부에서는 0-based 로 다뤘으므로 시작 위치만 +1 해서 1-based 로 변환.
        // answerRight 는 이미 1-based 끝 위치라 그대로 사용한다.
        return new int[] { answerLeft + 1, answerRight };
    }
}