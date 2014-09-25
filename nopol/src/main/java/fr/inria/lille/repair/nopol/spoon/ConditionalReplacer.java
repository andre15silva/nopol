package fr.inria.lille.repair.nopol.spoon;

import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtStatement;

public class ConditionalReplacer extends ConditionalProcessor {

	public ConditionalReplacer(CtStatement target) {
		super(target, ((CtIf) target).getCondition().toString());
	}
	
	@Override
	public CtIf processCondition(CtStatement element, String newCondition) {
		logger.debug("Replacing:\n{}", element);
		CtCodeSnippetExpression<Boolean> snippet = element.getFactory().Core().createCodeSnippetExpression();
		snippet.setValue(newCondition);
		CtExpression<Boolean> condition = getCondition(element);
		condition.replace(snippet);
		return (CtIf) element;
	}
}
