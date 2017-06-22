package util;

public class DataFormat {

	public static Integer[] toIntegerArray(int[] arr){ 
		int n=arr.length; 
		Integer[] iarr=new Integer[n]; 
		for(int i=0;i<n;i++){ 
		iarr[i]=new Integer(arr[i]); 
		} 
		return iarr; 
		}

}
