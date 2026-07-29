package com.example.study.practice;

import java.util.HashMap;
import java.util.Map;

public class st_0729_1 {

    /*
     * 문제: 편집 거리 (Edit Distance / 레벤슈타인 거리) (난이도: 상)
     *
     * 문자열 word1을 word2로 바꾸려고 합니다.
     * 사용할 수 있는 연산은 3가지이고, 각각 비용 1입니다.
     *
     * 1) 삽입(insert) : 아무 위치에 문자 하나 끼워넣기
     * 2) 삭제(delete) : 문자 하나 지우기
     * 3) 교체(replace) : 문자 하나를 다른 문자로 바꾸기
     *
     * word1을 word2로 만들기 위한 최소 연산 횟수를 구하세요.
     *
     * 예시 1:
     * word1 = "horse", word2 = "ros"
     * -> 정답: 3
     * horse -> rorse (h를 r로 교체)
     * rorse -> rose (r 삭제)
     * rose -> ros (e 삭제)
     *
     * 예시 2:
     * word1 = "intention", word2 = "execution"
     * -> 정답: 5
     *
     * 제한:
     * - 각 문자열 길이 최대 500
     *
     * 힌트:
     * 1. LIS는 dp[i] 처럼 1차원 배열이었죠.
     * 이번엔 dp[i][j] = "word1의 앞 i글자"를 "word2의 앞 j글자"로
     * 바꾸는 최소 비용, 이렇게 2차원으로 정의해봅니다.
     * 2. 초기값을 먼저 생각하세요.
     * dp[i][0]은? (word2가 빈 문자열이면 i글자를 전부 지워야 함)
     * dp[0][j]는?
     * 3. word1의 i번째 문자와 word2의 j번째 문자가 같다면?
     * -> 아무것도 안 해도 됩니다. 어디서 값을 가져오면 될까요?
     * 4. 다르다면 세 가지 선택지 중 최솟값 + 1 입니다.
     * 삭제 / 삽입 / 교체가 각각 dp 표의 어느 칸에서 오는지 그려보세요.
     *
     * 함정:
     * - 인덱스 헷갈림 주의. dp 배열은 (n+1) x (m+1) 크기로 잡고,
     * dp[i][j]에서 실제 문자는 word1.charAt(i-1), word2.charAt(j-1) 입니다.
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

    /*
     *
     * dp[i][j] = word1의 앞 i글자를 word2의 앞 j글자로 바꾸는 최소 비용
     *
     * 위 코드와의 차이는 "커서가 2개"라는 점입니다.
     * i 는 word1 을 얼마나 소비했는지, j 는 word2 를 얼마나 만들었는지이고
     * 이 둘이 따로 움직이기 때문에 삽입/삭제를 표현할 수 있습니다.
     *
     * "horse" -> "ros" 표를 미리 그려두면 검산하기 좋습니다:
     *
     * " r o s
     * " 0 1 2 3
     * h 1 1 2 3
     * o 2 2 1 2
     * r 3 2 2 2
     * s 4 3 3 2
     * e 5 4 4 [3] <- 정답
     *
     * 세 연산이 표에서 오는 방향:
     * 삭제 (word1의 i번째를 버림) -> dp[i-1][j] (위)
     * 삽입 (word2의 j번째를 끼워넣음) -> dp[i][j-1] (왼쪽)
     * 교체 -> dp[i-1][j-1] (왼쪽 위)
     */
    static int minDistanceDp(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        // [1] 표 만들기: (n+1) x (m+1)
        //     +1 이 붙는 이유는 "빈 문자열" 이라는 상태가 필요하기 때문입니다.
        //     0번 행 = word1이 빈 문자열, 0번 열 = word2가 빈 문자열.
        //     그래서 dp의 i는 "인덱스"가 아니라 "앞에서부터 몇 글자를 썼는지(개수)"입니다.
        //     Java는 int 배열을 0으로 자동 초기화하므로 따로 채울 필요 없습니다.
        int[][] dp = new int[n + 1][m + 1];

        // [2] 0번 열 채우기 (맨 왼쪽 세로줄)
        //     dp[i][0] = "word1 앞 i글자" -> "빈 문자열"
        //     만들 게 없으니 i글자를 전부 삭제하는 수밖에 없습니다. 그래서 비용 i.
        //     예) dp[3][0] = 3  ("hor" -> "" : h,o,r 삭제 3번)
        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }

