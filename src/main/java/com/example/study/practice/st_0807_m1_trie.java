package com.example.study.practice;

/*
 * ============================================================
 *  전화번호 목록   [유형: 트라이 (Trie) / 문자열 검사]   (난이도: Medium)
 * ============================================================
 *
 * 전화번호부에 적힌 전화번호를 담은 배열 phone_book이 주어질 때,
 * 어떤 번호가 다른 번호의 접두어(Prefix)인 경우가 존재하면 false를,
 * 그렇지 않으면 true를 반환하세요.
 *
 * 단, 이번 문제에서는 공부를 위해 Trie(트라이) 자료구조를 직접 구현하여 풀어보세요!
 *
 * 예) phone_book = ["119", "97674223", "1195524421"]
 *     -> "119"는 "1195524421"의 접두어입니다. ➔ 정답: false
 *
 * 예) phone_book = ["123", "456", "789"]
 *     -> 어떤 번호도 다른 번호의 접두어가 아닙니다. ➔ 정답: true
 *
 * ------------------------------------------------------------
 * [이 유형의 핵심 개념]
 *
 * Trie (접두사 트리 / Prefix Tree):
 * 문자열 탐색 및 저장에 특화된 트리 구조로, 각 노드가 문자를 가지고 자식 노드를 가리킵니다.
 * 문자열 길이가 L일 때, 탐색 및 삽입 시간이 O(L)로 매우 빠릅니다.
 *
 * 1. Trie 노드 구조 (TrieNode):
 *    - `TrieNode[] children = new TrieNode[10];` ('0'~'9' 숫자에 해당)
 *    - `boolean isEnd;` (해당 노드가 단어의 끝인지 여부)
 *
 * 2. 접두사 검사 및 삽입 알고리즘:
 *    단어 s를 Trie에 삽입하는 동안:
 *    - 경로상에 `isEnd == true`인 노드를 만난 경우 -> (이전에 삽입된 짧은 단어가 현재 단어의 접두어임!)
 *    - 탐색이 끝났는데 이미 자식 노드가 존재하는 경우 -> (현재 단어가 이전에 삽입된 긴 단어의 접두어임!)
 *
 * ------------------------------------------------------------
 * [자주 하는 실수]
 * - 내 단어가 이전 단어의 접두어인 경우만 확인하고, 반대로 이전 단어가 내 단어의 접두어인 경우를 놓치는 실수
 * - 자식 노드 배열 인덱스 계산 시 '0' 문자를 빼지 않는 경우 (`c - '0'`)
 */
public class st_0807_m1_trie {

    static class TrieNode {
        TrieNode[] children = new TrieNode[10];
        boolean isEnd = false;
    }

    static class Trie {
        TrieNode root = new TrieNode();

        // 단어를 삽입하면서 접두어 관계가 발견되면 false 반환, 문제없으면 true 반환
        public boolean insert(String word) {
            TrieNode curr = root;
            for (int i = 0; i < word.length(); i++) {
                int digit = word.charAt(i) - '0';

                if (curr.children[digit] == null) {
                    curr.children[digit] = new TrieNode();
                }
                curr = curr.children[digit];

                // 이미 완성된 단어가 현재 단어의 접두어인 경우
                if (curr.isEnd) {
                    return false;
                }
            }

            // 현재 단어가 이미 존재하는 다른 단어의 접두어인 경우 (자식이 이미 존재하는 경우)
            for (TrieNode child : curr.children) {
                if (child != null) {
                    return false;
                }
            }

            curr.isEnd = true;
            return true;
        }
    }

    public static boolean solution(String[] phone_book) {
        // TODO: 직접 구현해 보세요!
        // 힌트:
        // 1. Trie trie = new Trie(); 생성
        // 2. phone_book의 각 번호 num에 대해 trie.insert(num) 실행
        // 3. insert가 false를 반환하면 즉시 false 반환
        // 4. 모두 무사히 삽입되면 true 반환
        Trie trie = new Trie();
        for (String num : phone_book) {
            if (!trie.insert(num)) {
                return false;
            }
        }

        return true;
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    public static void main(String[] args) {
        System.out.println("===== [Trie] 전화번호 목록 =====");

        check(new String[] { "119", "97674223", "1195524421" }, false);
        check(new String[] { "123", "456", "789" }, true);
        check(new String[] { "12", "123", "1235", "567", "88" }, false);
    }

    static void check(String[] phone_book, boolean expected) {
        boolean actual = solution(phone_book);
        if (actual == expected) {
            System.out.println("[PASS] 결과: " + actual);
        } else {
            System.out.println("[FAIL] 예상값: " + expected + ", 실제값: " + actual);
        }
    }
}
