package com.example.study.practice;

import java.util.ArrayList;
import java.util.List;

/*
 * ============================================================
 *  그룹 애너그램   [유형: 해시맵]   (난이도: 중)
 * ============================================================
 *
 * 문자열 배열이 주어집니다. 알파벳을 재배열하면 서로 같아지는 것끼리
 * 묶어서 반환하세요. (애너그램: "eat"과 "tea"처럼 글자 구성이 같은 단어)
 *
 * 예) ["eat","tea","tan","ate","nat","bat"]
 *     -> [["eat","tea","ate"], ["tan","nat"], ["bat"]]
 *
 * 그룹의 순서, 그룹 안의 순서는 상관없습니다.
 *
 * ------------------------------------------------------------
 * [이 유형의 핵심 개념]
 *
 * 해시맵 문제의 90%는 "무엇을 key로 삼을 것인가" 하나로 끝납니다.
 * 값 자체를 key로 쓰는 게 아니라, "같은 그룹이면 똑같아지는 무언가"를
 * 만들어서 그걸 key로 씁니다. 이걸 보통 시그니처 라고 부릅니다.
 *
 * "eat", "tea", "ate" 를 각각 글자순으로 정렬하면?  -> 전부 "aet"
 * 애너그램이면 정렬 결과가 반드시 같고, 아니면 반드시 다릅니다.
 * 즉 "정렬한 문자열"이 완벽한 key입니다.
 *
 * ★ 왜 해시맵인가:
 *   "이 key가 전에 나왔었나?" 를 O(1)에 물어볼 수 있기 때문입니다.
 *   리스트로 하면 매번 처음부터 훑어야 해서 O(N)이 됩니다.
 *
 * ------------------------------------------------------------
 * [힌트]
 *
 * - 문자열 정렬하는 법:
 *     char[] arr = s.toCharArray();
 *     Arrays.sort(arr);
 *     String key = new String(arr);
 *
 * - Map<String, List<String>> map 을 만들고,
 *   key가 처음 나오면 new ArrayList<>() 를 넣어준 뒤 거기에 단어를 add:
 *
 *     if (!map.containsKey(key)) {
 *         map.put(key, new ArrayList<>());
 *     }
 *     map.get(key).add(word);
 *
 *   -> 이걸 한 줄로 쓰면:
 *     map.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
 *
 * - 마지막에 new ArrayList<>(map.values()) 로 반환.
 *   (map.values() 는 Collection이라 List로 감싸주면 됩니다)
 *
 * ------------------------------------------------------------
 * [자주 하는 실수]
 *
 * - s.toCharArray() 결과를 그대로 key로 쓰면 안 됩니다.
 *   배열은 내용이 같아도 서로 다른 객체라서 Map의 key로 동작하지 않습니다.
 *   반드시 new String(arr) 로 문자열로 바꾸세요.
 *
 * - map.put(key, list) 를 매번 새로 하면 앞에 넣은 게 날아갑니다.
 *   리스트를 "꺼내서 add" 해야 합니다.
 *
 * ------------------------------------------------------------
 * [연습 더 하고 싶으면 - 같은 유형]
 * - 두 수의 합(Two Sum), 중복 문자 없는 가장 긴 부분 문자열,
 *   가장 많이 등장한 K개 원소
 */
public class st_0729_m1_hashmap {

    static List<List<String>> groupAnagrams(String[] strs) {
        // TODO
        String [] question = { "eat", "tea", "tan", "ate", "nat", "bat" };

        for (int i = 0; i < question.length; i++) {
            for (int j = 0; j < question[i].length(); j++) {
                
            }
        }
    

        return new ArrayList<>();
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    public static void main(String[] args) {
        System.out.println("===== groupAnagrams (해시맵) =====");
        check(new String[] { "eat", "tea", "tan", "ate", "nat", "bat" },
                "ate|eat|tea / bat / nat|tan");
        check(new String[] { "ab", "ba", "abc" }, "ab|ba / abc");
        check(new String[] { "a" }, "a");
        check(new String[] { "abc", "bca", "cab" }, "abc|bca|cab");
        check(new String[] { "ab", "cd", "ef" }, "ab / cd / ef");
        check(new String[] { "aab", "aba", "baa", "abb" }, "aab|aba|baa / abb");
    }

    // 그룹 안을 정렬, 그룹끼리도 정렬해서 비교 (순서 무관하게 채점)
    static void check(String[] input, String expected) {
        String actual = normalize(groupAnagrams(input));
        System.out.printf("%s [%s] -> 기대:%s 결과:%s%n",
                expected.equals(actual) ? "[통과]" : "[실패]",
                String.join(",", input), expected, actual);
    }

    static String normalize(List<List<String>> groups) {
        if (groups == null) {
            return "(null)";
        }
        if (groups.isEmpty()) {
            return "(비어있음)";
        }
        List<String> lines = new ArrayList<>();
        for (List<String> g : groups) {
            List<String> copy = new ArrayList<>(g);
            copy.sort(null);
            lines.add(String.join("|", copy));
        }
        lines.sort(null);
        return String.join(" / ", lines);
    }
}
