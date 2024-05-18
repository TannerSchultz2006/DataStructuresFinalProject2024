package cos.dstruct.klassroom.datastructures;

import java.util.ArrayList;

public class MinHeap<T extends Comparable<? super T>> {

    public ArrayList<T> heap;
    public int size;

    public MinHeap() {
        heap = new ArrayList<T>();
        size = 0;
    }

    public MinHeap(ArrayList<T> array) {
        buildHeap(array);
    }

    public static <Type extends Comparable<? super Type>> ArrayList<Type> heapSort(ArrayList<Type> array) {
        MinHeap<Type> bMh = new MinHeap<>(array);

        for (int i = array.size() - 1; i > 0; i--) {
            array.set(i, bMh.deleteMin());
        }
        return array;
    }

    public void buildHeap(ArrayList<T> array) {
        heap = array;
        size = array.size();
        // from the parent of the last node, all the way to the top
        for (int i = (size - 2) / 2; i >= 0; i--) {
            percDown(i);
        }
    }

    public T deleteMin() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException("Empty heap has no max");
        }
        T max_item = heap.get(0);
        heap.set(0, heap.get(size - 1));
        size--;
        percDown(0);
        return max_item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T findMin() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException("Empty heap has no max");
        }
        return heap.get(0);
    }

    public void insert(T item) {
        int curIndex;
        T tmp;

        curIndex = size;
        size++;
        heap.add(item);
        percUp(curIndex);

    }

    private void percDown(int curIndex) {
        int leftChild, rightChild, swapIndex;
        T tmp;

        while (true) {
            leftChild = curIndex * 2 + 1;
            rightChild = leftChild + 1;
            if (leftChild >= size) { // is leaf? done
                break;
            }
            swapIndex = curIndex;
            if (heap.get(leftChild).compareTo(heap.get(curIndex)) < 0) { // smallest of left and cur
                swapIndex = leftChild;
            }
            if (rightChild < size) { // smallest of right and swapIndex
                if (heap.get(rightChild).compareTo(heap.get(swapIndex)) < 0) {
                    swapIndex = rightChild;
                }
            }
            if (swapIndex == curIndex) { // if cur biggest done
                break;
            }
            tmp = heap.get(curIndex);
            heap.set(curIndex, heap.get(swapIndex));
            heap.set(swapIndex, tmp);

            curIndex = swapIndex;
        }
    }

    private void percUp(int curIndex) {
        int parent;
        T tmp;
        parent = (curIndex - 1) / 2;

        while (heap.get(curIndex).compareTo(heap.get(parent)) < 0) {
            tmp = heap.get(parent);
            heap.set(parent, heap.get(curIndex));
            heap.set(curIndex, tmp);
            curIndex = parent;
            parent = (curIndex - 1) / 2;
        }
    }

}
