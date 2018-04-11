package com.go2wheel.copyproject.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import com.go2wheel.copyproject.UtilForTe;
import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyDescription.COPY_STATE;
import com.go2wheel.copyproject.value.CopyDescriptionBuilder;
import com.go2wheel.copyproject.value.CopyResult;

public class TestReduce {
	
	private CopyDescriptionBuilder cdBuilder;
	
	@Before
	public void before() {
		cdBuilder = new CopyDescriptionBuilder(Paths.get(""), Paths.get(""), "a.b", "c.d", new ArrayList<>());
	}

//	@Test
//	public void tReduce() {
//		long start = System.currentTimeMillis();
//		int count = 10000;
//		CopyResult ttcr = IntStream.range(0, count).mapToObj(i -> {
//			if (i % 2 == 0) {
//				CopyDescription cd = cdBuilder.buildOne(Paths.get("1")); 
//				return cd;
//			} else {
//				CopyDescription cd = cdBuilder.buildOne(Paths.get("2"));
//				return cd;
//			}
//			}).parallel()
//				.reduce(new CopyResult(), (cr, ocr) -> {
//					return new CopyResult(cr, ocr);
//				}, (cr1, cr2) -> {
//					return new CopyResult(cr1, cr2);
//				});
//		UtilForTe.printme(System.currentTimeMillis() - start);
////		assertThat(ttcr.getFailed().size(), equalTo(count / 2));
//
//		ttcr = IntStream.range(0, count).mapToObj(i -> {
//			if (i % 2 == 0) {
//				CopyDescription cd = cdBuilder.buildOne(Paths.get("1")); 
//				return cd;
//			} else {
//				CopyDescription cd = cdBuilder.buildOne(Paths.get("2"));
//				return cd;
//			}
//			}).reduce(new CopyResult(), (cr, ocr) -> {
//			return new CopyResult(cr, ocr);
//		}, (cr1, cr2) -> {
//			return new CopyResult(cr1, cr2);
//		});
//		UtilForTe.printme(System.currentTimeMillis() - start);
////		assertThat(ttcr.getFailed().size(), equalTo(count / 2));
//	}

	@Test
	public void tCollect() {
		long start = System.currentTimeMillis();
		int count = 10000;
		CopyResult ttcr = IntStream.range(0, count).mapToObj(i -> {
			if (i % 2 == 0) {
				CopyDescription cd = cdBuilder.buildOne(Paths.get("1")); 
				cd.setState(COPY_STATE.FILE_COPY_SUCCESSED);
				return cd;
			} else {
				CopyDescription cd = cdBuilder.buildOne(Paths.get("2"));
				cd.setState(COPY_STATE.FILE_COPY_FAILED);
				return cd;
			}
			}).parallel()
				.collect(CopyResult::new, CopyResult::accept, CopyResult::combine);
		UtilForTe.printme(System.currentTimeMillis() - start);
		assertThat(ttcr.getDescriptionMap().get(COPY_STATE.FILE_COPY_SUCCESSED).size(), equalTo(0));
		assertThat(ttcr.getCountMap().get(COPY_STATE.FILE_COPY_SUCCESSED), equalTo(5000L));

		ttcr = IntStream.range(0, count).mapToObj(i -> {
			if (i % 2 == 0) {
				CopyDescription cd = cdBuilder.buildOne(Paths.get("1")); 
				cd.setState(COPY_STATE.FILE_COPY_SUCCESSED);
				return cd;
			} else {
				CopyDescription cd = cdBuilder.buildOne(Paths.get("2"));
				cd.setState(COPY_STATE.FILE_COPY_FAILED);
				return cd;
			}
			}).collect(CopyResult::new,
				CopyResult::accept, CopyResult::combine);
		UtilForTe.printme(System.currentTimeMillis() - start);
		assertThat(ttcr.getDescriptionMap().get(COPY_STATE.FILE_COPY_SUCCESSED).size(), equalTo(0));
		assertThat(ttcr.getCountMap().get(COPY_STATE.FILE_COPY_SUCCESSED), equalTo(5000L));
	}
}
