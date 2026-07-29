package com.example.study.practice;

import java.util.HashMap;
import java.util.Map;

/*
 * ============================================================
 *  st_0728_1 (최소 윈도우 부분 문자열) 로 가는 5단계 계단
 * ============================================================
 *
 *  위에서부터 순서대로 하나씩 풀어주세요.
 *  각 단계는 앞 단계에서 만든 걸 재사용합니다.
 *
 *   STEP 1  문자 개수 세기            (Map 연습)
 *   STEP 2  포함하는지 판정하기        (Map 비교 연습)
 *   STEP 3  무식하게 정답 구하기        (1+2 조합, 느려도 OK)
 *   STEP 4  가변 슬라이딩 윈도우 첫 경험 (Map 없이 숫자로만)
 *   STEP 5  최종 문제                 (3번을 4번 방식으로 빠르게)
 *
 *  실행하면 5단계 전부 채점됩니다.
 *  한 단계 풀고 실행 -> [통과] 확인 -> 다음 단계, 이렇게 가세요!
 */
public class st_0728_1_step {

    public static void main(String[] args) {
        step1();
        step2();
        step3();
        step4();
        step5();
    }

    // =========================================================
    // STEP 1. 문자 개수 세기
    // =========================================================
    /*
     * 문자열 s에 각 문자가 몇 개씩 있는지 Map으로 만들어 반환하세요.
     *
     * 예) "banana" -> {b=1, a=3, n=2}
     * 예) "" -> {} (빈 Map)
     *
     * 힌트:
     * - for (char c : s.toCharArray()) 로 한 글자씩 꺼낼 수 있습니다.
     * - map에 이미 그 문자가 있으면 기존값+1, 없으면 1을 넣습니다.
     * (containsKey / get / put 세 개만 쓰면 됩니다)
     */
    static Map<Character, Integer> countChars(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.merge(s.charAt(i), 1, Integer::sum);
        }
        return map;
    }

    // =========================================================
    // STEP 2. 이 구간이 t를 전부 포함하나?
    // =========================================================
    /*
     * window가 t의 모든 문자를 "개수까지" 포함하면 true를 반환하세요.
     *
     * 예) covers("ADOBEC", "ABC") -> true
     * 예) covers("ANC", "ABC") -> false (B가 없음)
     * 예) covers("AB", "AAB") -> false (A가 2개 필요한데 1개뿐)
     * 예) covers("BAAB", "AAB") -> true (A 2개, B 1개 있음. 남는 B는 상관없음)
     *
     * 힌트:
     * - STEP 1의 countChars를 두 번 써서 windowCount, needCount를 만드세요.
     * - needCount의 모든 키를 돌면서
     * windowCount에 그만큼 있는지 확인하면 됩니다.
     * - Map의 모든 키를 도는 방법: for (char key : needCount.keySet()) { ... }
     */
    static boolean covers(String window, String t) {
        boolean flag = true;
        // 입력받은 값이 몇개씩 있는지 확인
        // 문제 자체가 정해진 배열내부에 원하는 문자열이 존재하는지 그 길이는 이것이기 떄문에
        Map<Character, Integer> windowCount = countChars(window);
        // 필요한 문자열과 각 문자열의 갯수
        Map<Character, Integer> needCount = countChars(t);
        for (char key : needCount.keySet()) {
            if (windowCount.containsKey(key)) {
                if (windowCount.get(key) >= needCount.get(key)) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            } else {
                flag = false;
                break;
            }
        }

        return flag;
    }

    // =========================================================
    // STEP 3. 무식하게 정답 구하기 (브루트포스)
    // =========================================================
    /*
     * s의 모든 구간을 다 만들어보고, covers()가 true인 것 중
     * 가장 짧은 구간을 반환하세요. 없으면 "".
     *
     * 느려도 괜찮습니다. 여기서는 "정답이 뭔지" 감을 잡는 게 목적입니다.
     *
     * 예) minWindowBrute("ADOBECODEBANC", "ABC") -> "BANC"
     *
     * 힌트:
     * - 이중 for문으로 시작(i)과 끝(j)을 다 만들어봅니다.
     * - 구간 잘라내기: s.substring(i, j + 1) <- 끝 인덱스는 +1 주의!
     * - covers(구간, t)가 true이면 길이를 비교해서 더 짧으면 기록.
     * - 기록용 변수: String best = ""; 로 시작하고
     * best가 빈 문자열이거나 지금 게 더 짧으면 갱신.
     */
    static String minWindowBrute(String s, String t) {
        // ★ 여기서는 Map을 만들 필요가 없습니다!
        // 개수 세기/판정은 이미 covers()가 다 해줍니다. 그냥 물어보기만 하세요.

        String best = ""; // 지금까지 찾은 가장 짧은 정답 (아직 없으면 빈 문자열)

        for (int i = 0; i < s.length(); i++) { // i = 구간의 시작 위치
            for (int j = i; j < s.length(); j++) { // j = 구간의 끝 위치 (i부터 오른쪽으로)
                String part = s.substring(i, j + 1); // i번째 ~ j번째 잘라내기
                if (covers(part, t)) {
                    if (best.length() == 0 || best.length() > part.length()) {
                        best = part;
                    }
                }
            }
        }

        return best;
    }

    // =========================================================
    // STEP 4. 가변 슬라이딩 윈도우 첫 경험 (Map 없이!)
    // =========================================================
    /*
     * 양의 정수 배열 nums와 target이 주어집니다.
     * 원소들의 합이 target "이상"이 되는 연속 구간 중
     * 가장 짧은 것의 "길이"를 반환하세요. 없으면 0.
     *
     * 예) nums = [2,3,1,2,4,3], target = 7
     * -> 정답: 2 ([4,3] 의 합이 7)
     * 예) nums = [1,1,1,1,1,1,1,1], target = 11
     * -> 정답: 0 (다 더해도 8이라 불가능)
     *
     * ★ 이게 st_0728_1과 완전히 똑같은 구조입니다.
     * 다만 "조건 만족?" 판정이 Map이 아니라 그냥 (합 >= target) 이라 훨씬 쉬워요.
     * 이 단계에서 뼈대를 손에 익히는 게 목적입니다.
     *
     * 뼈대:
     * int sum = 0, left = 0, best = 0;
     * for (int right = 0; right < nums.length; right++) {
     * sum += nums[right]; // 오른쪽으로 넓히기
     * while (sum >= target) { // 조건 만족하는 동안
     * // 지금 길이(right - left + 1)를 best와 비교해서 기록
     * // sum에서 nums[left]를 빼고 left++ <- 왼쪽 줄이기
     * }
     * }
     * return best;
     *
     * 주의:
     * - best의 초기값을 0으로 두면 "더 짧은 것"을 비교할 수 없습니다.
     * Integer.MAX_VALUE로 시작해서, 끝까지 안 바뀌었으면 0을 반환하세요.
     */
    static int minLenSubArray(int[] nums, int target) {
        int sum = 0;
        int left = 0;
        int best = 0;

        // 시작점 for 문
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            // 시작점 부터 왼쪽으로 하나씩 체우면서 best 결과 확인
            while (sum >= target) {
                // best 값 확인해서 갱신
                best = (best == 0 || best > right - left + 1) ? right - left + 1 : best;
                // 하나씩 줄림!!
                sum -= nums[left];
                left++;
            }
        }
        if (best == 0) {
            return 0;
        }
        return best;
    }

    // =========================================================
    // STEP 5. 최종 문제
    // =========================================================
    /*
     * [문제]
     * 문자열 s와 문자열 t가 주어집니다.
     * t의 모든 문자를 (중복 개수까지) 포함하는 s의 연속된 구간 중에서
     * 길이가 가장 짧은 구간을 문자열로 반환하세요.
     *
     * [입력]
     * s : 검사할 문자열 (예: "ADOBECODEBANC")
     * t : 포함해야 할 문자들 (예: "ABC")
     *
     * [출력]
     * 조건을 만족하는 가장 짧은 구간. 없으면 빈 문자열 ""
     *
     * [예시]
     * s = "ADOBECODEBANC", t = "ABC" -> "BANC"
     * ("ADOBEC"도 A,B,C를 다 포함하지만 길이 6이라 더 김. "BANC"는 4)
     * s = "aabbcc", t = "abc" -> "abbc"
     * s = "a", t = "aa" -> "" (a가 2개 필요한데 1개뿐)
     *
     * [규칙]
     * - 구간은 연속이어야 합니다. (띄어서 고를 수 없음)
     * - 순서는 상관없습니다. t="ABC"인데 구간이 "BANC"여도 OK.
     * - t에 같은 문자가 여러 개면 그 개수만큼 있어야 합니다. t="AAB" -> A 2개 필요.
     * - 남는 문자는 있어도 됩니다. "BANC"에 N이 끼어 있어도 OK.
     *
     * [제한] ★ STEP 3과 다른 점은 이것뿐입니다
     * - s의 길이 최대 100,000
     * - STEP 3처럼 모든 구간을 잘라서 검사하면 구간이 약 50억 개라 시간 초과입니다.
     * - 구간을 한 칸씩 밀면서 O(N)에 풀어야 합니다.
     *
     * ------------------------------------------------------------
     * [힌트]
     *
     * STEP 4와 뼈대가 완전히 같습니다. 딱 한 군데만 다릅니다:
     *
     * STEP 4 STEP 5
     * sum += nums[right] haveMap 에서 그 문자 개수 +1
     * while (sum >= target) while (filledKind == requiredKind)
     * sum -= nums[left] haveMap 에서 그 문자 개수 -1
     *
     * 즉 "조건 만족?" 판정만 숫자 -> Map 으로 바뀝니다.
     * 그 판정을 매번 covers()로 하면 다시 느려지니, 카운터를 들고 다니세요:
     *
     * needMap : t에 필요한 문자와 개수 (countChars(t) 재사용)
     * haveMap : 지금 구간에 들어있는 문자와 개수
     * requiredKind : needMap.size() <- 채워야 하는 문자 "종류" 수
     * filledKind : 필요 개수까지 다 채운 종류 수
     *
     * -> filledKind == requiredKind 이면 조건 만족!
     *
     * 문자가 들어올 때: haveMap 개수 +1 하고,
     * 그 문자가 needMap에 있고 개수가 need와 "정확히" 같아진 순간에만 filledKind++
     * 문자가 나갈 때: haveMap 개수 -1 하고,
     * 그 문자가 needMap에 있고 개수가 need보다 "작아졌으면" filledKind--
     *
     * STEP 4는 길이(int)만 반환했지만 여기는 문자열을 반환해야 하므로,
     * 길이와 함께 시작 위치도 기록해두세요. (bestStart, bestLength)
     * 마지막에 s.substring(bestStart, bestStart + bestLength) 로 잘라냅니다.
     * 못 찾았으면 "" 반환. (bestStart를 -1로 초기화해두면 판단하기 쉽습니다)
     */
    static String minWindowFast(String s, String t) {
        Map<Character, Integer> haveMap = new HashMap<>();
        Map<Character, Integer> needMap = countChars(t);
        int requiredKind = needMap.size();
        int filledKind = 0;
        int startPoint = 0;
        int bestStart = -1;
        int bestLength = 0;
        String result = "";
        for (int endPoint = 0; endPoint < s.length(); endPoint++) {
            // ── 오른쪽으로 한 칸 넓히기 ──
            char inChar = s.charAt(endPoint);
            if (haveMap.containsKey(inChar)) {
                haveMap.put(inChar, haveMap.get(inChar) + 1);
            } else {
                haveMap.put(inChar, 1);
            }
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
                if (bestLength == 0 || bestLength > nowLength) {
                    bestLength = nowLength;
                    bestStart = startPoint;
                }
                char outChar = s.charAt(startPoint);
                startPoint++;
                if (haveMap.containsKey(outChar)) {
                    haveMap.put(outChar, haveMap.get(outChar) - 1);
                }
                if (needMap.containsKey(outChar)) {
                    int haveCount = haveMap.get(outChar);
                    int needCount = needMap.get(outChar);
                    if (haveCount < needCount) {
                        filledKind--;
                    }
                }

            }
        }
        if(bestStart<0 || bestLength<0) return "";

        result = s.substring(bestStart, bestStart + bestLength);
        return  result;
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    static void step1() {
        System.out.println("===== STEP 1: countChars =====");
        checkMap("banana", "b1,a3,n2");
        checkMap("aaa", "a3");
        checkMap("abc", "a1,b1,c1");
        checkMap("", "");
        System.out.println();
    }

    static void step2() {
        System.out.println("===== STEP 2: covers =====");
        checkBool("ADOBEC", "ABC", true);
        checkBool("ANC", "ABC", false);
        checkBool("AB", "AAB", false);
        checkBool("BAAB", "AAB", true);
        checkBool("abc", "abc", true);
        checkBool("", "a", false);
        checkBool("xyz", "", true);
        System.out.println();
    }

    static void step3() {
        System.out.println("===== STEP 3: minWindowBrute =====");
        checkStr(3, "ADOBECODEBANC", "ABC", "BANC");
        checkStr(3, "a", "a", "a");
        checkStr(3, "a", "aa", "");
        checkStr(3, "ab", "b", "b");
        checkStr(3, "cabwefgewcwaefgcf", "cae", "cwae");
        checkStr(3, "aabbcc", "abc", "abbc");
        System.out.println();
    }

    static void step4() {
        System.out.println("===== STEP 4: minLenSubArray =====");
        checkInt(new int[] { 2, 3, 1, 2, 4, 3 }, 7, 2);
        checkInt(new int[] { 1, 4, 4 }, 4, 1);
        checkInt(new int[] { 1, 1, 1, 1, 1, 1, 1, 1 }, 11, 0);
        checkInt(new int[] { 1, 2, 3, 4, 5 }, 15, 5);
        checkInt(new int[] { 5 }, 5, 1);
        checkInt(new int[] { 1, 2, 3 }, 100, 0);
        System.out.println();
    }

    static void step5() {
        System.out.println("===== STEP 5: minWindowFast =====");
        checkStr(5, "ADOBECODEBANC", "ABC", "BANC");
        checkStr(5, "a", "a", "a");
        checkStr(5, "a", "aa", "");
        checkStr(5, "aa", "aa", "aa");
        checkStr(5, "ab", "b", "b");
        checkStr(5, "cabwefgewcwaefgcf", "cae", "cwae");
        checkStr(5, "aabbcc", "abc", "abbc");
        checkStr(5, "", "A", "");
        System.out.println();
    }

    static void checkMap(String s, String expected) {
        Map<Character, Integer> actual = countChars(s);
        String actualStr = mapToStr(actual);
        String expectedStr = normalize(expected);
        boolean ok = actualStr.equals(expectedStr);
        System.out.printf("%s countChars(\"%s\") -> 기대:{%s} 결과:{%s}%n",
                ok ? "[통과]" : "[실패]", s, expectedStr, actualStr);
    }

    static void checkBool(String window, String t, boolean expected) {
        boolean actual = covers(window, t);
        System.out.printf("%s covers(\"%s\", \"%s\") -> 기대:%b 결과:%b%n",
                expected == actual ? "[통과]" : "[실패]", window, t, expected, actual);
    }

    static void checkInt(int[] nums, int target, int expected) {
        int actual = minLenSubArray(nums, target);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            sb.append(i == 0 ? "" : ",").append(nums[i]);
        }
        System.out.printf("%s [%s] target=%d -> 기대:%d 결과:%d%n",
                expected == actual ? "[통과]" : "[실패]", sb, target, expected, actual);
    }

    static void checkStr(int step, String s, String t, String expected) {
        String actual = (step == 3) ? minWindowBrute(s, t) : minWindowFast(s, t);
        boolean ok = actual != null
                && actual.length() == expected.length()
                && (expected.isEmpty() || covers(actual, t));
        System.out.printf("%s s=\"%s\", t=\"%s\" -> 기대:\"%s\" 결과:\"%s\"%n",
                ok ? "[통과]" : "[실패]", s, t, expected, actual);
    }

    // 채점 편의용: Map을 "a3,b1" 형태 문자열로 (키 순서 정렬)
    static String mapToStr(Map<Character, Integer> map) {
        StringBuilder sb = new StringBuilder();
        for (char c = 0; c < 128; c++) {
            if (map.containsKey(c)) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(c).append(map.get(c));
            }
        }
        return sb.toString();
    }

    static String normalize(String expected) {
        Map<Character, Integer> m = new HashMap<>();
        if (!expected.isEmpty()) {
            for (String part : expected.split(",")) {
                m.put(part.charAt(0), Integer.parseInt(part.substring(1)));
            }
        }
        return mapToStr(m);
    }
}
