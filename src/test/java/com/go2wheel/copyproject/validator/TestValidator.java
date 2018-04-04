package com.go2wheel.copyproject.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.go2wheel.copyproject.commands.CopyProjectCommands;

public class TestValidator {
	
	@Test
	public void tPackageName() {
		Pattern ptn = Pattern.compile(CopyProjectCommands.PACKAGE_PATTERN);
		Matcher m = ptn.matcher("c.");
		assertFalse("c. is not a valid packagename", m.matches());
		
		m = ptn.matcher("0c");
		assertFalse("0c is not a valid packagename", m.matches());

		m = ptn.matcher("c0");
		assertTrue("c0 is a valid packagename", m.matches());

		m = ptn.matcher("c0");
		assertTrue("c0 is a valid packagename", m.matches());

		m = ptn.matcher("c0.");
		assertFalse("c0. is a not valid packagename", m.matches());
		
		m = ptn.matcher("c.b.c");
		assertTrue("c.b.c is a valid packagename", m.matches());
		
		m = ptn.matcher("c");
		assertTrue("c is a valid packagename", m.matches());
	}

}
