package com.github.kumaraman21.intellijbehave.service;

import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
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

    public boolean matches(String stepName) {
        Set<StepMatcher> stepMatchers = getStepMatchers();
        for (StepMatcher stepMatcher : stepMatchers) {
            if (stepMatcher.matches(stepName)) {
                return true;
            }
        }

        return false;
    }

    @Nullable
    public PsiMethod getElement() {
        return myElementPointer.getElement();
    }

    public Set<StepMatcher> getStepMatchers() {
        final String annotationText = getAnnotationText();
        if (annotationText == null) {
            return ImmutableSet.of();
        }

        final StepType annotationType = getAnnotationType();

        return from(new PatternVariantBuilder(annotationText).allVariants())
                .transform(toStepMatchers(annotationType)).toSet();
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
