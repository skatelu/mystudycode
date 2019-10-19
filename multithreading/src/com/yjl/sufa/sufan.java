package com.yjl.sufa;

public class sufan {

    /**
     * 快速排序
     */
    public void quickSort(int[] arr,int start, int end){
        int i,j,temp,t;
        i = start;
        j = end;
        temp = arr[start];
        if(start > end){
            return;
        }


    }



    /**
     * 冒泡排序
     */
    public static void BubbleSort(int[] array){
        //利用嵌套for循环 来实现
        int temp = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if(array[j] > array[j+1]){
                    //交换两个数组元素的值
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }

        //遍历输出数组元素
        for (int i : array) {
            System.out.println(i + ",");
        }

    }

    public static void main(String[] args) {
        int[] array = {15, 23, 8, 10, 7};

        int temp = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if(array[j] > array[j+1]){
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }

        for (int i : array) {
            System.out.println(i+",");
        }
    }
}
