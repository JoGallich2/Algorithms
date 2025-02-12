/**
 *
 * @author Ouda
 */

//importing the libraries that will be needed in this program

import javax.swing.*;
import java.awt.*;
import java.util.*;

//The class that has all the sorts in it
public class SortShow extends JPanel { 

	
		// An array to hold the lines_lengths to be sorted
		public int[] lines_lengths;
		//The amount of lines needed
		public final int total_number_of_lines = 256;
		 // An array to holds the scrambled lines_lengths
		public int[] scramble_lines;
		//A temp Array that is used later for sorts
		public int[] tempArray;
		
		//the default constructor for the SortShow class
		public SortShow(){
			//assigning the size for the lines_lengths below
			lines_lengths = new int[total_number_of_lines];
			for(int i = 0; i < total_number_of_lines; i++) 
				lines_lengths[i] =  i+5;
			
		}
		

		//A method that scrambles the lines
		public void scramble_the_lines(){
			//A random generator
			Random num = new Random(); 
			//Randomly switching the lines
			for(int i = 0; i < total_number_of_lines; i++){
				//getting a random number using the nextInt method (a number between 0 to i + 1)
				int j = num.nextInt(i + 1); 
				//swapping The element at i and j 
				swap(i, j);
			}
			//assigning the size for the scramble_lines below
			scramble_lines = new int[total_number_of_lines];
			//copying the now scrambled lines_lengths array into the scramble_lines array 
			//to store for reuse for other sort methods
			//so that all sort methods will use the same scrambled lines for fair comparison 
			for (int i = 0; i < total_number_of_lines; i++)
			{
				scramble_lines[i] = lines_lengths[i];
			}
			//Drawing the now scrambled lines_lengths
			paintComponent(this.getGraphics());
		}
		
		//Swapping method that swaps two elements in the lines_lengths array
		public void swap(int i, int j){
			//storing the i element in lines_lengths in temp
			int temp = lines_lengths[i];
			//giving i element in lines_lengths the value of j element in lines_lengths
			lines_lengths[i] = lines_lengths[j];
			//giving j element in lines_lengths the value of temp
			lines_lengths[j] = temp;
		}
		
		//The selectionSort method
		public void SelectionSort(){
			//getting the date and time when the selection sort starts
			Calendar start = Calendar.getInstance();
			//Using the selection sort to lines_lengths sort the array

			for(int i = 0; i < total_number_of_lines - 1; i++){
				//Get the minimum index from current point to the end of the array
				int min_index = getIndexOfSmallest(i, total_number_of_lines-1);
				//Swap min index with the current index
				swap(i, min_index);
				//Draw the swap that has occurred
				paintComponent(this.getGraphics());
				//Causing a delay for 10ms
				delay(10);
			}

			//getting the date and time when the selection sort ends
			Calendar end = Calendar.getInstance();
			//getting the time it took for the selection sort to execute 
			//subtracting the end time with the start time
	        SortGUI.selectionTime = end.getTime().getTime() - start.getTime().getTime();
		}
		
		//this method gets the smallest element in the array of lines_lengths
		public int getIndexOfSmallest(int first, int last){
			//Set the minimum index to the first element of the array
			int minIndex = first;

			//Run a loop to go through the entire list
			for(int i = first + 1; i <= last; i++) {
				//If the current index is smaller than the previously found min, set the new min
				if (lines_lengths[i] < lines_lengths[minIndex]) {
					minIndex = i;
				}

			}
			return minIndex; //modify this line
		}
		
	///////////////////////////////////////////////////////////////////////////////////


		//recursive merge sort method
		public void R_MergeSort(){
			//getting the date and time when the recursive merge sort starts
			Calendar start = Calendar.getInstance();
			//assigning the size for the tempArray below
			tempArray = new int[total_number_of_lines];
			R_MergeSort(0, total_number_of_lines - 1);

			Calendar end = Calendar.getInstance();
			//getting the time it took for the iterative merge sort to execute
			//subtracting the end time with the start time
			SortGUI.rmergeTime = end.getTime().getTime() - start.getTime().getTime();
		}

