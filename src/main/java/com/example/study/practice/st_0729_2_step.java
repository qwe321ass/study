package com.example.study.practice;

/*
 * ============================================================
 *  DP 계단 (st_0729_1 편집 거리로 가는 5단계)
 * ============================================================
 *
 *  편집 거리가 어려웠던 건 실력 문제가 아니라 순서 문제입니다.
 *  편집 거리는 "2차원 표 + 세 방향 선택 + 두 문자열 비교"가
 *  한꺼번에 나오는 문제라, DP 중에서도 꽤 뒤쪽에 있습니다.
 *
 *  아래는 그 세 가지를 하나씩 따로 익히는 계단입니다.
 *
 *   STEP 1  계단 오르기        1차원 표 + 표에 적어두기 개념      (난이도: 하)
 *   STEP 2  도둑질            1차원 표 + 선택지 중 고르기         (난이도: 하)
 *   STEP 3  격자 경로 수       2차원 표 첫 경험 (위 + 왼쪽)        (난이도: 하)
 *   STEP 4  격자 최소 비용     2차원 표 + min 선택                (난이도: 중하)
 *   STEP 5  최장 공통 부분수열  2차원 표 + 두 문자열 비교           (난이도: 중)
 *
 *  ------------------------------------------------------------
 *  각 단계가 편집 거리의 어느 부분인지:
 *
 *   STEP 1  "이미 계산한 답을 배열에 적어두고 재사용한다"  <- DP의 정의 자체
 *   STEP 2  "여러 경로 중 최선을 고른다"                <- Math.min(...) 부분
 *   STEP 3  "dp[i][j]가 위/왼쪽 칸에서 온다"            <- 삭제/삽입 방향
 *   STEP 4  "위/왼쪽 중 싼 쪽을 고른다"                 <- min(위, 왼쪽) 부분
 *   STEP 5  "두 문자열의 i번째, j번째를 비교한다"        <- charAt(i-1) == charAt(j-1)
 *
 *  STEP 5까지 하고 편집 거리를 다시 보면 "대각선 하나 추가된 거네" 로 보입니다.
 *  ------------------------------------------------------------
 *
 *  실행하면 5단계 전부 채점됩니다.
 *  한 단계 풀고 실행 -> [통과] 확인 -> 다음 단계, 이렇게 가세요.
 */
public class st_0729_2_step {

    public static void main(String[] args) {
        step1();
        step2();
        step3();
        step4();
        step5();
    }

    // =========================================================
    // STEP 1. 계단 오르기 (난이도: 하)
    // =========================================================
    /*
     * 계단이 n칸 있습니다. 한 번에 1칸 또는 2칸씩 오를 수 있습니다.
     * 맨 위까지 오르는 방법이 몇 가지인지 반환하세요.
     *
     * 예) n = 1 -> 1   (1)
     * 예) n = 2 -> 2   (1+1, 2)
     * 예) n = 3 -> 3   (1+1+1, 1+2, 2+1)
     * 예) n = 4 -> 5   (1111, 112, 121, 211, 22)
     *
     * ------------------------------------------------------------
     * [핵심 아이디어]
     *
     * 4번째 칸에 서 있다고 생각해보세요. 방금 어디서 왔을까요?
     *   - 3번째 칸에서 1칸 올라왔거나
     *   - 2번째 칸에서 2칸 올라왔거나
     * 이 둘뿐입니다.
     *
     * 그러니까 "4칸 오르는 방법 수" = "3칸 오르는 방법 수" + "2칸 오르는 방법 수"
     *
     * [힌트]
     * - dp[i] = i칸을 오르는 방법의 수, 로 정의하세요.
     * - dp[1] = 1, dp[2] = 2 를 손으로 채워두고 시작합니다.
     * - dp[i] = dp[i-1] + dp[i-2]
     * - n이 1일 때 dp[2]에 접근하면 터집니다. 배열 크기를 넉넉히 잡거나
     *   n == 1 이면 바로 1을 반환하세요.
     *
     * ★ 왜 배열이 필요한가:
     *   재귀로 f(n) = f(n-1) + f(n-2) 만 써도 답은 나오지만,
     *   f(30)을 구할 때 f(10)을 수만 번 다시 계산합니다.
     *   배열에 한 번 적어두면 각 칸을 딱 한 번만 계산합니다.
     */
    static int climbStairs(int n) {
        // TODO
        return 0;
    }

