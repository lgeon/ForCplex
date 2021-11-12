package sort;

public class SortHelper {
    public static <T extends Comparable<T>> boolean less(T obj1, T obj2){
        return obj1.compareTo(obj2) <= 0;
    }

    public static <T extends Comparable<T>> void exchange(int i, int j, T... a){
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

}