		//recursive merge sort method
		public void R_MergeSort(int first, int last){
			if(first < last){
				//Identify the index of the middle
				int mid = (first + last) / 2;

				//Recursively call Merge sort for the first half of the array
				R_MergeSort(first, mid);
				//Recursively call Merge sort for the second half of the array
				R_MergeSort(mid + 1, last);

				//Merge the two sorted halves
				R_Merge(first, mid, last);

				//Draw the updated array
				paintComponent(this.getGraphics());
				//Causing a delay for 10ms
				delay(10);
			}
		}


		//recursive merge sort method
		public void R_Merge(int first, int mid, int last){
			//Define subarray indices
			int beginHalf1 = first;
			int endHalf1 = mid;
			int beginHalf2 = mid + 1;
			int endHalf2 = last;

			//Create temporary array to store merged result
			// Create index for tempArray
			int[] tempArray = new int[last - first + 1];
			int index = 0;

			// while both subarrays have elements compare
			while (beginHalf1 <= endHalf1 && beginHalf2 <= endHalf2) {
				// Compare elements
				if (lines_lengths[beginHalf1] <= lines_lengths[beginHalf2]) {
					//copy smaller element into tempArray
					tempArray[index] = lines_lengths[beginHalf1];
					beginHalf1++;
				} else {
					tempArray[index] = lines_lengths[beginHalf2];
					beginHalf2++;
				}
				index++;
			}

			// Copy remaining elements from the first half
			while (beginHalf1 <= endHalf1) {
				tempArray[index] = lines_lengths[beginHalf1];
				beginHalf1++;
				index++;
			}

			// Copy remaining elements from the second half
			while (beginHalf2 <= endHalf2) {
				tempArray[index] = lines_lengths[beginHalf2];
				beginHalf2++;
				index++;
			}

			// Copy merged elements back into array
			for (int i = 0; i < tempArray.length; i++) {
				lines_lengths[first + i] = tempArray[i];
			}
		}
		
		//

	//////////////////////////////////////////////////////////////////////////////////////////
		
		//iterative merge sort method
		public void I_MergeSort()
		{
		//getting the date and time when the iterative merge sort starts
		Calendar start = Calendar.getInstance();
		//assigning the size for the tempArray below
		tempArray = new int[total_number_of_lines]; 
		//saving the value of total_number_of_lines
		int beginLeftovers = total_number_of_lines;

		
		for (int segmentLength = 1; segmentLength <= total_number_of_lines/2; segmentLength = 2*segmentLength)
		{
			beginLeftovers = I_MergeSegmentPairs(total_number_of_lines, segmentLength);
			int endSegment = beginLeftovers + segmentLength - 1;
			if (endSegment < total_number_of_lines - 1) 
			{
			I_Merge(beginLeftovers, endSegment, total_number_of_lines - 1);
			}
		} 

		// merge the sorted leftovers with the rest of the sorted array
		if (beginLeftovers < total_number_of_lines) {
			I_Merge(0, beginLeftovers-1, total_number_of_lines - 1);
		}
		//getting the date and time when the iterative merge sort ends
		Calendar end = Calendar.getInstance();
		//getting the time it took for the iterative merge sort to execute 
		//subtracting the end time with the start time
	    SortGUI.imergeTime = end.getTime().getTime() - start.getTime().getTime();
	} 

	// Merges segments pairs (certain length) within an array 
	public int I_MergeSegmentPairs(int l, int segmentLength)
	{
		//The length of the two merged segments 

		//You suppose  to complete this part (Given).
		int mergedPairLength = 2 * segmentLength;
		int numberOfPairs = l / mergedPairLength;

		int beginSegment1 = 0;
		for (int count = 1; count <= numberOfPairs; count++)
		{
			int endSegment1 = beginSegment1 + segmentLength - 1;

			int beginSegment2 = endSegment1 + 1;
			int endSegment2 = beginSegment2 + segmentLength - 1;
			I_Merge(beginSegment1, endSegment1, endSegment2);

			beginSegment1 = endSegment2 + 1;
			//redrawing the lines_lengths
			paintComponent(this.getGraphics());
			//Causing a delay for 10ms
			delay(10);
		}
		// Returns index of last merged pair
		return beginSegment1;
		//return 1;//modify this line
	}

