package com.example.study.practice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * ============================================================
 *  [카카오 2020 인턴십 기출] 보석 쇼핑
 *  유형: 투 포인터(Two Pointers) / 슬라이딩 윈도우 + HashMap
 *  난이도: 프로그래머스 Lv 3 (카카오 인턴/공채 대표 중상 문제)
 * ============================================================
 *
 * [문제 설명]
 * 진열대에 나열된 보석들의 이름이 순서대로 담긴 배열 gems가 주어집니다.
 * "모든 종류의 보석을 적어도 1개 이상 포함하는 가장 짧은 연속 구간"을 찾아서
 * [시작 진열대 번호, 끝 진열대 번호]를 반환하세요. (진열대 번호는 1번부터 시작합니다.)
 *
 * 만약 가장 짧은 구간이 여러 개라면, 시작 진열대 번호가 더 작은 구간을 선택합니다.
 *
 * 예) gems = ["DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"]
 *     - 보석 전체 종류: {DIA, RUBY, EMERALD, SAPPHIRE} (총 4종류)
 *     - 모든 종류를 포함하는 가장 짧은 구간: [3, 7]
 *       (3번 "RUBY"부터 7번 "SAPPHIRE"까지 5개 보석에 4종류가 모두 들어있음)
 *
 * ------------------------------------------------------------
 * [이 유형의 핵심 개념]
 *
 * 1. 데이터 크기 확인:
 *    gems 배열의 길이가 최대 100,000입니다.
 *    이중 for문(O(N^2))으로 모든 구간을 검사하면 시간 초과(Time Limit Exceeded)가 발생합니다.
 *    따라서 선형 시간 O(N)으로 해결할 수 있는 "투 포인터(Two Pointers)" 기법을 써야 합니다.
 *
 * 2. 투 포인터 윈도우 전략 (left, right):
 *    - HashSet으로 전체 보석 종류의 개수(totalTypes)를 먼저 구합니다.
 *    - right 포인터를 오른쪽으로 한 칸씩 이동하며 보석을 HashMap에 추가하고 카운트를 증가시킵니다.
 *    - 현재 윈도우에 모든 종류의 보석이 들어왔다면(map.size() == totalTypes):
 *        a. 현재 구간의 길이(right - left)가 최소 길이보다 짧으면 정답 구간을 갱신합니다.
 *        b. left 포인터를 오른쪽으로 땡기면서(left 보석 카운트 줄이기) 구간을 줄여봅니다.
 *        c. 보석 종류가 모자라질 때까지 left를 밀어붙입니다!
 *
 * ------------------------------------------------------------
 * [힌트]
 *
 * Map<String, Integer> map = new HashMap<>();
 * Set<String> totalTypes = new HashSet<>(Arrays.asList(gems));
 * int totalCount = totalTypes.size();
 *
 * int left = 0;
 * int minLen = Integer.MAX_VALUE;
 * int[] answer = new int[2];
 *
 * for (int right = 0; right < gems.length; right++) {
 *     map.put(gems[right], map.getOrDefault(gems[right], 0) + 1);
 *
 *     while (map.size() == totalCount) {
 *         // 1. 최소 길이 갱신 체크
 *         if (right - left < minLen) {
 *             minLen = right - left;
 *             answer[0] = left + 1;  // 1-based index
 *             answer[1] = right + 1;
 *         }
 *         // 2. left 보석 제거하고 left++
 *         map.put(gems[left], map.get(gems[left]) - 1);
 *         if (map.get(gems[left]) == 0) {
 *             map.remove(gems[left]);
 *         }
 *         left++;
 *     }
 * }
 * return answer;
 *
 * ------------------------------------------------------------
 * [자주 하는 실수]
 *
 * - 1-based index 인 것을 놓치고 0-based index 반환
 * - map에서 보석 개수가 0이 되었을 때 `map.remove(key)`를 해주지 않아서 `map.size()`가 안 줄어드는 실수
 */
public class st_0803_kakao1_gems {
    public static int[] solution(String[] gems) {
        Set<String> setGame = new HashSet<>(Arrays.asList(gems));
        int[] answer = new int[2];
        int left = 0;
        int minLen = Integer.MAX_VALUE;
        Map<String, Integer> map = new HashMap<>();
        for (int right = 0; right < gems.length; right++) {
            map.put(gems[right], map.getOrDefault(gems[right], 0) + 1);
            while (map.size() == setGame.size()) {
                if (right - left < minLen) {
                    minLen = right - left;
                    answer[0] = left + 1;
                    answer[1] = right + 1;
                }
                map.put(gems[left], map.get(gems[left]) - 1);
                if (map.get(gems[left]) == 0) {
                    map.remove(gems[left]);
                }
                left++;
            }
        }
        return answer;
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    public static void main(String[] args) {
        System.out.println("===== [카카오 2020 인턴십] 보석 쇼핑 =====");

        check(new String[] { "DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA" },
                new int[] { 3, 7 });

        check(new String[] { "AA", "AB", "AC", "AA", "AC" },
                new int[] { 1, 3 });

        check(new String[] { "XYZ", "XYZ", "XYZ" },
                new int[] { 1, 1 });

        check(new String[] { "ZZZ", "YYY", "NNNN", "YYY", "BBB" },
                new int[] { 1, 5 });
    }

    static void check(String[] gems, int[] expected) {
        int[] actual = solution(gems);
        if (Arrays.equals(actual, expected)) {
            System.out.println("[PASS] 결과: " + Arrays.toString(actual));
        } else {
            System.out.println("[FAIL] 예상값: " + Arrays.toString(expected) + ", 실제값: " + Arrays.toString(actual));
        }
    }
}
