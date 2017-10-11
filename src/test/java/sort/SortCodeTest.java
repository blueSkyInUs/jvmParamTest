package sort;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by lesson on 2017/10/10.
 */
public class SortCodeTest {

    private int []ages;
    private int [] result;

    private static final int DATA_LENGTH=1000000;
    @Before
    public void initData(){
        ages=getInitData();
        result=new int[ages.length];
    }


    @Test
    public void insertSort(){
        assert ages!=null&&ages.length>0;

        if (ages.length==1){
            System.out.println("already sort");
            System.out.println(Arrays.toString(ages));
        }


        for (int i=0;i<ages.length-1;i++){
            for (int j=i+1;j>0;j--){
                if (ages[j]>=ages[j-1]){
                    break;
                }
                int tmp=ages[j];
                ages[j]=ages[j-1];
                ages[j-1]=tmp;
            }
        }
    }

    @Test
    public void chooseSort(){
        assert ages!=null&&ages.length>0;

        if (ages.length==1){
            System.out.println("already sort");
            System.out.println(Arrays.toString(ages));
        }

        for (int i=0;i<ages.length-1;i++){
            int min=i;
            for (int j=i+1;j<ages.length;j++){
                if (ages[j]<ages[min]){
                    min=j;
                }
            }
            if (min!=i){
                int tmp=ages[i];
                ages[i]=ages[min];
                ages[min]=tmp;
            }
        }
    }

    @Test
    public void heapSort(){
        int middleIndex=(ages.length-1>>1) -1;

        for (int i=middleIndex;i>=0;i--){
            maxHeap(i,ages.length-1);
        }

        for (int i=ages.length-1;i>0;i--){
            int tmp=ages[0];
            ages[0]=ages[i];
            ages[i]=tmp;
            maxHeap(0,i-1);
        }

    }

    @Test
    public void buddleSort(){
        for (int i=ages.length-1;i>=0;i--){
            for (int j=0;j<i;j++){
                 if (ages[j]>ages[j+1]){
                     int tmp=ages[j];
                     ages[j]=ages[j+1];
                     ages[j+1]=tmp;
                 }
            }
        }

    }

    @Test
    public void quicklySort(){
        internalQuickSort(0,ages.length-1);
    }


    @Test
    public void mergeSort(){
        internalMergeSort(0,ages.length-1);
    }

    private void internalMergeSort(int begin, int end) {
        if (begin>=end)return;

        int mid= ((end-begin)>>1)+begin;

        internalMergeSort(begin, mid);
        internalMergeSort(mid+1,end);

        int begin1=begin,end1=mid,begin2=mid+1,end2=end;

        int k=begin;
        while (begin1<=end1&& begin2<=end2){
            result[k++]= ages[begin1]<ages[begin2] ?ages[begin1++] :ages[begin2++];
        }
        while (begin1<=end1){
            result[k++]=ages[begin1++];
        }
        while (begin2<=end2){
            result[k++]=ages[begin2++];
        }

        for (int i=begin;i<=end;i++){
            ages[i]=result[i];
        }




    }

    private void internalQuickSort(int beginIndex,int endIndex) {
        if (beginIndex>=endIndex)return;

        int mid=ages[endIndex];

        int left=beginIndex,right=endIndex-1;

        while (left<right){
            while ( ages[left]<=mid && left<right ){
                left++;
            }

            while (ages[right]>mid && left<right){
                right--;
            }

            int tmp=ages[left];
            ages[left]=ages[right];
            ages[right]=tmp;
        }
        if (ages[left]>ages[endIndex]){
            swapArrayElement(left,endIndex);
        }else{
            swapArrayElement(++left,endIndex);
        }
        internalQuickSort(beginIndex, left-1);
        internalQuickSort(left+1,endIndex);


    }

    private void swapArrayElement(int index1,int index2){
        int tmp=ages[index1];
        ages[index1]=ages[index2];
        ages[index2]=tmp;
    }

    @After
    public void validateResult(){
        assert validate(ages);
    }

    private void maxHeap(int index,int length){
        int leftNodeIndex=index<<1;
        int rightNodeIndex=leftNodeIndex+1;
        if (leftNodeIndex>length) return;
        int maxNodeIndex= leftNodeIndex;
        if (rightNodeIndex<=length&&ages[rightNodeIndex]>ages[leftNodeIndex]){
            maxNodeIndex=rightNodeIndex;
        }

        if (ages[index]<ages[maxNodeIndex]){
            int tmp=ages[index];
            ages[index]=ages[maxNodeIndex];
            ages[maxNodeIndex]=tmp;
            maxHeap(maxNodeIndex,length);
        }
    }


    private int[] getInitData() {
        int [] ages =new int[DATA_LENGTH];
        for (int i=0;i<DATA_LENGTH;i++){
            ages[i]=new Random().nextInt(100000);
        }
        return ages;
    }




    private boolean validate(int[] ages) {
       for (int i=0;i<ages.length-1;i++){
           if (ages[i]>ages[i+1]){
               return false;
           }
       }
        System.out.println(Arrays.toString(ages));
       return true;
    }


}