	public void I_Merge(int first, int mid, int last)
	{
		//You suppose  to complete this part (Given).
		// Two adjacent sub-arrays
		int beginHalf1 = first;
		int endHalf1 = mid;
		int beginHalf2 = mid + 1;
		int endHalf2 = last;

		// While both sub-arrays are not empty, copy the
		// smaller item into the temporary array
		int index = beginHalf1; // Next available location in tempArray
		for (; (beginHalf1 <= endHalf1) && (beginHalf2 <= endHalf2); index++)
		{
			// Invariant: tempArray[beginHalf1..index-1] is in order
			if (lines_lengths[beginHalf1] < lines_lengths[beginHalf2])
			{
				tempArray[index] = lines_lengths[beginHalf1];
				beginHalf1++;
			}
			else
			{
				tempArray[index] = lines_lengths[beginHalf2];
				beginHalf2++;
			}
		}
		//redrawing the lines_lengths
		//paintComponent(this.getGraphics());

		// Finish off the nonempty sub-array

		// Finish off the first sub-array, if necessary
		for (; beginHalf1 <= endHalf1; beginHalf1++, index++)
			// Invariant: tempArray[beginHalf1..index-1] is in order
			tempArray[index] = lines_lengths[beginHalf1];

		// Finish off the second sub-array, if necessary
		for (; beginHalf2 <= endHalf2; beginHalf2++, index++)
			// Invariant: tempa[beginHalf1..index-1] is in order
			tempArray[index] = lines_lengths[beginHalf2];

		// Copy the result back into the original array
		for (index = first; index <= last; index++)
			lines_lengths[index] = tempArray[index];
	}

	//////////////////////////////////////////////////////////////////////

	public void BubbleSort(){
		//getting the date and time when the Bubble sort starts
		Calendar start = Calendar.getInstance();

		//getting the date and time when the Bubble sort ends
		Calendar end = Calendar.getInstance();
		//getting the time it took for the Bubble sort to execute
		//subtracting the end time with the start time
		SortGUI.BubbleTime = end.getTime().getTime() - start.getTime().getTime();
	}

	//////////////////////////////////////////////////////////////////////

	public void InsertionSort(){
		//getting the date and time when the Insertion sort starts
		Calendar start = Calendar.getInstance();

		//getting the date and time when the Insertion sort ends
		Calendar end = Calendar.getInstance();
		//getting the time it took for the Insertion sort to execute
		//subtracting the end time with the start time
		SortGUI.InsertionTime = end.getTime().getTime() - start.getTime().getTime();
	}

	//////////////////////////////////////////////////////////////////////

	public void ShellSort(){
		//getting the date and time when the Shell sort starts
		Calendar start = Calendar.getInstance();

		//getting the date and time when the Shell sort ends
		Calendar end = Calendar.getInstance();
		//getting the time it took for the Shell sort to execute
		//subtracting the end time with the start time
		SortGUI.ShellTime = end.getTime().getTime() - start.getTime().getTime();
	}

	//////////////////////////////////////////////////////////////////////

	public void QuickSort(){
		//getting the date and time when the Quick sort starts
		Calendar start = Calendar.getInstance();

		//Call quickSort()
		QuickSort(0, total_number_of_lines-1);

		//getting the date and time when the Quick sort ends
		Calendar end = Calendar.getInstance();
		//getting the time it took for the Quick sort to execute
		//subtracting the end time with the start time
		SortGUI.QuickTime = end.getTime().getTime() - start.getTime().getTime();
	}

	public void QuickSort(int left, int right){
		//If left greater than/equal to right, return.
		if (left >= right) {
			return;
		}
		//Get pivot (the rightmost element in the array)
		int pivot = lines_lengths[right];
		//Set an extra left and right index that will be incremented/decremented
		int l = left;
		int r = right - 1;

		while (l <= r) {
			//While the indices have not crossed, and the l index element is less than/equal to the pivot, increment l.
			while(l <= r && lines_lengths[l] <= pivot) {
				l++;
			}
			//While the indices have not crossed, and the r index element is greater than the pivot, decrement r.
			while(l <= r && lines_lengths[r] > pivot) {
				r--;
			}
			//After the whiles, if l < r, swap the elements at l and r. (And print to screen to show change)
			if(l<r) {
				swap(l, r);

				//Draw the updated array
				paintComponent(this.getGraphics());
				//Causing a delay for 10ms
				delay(10);
			}
		}
		//After partition, swap pivot and l.
		swap(l, right);

		//Draw the updated array
		paintComponent(this.getGraphics());
		//Causing a delay for 10ms
		delay(10);

		//Recursively sort left and right subarrays.
		QuickSort(left, l-1);
		QuickSort(l+1, right);
	}

