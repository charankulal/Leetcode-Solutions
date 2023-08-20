class Solution {
    public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        
        Map<Integer, List<Integer>> groupMap = buildGroup(n, m, group);
        Map<Integer, List<Integer>> groupGraph = buildGroupGraph(n, group, beforeItems);
        List<Integer> sortedGroupIds = topoSort(groupGraph, groupMap.keySet());
        List<Integer> rst = new ArrayList<>();
        for (int groupId : sortedGroupIds) {
            Map<Integer, List<Integer>> itemGraph = buildItemGraph(groupMap.get(groupId), beforeItems);
            List<Integer> sortedItemIds = topoSort(itemGraph, new HashSet<>(groupMap.get(groupId)));
            rst.addAll(sortedItemIds);
        }
        
        if (rst.size() != n) return new int[] {};
        
        int[] result = new int[n];
        for (int i = 0; i < rst.size(); i++) {
            result[i] = rst.get(i);
        }
        return result;
    }
    
    private Map<Integer, List<Integer>> buildGroup(int n, int m, int[] group) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int groupId = group[i] == -1 ? m++ : group[i];
            group[i] = groupId;
            map.putIfAbsent(groupId, new ArrayList<>());
            map.get(groupId).add(i);
        }
        return map;
    }
    
    private List<Integer> topoSort(Map<Integer, List<Integer>> graph, Set<Integer> set) {
        Map<Integer, Integer> indegree = new HashMap<>();
        for (int item : set) { // init all elements from the set
            indegree.put(item, 0);
        }
        for (int node : graph.keySet()) { // sink node
            if (!set.contains(node)) continue; // ignore the dependency on nodes outside of the target set
            for (int parent : graph.get(node)) { // parent node
                indegree.put(parent, indegree.get(parent) + 1);
            }
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int key : indegree.keySet()) {
            if (indegree.get(key) == 0) queue.offer(key);
        }
        
        List<Integer> rst = new ArrayList<>();
        while (!queue.isEmpty()) {
            int node = queue.poll();
            rst.add(node);
            if (!graph.containsKey(node)) continue;
            for (int parent : graph.get(node)) {
                indegree.put(parent, indegree.get(parent) - 1);
                if (indegree.get(parent) == 0) queue.offer(parent);
            }
        }
        
        return rst;
    }
    
    private Map<Integer, List<Integer>> buildItemGraph(List<Integer> items, List<List<Integer>> beforeItems) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i : items) {
            for (int item : beforeItems.get(i)) {
                graph.putIfAbsent(item, new ArrayList<>());
                graph.get(item).add(i);
            }
        }
        return graph;
    }

    private Map<Integer, List<Integer>> buildGroupGraph(int n, int[] group, List<List<Integer>> beforeItems) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int groupId = group[i];
            for (int item : beforeItems.get(i)) {
                if (groupId == group[item]) continue;
                graph.putIfAbsent(group[item], new ArrayList<>());
                graph.get(group[item]).add(groupId);
            }
        }
        return graph;
    }

}
