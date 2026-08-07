package com.example.study.practice;

import java.util.Arrays;

/*
 * ============================================================
 *  [카카오 2022 블라인드] k진수에서 소수 개수 구하기
 *  유형: 진법 변환 / 문자열 파싱 (split) / 소수 판별 (Prime Check)
 *  난이도: 프로그래머스 Lv 2
 * ============================================================
 *
 * [문제 설명]
 * 양의 정수 n을 k진수로 바꾼 후, 변환된 수 안에 조건에 맞는 소수(Prime)가 몇 개인지 구하세요.
 *
 * 소수 조건 (자연수 0을 포함하지 않는 소수 P):
 *  1. 0P0 : 소수 양옆에 0이 있는 경우
 *  2. P0  : 소수 오른쪽에만 0이 있고 왼쪽엔 아무것도 없는 경우
 *  3. 0P  : 소수 왼쪽에만 0이 있고 오른쪽엔 아무것도 없는 경우
 *  4. P   : 소수 양옆에 아무것도 없는 경우
 *
 * 예) n = 437674, k = 3
 *     - 437674를 3진수로 변환 ➔ "211020101011"
 *     - 0을 기준으로 나누면 (0+ 정규식 split): ["211", "2", "1", "1", "11"]
 *     - 이 중 소수: 211 (소수), 2 (소수), 1 (소수x), 1 (소수x), 11 (소수)
 *     - 총 3개의 소수 ➔ 정답: 3
 *
 * ------------------------------------------------------------
 * [이 유형의 핵심 개념]
 *
 * 1. 복잡한 소수 조건 해석 ➔ "0을 기준으로 문자열 쪼개기":
 *    문제 조건(0P0, P0, 0P, P)을 거꾸로 해석하면 결국 "0"으로 쪼갠 숫자들이 소수인가?와 동일합니다.
 *    자바에서는 `Integer.toString(n, k)` 후 `str.split("0+")`으로 0이 연속해서 나오는 경우까지 한 번에 쪼갤 수 있습니다.
 *
 * 2. 오버플로우 방지 (Long 타입):
 *    k진수로 변환된 문자열 길이에 따라 int 범위(21억)를 쉽게 초과할 수 있습니다!
 *    반드시 `Long.parseLong(s)`을 사용하여 숫자로 변환해야 합니다.
 *
 * 3. 효율적인 소수 판별법 (O(sqrt(N))):
 *    소수 판별 시 2부터 sqrt(N)까지 나눠보아야 시간 초과를 방지할 수 있습니다.
 *    `for (long i = 2; i * i <= num; i++) { if (num % i == 0) return false; }`
 *
 * ------------------------------------------------------------
 * [자주 하는 실수]
 * - Integer.parseInt() 사용으로 인한 NumberFormatException (int 범위 초과)
 * - 1을 소수로 착각하는 경우 (1은 소수가 아님)
 * - 연속된 0 처리 미흡 ("10001" ➔ split("0+") 쓰지 않으면 빈 문자열 처리 에러)
 */
public class st_0805_kakao1_k_prime {

    public static int solution(int n, int k) {
        // TODO: 직접 구현해 보세요!
        // 힌트:
        // 1. n을 k진수 문자열로 변환 (Integer.toString(n, k))
        // 2. "0+" 정규식으로 split 하여 숫자 조각 배열 생성
        // 3. 각 조각을 Long.parseLong()으로 변환 후 소수 판별 (1은 소수 제외, 2부터 sqrt(N) 탐색)
        // int answer = 0;
        // int a = 0;
        // String b = "";
        // while (n > 0) {
        // a = n % k;
        // n /= k;
        // b = String.valueOf(a) + b;
        // }

        // System.out.println(b);
        int answer = 0;

        String r = Integer.toString(n, k);
        System.out.println(r);

        String[] parth = r.split("0+");
        for (String s : parth) {
            if (s.isEmpty()) {
                continue;
            }
            int a = Integer.parseInt(s);
            if (isPrime(a)) {
                answer++;
            }
        }

        return answer;
    }

    static boolean isPrime(int num) {
        if (num == 1) {
            return false;
        }
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    public static void main(String[] args) {
        System.out.println("===== [카카오 2022 블라인드] k진수에서 소수 개수 구하기 =====");

        check(437674, 3, 3);
        check(110011, 10, 2);
        check(10, 2, 0);

    }

    static void check(int n, int k, int expected) {
        int actual = solution(n, k);
        if (actual == expected) {
            System.out.println("[PASS] 결과: " + actual);
        } else {
            System.out.println("[FAIL] 예상값: " + expected + ", 실제값: " + actual);
        }
    }
}
