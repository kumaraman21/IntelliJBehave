package com.github.kumaraman21.intellijbehave.service;

import com.github.kumaraman21.intellijbehave.language.StoryFileType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.QueryExecutorBase;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.MethodReferencesSearch.SearchParameters;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.util.Processor;
import com.intellij.util.Query;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.getAnnotationTexts;
import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.getTheBiggestWordToSearchByIndex;
import static com.intellij.openapi.util.text.StringUtil.isNotEmpty;

public class JBehaveJavaMethodUsageSearcher extends QueryExecutorBase<PsiReference, SearchParameters> {

    @Override
    public void processQuery(@NotNull SearchParameters queryParameters, @NotNull Processor<? super PsiReference> consumer) {
        final PsiMethod method = queryParameters.getMethod();

        List<String> stepTexts = ApplicationManager.getApplication().runReadAction((Computable<List<String>>) () -> getAnnotationTexts(method));

        for (String stepText : stepTexts) {
            String word = getTheBiggestWordToSearchByIndex(stepText);
            if (isNotEmpty(word)) {
                if (queryParameters.getScopeDeterminedByUser() instanceof GlobalSearchScope) {
                    GlobalSearchScope restrictedScope = GlobalSearchScope.getScopeRestrictedByFileTypes((GlobalSearchScope) queryParameters.getScopeDeterminedByUser(), StoryFileType.STORY_FILE_TYPE);
                    Query<PsiReference> query = ReferencesSearch.search(new ReferencesSearch.SearchParameters(method, restrictedScope, false, queryParameters.getOptimizer()));
                    query.forEach(consumer);
                }
            }
        }
    }
}
