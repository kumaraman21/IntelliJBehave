package com.github.kumaraman21.intellijbehave.service;

import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.SmartPointerManager;
import com.intellij.psi.SmartPsiElementPointer;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.parsers.StepMatcher;
import org.jbehave.core.parsers.StepPatternParser;
import org.jbehave.core.steps.PatternVariantBuilder;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.getJBehaveStepAnnotation;
import static com.github.kumaraman21.intellijbehave.utility.StepTypeMappings.ANNOTATION_TO_STEP_TYPE_MAPPING;
import static com.google.common.collect.FluentIterable.from;

public class JavaStepDefinition {
    private final SmartPsiElementPointer<PsiMethod> myElementPointer;
    private final StepPatternParser stepPatternParser = new RegexPrefixCapturingPatternParser();

    public JavaStepDefinition(PsiMethod method) {
        myElementPointer = SmartPointerManager.getInstance(method.getProject()).createSmartPsiElementPointer(method);
    }

    public boolean matches(String stepText) {
        Set<StepMatcher> stepMatchers = getStepMatchers();
        for (StepMatcher stepMatcher : stepMatchers) {
            if (stepMatcher.matches(stepText)) {
                return true;
            }
        }

        return false;
    }

    @Nullable
    public String getAnnotationTextFor(String stepText) {
        Set<String> annotationTextVariants = getAnnotationTextVariants();

        if (annotationTextVariants.size() == 1) {//small optimization: it doesn't create matchers if no step variants found
            return Iterables.getFirst(annotationTextVariants, null);
        }

        Set<StepMatcher> stepMatchers = getStepMatchers(annotationTextVariants);

        for (StepMatcher stepMatcher : stepMatchers) {
            if (stepMatcher.matches(stepText)) {
                return stepMatcher.pattern().annotated();
            }
        }

        return null;
    }

    @Nullable
    public PsiMethod getElement() {
        return myElementPointer.getElement();
    }

    private Set<StepMatcher> getStepMatchers() {
        return getStepMatchers(getAnnotationTextVariants());
    }

    private Set<StepMatcher> getStepMatchers(Set<String> annotationTextVariants) {
        final StepType annotationType = getAnnotationType();

        return from(annotationTextVariants)
                .transform(toStepMatchers(annotationType)).toSet();
    }

    private Set<String> getAnnotationTextVariants() {
        final String annotationText = getAnnotationText();

        if (annotationText == null) {
            return ImmutableSet.of();
        }

        return new PatternVariantBuilder(annotationText).allVariants();
    }

    private Function<String, StepMatcher> toStepMatchers(final StepType annotationType) {
        return new Function<String, StepMatcher>() {
            @Override
            public StepMatcher apply(@Nullable String annotationText) {
                return stepPatternParser.parseStep(annotationType, annotationText);
            }
        };
    }

    @Nullable
    public String getAnnotationText() {
        PsiMethod element = getElement();
        if (element == null) {
            return null;
        }

        PsiAnnotation stepAnnotation = getJBehaveStepAnnotation(element);

        assert stepAnnotation != null;

        return JBehaveUtil.getAnnotationText(stepAnnotation);
    }

    @Nullable
    public StepType getAnnotationType() {
        PsiMethod element = getElement();
        if (element == null) {
            return null;
        }

        final PsiAnnotation stepAnnotation = getJBehaveStepAnnotation(element);

        assert stepAnnotation != null;

        String qualifiedName = ApplicationManager.getApplication().runReadAction(new Computable<String>() {
            public String compute() {
                return stepAnnotation.getQualifiedName();
            }
        });

        return ANNOTATION_TO_STEP_TYPE_MAPPING.get(qualifiedName);
    }

    @NotNull
    public Integer getAnnotationPriority() {
        PsiMethod element = getElement();
        if (element == null) {
            return -1;
        }

        PsiAnnotation stepAnnotation = getJBehaveStepAnnotation(element);

        assert stepAnnotation != null;

        return JBehaveUtil.getAnnotationPriority(stepAnnotation);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && getClass() == o.getClass()) {
            JavaStepDefinition that = (JavaStepDefinition) o;
            return myElementPointer.equals(that.myElementPointer);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return myElementPointer.hashCode();
    }

    public boolean supportsStep(@NotNull JBehaveStep step) {
        StepType stepType = step.getStepType();
        StepType annotationType = getAnnotationType();

        return Objects.equal(stepType, annotationType);
    }
}
