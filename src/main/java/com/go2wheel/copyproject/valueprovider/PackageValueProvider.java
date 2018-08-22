package com.go2wheel.copyproject.valueprovider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProvider;

import com.go2wheel.copyproject.commands.CopyProjectCommands;

public class PackageValueProvider  implements ValueProvider {

    @Override
    public boolean supports(MethodParameter parameter, CompletionContext completionContext) {
    	Method md = parameter.getMethod();
    	Class<?> mclass = md.getDeclaringClass();
    	String name = md.getName();
    	int i = parameter.getParameterIndex();
    	if (mclass == CopyProjectCommands.class 
    			&& "copyProject".equals(name)
    			&& i > 1) {
    		return true;
    	}
        return false;
    }
    


    @Override
    public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {

    	String input = completionContext.currentWordUpToCursor();
        // The input may be -- or --xxx. Because it's might a positional parameter.
        if (input.startsWith("-")) {
        	return new ArrayList<>();
        }
        List<String> words = completionContext.getWords();
        String srcFolder = getSrcFolder(words);
        if (srcFolder != null && Files.exists(Paths.get(srcFolder))) {
        	return getCandidates(srcFolder, input).stream().map(CompletionProposal::new).collect(Collectors.toList());
        }
        return new ArrayList<>();

    }



	protected List<String> getCandidates(String srcFolder, String input) {
		String inputSlash = input.replace('.', '/');
		List<String> candidates = new ArrayList<>();
        	Path srcPath = Paths.get(srcFolder);
        	SimpleFileVisitor<Path> sfv = new SimpleFileVisitor<Path>() {
        		@Override
        		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        			String ps = dir.toString().replace('\\', '/');
        			int i = ps.indexOf(inputSlash);
        			if (i != -1) {
        				String found = ps.substring(i).replace('/', '.');
        				candidates.add(found);
        			}
        			return FileVisitResult.CONTINUE;
        		}
        	};
        	
        	try {
				Files.walkFileTree(srcPath, sfv);
			} catch (IOException e) {
				e.printStackTrace();
			}
     
        return candidates;
	}



	protected String getSrcFolder(List<String> words) {
		String srcFolder = null;
        int si = words.size();
        for (int i = 0; i < si; i++) {
			String s = words.get(i);
			if ("--src-folder".equals(s) && i + 1 < si) {
				srcFolder = words.get(i + 1);
				break;
			}
		}
		return srcFolder;
	}
    
}