        // [3] 0번 행 채우기 (맨 위 가로줄)
        //     dp[0][j] = "빈 문자열" -> "word2 앞 j글자"
        //     가진 게 없으니 j글자를 전부 삽입해야 합니다. 그래서 비용 j.
        //     예) dp[0][3] = 3  ("" -> "ros" : r,o,s 삽입 3번)
        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }

        // [4] 나머지 칸을 왼쪽->오른쪽, 위->아래 순으로 채웁니다.
        //     이 순서여야 하는 이유: 아래에서 쓰는 세 칸이
        //       dp[i-1][j]   -> 이전 행 (이미 완성)
        //       dp[i-1][j-1] -> 이전 행 (이미 완성)
        //       dp[i][j-1]   -> 같은 행에서 방금 채운 칸
        //     전부 "이미 계산된 값"이 됩니다. 1부터 시작하는 건 0행/0열을 이미 채웠으니까요.
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {

                // [5] 지금 비교하는 두 글자를 꺼냅니다.
                //     i가 "개수"라서 실제 문자는 한 칸 앞인 charAt(i-1) 입니다.
                //     (i=1 이면 첫 글자 charAt(0))
                //     char는 primitive이므로 == 비교가 값 비교로 정상 동작합니다.
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {

                    // [6] 두 글자가 같음 -> 이 글자에는 아무 연산도 필요 없습니다.
                    //     양쪽 커서를 하나씩 뒤로 물린 상태의 답을 "비용 추가 없이" 그대로 씁니다.
                    //     +1 이 안 붙는 게 핵심입니다.
                    //     예) "ho" -> "ro" 는 앞의 h->r 교체 1번이 전부고, 뒤의 o는 공짜.
                    dp[i][j] = dp[i - 1][j - 1];

                } else {

                    // [7] 두 글자가 다름 -> 세 연산 중 뭘 할지 선택해야 하고, 어느 쪽이든 비용 1.
                    //     "직전 상태 3개 중 가장 싼 것 + 1" 을 고릅니다.
                    //
                    //     dp[i-1][j]   (위)     삭제: word1의 i번째 글자를 버림.
                    //                           word1 커서만 후퇴하고 word2는 그대로.
                    //     dp[i][j-1]   (왼쪽)   삽입: word2의 j번째 글자를 끼워넣음.
                    //                           word2 커서만 후퇴하고 word1은 그대로.
                    //     dp[i-1][j-1] (왼쪽위) 교체: 두 글자를 맞바꿈. 양쪽 커서 모두 후퇴.
                    //
                    //     여기서 삭제/삽입이 "커서 한쪽만" 움직이는 게 정렬을 밀어주는 부분입니다.
                    //     세 방향을 다 재보기 때문에 앞에서 실패했던 sunday -> saturday 처럼
                    //     중간에 글자를 끼워넣는 경우도 자동으로 찾아냅니다.
                    //
                    //     Math.min은 인자를 2개만 받아서 중첩했습니다.
                    dp[i][j] = Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1])) + 1;
                }
            }
        }

        // [8] 표의 오른쪽 맨 아래 칸 = word1 전체(n글자)를 word2 전체(m글자)로 바꾸는 비용.
        //     이게 우리가 원하던 답입니다.
        //     n=0, m=0 이면 위 이중 루프를 아예 안 돌고 dp[0][0]=0 을 그대로 반환합니다.
        //     그래서 "" -> "" 같은 경계 케이스가 따로 처리 없이 맞습니다.
        return dp[n][m];
    }

    static void check(String a, String b, int expected) {
        int actual = minDistanceDp(a, b);
        System.out.printf("%s \"%s\" -> \"%s\" | 기대:%d 결과:%d%n",
                expected == actual ? "[통과]" : "[실패]", a, b, expected, actual);
    }
}
