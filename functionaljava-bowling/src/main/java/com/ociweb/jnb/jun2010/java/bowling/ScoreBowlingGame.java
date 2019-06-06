package com.ociweb.jnb.jun2010.java.bowling;

import static fj.P.p;
import static fj.data.Option.none;
import static fj.data.Option.some;

import java.util.Arrays;

import fj.F;
import fj.F2;
import fj.P2;
import fj.data.List;
import fj.data.Option;

public class ScoreBowlingGame extends F<java.util.List<Integer>, java.util.List<Integer>> {
	public static class InvalidPinCountException extends RuntimeException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public InvalidPinCountException() {
			super("Invalid Pin Count");
		}
	}

	private static <A, B> List<B> reduce(A ys, F<A, Option<P2<B, A>>> f) {
		Option<P2<B, A>> result = f.f(ys);
		if (result.isSome()) {
			P2<B, A> value = result.some();
			return List.cons(value._1(), reduce(value._2(), f));
		} else {
			return List.nil();
		}
	}

	private static void invalidPinCount() throws InvalidPinCountException {
		throw new InvalidPinCountException();
	}

	private static F<List<Integer>, Option<P2<Integer, List<Integer>>>> scoreFrame = new F<List<Integer>, Option<P2<Integer, List<Integer>>>>() {

		public Option<P2<Integer, List<Integer>>> f(List<Integer> balls) {
			if (balls.isNotEmpty()) {
				int x1 = balls.head();
				if (x1 < 0 || x1 > 10)
					invalidPinCount();
				if (balls.tail().isNotEmpty()) {
					int x2 = balls.tail().head();
					if (x2 < 0 || x2 > 10 || (x1 < 10 && x1 + x2 > 10))
						invalidPinCount();
					if (balls.tail().tail().isNotEmpty()) {
						int x3 = balls.tail().tail().head();
						if (x1 == 10)
							return some(p(x1 + x2 + x3, balls.tail()));
						if (x1 + x2 == 10)
							return some(p(x1 + x2 + x3, balls.tail().tail()));
						return some(p(x1 + x2, balls.tail().tail()));
					}
					if (x1 + x2 < 10)
						return some(p(x1 + x2, List.<Integer>nil()));
				}
			}
			return none();
		}
	};

	public static List<Integer> scoreGame(List<Integer> balls) {
		return reduce(balls, scoreFrame).take(10);
	}

	public static List<Integer> tallyScores(List<Integer> scores) {
		return scores.foldLeft(new F2<List<Integer>, Integer, List<Integer>>() {

			public List<Integer> f(List<Integer> lst, Integer score) {
				return lst.isEmpty() ? List.list(score) : List.cons(score + lst.head(), lst);
			}
		}, List.<Integer>nil()).reverse();
	}

	public java.util.List<Integer> f(java.util.List<Integer> rolls) {
		List.Buffer<Integer> fjBuffer = new List.Buffer<Integer>();
		for (Integer roll : rolls) {
			fjBuffer.snoc(roll);
		}
		return new java.util.ArrayList<Integer>(tallyScores(scoreGame(fjBuffer.toList())).toCollection());
	}

	public static void main(String[] args) {
		java.util.List<Integer> scores = new ScoreBowlingGame()
				.f(Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10));
		for (Integer score : scores) {
			System.out.print(score + " ");
		}
	}
}