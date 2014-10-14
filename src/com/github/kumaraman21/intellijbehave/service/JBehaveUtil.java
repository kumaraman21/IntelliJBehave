package com.github.kumaraman21.intellijbehave.service;

import com.github.kumaraman21.intellijbehave.language.StoryFileType;
import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiSearchHelper;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.TextOccurenceProcessor;
import com.intellij.util.Processor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.openapi.util.text.StringUtil.isEmptyOrSpaces;

public class JBehaveUtil {
    public static boolean isJBehaveStepAnnotation(@NotNull PsiAnnotation annotation) {
        String annotationName = getAnnotationName(annotation);
        if (annotationName == null) {
            return false;
        }

        String annotationSuffix = getJBehaveAnnotationSuffix(annotationName);

        return !annotationSuffix.isEmpty();
    }

    private static String getJBehaveAnnotationSuffix(@NotNull String name) {
        if (name.startsWith("org.jbehave.core.annotations.")) {
            return name.substring("org.jbehave.core.annotations.".length());
        } else {
            return "";
        }
    }

    @Nullable
    private static String getAnnotationName(@NotNull final PsiAnnotation annotation) {
        return ApplicationManager.getApplication().runReadAction(new Computable<String>() {
            @Override
            public String compute() {
                return annotation.getQualifiedName();
            }
        });
    }

    @Nullable
    public static PsiAnnotation getJBehaveStepAnnotation(PsiMethod method) {
        PsiAnnotation[] annotations = method.getModifierList().getAnnotations();

        for (PsiAnnotation annotation : annotations) {
            if (annotation != null && isJBehaveStepAnnotation(annotation)) {
                return annotation;
            }
        }

        return null;
    }

    public static boolean isStepDefinition(@NotNull PsiMethod method) {
        PsiAnnotation stepAnnotation = getJBehaveStepAnnotation(method);
        return stepAnnotation != null && stepAnnotation.findAttributeValue("value") != null;
    }

    @Nullable
    public static String getAnnotationText(PsiAnnotation stepAnnotation) {
        return AnnotationUtil.getStringAttributeValue(stepAnnotation, "value");
    }

    public static Integer getAnnotationPriority(PsiAnnotation stepAnnotation) {
        PsiAnnotationMemberValue attrValue = stepAnnotation.findAttributeValue("priority");
        Object constValue = JavaPsiFacade.getInstance(stepAnnotation.getProject()).getConstantEvaluationHelper().computeConstantExpression(attrValue);
        Integer priority = constValue instanceof Integer ? (Integer) constValue : null;

        if (priority != null) {
            return priority;
        }

        return -1;
    }

    public static boolean findJBehaveReferencesToElement(@NotNull PsiElement stepDefinitionElement, @NotNull String stepText, @NotNull Processor<PsiReference> consumer, @NotNull SearchScope effectiveSearchScope) {
        return findPossibleJBehaveElementUsages(stepDefinitionElement, stepText, new MyReferenceCheckingProcessor(stepDefinitionElement, consumer), effectiveSearchScope);
    }

    public static boolean findPossibleJBehaveElementUsages(@NotNull PsiElement stepDefinitionElement, @NotNull String stepText, @NotNull TextOccurenceProcessor processor, @NotNull final SearchScope effectiveSearchScope) {
        String word = getTheBiggestWordToSearchByIndex(stepText);

        if (isEmptyOrSpaces(word)) {
            return true;
        }

        SearchScope searchScope = restrictScopeToJBehaveFiles(new Computable<SearchScope>() {
            public SearchScope compute() {
                return effectiveSearchScope;
            }
        });

        PsiSearchHelper instance = PsiSearchHelper.SERVICE.getInstance(stepDefinitionElement.getProject());
        return instance.processElementsWithWord(processor, searchScope, word, (short) 5, true);
    }

    public static SearchScope restrictScopeToJBehaveFiles(final Computable<SearchScope> originalScopeComputation) {
        return (SearchScope) ApplicationManager.getApplication().runReadAction(new Computable() {
            public SearchScope compute() {
                SearchScope originalScope = originalScopeComputation.compute();
                if (originalScope instanceof GlobalSearchScope) {
                    return GlobalSearchScope.getScopeRestrictedByFileTypes((GlobalSearchScope) originalScope, StoryFileType.STORY_FILE_TYPE);
                } else {
                    return originalScope;
                }
            }
        });
    }

    public static String getTheBiggestWordToSearchByIndex(@NotNull String stepText) {
        String result = "";

        int par = 0;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < stepText.length(); ++i) {
            char c = stepText.charAt(i);

            if (c == '$') {
                ++par;
            }

            if (c == ' ' && par > 0) {
                --par;
            }

            if (par > 0) {
                if (par == 1) {
                    sb = new StringBuilder();
                }
            } else if (Character.isLetterOrDigit(c)) {
                sb.append(c);
                if (sb.length() > 0 && sb.toString().length() > result.length()) {
                    result = sb.toString();
                }
            } else {
                sb = new StringBuilder();
            }
        }

        if (sb.length() > 0 && sb.toString().length() > result.length()) {
            result = sb.toString();
        }

        return result;
    }

    private static class MyReferenceCheckingProcessor implements TextOccurenceProcessor {
        @NotNull
        private final PsiElement myElementToFind;
        @NotNull
        private final Processor<PsiReference> myConsumer;

        private MyReferenceCheckingProcessor(@NotNull PsiElement elementToFind, @NotNull Processor<PsiReference> consumer) {
            myElementToFind = elementToFind;
            myConsumer = consumer;
        }

        public boolean execute(@NotNull PsiElement element, int offsetInElement) {
            PsiElement parent = element.getParent();
            boolean result = executeInternal(element);
            return result && parent != null ? executeInternal(parent) : result;
        }

        private boolean executeInternal(@NotNull PsiElement referenceOwner) {
            for (PsiReference ref : referenceOwner.getReferences()) {
                if (ref != null && ref.isReferenceTo(myElementToFind) && !myConsumer.process(ref)) {
                    return false;
                }
            }

            return true;
        }
    }
}
