import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Optimizer {
	public static void main(String[] args) throws IOException {
		// Set up problem
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		/*
		 * System.out.println("Please input a comma-delimited list of numbers: ")
		 * ; String line = br.readLine();
		 */
		String line = "0,0,1,5,2,-1,0,-3,1,5,-6,-2,3,6,-4,2,-3,2";
		StringTokenizer st = new StringTokenizer(line, ",");
		ArrayList<Integer> list = new ArrayList<Integer>();
		while (st.hasMoreTokens()) {
			list.add(Integer.parseInt(st.nextToken().trim()));
		}

		reduce(list);
		int max = collapse(list);

		System.out.println(max);

	}

	public static void reduce(ArrayList<Integer> list) {
		// Remove all leading zeros
		while (list.get(0) == 0) {
			list.remove(0);
		}

		// Reduce the list into a series of positive and negative numbers only
		for (int i = 0; i < list.size() - 1;) {
			// The number is a zero
			if (list.get(i + 1) == 0) {
				list.remove(i + 1);
				continue;
			}
			// They have the same sign
			int first = list.get(i);
			int second = list.get(i + 1);
			if (Math.signum(first) == Math.signum(second)) {
				// Combine them
				list.set(i, first + second);
				list.remove(i + 1);
				continue;
			}
			i++;
		}

		// If the list starts with a negative number, pad it with a zero.
		if (list.get(0) < 0) {
			list.add(0, 0);
		}
		// If the list ends with a negative number, pad it with a zero.
		if (list.get(list.size() - 1) < 0) {
			list.add(0);
		}
	}

	public static int collapse(ArrayList<Integer> list) {
		boolean changeMade;

		do {
			System.out.println(list);

			changeMade = false;
			// Go through the list and attempt to collapse a triplet of numbers
			for (int i = 0; i < list.size() - 2; i += 2) {
				int left = list.get(i); // Left is always positive
				int middle = list.get(i + 1); // Middle is always negative
				int right = list.get(i + 2); // Right is always positive
				int combined = left + middle + right;

				// If the triplet's sum is larger than its left and right parts,
				// then replace it with it's sum
				if (combined > left && combined > right) {
					list.remove(i + 2);
					list.remove(i + 1);
					list.remove(i);
					list.add(i, combined);
					changeMade = true;
					break;
				}
			}
			// When no more changes are made, the list is in its minimal state
		} while (changeMade);

		// The largest number in this reduced list is the largest sum that can
		// be generated
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) > max) {
				max = list.get(i);
			}
		}
		return max;
	}
}
