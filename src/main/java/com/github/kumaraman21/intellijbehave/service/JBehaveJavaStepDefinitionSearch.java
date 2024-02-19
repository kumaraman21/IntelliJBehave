package com.github.kumaraman21.intellijbehave.service;

import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.findJBehaveReferencesToElement;
import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.getAnnotationTexts;
import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.isStepDefinition;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.searches.ReferencesSearch.SearchParameters;
import com.intellij.util.Processor;
import com.intellij.util.QueryExecutor;

public class JBehaveJavaStepDefinitionSearch implements QueryExecutor<PsiReference, SearchParameters> {

    @Override
    public boolean execute(@NotNull SearchParameters queryParameters, @NotNull Processor<? super PsiReference> consumer) {
        PsiElement myElement = queryParameters.getElementToSearch();

        if (!(myElement instanceof PsiMethod)) {
            return true;
        }

        final PsiMethod method = (PsiMethod) myElement;

        Boolean isStepDefinition = ApplicationManager.getApplication().runReadAction((Computable<Boolean>) () -> isStepDefinition(method));

        if (!isStepDefinition) {
            return true;
        }

        List<String> stepTexts = ApplicationManager.getApplication().runReadAction((Computable<List<String>>) () -> getAnnotationTexts(method));
        SearchScope searchScope = ApplicationManager.getApplication().runReadAction((Computable<SearchScope>) queryParameters::getEffectiveSearchScope);

        boolean result = true;

        for (String stepText : stepTexts) {
            if (stepText == null) {
                return true;
            }

            result &= findJBehaveReferencesToElement(myElement, stepText, consumer, searchScope);
        }

        return result;
    }
}
