import java.util.*;

public class PriorityQueue<E extends Comparable<E>> implements AbstractQueue<E> {
    ArrayList<E> queue;
    HashMap<E, Integer> elementsMap;
    Comparator<E> comparator;

    public PriorityQueue(Comparator<E> comparator) {
        this.comparator = comparator;
        this.queue = new ArrayList<>();
        this.elementsMap = new HashMap<>();
    }

    public boolean empty() {
        return queue.isEmpty();
    }

    public boolean push(E e) {
        if (elementsMap.containsKey(e) || e == null) {
            return false; // the element is already in the queue
        }
        queue.add(e);
        elementsMap.put(e, queue.size() - 1);
        heapUp(queue.size() - 1);
        return true;
    }

    public boolean contains(E e) {
        return elementsMap.containsKey(e);
    }

    public E top() {
        if (empty()) {
            return null;
        }
        return queue.get(0);
    }

    public void pop() {
        if (empty()) {
            return;
        }
        int lastIndex = queue.size() - 1;
        swap(0, lastIndex);
        elementsMap.remove(queue.get(lastIndex));
        queue.remove(lastIndex);
        if (!empty()) {
            heapDown(0);
        }
    }

    public boolean remove(E e) {
        if (!elementsMap.containsKey(e)) { // Element does not exist in the queue
            return false;
        }
        int indexToRemove = elementsMap.get(e);
        int lastIndex = queue.size() - 1;
        if (indexToRemove != lastIndex) {
            swap(indexToRemove, lastIndex);
        }
        queue.remove(lastIndex);
        elementsMap.remove(e);
        if (indexToRemove != lastIndex) {
            updatePriority(indexToRemove);
        }
        return true;
    }

    private void heapUp(int index) {
        E element = queue.get(index);
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            E parent = queue.get(parentIndex);
            if (comparator.compare(element, parent) >= 0) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    private void heapDown(int index) {
        int size = queue.size();
        E element = queue.get(index);
        while (true) {
            int childIndex = 2 * index + 1;
            if (childIndex >= size) {
                break;
            }
            if (childIndex + 1 < size && comparator.compare(queue.get(childIndex + 1), queue.get(childIndex)) < 0) {
                childIndex++;
            }
            if (comparator.compare(queue.get(childIndex), element) >= 0) {
                break;
            }
            swap(index, childIndex);
            index = childIndex;
        }
    }

    private void swap(int i, int j) {
        E temp = queue.get(i);
        queue.set(i, queue.get(j));
        queue.set(j, temp);
        elementsMap.put(queue.get(i), i);
        elementsMap.put(queue.get(j), j);
    }

    private void updatePriority(int index) {
        if (index > 0 && comparator.compare(queue.get(index), queue.get((index - 1) / 2)) < 0) {
            heapUp(index);
        } else {
            heapDown(index);
        }
    }

}