package com.github.kumaraman21.intellijbehave.completion;

import static com.github.kumaraman21.intellijbehave.utility.ProjectUtils.getProjectFileIndex;

import com.github.kumaraman21.intellijbehave.parser.StepPsiElement;
import com.github.kumaraman21.intellijbehave.resolver.StepDefinitionAnnotation;
import com.github.kumaraman21.intellijbehave.resolver.StepDefinitionIterator;
import com.github.kumaraman21.intellijbehave.resolver.StepPsiReference;
import com.github.kumaraman21.intellijbehave.utility.ParametrizedString;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.completion.CompletionUtil;
import com.intellij.codeInsight.completion.PrefixMatcher;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.util.Consumer;

import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.steps.StepType;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class StoryCompletionContributor extends CompletionContributor {
    public StoryCompletionContributor() {
    }

    @Override
    public void fillCompletionVariants(CompletionParameters parameters, final CompletionResultSet _result) {
        if (parameters.getCompletionType() == CompletionType.BASIC) {
            String prefix = CompletionUtil.findReferenceOrAlphanumericPrefix(parameters);
            CompletionResultSet result = _result.withPrefixMatcher(prefix);

            Consumer<LookupElement> consumer = newConsumer(_result);

            addAllKeywords(result.getPrefixMatcher(), consumer);
            addAllSteps(parameters, parameters.getInvocationCount() <= 1, result.getPrefixMatcher(), consumer);
        }
    }

    private static Consumer<LookupElement> newConsumer(final CompletionResultSet result) {
        return new Consumer<LookupElement>() {
            @Override
            public void consume(LookupElement element) {
                result.addElement(element);
            }
        };
    }

    private static void addAllKeywords(PrefixMatcher prefixMatcher,
                                       Consumer<LookupElement> consumer)
    {
        LocalizedKeywords keywords = new LocalizedKeywords();
        addIfMatches(consumer, prefixMatcher, keywords.narrative());
        addIfMatches(consumer, prefixMatcher, keywords.asA());
        addIfMatches(consumer, prefixMatcher, keywords.inOrderTo());
        addIfMatches(consumer, prefixMatcher, keywords.iWantTo());
        //
        addIfMatches(consumer, prefixMatcher, keywords.givenStories());
        addIfMatches(consumer, prefixMatcher, keywords.ignorable());
        addIfMatches(consumer, prefixMatcher, keywords.scenario());
        addIfMatches(consumer, prefixMatcher, keywords.examplesTable());
        //
        addIfMatches(consumer, prefixMatcher, keywords.given());
        addIfMatches(consumer, prefixMatcher, keywords.when());
        addIfMatches(consumer, prefixMatcher, keywords.then());
        addIfMatches(consumer, prefixMatcher, keywords.and());
    }

    private static void addIfMatches(Consumer<LookupElement> consumer, PrefixMatcher prefixMatchers, String input) {
        if (prefixMatchers.prefixMatches(input)) {
            consumer.consume(LookupElementBuilder.create(input));
        }
    }

    private static void addAllSteps(CompletionParameters parameters,
                                    boolean filterByScope,
                                    PrefixMatcher prefixMatcher,
                                    Consumer<LookupElement> consumer)
    {

        StepPsiElement stepPsiElement = getStepPsiElement(parameters);
        if (stepPsiElement == null) {
            return;
        }

        StepType stepType = stepPsiElement.getStepType();
        String actualStepPrefix = stepPsiElement.getActualStepPrefix();
        //
        String textBeforeCaret = CompletionUtil.findReferenceOrAlphanumericPrefix(parameters);

        StepSuggester stepAnnotationFinder = new StepSuggester(prefixMatcher,
                stepType,
                actualStepPrefix,
                textBeforeCaret,
                consumer);
        getProjectFileIndex().iterateContent(stepAnnotationFinder);
    }

    private static StepPsiElement getStepPsiElement(CompletionParameters parameters) {
        PsiElement position = parameters.getPosition();
        PsiElement positionParent = position.getParent();
        if (positionParent instanceof StepPsiElement) {
            return (StepPsiElement) positionParent;
        }
        else if (position instanceof StepPsiReference) {
            return (StepPsiElement) ((StepPsiReference) position).getElement();
        }
        else if (position instanceof StepPsiElement) {
            return (StepPsiElement) position;
        }
        else {
            return null;
        }
    }

    private static class StepSuggester extends StepDefinitionIterator {

        private final PrefixMatcher prefixMatcher;
        private final StepType stepType;
        private final String actualStepPrefix;
        private final String textBeforeCaret;
        private final Consumer<LookupElement> consumer;

        private StepSuggester(PrefixMatcher prefixMatcher,
                              StepType stepType,
                              String actualStepPrefix,
                              String textBeforeCaret, Consumer<LookupElement> consumer)
        {
            super(null);
            this.prefixMatcher = prefixMatcher;
            this.stepType = stepType;
            this.actualStepPrefix = actualStepPrefix;
            this.textBeforeCaret = textBeforeCaret;
            this.consumer = consumer;
        }

        @Override
        public boolean processStepDefinition(StepDefinitionAnnotation stepDefinitionAnnotation) {
            StepType annotationStepType = stepDefinitionAnnotation.getStepType();
            if (annotationStepType != stepType) {
                return true;
            }
            String annotationText = stepDefinitionAnnotation.getAnnotationText();
            String adjustedAnnotationText = actualStepPrefix + " " + annotationText;

            ParametrizedString pString = new ParametrizedString(adjustedAnnotationText);
            String complete = pString.complete(textBeforeCaret);
            if(StringUtil.isNotEmpty(complete)) {
                PsiAnnotation matchingAnnotation = stepDefinitionAnnotation.getAnnotation();
                consumer.consume(LookupElementBuilder.create(matchingAnnotation, textBeforeCaret + complete));
            }
            else if (prefixMatcher.prefixMatches(adjustedAnnotationText)) {
                PsiAnnotation matchingAnnotation = stepDefinitionAnnotation.getAnnotation();
                consumer.consume(LookupElementBuilder.create(matchingAnnotation, adjustedAnnotationText));
            }
            return true;
        }
    }
}
