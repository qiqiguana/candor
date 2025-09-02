package original;

import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Queue;
class Solution2402 {
    public int mostBooked(int n, int[][] meetings) {
  /**
  You are given an integer n. There are n rooms numbered from 0 to n - 1. You are given a 2D integer array meetings where meetings[i] = [starti, endi] means that a meeting will be held during the half-closed time interval [starti, endi). All the values of starti are unique. Meetings are allocated to rooms in the following manner: Each meeting will take place in the unused room with the lowest number. If there are no available rooms, the meeting will be delayed until a room becomes free. The delayed meeting should have the same duration as the original meeting. When a room becomes unused, meetings that have an earlier original start time should be given the room. Return the number of the room that held the most meetings. If there are multiple rooms, return the room with the lowest number. A half-closed interval [a, b) is the interval between a and b including a and not including b. &nbsp; Example 1: Input: n = 2, meetings = [[0,10],[1,5],[2,7],[3,4]] Output: 0 Explanation: - At time 0, both rooms are not being used. The first meeting starts in room 0. - At time 1, only room 1 is not being used. The second meeting starts in room 1. - At time 2, both rooms are being used. The third meeting is delayed. - At time 3, both rooms are being used. The fourth meeting is delayed. - At time 5, the meeting in room 1 finishes. The third meeting starts in room 1 for the time period [5,10). - At time 10, the meetings in both rooms finish. The fourth meeting starts in room 0 for the time period [10,11). Both rooms 0 and 1 held 2 meetings, so we return 0. Example 2: Input: n = 3, meetings = [[1,20],[2,10],[3,5],[4,9],[6,8]] Output: 1 Explanation: - At time 1, all three rooms are not being used. The first meeting starts in room 0. - At time 2, rooms 1 and 2 are not being used. The second meeting starts in room 1. - At time 3, only room 2 is not being used. The third meeting starts in room 2. - At time 4, all three rooms are being used. The fourth meeting is delayed. - At time 5, the meeting in room 2 finishes. The fourth meeting starts in room 2 for the time period [5,10). - At time 6, all three rooms are being used. The fifth meeting is delayed. - At time 10, the meetings in rooms 1 and 2 finish. The fifth meeting starts in room 1 for the time period [10,12). Room 0 held 1 meeting while rooms 1 and 2 each held 2 meetings, so we return 1. &nbsp; Constraints: 1 &lt;= n &lt;= 100 1 &lt;= meetings.length &lt;= 105 meetings[i].length == 2 0 &lt;= starti &lt; endi &lt;= 5 * 105 All the values of starti are unique.
  */
        Arrays.sort(meetings, (a, b) -> a[0] - b[0]);
        PriorityQueue<int[]> busy
            = new PriorityQueue<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        PriorityQueue<Integer> idle = new PriorityQueue<>();
        for (int i = 0; i < n; ++i) {
            idle.offer(i);
        }
        int[] cnt = new int[n];
        for (var v : meetings) {
            int s = v[0], e = v[1];
            while (!busy.isEmpty() && busy.peek()[0] <= s) {
                idle.offer(busy.poll()[1]);
            }
            int i = 0;
            if (!idle.isEmpty()) {
                i = idle.poll();
                busy.offer(new int[] {e, i});
            } else {
                var x = busy.poll();
                i = x[1];
                busy.offer(new int[] {x[0] + e - s, i});
            }
            ++cnt[i];
        }
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            if (cnt[ans] < cnt[i]) {
                ans = i;
            }
        }
        return ans;
    }
}