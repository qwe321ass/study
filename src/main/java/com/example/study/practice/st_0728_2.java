package com.example.study.practice;

public class st_0728_2 {

    /*
     * 문제: 편집 거리 (Edit Distance / 레벤슈타인 거리) (난이도: 상)
     *
     * 문자열 word1을 word2로 바꾸려고 합니다.
     * 사용할 수 있는 연산은 3가지이고, 각각 비용 1입니다.
     *
     *   1) 삽입(insert)  : 아무 위치에 문자 하나 끼워넣기
     *   2) 삭제(delete)  : 문자 하나 지우기
     *   3) 교체(replace) : 문자 하나를 다른 문자로 바꾸기
     *
     * word1을 word2로 만들기 위한 최소 연산 횟수를 구하세요.
     *
     * 예시 1:
     *   word1 = "horse", word2 = "ros"
     *   -> 정답: 3
     *      horse -> rorse  (h를 r로 교체)
     *      rorse -> rose   (r 삭제)
     *      rose  -> ros    (e 삭제)
     *
     * 예시 2:
     *   word1 = "intention", word2 = "execution"
     *   -> 정답: 5
     *
     * 제한:
     * - 각 문자열 길이 최대 500
     *
     * 힌트:
     * 1. LIS는 dp[i] 처럼 1차원 배열이었죠.
     *    이번엔 dp[i][j] = "word1의 앞 i글자"를 "word2의 앞 j글자"로
     *    바꾸는 최소 비용, 이렇게 2차원으로 정의해봅니다.
     * 2. 초기값을 먼저 생각하세요.
     *    dp[i][0]은? (word2가 빈 문자열이면 i글자를 전부 지워야 함)
     *    dp[0][j]는?
     * 3. word1의 i번째 문자와 word2의 j번째 문자가 같다면?
     *    -> 아무것도 안 해도 됩니다. 어디서 값을 가져오면 될까요?
     * 4. 다르다면 세 가지 선택지 중 최솟값 + 1 입니다.
     *    삭제 / 삽입 / 교체가 각각 dp 표의 어느 칸에서 오는지 그려보세요.
     *
     * 함정:
     * - 인덱스 헷갈림 주의. dp 배열은 (n+1) x (m+1) 크기로 잡고,
     *   dp[i][j]에서 실제 문자는 word1.charAt(i-1), word2.charAt(j-1) 입니다.
     *
     * 보너스(여유되면):
     * - 메모리를 O(n*m)이 아니라 O(m)만 쓰도록 줄여보세요. (행 2줄만 유지)
     */

    public static void main(String[] args) {
        check("horse", "ros", 3);
        check("intention", "execution", 5);
        check("", "abc", 3);
        check("abc", "", 3);
        check("", "", 0);
        check("same", "same", 0);
        check("kitten", "sitting", 3);
        check("sunday", "saturday", 3);
    }

    static int minDistance(String word1, String word2) {
        // TODO: 여기를 구현하세요
        return -1;
    }

    // ---- 아래는 채점용 ----
    static void check(String a, String b, int expected) {
        int actual = minDistance(a, b);
        System.out.printf("%s \"%s\" -> \"%s\" | 기대:%d 결과:%d%n",
                expected == actual ? "[통과]" : "[실패]", a, b, expected, actual);
    }
}
