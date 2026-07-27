import java.util.HashSet;
import java.util.Set;

public class st_0727_2 {

    /*
     * 문제: 두 수의 합이 target이 되는 쌍의 개수 구하기
     *
     * 정수 배열 nums와 정수 target이 주어졌을 때,
     * 배열에서 두 수를 골라 합이 target이 되는 쌍의 개수를 구하세요.
     *
     * 조건:
     * - 서로 다른 인덱스의 두 수를 사용해야 합니다.
     * - 예시: nums = [1, 2, 3, 4, 5], target = 6
     * -> 정답: 2
     * -> (1, 5), (2, 4)
     *
     * 힌트:
     * - 이중 반복문도 가능하지만,
     * 더 효율적인 방법도 생각해볼 수 있습니다.
     */

    public static void main(String[] args) {
        int[] nums = {1, 2,5,7};
        int target = 6;

        Set<Integer> seen = new HashSet<>();
        int result = 0;

        for (int num : nums) {
            int partner = target - num;
            System.out.println(seen);
            System.out.println(partner);
            if (seen.contains(partner)) {
                result++;
            }
            seen.add(num);
        }

        System.out.println("결과: " + result);
    }
}