	//////////////////////////////////////////////////////////////////////

	//Get the max element of the array to determine how many numbers in the max element.
	private int getMax() {
		int max = lines_lengths[0];
		for(int i = 0; i < total_number_of_lines; i++) {
			if(lines_lengths[i] > max) {
				max = lines_lengths[i];
			}
		}
		return max;
	}

	//Bucket sort subroutine for Radix Sort. Exponent for sorting buckets into digit specific buckets.
	private void BucketSort(int exp) {
		//Get array length.
		int n = total_number_of_lines;

		//Create buckets and subbuckets for sorting.
		ArrayList<Integer>[] buckets = new ArrayList[10];
		for (int i = 0; i < 10; i++) {
			buckets[i] = new ArrayList<>();
		}

		//Add numbers to respective digit buckets.
		for(int number : lines_lengths) {
			int digit = (number / exp) % 10;
			buckets[digit].add(number);
		}

		//For all numbers in buckets, put them back into the array. (And print to screen to show change)
		int index = 0;
		for (int i = 0; i < 10; i++) {
			for (int number : buckets[i]) {
				lines_lengths[index] = number;
				index += 1;

				//Draw the updated array
				paintComponent(this.getGraphics());
				//Causing a delay for 10ms
				delay(10);
			}
		}
	}

	//Radix Sort method.
	public void RadixSort(){
		//getting the date and time when the Radix sort starts
		Calendar start = Calendar.getInstance();

		//Get the max element of the array to see how many digits in the number.
		int max = getMax();
		//For each digit in the largest number, sort the numbers of the array.
		for(int exp = 1; max/exp > 0; exp *= 10) {
			BucketSort(exp);
		}

		//getting the date and time when the Radix sort ends
		Calendar end = Calendar.getInstance();
		//getting the time it took for the Radix sort to execute
		//subtracting the end time with the start time
		SortGUI.Radixtime = end.getTime().getTime() - start.getTime().getTime();
	}

	///////////////////////////////////////////////////////////////////////////////////

		//This method resets the window to the scrambled lines display
		public void reset(){
			if(scramble_lines != null)
			{
				//copying the old scrambled lines into lines_lengths
				for (int i = 0; i < total_number_of_lines; i++)
				{
					lines_lengths[i] = scramble_lines[i] ;
				}
			//Drawing the now scrambled lines_lengths
			paintComponent(this.getGraphics());
		}
			}
		
	
		//This method colours the lines and prints the lines
		public void paintComponent(Graphics g){
 			super.paintComponent(g);
			//A loop to assign a colour to each line
			for(int i = 0; i < total_number_of_lines; i++){
				//using eight colours for the lines
				if(i % 8 == 0){
					g.setColor(Color.green);
				} else if(i % 8 == 1){
					g.setColor(Color.blue);
				} else if(i % 8 == 2){
					g.setColor(Color.yellow);
				} else if(i%8 == 3){
					g.setColor(Color.red);
				} else if(i%8 == 4){
					g.setColor(Color.black);
				} else if(i%8 == 5){
					g.setColor(Color.orange);
				} else if(i%8 == 6){
					g.setColor(Color.magenta);
				} else
					g.setColor(Color.gray);
				
				//Drawing the lines using the x and y-components 
				g.drawLine(4*i + 25, 300, 4*i + 25, 300 - lines_lengths[i]);
			}
			
		}
		
		//A delay method that pauses the execution for the milliseconds time given as a parameter
		public void delay(int time){
			try{
	        	Thread.sleep(time);
	        }catch(InterruptedException ie){
	        	Thread.currentThread().interrupt();
	        }
		}
		
	}

