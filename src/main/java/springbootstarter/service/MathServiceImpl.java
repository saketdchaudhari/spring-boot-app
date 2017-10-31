package springbootstarter.service;

import java.util.ArrayList;
import java.util.List;

public class MathServiceImpl implements IMathService {

	@Override
	public List<Integer> findFirstNFibonacciNumbers(int n) {
		List<Integer> numbers = new ArrayList<>();
		return generatefibonacciSerierUptoNRecursively(numbers, n);
	}

	static List<Integer> generatefibonacciSerierUptoNRecursively(List<Integer> list, int n) {
		if (n == 0) {
			return list;
		}
		switch (list.size()) {
		case 0:
			list.add(0);
			return generatefibonacciSerierUptoNRecursively(list, n - 1);
		case 1:
			list.add(1);
			return generatefibonacciSerierUptoNRecursively(list, n - 1);
		default:
			list.add(list.get(list.size() - 1) + list.get(list.size() - 2));
			return generatefibonacciSerierUptoNRecursively(list, n - 1);
		}
	}

}
