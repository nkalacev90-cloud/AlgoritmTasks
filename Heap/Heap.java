package AlgoritmTask.Heap;

import java.util.ArrayList;

public class Heap {
    private ArrayList<Integer> heap;

    public Heap() {
        heap = new ArrayList<>();
    }

    // Индексы в бинарной куче (корень = 0)
    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public void insert(int value) {
        heap.add(value);
        int i = heap.size() - 1;
        while (i > 0 && heap.get(parent(i)) < heap.get(i)) {
            swap(i, parent(i));
            i = parent(i);
        }
    }


    public void extractNumber(int i) {
        if (heap.isEmpty()) {
            throw new RuntimeException("Куча пуста");
        }
        int index = heap.indexOf(i);
        int last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(index, last);
            siftDown(index);
        }
    }

    // Просеивание вниз: поддерживаем свойство кучи
    private void siftDown(int i) {
        int largest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < heap.size() && heap.get(left) > heap.get(largest)) {
            largest = left;
        }
        if (right < heap.size() && heap.get(right) > heap.get(largest)) {
            largest = right;
        }

        if (largest != i) {
            swap(i, largest);
            siftDown(largest);
        }
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    public Integer peek() {
        if (heap.isEmpty()) {
            return null;
        }
        return heap.get(0);
    }

    // Для отладки: текущее состояние кучи
    public ArrayList<Integer> getHeap() {
        return new ArrayList<>(heap);
    }

    public static ArrayList<Integer> sortHeap(int[] arr){
        Heap t = new Heap();
        for (int i = 0; i < arr.length; i++){
            t.insert(arr[i]);
        }
        return new ArrayList<>(t.getHeap());
    }

    public static void main(String[] args) {
        Heap heap = new Heap();

        int[] arr1 = {10, 5, 20, 3, 15, 100};
        int[] arr2 = {10, 5, 20, 3, 15, 100};

        for (int i = 0; i < arr1.length; i++) {
            heap.insert(arr1[i]);
        }

        int i = 20;

        heap.extractNumber(i);

        System.out.println("Состояние кучи1: " + heap.getHeap());
        System.out.println("Состояние кучи2: " + Heap.sortHeap(arr2));
        System.out.println("Извлекаем число из кучи1: " + i);
        System.out.println("Кучи1 после извлечения: " + heap.getHeap());
    }
}
