package com.github.kumaraman21.intellijbehave.completion;

import com.github.kumaraman21.intellijbehave.highlighter.StoryTokenType;
import com.github.kumaraman21.intellijbehave.parser.StepPsiElement;
import com.github.kumaraman21.intellijbehave.resolver.StepDefinitionAnnotation;
import com.github.kumaraman21.intellijbehave.resolver.StepDefinitionIterator;
import com.github.kumaraman21.intellijbehave.resolver.StepPsiReference;
import com.github.kumaraman21.intellijbehave.utility.LocalizedStorySupport;
import com.github.kumaraman21.intellijbehave.utility.ParametrizedString;
import com.github.kumaraman21.intellijbehave.utility.ScanUtils;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.ASTNode;
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
    public void fillCompletionVariants(final CompletionParameters parameters, final CompletionResultSet _result) {
        if (parameters.getCompletionType() == CompletionType.BASIC) {
            final String prefix = CompletionUtil.findReferenceOrAlphanumericPrefix(parameters);
            final CompletionResultSet result = _result.withPrefixMatcher(prefix);

            final LocalizedKeywords keywords = lookupLocalizedKeywords(parameters);
            final Consumer<LookupElement> consumer = newConsumer(_result);

            addAllKeywords(result.getPrefixMatcher(), consumer, keywords);
            addAllSteps(parameters,
                    result.getPrefixMatcher(),
                    consumer,
                    keywords);
        }
    }

    private LocalizedKeywords lookupLocalizedKeywords(final CompletionParameters parameters) {
        String locale = "en";
        final ASTNode localeNode = parameters.getOriginalFile().getNode().findChildByType(StoryTokenType.COMMENT_WITH_LOCALE);
        if (localeNode != null) {
            final String localeFound = LocalizedStorySupport.checkForLanguageDefinition(localeNode.getText());
            if (localeFound != null) {
                locale = localeFound;
            }
        }
        return new LocalizedStorySupport().getKeywords(locale);
    }

    private static Consumer<LookupElement> newConsumer(final CompletionResultSet result) {
        return new Consumer<LookupElement>() {
            @Override
            public void consume(final LookupElement element) {
                result.addElement(element);
            }
        };
    }

    private static void addAllKeywords(final PrefixMatcher prefixMatcher,
                                       final Consumer<LookupElement> consumer,
                                       final LocalizedKeywords keywords) {
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

    private static void addIfMatches(final Consumer<LookupElement> consumer, final PrefixMatcher prefixMatchers, final String input) {
        if (prefixMatchers.prefixMatches(input)) {
            consumer.consume(LookupElementBuilder.create(input));
        }
    }

    private static void addAllSteps(final CompletionParameters parameters,
                                    final PrefixMatcher prefixMatcher,
                                    final Consumer<LookupElement> consumer, final LocalizedKeywords keywords) {
        final StepPsiElement stepPsiElement = getStepPsiElement(parameters);
        if (stepPsiElement == null) {
            return;
        }

        final StepType stepType = stepPsiElement.getStepType();
        final String actualStepPrefix = stepPsiElement.getActualStepPrefix();
        //
        final String textBeforeCaret = CompletionUtil.findReferenceOrAlphanumericPrefix(parameters);

        // suggest only if at least the actualStepPrefix is complete
        if (isStepTypeComplete(keywords, textBeforeCaret)) {
            final StepSuggester stepAnnotationFinder = new StepSuggester(prefixMatcher,
                    stepType,
                    actualStepPrefix,
                    textBeforeCaret,
                    consumer,
                    stepPsiElement);
            ScanUtils.iterateInContextOf(stepPsiElement, stepAnnotationFinder);
        }
    }

    private static boolean isStepTypeComplete(final LocalizedKeywords keywords, final String input) {
        return input.startsWith(keywords.given())
                || input.startsWith(keywords.when())
                || input.startsWith(keywords.then())
                || input.startsWith(keywords.and());
    }

    private static StepPsiElement getStepPsiElement(final CompletionParameters parameters) {
        final PsiElement position = parameters.getPosition();
        final PsiElement positionParent = position.getParent();
        if (positionParent instanceof StepPsiElement) {
            return (StepPsiElement) positionParent;
        } else if (position instanceof StepPsiReference) {
            return (StepPsiElement) ((StepPsiReference) position).getElement();
        } else if (position instanceof StepPsiElement) {
            return (StepPsiElement) position;
        } else {
            return null;
        }
    }

    private static class StepSuggester extends StepDefinitionIterator {

        private final PrefixMatcher prefixMatcher;
        private final StepType stepType;
        private final String actualStepPrefix;
        private final String textBeforeCaret;
        private final Consumer<LookupElement> consumer;

        private StepSuggester(final PrefixMatcher prefixMatcher,
                              final StepType stepType,
                              final String actualStepPrefix,
                              final String textBeforeCaret, final Consumer<LookupElement> consumer,
                              final StepPsiElement storyRef) {
            super(null, storyRef);
            this.prefixMatcher = prefixMatcher;
            this.stepType = stepType;
            this.actualStepPrefix = actualStepPrefix;
            this.textBeforeCaret = textBeforeCaret;
            this.consumer = consumer;
        }

        @Override
        public boolean processStepDefinition(final StepDefinitionAnnotation stepDefinitionAnnotation) {
            final StepType annotationStepType = stepDefinitionAnnotation.getStepType();
            if (annotationStepType != stepType) {
                return true;
            }
            final String annotationText = stepDefinitionAnnotation.getAnnotationText();
            final String adjustedAnnotationText = actualStepPrefix + " " + annotationText;

            final ParametrizedString pString = new ParametrizedString(adjustedAnnotationText);
            final String complete = pString.complete(textBeforeCaret);
            if (StringUtil.isNotEmpty(complete)) {
                final PsiAnnotation matchingAnnotation = stepDefinitionAnnotation.getAnnotation();
                consumer.consume(LookupElementBuilder.create(matchingAnnotation, textBeforeCaret + complete));
            } else if (prefixMatcher.prefixMatches(adjustedAnnotationText)) {
                final PsiAnnotation matchingAnnotation = stepDefinitionAnnotation.getAnnotation();
                consumer.consume(LookupElementBuilder.create(matchingAnnotation, adjustedAnnotationText));
            }
            return true;
        }
    }
}
