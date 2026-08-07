package com.example.study.practice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/*
 * ============================================================
 *  [카카오 2022 블라인드] 주차 요금 계산
 *  유형: 구현 / 문자열 파싱 / Map & TreeMap
 *  난이도: 프로그래머스 Lv 2 (카카오 블라인드 대표 구현 문제)
 * ============================================================
 *
 * [문제 설명]
 * 주차장의 요금표(fees)와 차량의 입출차 기록(records)이 주어집니다.
 * 차량 번호가 작은 자동차부터 청구할 최종 주차 요금을 순서대로 배열에 담아 반환하세요.
 *
 * 요금표 fees: [기본 시간(분), 기본 요금(원), 단위 시간(분), 단위 요금(원)]
 * 예) [180, 5000, 10, 600]
 *     - 180분 이하: 5,000원
 *     - 180분 초과 시: 5,000원 + ceil((누적시간 - 180) / 10) * 600원
 *
 * 입출차 기록 records: ["HH:MM 차량번호 IN/OUT", ...]
 * 예) ["05:34 5961 IN", "06:00 0000 IN", "06:34 0000 OUT", "07:59 5961 OUT", "22:59 5961 IN", "23:00 5961 OUT"]
 *
 * 규칙:
 * 1. 입차 후 출차된 기록이 없다면 23:59에 출차된 것으로 간주합니다.
 * 2. 누적 주차 시간이 기본 시간 이하라면 기본 요금을 청구합니다.
 * 3. 초과 시간이 단위 시간으로 나누어 떨어지지 않으면 올림(ceil) 처리합니다.
 * 4. 차량 번호 오름차순으로 요금을 정렬하여 반환합니다.
 *
 * ------------------------------------------------------------
 * [이 유형의 핵심 개념]
 *
 * 1. 시각 -> 분(Minute) 변환:
 *    "HH:MM" 형태의 문자열을 `시 * 60 + 분` 형태의 정수로 변환하면 시간 계산이 훨씬 쉬워집니다.
 *    예: "05:34" -> 5 * 60 + 34 = 334분, "23:59" -> 23 * 60 + 59 = 1439분
 *
 * 2. 차량 번호 자동 정렬 (TreeMap):
 *    Key를 차량 번호(String)로 관리하는 `TreeMap`을 사용하면 키가 자동으로 오름차순 정렬됩니다.
 *
 * 3. 요금 계산 (올림 공식):
 *    Math.ceil((double) (totalTime - baseTime) / unitTime) * unitFee
 *
 * ------------------------------------------------------------
 * [힌트]
 *
 * Map<String, Integer> inTimeMap = new HashMap<>();
 * Map<String, Integer> totalTimeMap = new TreeMap<>(); // 차량번호 기준 자동 정렬
 *
 * 1. records 순회하며 IN 시각과 OUT 시각 차이를 totalTimeMap에 누적합니다.
 * 2. inTimeMap에 남아있는 차량은 23:59(1439분)에 출차한 것으로 간주하여 주차 시간을 누적합니다.
 * 3. totalTimeMap의 주차 시간을 기준으로 요금을 계산합니다.
 */
public class st_0804_kakao1_parking {

    public static int[] solution(int[] fees, String[] records) {
        int baseTime = fees[0];
        int baseFee = fees[1];
        int unitTime = fees[2];
        int unitFee = fees[3];

        Map<String, Integer> inTimeMap = new HashMap<>();
        Map<String, Integer> totalTimeMap = new TreeMap<>(); // Key(차량번호) 오름차순 자동 정렬

        for (String record : records) {
            String[] parts = record.split(" ");
            int timeInMinutes = parseMinutes(parts[0]);
            String carNum = parts[1];
            String type = parts[2];

            if (type.equals("IN")) {
                inTimeMap.put(carNum, timeInMinutes);
            } else {
                int inTime = inTimeMap.remove(carNum);
                int duration = timeInMinutes - inTime;
                totalTimeMap.put(carNum, totalTimeMap.getOrDefault(carNum, 0) + duration);
            }
        }

        // 출차 기록이 없는 차량은 23:59(1439분) 출차 처리
        int maxTime = parseMinutes("23:59");
        for (Map.Entry<String, Integer> entry : inTimeMap.entrySet()) {
            String carNum = entry.getKey();
            int inTime = entry.getValue();
            int duration = maxTime - inTime;
            totalTimeMap.put(carNum, totalTimeMap.getOrDefault(carNum, 0) + duration);
        }

        // 요금 계산
        int[] answer = new int[totalTimeMap.size()];
        int idx = 0;
        for (int totalTime : totalTimeMap.values()) {
            if (totalTime <= baseTime) {
                answer[idx++] = baseFee;
            } else {
                int extraTime = totalTime - baseTime;
                int extraFee = (int) Math.ceil((double) extraTime / unitTime) * unitFee;
                answer[idx++] = baseFee + extraFee;
            }
        }

        return answer;
    }

    private static int parseMinutes(String timeStr) {
        String[] split = timeStr.split(":");
        return Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
    }

    // =========================================================
    // ↓↓↓ 아래는 채점용입니다. 건드리지 않아도 됩니다. ↓↓↓
    // =========================================================

    public static void main(String[] args) {
        System.out.println("===== [카카오 2022 블라인드] 주차 요금 계산 =====");

        check(new int[] { 180, 5000, 10, 600 },
                new String[] { "05:34 5961 IN", "06:00 0000 IN", "06:34 0000 OUT", "07:59 5961 OUT", "07:59 0148 IN",
                        "18:59 0148 OUT", "22:59 5961 IN" },
                new int[] { 5000, 33800, 6800 });

        check(new int[] { 120, 0, 60, 591 },
                new String[] { "16:00 3585 IN", "16:00 0546 IN", "16:00 2368 IN", "18:00 3585 OUT", "18:00 0546 OUT",
                        "18:59 2368 OUT" },
                new int[] { 0, 591, 0 });

        check(new int[] { 1, 900, 1, 900 },
                new String[] { "00:00 1234 IN" },
                new int[] { 1295100 });
    }

    static void check(int[] fees, String[] records, int[] expected) {
        int[] actual = solution(fees, records);
        if (Arrays.equals(actual, expected)) {
            System.out.println("[PASS] 결과: " + Arrays.toString(actual));
        } else {
            System.out.println("[FAIL] 예상값: " + Arrays.toString(expected) + ", 실제값: " + Arrays.toString(actual));
        }
    }
}
