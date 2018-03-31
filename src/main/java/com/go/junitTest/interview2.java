package com.go.junitTest;

public class interview2 {

    public static int[] mp(int a[]) {
        int swap = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j < a.length; j++) {
                if (a[i] > a[j]) {
                    swap = a[j];
                    a[j] = a[i];
                    a[i] = swap;
                }
            }
        }
        return a;
    }

    public static int[] xz(int a[]) {
        int swap, tem;
        for (int i = 0; i < a.length - 1; i++) {
            swap = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[swap]) {
                    swap = j;
                }
            }
            tem = a[i];
            a[i] = a[swap];
            a[swap] = tem;
        }
        return a;
    }


    public static int[] insertionSort(int arr[]) {
        int len = arr.length;
        int preIndex, current;
        for (int i = 1; i < len; i++) {
            preIndex = i - 1;
            current = arr[i];
            while (preIndex >= 0 && arr[preIndex] > current) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex + 1] = current;
        }
        return arr;
    }


    public static int[] cr(int a[]) {
        int preIndex, current;
        for (int i = 0; i < a.length; i++) {
            preIndex = i - 1;
            current = a[i];
            for (int j = preIndex; j >= 0 && a[preIndex] > current; j--) {
                a[preIndex + 1] = a[preIndex];
                preIndex--;
            }
            /*while (preIndex >= 0 && a[preIndex] > current) {
                a[preIndex + 1] = a[preIndex];
                preIndex--;
            }*/
            a[preIndex + 1] = current;
        }
        return a;
    }


    public static void sortQ(int arr[], int low, int hight) {
        if (low > hight) {
            return;
        }
        int i = low;
        int j = hight;
        int index = arr[low];
        //查找
        while (i < j) {
            //从左边找
            while (i < j && arr[j] > index) {
                j--;
            }
            //从右边找
            while (i < j && arr[i] <= index) {
                i++;
            }
            if (i < j) {
                int p = arr[j];
                arr[j] = arr[i];
                arr[i] = p;
            }
        }
        //调整index的位置
        int swap = arr[i];
        arr[i] = arr[low];
        arr[low] = swap;
        sortQ(arr, low, i - 1);
        sortQ(arr, i + 1, hight);
    }


    public static void zs(int n) {
        int count = 0;
        int j;
        for (int i = 2; i <= n; i++) {// 1不是素数，所以直接从2开始循环
            j = 2;
            while (i % j != 0) {
                j++; // 测试2至i的数字是否能被i整除，如不能就自加
            }
            if (j == i) {// 当有被整除的数字时，判断它是不是自身
                System.out.print(i + "-"); // 如果是就打印出数字
                /*if (isSS(i - 1) && isSS(i + 1)) {
                    count++;

                }*/
            }
        }
        System.out.println(count + "---");
    }

    public static Boolean isSS(int n) {
        int i = 2;
        while (n % i != 0) {
            i++;
        }
        if (n == i) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int a[] = {5, 4, 6, 9, 4, 3, 2, 7, 8, 6};
//        interview2.sortQ(a, 0, a.length - 1);
        interview2.zs(1000);
        for (int i = 0; i < a.length; i++) {
//            System.out.print(a[i] + " ");
        }
    }
}
