class Solution {
    public int shortestPathLength(int[][] graph) {
        int len = graph.length;
        int expect = (1 << len) - 1; //This set all bits to 1.
        Queue<int[]> q = new LinkedList<>();    // int[] saves current node, visited states
        for(int i = 0; i < len; i++)
            q.offer(new int[]{i, 1 << i});  // We could start from any points.
        // if for current node and state, we visit it again will be dulplicate, we can continue.
        boolean[][] visited = new boolean[len][1 << len];
        int step = -1;
        while(!q.isEmpty()){
            int size = q.size();
            ++step;
            for(int i = 0; i < size; i++){
                int[] pair = q.poll();
                int node = pair[0];
                int state = pair[1];
                // We've visited all of the nodes
                if(state == expect) return step;
                if(visited[node][state]) continue;
                visited[node][state] = true;
                for(int next : graph[node]){
                    q.offer(new int[]{next, state | (1 << next)});
                }
            }
        }
        return -1;
    }
}