    // =========================================================
    // STEP 2. 도둑질 (난이도: 하)
    // =========================================================
    /*
     * 집들이 일렬로 있고 각 집에 돈이 nums[i] 만큼 있습니다.
     * 단, 붙어 있는 두 집을 연속으로 털면 경보가 울립니다.
     * 훔칠 수 있는 최대 금액을 반환하세요.
     *
     * 예) nums = [1,2,3,1]   -> 4    (1 + 3)
     * 예) nums = [2,7,9,3,1] -> 12   (2 + 9 + 1)
     * 예) nums = [2,1,1,2]   -> 4    (2 + 2)
     * 예) nums = []          -> 0
     *
     * ------------------------------------------------------------
     * [핵심 아이디어]
     *
     * STEP 1과 구조는 같은데, 이번엔 "더하기"가 아니라 "고르기"입니다.
     *
     * i번째 집 앞에 섰을 때 선택지가 딱 2개입니다.
     *   1) 이 집을 턴다  -> nums[i] + (i-2번째까지의 최대) = nums[i] + dp[i-2]
     *   2) 이 집을 건너뛴다 -> (i-1번째까지의 최대)        = dp[i-1]
     * 둘 중 큰 쪽이 답입니다.
     *
     * [힌트]
     * - dp[i] = i번째 집까지 봤을 때의 최대 금액
     * - dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i])
     * - dp[0] = nums[0], dp[1] = Math.max(nums[0], nums[1]) 로 시작
     * - nums.length가 0 또는 1인 경우를 먼저 처리하세요.
     *
     * ★ 여기서 나오는 Math.max 고르기가
     *   편집 거리의 Math.min(삭제, 삽입, 교체) 와 완전히 같은 발상입니다.
     *   "선택지를 나열하고, 각각의 답은 이미 표에 있고, 그중 최선을 고른다"
     */
    static int rob(int[] nums) {
        // TODO
        return 0;
    }

    // =========================================================
    // STEP 3. 격자 경로 수 (난이도: 하) ★ 2차원 표 첫 경험
    // =========================================================
    /*
     * rows x cols 크기의 격자가 있습니다.
     * 왼쪽 맨 위에서 출발해 오른쪽 맨 아래까지 갑니다.
     * 오른쪽(→) 또는 아래쪽(↓) 으로만 움직일 수 있습니다.
     * 서로 다른 경로가 몇 가지인지 반환하세요.
     *
     * 예) rows = 3, cols = 3 -> 6
     * 예) rows = 3, cols = 7 -> 28
     * 예) rows = 1, cols = 5 -> 1    (계속 오른쪽으로만)
     * 예) rows = 2, cols = 2 -> 2    (→↓ 또는 ↓→)
     *
     * ------------------------------------------------------------
     * [핵심 아이디어]
     *
     * (i, j) 칸에 서 있을 때, 방금 어디서 왔을까요?
     *   - 위 칸 (i-1, j) 에서 아래로 내려왔거나
     *   - 왼쪽 칸 (i, j-1) 에서 오른쪽으로 왔거나
     * 이 둘뿐입니다. (STEP 1의 "1칸/2칸"과 똑같은 발상, 방향만 2차원)
     *
     * dp[i][j] = dp[i-1][j] + dp[i][j-1]
     *
     * [힌트]
     * - 첫 행은 전부 1입니다. (오른쪽으로만 오는 길 하나뿐)
     * - 첫 열도 전부 1입니다. (아래로만 오는 길 하나뿐)
     *   -> 편집 거리에서 0행/0열을 먼저 채웠던 것과 같은 이유입니다.
     * - 나머지는 i=1, j=1 부터 이중 for문으로 채우면 됩니다.
     *
     * ★ 3x3 표를 손으로 채워보세요. 이게 되면 편집 거리 표가 안 무섭습니다.
     *
     *        1   1   1
     *        1   2   3
     *        1   3  [6]   <- 답
     *
     *   위+왼쪽을 더하는 것뿐입니다. 편집 거리는 여기서
     *   "더하기" 대신 "min 고르기"를 하고, 대각선 하나를 더 볼 뿐입니다.
     */
    static int uniquePaths(int rows, int cols) {
        // TODO
        return 0;
    }

