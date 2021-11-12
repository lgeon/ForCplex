package entity;

import sort.SortHelper;


/**
 * 已改为小顶堆
 * @param <T>
 */
public  class Heap <T extends Comparable<T>> {
    private int size = 0;
    private T[] elements;

    public Heap(T[] e){
        this.elements = e;
        this.size = elements.length - 1;
    }

    public Heap(int size){
        elements = (T[]) new Comparable[size];
    }

    public T[] getElements(){
        return this.elements;
    }

    public void addElement(T e){
        elements[++size] = e;
        swim(size);
    }

    public T removeElement(){
        T max = elements[1];
        SortHelper.exchange(1, size, elements);
        elements[size--] = null;
        sink(1);

        return max;
    }

    public int getSize(){
         return this.size;
    }

    public boolean contains(int w){
        for(int i = 1; i <= this.size; i++){
            Vertices v = (Vertices) elements[i];
            if(v.getV() == w)
                return true;
        }
        return false;
    }

    public void changeDist(int w, double dist){
        for(int i = 1; i <= this.size; i++){
            Vertices t = (Vertices) elements[i];
            if(t.getV() == w)
                t.setDist(dist);
        }
    }

    public void swim(int k){
        while(k > 1 && SortHelper.less(elements[k], elements[k/2])){
            SortHelper.exchange(k/2, k, elements);
            k /= 2;
        }
    }

    public void sink(int k){
        while (2*k <= size){
            int j = 2*k;
            if( j + 1 <= size && SortHelper.less(elements[j+1], elements[j]))
                j++;
            if(!SortHelper.less(elements[j], elements[k]))
                break;
            SortHelper.exchange(k, j, elements);
            k = j;
        }
    }

}
