package com.example.study.practice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * ============================================================
 *  [카카오 2021 블라인드] 메뉴 리뉴얼
 *  유형: 조합 (Combination / DFS) / Map & Sorting
 *  난이도: 프로그래머스 Lv 2
 * ============================================================
 *
 * [문제 설명]
 * 각 손님이 주문한 단품메뉴 조합들이 문자열 배열 orders로 주어지고,
 * 새로 추가하고 싶은 코스요리의 단품메뉴 개수들이 배열 course로 주어집니다.
 *
 * 최소 2명 이상의 손님으로부터 주문된 조합 중, 각 코스요리 메뉴 개수별로
 * 가장 많이 주문된 단품메뉴 조합을 구하여 오름차순 정렬 후 반환하세요.
 *
 * 예) orders = ["ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH"], course = [2, 3, 4]
 *     - 2개 코스: "AC" (4회 주문으로 최고)
 *     - 3개 코스: "CDE" (3회 주문으로 최고)
 *     - 4개 코스: "ACDE" (2회), "BCFG" (2회) (공동 최고)
 *     -> 정답: ["AC", "ACDE", "BCFG", "CDE"] (오름차순 정렬)
 *
 * ------------------------------------------------------------
 * [이 유형의 핵심 개념]
 *
 * 1. 각 주문(order)의 알파벳 정렬:
 *    손님이 "WX", "XW"처럼 순서를 다르게 주문해도 같은 조합으로 취급해야 합니다.
 *    따라서 탐색 전 `char[] arr = order.toCharArray(); Arrays.sort(arr);` 정렬이 필수입니다.
 *
 * 2. DFS를 이용한 조합(Combination) 생성:
 *    각 order 문자열에서 원하는 코스 길이 len을 갖는 모든 메뉴 조합을 만들어
 *    `Map<String, Integer>`에 등장 횟수를 누적 카운트합니다.
 *
 * 3. 길이별 최댓값(Max Count) 추출:
 *    course의 각 길이 len마다 Map에서 가장 높은 주문 횟수(최소 2회 이상)를 찾은 뒤,
 *    해당 최댓값을 기록한 모든 조합을 최종 결과에 담습니다.
 *
 * ------------------------------------------------------------
 * [자주 하는 실수]
 * - 단품메뉴 알파벳 정렬 누락 ("BA"와 "AB"가 서로 다른 조합으로 인식됨)
 * - 최소 2명 이상 주문 조건(count >= 2) 누락
 * - 공동 1위 메뉴가 여러 개일 때 1개만 선택하는 실수 (모두 포함시켜야 함)
 */
public class st_0805_kakao2_menu {

    public static String[] solution(String[] orders, int[] course) {
        // TODO: 직접 구현해 보세요!
        // 힌트:
        // 1. course의 각 길이 len에 대해 반복
        // 2. orders의 각 주문을 char 배열로 바꿔 알파벳 정렬 후, DFS 조합 생성하여 Map에 누적
        // 3. 해당 길이에서 가장 많이 주문된 횟수(maxCount >= 2)를 찾아서 그 조합들을 리스트에 추가
        // 4. 최종 결과 리스트를 Collections.sort() 후 String[]으로 반환
        String[] result = new String[0];

        for (String order : orders) {
            char[] arr = order.toCharArray();
            Arrays.sort(arr);
            // 경우의 수로 만들어서 넣기
            for (int len : course) {
                dfs(arr, len, 0, new StringBuilder());

            }
        }

        return result;
    }

    static void dfs(char[] arr, int len, int depth, StringBuilder sb) {
        Map<String, Integer> map = new HashMap<>();
        if (sb.length() == len) {
            map.put(sb.toString(), map.getOrDefault(sb.toString(), 0) + 1);
            return;
        }
        for (int i = depth; i < arr.length; i++) {
            sb.append(arr[i]);
            dfs(arr, len, i + 1, sb);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    public static void main(String[] args) {
        System.out.println("===== [카카오 2021 블라인드] 메뉴 리뉴얼 =====");

        check(new String[] { "ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH" },
                new int[] { 2, 3, 4 },
                new String[] { "AC", "ACDE", "BCFG", "CDE" });

        check(new String[] { "ABCDE", "AB", "CD", "ADE", "XYZ", "XYZ", "ACD" },
                new int[] { 2, 3, 5 },
                new String[] { "ACD", "AD", "ADE", "CD", "XYZ" });

        check(new String[] { "XYZ", "XWY", "WXA" },
                new int[] { 2, 3, 4 },
                new String[] { "WX", "XY" });
    }

    static void check(String[] orders, int[] course, String[] expected) {
        String[] actual = solution(orders, course);
        if (Arrays.equals(actual, expected)) {
            System.out.println("[PASS] 결과: " + Arrays.toString(actual));
        } else {
            System.out.println("[FAIL] 예상값: " + Arrays.toString(expected) + ", 실제값: " + Arrays.toString(actual));
        }
    }
}
