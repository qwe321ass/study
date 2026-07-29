package com.example.study.practice;

/*
 * ============================================================
 *  일일 온도   [유형: 스택 (단조 스택)]   (난이도: 중)
 * ============================================================
 *
 * 날짜별 기온 배열이 주어집니다. 각 날짜에 대해
 * "며칠 뒤에 더 따뜻해지는가"를 구하세요. 그런 날이 없으면 0.
 *
 * 예) [73,74,75,71,69,72,76,73]
 *     -> [1, 1, 4, 2, 1, 1, 0, 0]
 *
 *     73 -> 다음날 74로 오름          : 1
 *     74 -> 다음날 75로 오름          : 1
 *     75 -> 4일 뒤 76이 되어야 오름    : 4
 *     71 -> 2일 뒤 72                : 2
 *     69 -> 다음날 72                : 1
 *     72 -> 다음날 76                : 1
 *     76 -> 이후에 더 따뜻한 날 없음    : 0
 *     73 -> 이후에 더 따뜻한 날 없음    : 0
 *
 * 제한: 배열 길이 최대 100,000  (이중 for문은 시간 초과)
 *
 * ------------------------------------------------------------
 * [이 유형의 핵심 개념]
 *
 * 스택은 "아직 답을 못 정한 것들을 잠깐 쌓아두는 곳"으로 쓸 때 강력합니다.
 *
 * 왼쪽부터 훑으면서 생각해보세요.
 *   73을 봤을 때는 아직 답을 모릅니다.       -> "미결" 상태로 스택에 넣어둠
 *   다음에 74를 보면? 스택 맨 위 73보다 큼   -> 73의 답이 지금 확정됨! (1일)
 *   확정된 건 스택에서 꺼냅니다(pop).
 *
 * 이렇게 하면 스택 안에는 항상 "내려가는 순서"의 값만 남습니다.
 * (73을 꺼냈으니 남은 건 73보다 큰 것들뿐)
 * 그래서 단조 스택(monotonic stack) 이라고 부릅니다.
 *
 * ★ 왜 O(N)인가:
 *   while문이 안에 있어서 O(N^2) 같아 보이지만, 각 원소는
 *   스택에 딱 한 번 들어가고 딱 한 번 나옵니다. 그래서 총 2N번입니다.
 *
 * ★ 이 패턴을 쓰는 신호:
 *   "다음으로 더 큰/작은 값" 을 묻는 문제라면 거의 항상 단조 스택입니다.
 *
 * ------------------------------------------------------------
 * [힌트]
 *
 * - 스택에는 기온이 아니라 인덱스 를 넣으세요.
 *   "며칠 뒤"를 계산하려면 위치를 알아야 하니까요. (현재 i - 꺼낸 인덱스)
 *
 * - 뼈대:
 *     int[] answer = new int[temps.length];
 *     Deque<Integer> stack = new ArrayDeque<>();
 *
 *     for (int i = 0; i < temps.length; i++) {
 *         while (!stack.isEmpty() && temps[i] > temps[stack.peek()]) {
 *             int idx = stack.pop();
 *             answer[idx] = i - idx;
 *         }
 *         stack.push(i);
 *     }
 *     return answer;
 *
 * - 끝까지 스택에 남은 것들은 답이 0인데,
 *   int 배열은 0으로 자동 초기화되니 그냥 두면 됩니다.
 *
 * ------------------------------------------------------------
 * [Java 사용법 - Deque]
 *
 *   Deque<Integer> stack = new ArrayDeque<>();
 *   stack.push(x)     넣기
 *   stack.pop()       꺼내면서 제거
 *   stack.peek()      맨 위 보기 (제거 안 함)
 *   stack.isEmpty()   비었나?
 *
 * ★ Stack 클래스도 있지만 내부에 동기화가 걸려 있어 느립니다.
 *   실무/코테에서는 ArrayDeque 를 씁니다.
 *
 * ------------------------------------------------------------
 * [자주 하는 실수]
 *
 * - stack.isEmpty() 체크를 while 조건 앞에 안 두면
 *   빈 스택에서 peek() 해서 예외가 납니다. 순서 주의.
 *
 * - 기온 값을 스택에 넣으면 "며칠 뒤"를 계산할 수 없습니다. 인덱스를 넣으세요.
 *
 * - >= 로 비교하면 같은 기온일 때도 꺼내버립니다.
 *   "더 따뜻한" 이므로 > 가 맞습니다.
 *
 * ------------------------------------------------------------
 * [연습 더 하고 싶으면 - 같은 유형]
 * - 올바른 괄호 문자열, 큰 수 만들기, 히스토그램에서 가장 큰 직사각형
 */
public class st_0729_m2_stack {

    static int[] dailyTemperatures(int[] temps) {
        // TODO
        return new int[temps.length];
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    public static void main(String[] args) {
        System.out.println("===== dailyTemperatures (스택) =====");
        check(new int[] { 73, 74, 75, 71, 69, 72, 76, 73 },
                new int[] { 1, 1, 4, 2, 1, 1, 0, 0 });
        check(new int[] { 30, 40, 50, 60 }, new int[] { 1, 1, 1, 0 });
        check(new int[] { 30, 60, 90 }, new int[] { 1, 1, 0 });
        check(new int[] { 50 }, new int[] { 0 });
        check(new int[] { 90, 80, 70 }, new int[] { 0, 0, 0 });
        check(new int[] { 55, 38, 65, 52, 49, 71, 45 }, new int[] { 2, 1, 3, 2, 1, 0, 0 });
        check(new int[] { 70, 70, 70, 71 }, new int[] { 3, 2, 1, 0 });
    }

    static void check(int[] temps, int[] expected) {
        int[] actual = dailyTemperatures(temps);
        boolean ok = actual != null && arrToStr(actual).equals(arrToStr(expected));
        System.out.printf("%s [%s] -> 기대:[%s] 결과:[%s]%n",
                ok ? "[통과]" : "[실패]", arrToStr(temps),
                arrToStr(expected), actual == null ? "null" : arrToStr(actual));
    }

    static String arrToStr(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(i == 0 ? "" : ",").append(arr[i]);
        }
        return sb.toString();
    }
}
