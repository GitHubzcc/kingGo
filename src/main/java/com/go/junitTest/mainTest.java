package com.go.junitTest;

import java.util.HashMap;
import java.util.Map;

public class mainTest {

    public static int[] mp(int[] a) {
        int swap;
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j < a.length; j++) {
                if (a[j] < a[i]) {
                    swap = a[i];
                    a[i] = a[j];
                    a[j] = swap;
                }
            }
        }
        return a;
    }


    public static int ef(int a[], int tag) {
        int first = 0;
        int end = a.length - 1;
        for (int i = 0; i < a.length; i++) {
            int middle = end + first / 2;
            if (a[middle] == tag) return middle;
            if (tag < a[middle]) {
                end = middle - 1;
            }
            if (tag > a[middle]) {
                first = middle + 1;
            }

        }
        return 0;
    }

    //1.输入一个正数n，输出所有和为n的连续正数序列。
    public static void getAns(int n) {
        int min = 1;
        int sum = 1;
        int max = 1;
        while (min <= n / 2 + 1) {
            if (sum == n) {
                for (int k = min; k <= max; k++) {
                    System.out.print(k + "");
                }
                System.out.println();
                sum = sum - min;
                min++;
                max++;
                sum = sum + max;
            }
            if (sum > n) {
                sum = sum - min;
                min++;
            } else {
                max++;
                sum = sum + max;
            }
        }
    }

    public static void sumN(int n) {
        int sum = 0;
//        while ()
    }

    public static int aa(){
        int i=1; int j = 10;
        do {
            if(i++>--j)continue;
        }while (i<5);
        System.out.println(i+"--"+j);
        return 0;
    }


    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<String, Object>();

//        int a[] = {3,2,5,7,3};
//        int a[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};

//        int b = mainTest.ef(a, 1);
//        mainTest.getAns(15);
//        System.out.println(15 / 2);
//        System.out.println(mainTest.aa());

        float f = 4.2f;
        Float g = new Float(4.2F);
        Double d = new Double(4.2);
        System.out.println(f==g);
        System.out.println(g==g);
        System.out.println(f==d);
        System.out.println(d.equals(f));
        System.out.println(d.equals(g));
        System.out.println(g.equals(4.2));

    }
}