    // =========================================================
    // STEP 4. 격자 최소 비용 (난이도: 중하)
    // =========================================================
    /*
     * grid[i][j] 에 통행료가 적혀 있습니다.
     * 왼쪽 맨 위에서 오른쪽 맨 아래까지 (→ 또는 ↓ 로만) 가면서
     * 지나간 칸의 숫자를 모두 더합니다.
     * 그 합이 가장 작아지는 경로의 합을 반환하세요.
     *
     * 예) grid = {{1,3,1},
     *             {1,5,1},
     *             {4,2,1}}   -> 7   (1→3→1→1→1)
     *
     * 예) grid = {{1,2,3},
     *             {4,5,6}}   -> 12  (1→2→3→6)
     *
     * ------------------------------------------------------------
     * [핵심 아이디어]
     *
     * STEP 3과 표 모양이 완전히 같습니다. 딱 두 군데만 다릅니다.
     *
     *   STEP 3                          STEP 4
     *   dp[i-1][j] + dp[i][j-1]         Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j]
     *   (경로 수를 "더한다")              (더 싼 길을 "고르고", 지금 칸 요금을 더한다)
     *
     * [힌트]
     * - dp[i][j] = 출발점에서 (i,j) 까지 오는 최소 비용
     * - dp[0][0] = grid[0][0]
     * - 첫 행: 왼쪽에서 오는 수밖에 없음 -> dp[0][j] = dp[0][j-1] + grid[0][j]
     * - 첫 열: 위에서 오는 수밖에 없음   -> dp[i][0] = dp[i-1][0] + grid[i][0]
     * - 세로 길이는 grid.length, 가로 길이는 grid[0].length
     *
     * ★ 이 문제를 풀면 편집 거리의 구조를 사실상 다 익힌 겁니다.
     *   편집 거리도 "출발점에서 (i,j)까지 오는 최소 비용"이었고,
     *   방향이 위/왼쪽에 대각선 하나만 추가된 형태입니다.
     */
    static int minPathSum(int[][] grid) {
        // TODO
        return 0;
    }

