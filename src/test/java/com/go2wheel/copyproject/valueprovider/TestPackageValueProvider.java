package com.go2wheel.copyproject.valueprovider;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.go2wheel.copyproject.valueprovider.PackageValueProvider;

public class TestPackageValueProvider {

	@Test
	public void t() {
		PackageValueProvider pvp = new PackageValueProvider();
		
		List<String> candidates = pvp.getCandidates("fixtures/demoproject", "a");
		
		assertThat(candidates.size(), equalTo(3));
		
		candidates = pvp.getCandidates("fixtures/demoproject", "com");
		
		assertThat(candidates.size(), equalTo(3));
		
		candidates = pvp.getCandidates("fixtures/demoproject", "com.");
		
		assertThat(candidates.size(), equalTo(2));
		
		candidates = pvp.getCandidates("fixtures/demoproject", "com.a");
		
		assertThat(candidates.size(), equalTo(0));
	}
}
