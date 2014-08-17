package fr.inria.lille.commons.spoon.filter;

import java.io.File;

import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import xxl.java.library.FileLibrary;

public class CodeSnippetFilter extends AbstractFilter<CtElement> {

	public CodeSnippetFilter(File classSourceFile, String codeSnippet) {
		super(CtElement.class);
		this.codeSnippet = codeSnippet;
		this.classSourceFile = classSourceFile;
	}
	
	@Override
	public boolean matches(CtElement element) {
		SourcePosition position = element.getPosition();
		if (position != null) {
			return  FileLibrary.isSameFile(classSourceFile(), position.getFile()) && codeSnippet().equals(element.toString());
		}
		return false;
	}

	private File classSourceFile() {
		return classSourceFile;
	}
	
	private String codeSnippet() {
		return codeSnippet;
	}
	
	private File classSourceFile;
	private String codeSnippet;
}