    // =========================================================
    // STEP 5. 최장 공통 부분수열 (LCS) (난이도: 중)
    // =========================================================
    /*
     * 두 문자열에서 "순서를 유지하면서" 양쪽에 공통으로 나타나는
     * 가장 긴 부분수열의 길이를 반환하세요.
     * 연속이 아니어도 되고, 순서만 지키면 됩니다.
     *
     * 예) "abcde", "ace"     -> 3   ("ace")
     * 예) "abc", "abc"       -> 3
     * 예) "abc", "def"       -> 0
     * 예) "AGGTAB", "GXTXAYB"-> 4   ("GTAB")
     * 예) "", "abc"          -> 0
     *
     * ------------------------------------------------------------
     * [핵심 아이디어]
     *
     * 드디어 편집 거리와 같은 모양입니다. 두 문자열이니 커서가 2개죠.
     * dp[i][j] = word1 앞 i글자와 word2 앞 j글자의 LCS 길이
     *
     * word1의 i번째 글자와 word2의 j번째 글자를 비교합니다.
     *
     *   같으면  -> 이 글자를 LCS에 넣을 수 있습니다.
     *             dp[i-1][j-1] + 1        (대각선 + 1)
     *
     *   다르면  -> 이 글자쌍은 같이 쓸 수 없습니다.
     *             한쪽을 포기해야 하니 둘 중 큰 쪽:
     *             Math.max(dp[i-1][j], dp[i][j-1])   (위 또는 왼쪽)
     *
     * [힌트]
     * - 배열 크기는 (n+1) x (m+1). +1은 "빈 문자열" 자리입니다.
     * - 0행/0열은 전부 0입니다. (한쪽이 빈 문자열이면 공통 부분도 없음)
     *   Java는 int 배열을 0으로 초기화하니 따로 채울 필요 없습니다.
     * - 실제 문자는 word1.charAt(i-1), word2.charAt(j-1)  <- 한 칸 앞!
     *
     * ★ 편집 거리와 비교해보세요. 구조가 거울처럼 같습니다.
     *
     *              편집 거리(최소화)              LCS(최대화)
     *   같을 때    dp[i-1][j-1]                  dp[i-1][j-1] + 1
     *   다를 때    min(위, 왼쪽, 대각선) + 1       max(위, 왼쪽)
     *   초기값     dp[i][0]=i, dp[0][j]=j        전부 0
     *   답         dp[n][m]                      dp[n][m]
     *
     *   "같으면 대각선, 다르면 다른 방향들 중에서 고르기" 라는 뼈대가 동일합니다.
     */
    static int lcs(String word1, String word2) {
        // TODO
        return 0;
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    static void step1() {
        System.out.println("===== STEP 1: climbStairs =====");
        checkStairs(1, 1);
        checkStairs(2, 2);
        checkStairs(3, 3);
        checkStairs(4, 5);
        checkStairs(5, 8);
        checkStairs(10, 89);
        checkStairs(20, 10946);
        System.out.println();
    }

    static void step2() {
        System.out.println("===== STEP 2: rob =====");
        checkRob(new int[] { 1, 2, 3, 1 }, 4);
        checkRob(new int[] { 2, 7, 9, 3, 1 }, 12);
        checkRob(new int[] { 2, 1, 1, 2 }, 4);
        checkRob(new int[] { 5 }, 5);
        checkRob(new int[] { 1, 2 }, 2);
        checkRob(new int[] {}, 0);
        checkRob(new int[] { 100, 1, 1, 100 }, 200);
        System.out.println();
    }

    static void step3() {
        System.out.println("===== STEP 3: uniquePaths =====");
        checkPaths(3, 3, 6);
        checkPaths(3, 7, 28);
        checkPaths(1, 1, 1);
        checkPaths(2, 2, 2);
        checkPaths(1, 5, 1);
        checkPaths(3, 2, 3);
        checkPaths(5, 5, 70);
        System.out.println();
    }

    static void step4() {
        System.out.println("===== STEP 4: minPathSum =====");
        checkGrid(new int[][] { { 1, 3, 1 }, { 1, 5, 1 }, { 4, 2, 1 } }, 7);
        checkGrid(new int[][] { { 1, 2, 3 }, { 4, 5, 6 } }, 12);
        checkGrid(new int[][] { { 1, 2 }, { 1, 1 } }, 3);
        checkGrid(new int[][] { { 5 } }, 5);
        checkGrid(new int[][] { { 1, 1, 1, 1 } }, 4);
        checkGrid(new int[][] { { 1 }, { 2 }, { 3 } }, 6);
        System.out.println();
    }

    static void step5() {
        System.out.println("===== STEP 5: lcs =====");
        checkLcs("abcde", "ace", 3);
        checkLcs("abc", "abc", 3);
        checkLcs("abc", "def", 0);
        checkLcs("AGGTAB", "GXTXAYB", 4);
        checkLcs("", "abc", 0);
        checkLcs("abc", "", 0);
        checkLcs("bl", "yby", 1);
        checkLcs("horse", "ros", 2);
        System.out.println();
    }

    static void checkStairs(int n, int expected) {
        int actual = climbStairs(n);
        System.out.printf("%s climbStairs(%d) -> 기대:%d 결과:%d%n",
                expected == actual ? "[통과]" : "[실패]", n, expected, actual);
    }

    static void checkRob(int[] nums, int expected) {
        int actual = rob(nums);
        System.out.printf("%s rob([%s]) -> 기대:%d 결과:%d%n",
                expected == actual ? "[통과]" : "[실패]", join(nums), expected, actual);
    }

    static void checkPaths(int rows, int cols, int expected) {
        int actual = uniquePaths(rows, cols);
        System.out.printf("%s uniquePaths(%d, %d) -> 기대:%d 결과:%d%n",
                expected == actual ? "[통과]" : "[실패]", rows, cols, expected, actual);
    }

    static void checkGrid(int[][] grid, int expected) {
        int actual = minPathSum(grid);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            sb.append(i == 0 ? "" : " ").append("[").append(join(grid[i])).append("]");
        }
        System.out.printf("%s minPathSum(%s) -> 기대:%d 결과:%d%n",
                expected == actual ? "[통과]" : "[실패]", sb, expected, actual);
    }

    static void checkLcs(String a, String b, int expected) {
        int actual = lcs(a, b);
        System.out.printf("%s lcs(\"%s\", \"%s\") -> 기대:%d 결과:%d%n",
                expected == actual ? "[통과]" : "[실패]", a, b, expected, actual);
    }

    static String join(int[] nums) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            sb.append(i == 0 ? "" : ",").append(nums[i]);
        }
        return sb.toString();
    }
}
