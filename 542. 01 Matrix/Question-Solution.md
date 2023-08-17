## 542. 01 Matrix

Given an `m x n` binary matrix `mat`, return the distance of the nearest `0` for each cell.

The distance between two adjacent cells is `1`.

 

### Example 1:

```
Input: mat = [[0,0,0],[0,1,0],[0,0,0]]
Output: [[0,0,0],[0,1,0],[0,0,0]]
```
### Example 2:

```
Input: mat = [[0,0,0],[0,1,0],[1,1,1]]
Output: [[0,0,0],[0,1,0],[1,2,1]]
``` 

### Constraints:
```
m == mat.length
n == mat[i].length
1 <= m, n <= 104
1 <= m * n <= 104
```
`mat[i][j]` is either `0` or `1`.
There is at least one `0` in mat.

## Solution

### **Java**

```java
class Solution {
    public int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    
    public void bfs(Queue<int[]> q, int[][] matrix, int r, int c) {
        while (!q.isEmpty()) {
            int[] cell = q.poll();
            for (int[] d : dirs) {
                int x = cell[0] + d[0];
                int y = cell[1] + d[1];
                if (x < 0 || x >= r || y < 0 || y >= c || 
                    matrix[x][y] <= matrix[cell[0]][cell[1]] + 1) continue;
                q.add(new int[] {x, y});
                matrix[x][y] = matrix[cell[0]][cell[1]] + 1;
            }
        }
    }
    
    public int[][] updateMatrix(int[][] matrix) {
        int r = matrix.length;
        int c = matrix[0].length;
        
        Queue<int[]> q = new LinkedList<>();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (matrix[i][j] == 0) {
                    q.offer(new int[] {i, j});
                }
                else {
                    matrix[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        
        bfs(q, matrix, r, c);
        
        
        
        return matrix;
    }
}
```
