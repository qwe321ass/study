package com.example.study.practice;

import java.util.*;

public class st_0728_1 {

    /*
     * 문제: 최소 윈도우 부분 문자열 (난이도: 상)
     *
     * 문자열 s와 문자열 t가 주어졌을 때,
     * t의 모든 문자를 (중복 개수까지 포함해서) 포함하는
     * s의 부분 문자열 중 가장 짧은 것을 구하세요.
     *
     * 조건:
     * - 부분 문자열은 s에서 연속된 구간이어야 합니다.
     * - t의 문자가 중복되면 그 개수만큼 다 들어있어야 합니다.
     * 예) t = "AABC" 라면 윈도우 안에 A가 최소 2개 있어야 함
     * - 순서는 상관 없습니다.
     * - 답이 없으면 빈 문자열("")을 반환하세요.
     * - 답이 여러 개면 아무거나 하나 (길이만 같으면 됨)
     *
     * 예시 1:
     * s = "ADOBECODEBANC", t = "ABC"
     * -> 정답: "BANC"
     * ("ADOBEC"도 A,B,C를 다 포함하지만 길이가 6이라 더 김)
     *
     * 예시 2:
     * s = "a", t = "aa"
     * -> 정답: "" (a가 2개 필요한데 1개뿐)
     *
     * 제한:
     * - s.length() 최대 100,000
     * - 즉, O(N^2) 이중 반복문은 시간 초과입니다. O(N)으로 푸세요.
     *
     * 힌트:
     * 1. 지난번 슬라이딩 윈도우는 크기가 고정이었지만,
     * 이번엔 윈도우 크기가 늘었다 줄었다 합니다. (가변 슬라이딩 윈도우)
     * 2. right를 오른쪽으로 밀면서 윈도우를 넓히다가,
     * "조건을 만족하는 순간" left를 당겨서 최대한 줄입니다.
     * 3. 매번 윈도우 전체를 검사해서 "조건 만족?"을 확인하면 O(N^2)입니다.
     * need(필요 개수) 맵과, "몇 종류의 문자가 이미 충족됐는지" 세는
     * 카운터 하나를 들고 다니면 O(1)로 판정할 수 있습니다.
     *
     * 함정:
     * - 이미 충분히 들어있는 문자가 더 들어와도 "충족 종류 수"가
     * 중복으로 늘어나면 안 됩니다.
     */

    public static void main(String[] args) {
        check("ADOBECODEBANC", "ABC", "BANC");
        check("a", "a", "a");
        check("a", "aa", "");
        check("aa", "aa", "aa");
        check("ab", "b", "b");
        check("cabwefgewcwaefgcf", "cae", "cwae");
        check("", "A", "");
    }

