package com.go2wheel.copyproject.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.stream.Stream;

import org.junit.Test;

public class TestReduce {
	
	@Test
	public void t() {
		CopyResult ttcr = Stream.of(1, 2, 3, 4, 5, 6).map(i -> new OneFileCopyResult()).parallel().reduce(new CopyResult(), (cr, ocr) -> {
				return cr.add(ocr);
			}, (cr1, cr2) -> {
			System.out.println(cr1 == cr2);
//			if (cr1 == cr2) {
//				return cr1;
//			} else {
				return cr2.plus(cr1);
//			}
		});
		
		assertThat(ttcr.getFailed().size(), equalTo(6));
	}

}
