package com.github.kumaraman21.intellijbehave.service;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.searches.ReferencesSearch.SearchParameters;
import com.intellij.util.Processor;
import com.intellij.util.QueryExecutor;
import org.jetbrains.annotations.NotNull;

import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.*;

public class JBehaveJavaStepDefinitionSearch implements QueryExecutor<PsiReference, SearchParameters> {
    public boolean execute(@NotNull final SearchParameters queryParameters, @NotNull Processor<PsiReference> consumer) {
        PsiElement myElement = queryParameters.getElementToSearch();

        if (!(myElement instanceof PsiMethod)) {
            return true;
        }

        final PsiMethod method = (PsiMethod) myElement;

        Boolean isStepDefinition = ApplicationManager.getApplication().runReadAction(new Computable<Boolean>() {
            public Boolean compute() {
                return isStepDefinition(method);
            }
        });

        if (!isStepDefinition) {
            return true;
        }

        PsiAnnotation stepAnnotation = ApplicationManager.getApplication().runReadAction(new Computable<PsiAnnotation>() {
            public PsiAnnotation compute() {
                return getJBehaveStepAnnotation(method);
            }
        });

        String stepText = getAnnotationText(stepAnnotation);
        if (stepText == null) {
            return true;
        }

        SearchScope searchScope = ApplicationManager.getApplication().runReadAction(new Computable<SearchScope>() {
            public SearchScope compute() {
                return queryParameters.getEffectiveSearchScope();
            }
        });

        return findJBehaveReferencesToElement(myElement, stepText, consumer, searchScope);
    }
}