    static String minWindow(String s, String t) {
        // 빈 문자열 방어. "".split("") 은 길이 0이 아니라 [""] (길이 1)이라 꼭 막아줘야 함
        if (s.isEmpty() || t.isEmpty() || s.length() < t.length()) {
            return "";
        }

        String[] questionPoint = s.split("");
        String[] searchPoint = t.split("");

        // 1) 찾아야 하는 문자와 "몇 개" 필요한지
        // (나중에 익숙해지면 needMap.merge(ch, 1, Integer::sum) 한 줄로 줄일 수 있음)
        Map<String, Integer> needMap = new HashMap<>();
        for (String ch : searchPoint) {
            if (needMap.containsKey(ch)) {
                needMap.put(ch, needMap.get(ch) + 1);
            } else {
                needMap.put(ch, 1);
            }
        }

        // 2) 지금 구간(startPoint ~ endPoint)에 들어있는 문자와 개수
        Map<String, Integer> haveMap = new HashMap<>();

        int requiredKind = needMap.size(); // 채워야 하는 문자 종류 수
        int filledKind = 0; // 필요 개수까지 다 채운 종류 수

        int startPoint = 0;
        int bestStart = -1; // 0은 유효한 시작점이라 sentinel로 -1을 씀
        int bestLength = Integer.MAX_VALUE;

        for (int endPoint = 0; endPoint < questionPoint.length; endPoint++) {
            // ── 오른쪽으로 한 칸 넓히기 ──
            String inChar = questionPoint[endPoint];

            // haveMap에서 inChar 개수 +1
            if (haveMap.containsKey(inChar)) {
                haveMap.put(inChar, haveMap.get(inChar) + 1);
            } else {
                haveMap.put(inChar, 1);
            }

            // 필요한 문자이고, 개수가 "정확히" 딱 맞아떨어진 순간에만 카운트
            if (needMap.containsKey(inChar)) {
                int haveCount = haveMap.get(inChar);
                int needCount = needMap.get(inChar);
                if (haveCount == needCount) {
                    filledKind++;
                }
            }

            // ── 조건을 만족하는 동안 왼쪽을 최대한 당기기 ──
            while (filledKind == requiredKind) {
                int nowLength = endPoint - startPoint + 1;
                if (nowLength < bestLength) {
                    bestLength = nowLength;
                    bestStart = startPoint;
                }

                String outChar = questionPoint[startPoint];
                startPoint++;

                // haveMap에서 outChar 개수 -1
                // (윈도우에 들어올 때 이미 넣어둔 문자라 containsKey 검사는 필요 없음)
                haveMap.put(outChar, haveMap.get(outChar) - 1);

                // 빠져서 부족해졌으면 종류 하나가 깨진 것
                if (needMap.containsKey(outChar)) {
                    int haveCount = haveMap.get(outChar);
                    int needCount = needMap.get(outChar);
                    if (haveCount < needCount) {
                        filledKind--;
                    }
                }
            }
        }

        if (bestStart < 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        for (int i = bestStart; i < bestStart + bestLength; i++) {
            result.append(questionPoint[i]);
        }
        return result.toString();
    }

    /*
     * ── 내가 처음 시도한 방향 (보관용) ──
     *
     * static String minWindow(String s, String t) {
     * String[] questionPoint = s.split("");
     * String[] searchPoint = t.split("");
     * int startPoint = 0;
     * int endPoint = 0;
     *
     * String result = "";
     * Map<Integer, String> map = new HashMap<>();
     * for (int i = 0; i < questionPoint.length; i++) {
     * for (int j = 0; j < searchPoint.length; j++) {
     * // 사용 가능한 배열을 찾은 시작점
     * if (questionPoint[i] == searchPoint[j]) { // (1) == 비교
     * if (startPoint == 0) { // (2) 0을 sentinel로 사용
     * map.put(i, questionPoint[i]);
     * startPoint = i;
     * } else if (map.size() == searchPoint.length) { // (3) 종류 vs 길이
     * for (int j2 = 0; j2 < map.size(); j2++) {
     * result += questionPoint[j2]; // (4) 인덱스가 0부터
     * }
     * return result;
     * }
     * } else {
     * }
     * break;
     * }
     * }
     * return "";
     * }
     */

    // ---- 아래는 채점용, 건드리지 않아도 됩니다 ----
    static void check(String s, String t, String expected) {
        String actual = minWindow(s, t);
        boolean ok = expected.length() == actual.length()
                && (expected.isEmpty() || containsAll(actual, t));
        System.out.printf("%s s=\"%s\", t=\"%s\" -> 기대:\"%s\" 결과:\"%s\"%n",
                ok ? "[통과]" : "[실패]", s, t, expected, actual);
    }

    static boolean containsAll(String window, String t) {
        Map<Character, Integer> cnt = new HashMap<>();
        for (char c : window.toCharArray()) {
            if (cnt.containsKey(c)) {
                cnt.put(c, cnt.get(c) + 1);
            } else {
                cnt.put(c, 1);
            }
        }
        for (char c : t.toCharArray()) {
            int have = 0;
            if (cnt.containsKey(c)) {
                have = cnt.get(c);
            }
            if (have == 0) {
                return false; // 더 이상 쓸 수 있는 문자가 없음
            }
            cnt.put(c, have - 1); // 하나 사용 처리
        }
        return true;
    }
}
