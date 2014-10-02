package com.github.kumaraman21.intellijbehave.service;

import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.google.common.base.Objects;
import com.intellij.psi.*;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.parsers.StepMatcher;
import org.jbehave.core.parsers.StepPatternParser;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.getJBehaveStepAnnotation;
import static com.github.kumaraman21.intellijbehave.utility.StepTypeMappings.ANNOTATION_TO_STEP_TYPE_MAPPING;

public class JavaStepDefinition {
    private final SmartPsiElementPointer<PsiMethod> myElementPointer;
    private final StepPatternParser stepPatternParser = new RegexPrefixCapturingPatternParser();

    public JavaStepDefinition(PsiMethod method) {
        myElementPointer = SmartPointerManager.getInstance(method.getProject()).createSmartPsiElementPointer(method);
    }

    @Nullable
    protected StepType getAnnotationType(PsiElement element) {
        if (!(element instanceof PsiMethod)) {
            return null;
        } else {
            PsiAnnotation stepAnnotation = getJBehaveStepAnnotation((PsiMethod) element);

            assert stepAnnotation != null;

            return ANNOTATION_TO_STEP_TYPE_MAPPING.get(stepAnnotation.getQualifiedName());
        }
    }

    @NotNull
    protected Integer getAnnotationPriority(PsiElement element) {
        if (!(element instanceof PsiMethod)) {
            return -1;
        } else {
            int result = -1;
            PsiAnnotation stepAnnotation = getJBehaveStepAnnotation((PsiMethod) element);

            assert stepAnnotation != null;

            PsiAnnotationMemberValue annotationValue = stepAnnotation.findAttributeValue("priority");
            if (annotationValue != null) {
                PsiConstantEvaluationHelper evaluationHelper = JavaPsiFacade.getInstance(element.getProject()).getConstantEvaluationHelper();
                Object constantValue = evaluationHelper.computeConstantExpression(annotationValue, false);
                if (constantValue != null) {
                    result = Integer.valueOf(constantValue.toString());
                }
            }

            return result;
        }
    }

    public boolean matches(String stepName) {
        StepMatcher stepMatcher = getStepMatcher();
        return stepMatcher != null && stepMatcher.matches(stepName);
    }

    @Nullable
    public PsiElement getElement() {
        return myElementPointer.getElement();
    }

    @Nullable
    public StepMatcher getStepMatcher() {
        String annotationText = getAnnotationText();
        if (annotationText == null) {
            return null;
        } else {
            return stepPatternParser.parseStep(getAnnotationType(), annotationText);
        }
    }

    @Nullable
    public String getAnnotationText() {
        return JBehaveUtil.getAnnotationText(getElement());
    }

    @Nullable
    public StepType getAnnotationType() {
        return getAnnotationType(getElement());
    }

    @NotNull
    public Integer getAnnotationPriority() {
        return getAnnotationPriority(getElement());
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
