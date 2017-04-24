package org._7hills.liueri19.game;

import java.util.Comparator;

public class SquareComparator implements Comparator<int[]> {

	@Override
	public int compare(int[] s1, int[] s2) {
		if (s1[1] == s2[1]) {
			if (s1[0] < s2[0])
				return -1;
			return 1;
		}
		if (s1[1] > s2[1])
			return -1;
		return 1;
	}
}
