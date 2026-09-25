package AlgoritmTask;

import java.util.Arrays;

class treeNode {
    int val;
    treeNode left;
    treeNode right;

    treeNode(int val) {
        this.val = val;
    }
}

public class BalancedBSTBuilder {

    public static treeNode buildBalancedBST(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }

        java.util.Arrays.sort(arr);
        return buildFromSorted(arr, 0, arr.length - 1);
    }

    private static treeNode buildFromSorted(int[] arr, int left, int right) {
        // Базовый случай: диапазон пуст
        if (left > right) {
            return null;
        }

        int mid = left + (right - left) / 2;

        treeNode node = new treeNode(arr[mid]);

        node.left = buildFromSorted(arr, left, mid - 1);
        node.right = buildFromSorted(arr, mid + 1, right);

        return node;
    }

    public static void main(String[] args) {
        int[] arr = {17, 6, 5, 20, 19, 18, 11, 14, 12, 13, 2, 4, 10};
        treeNode root = buildBalancedBST(arr);

        System.out.println("Родитель с дочерними:");
        printParentWithChildren(root);
        System.out.println(Arrays.toString(arr));

    }

    public static void printParentWithChildren(treeNode node) {
        if (node == null) {
            return;
        }

        // Выводим родителя
        System.out.print("Родитель: " + node.val);

        // Выводим левого ребёнка
        if (node.left != null) {
            System.out.print(", Левое: " + node.left.val);
        } else {
            System.out.print(", Левое: null");
        }

        // Выводим правого ребёнка
        if (node.right != null) {
            System.out.print(", Правое: " + node.right.val);
        } else {
            System.out.print(", Правое: null");
        }
        System.out.println();

        // Рекурсивно идём по детям
        printParentWithChildren(node.left);
        printParentWithChildren(node.right);
    }
}
