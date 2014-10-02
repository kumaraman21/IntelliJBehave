package com.github.kumaraman21.intellijbehave.service;

import com.github.kumaraman21.intellijbehave.language.StoryFileType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.QueryExecutorBase;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.MethodReferencesSearch.SearchParameters;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.util.Processor;
import com.intellij.util.Query;
import org.jetbrains.annotations.NotNull;

import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.getAnnotationText;
import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.getJBehaveStepAnnotation;

public class JBehaveJavaMethodUsageSearcher extends QueryExecutorBase<PsiReference, SearchParameters> {
    public void processQuery(@NotNull SearchParameters p, @NotNull Processor<PsiReference> consumer) {
        final PsiMethod method = p.getMethod();
        String stepText = (String) ApplicationManager.getApplication().runReadAction(new Computable() {
            public String compute() {
                PsiAnnotation stepAnnotation = getJBehaveStepAnnotation(method);
                return stepAnnotation != null ? getAnnotationText(stepAnnotation) : null;
            }
        });
        if (stepText != null) {
            String word = JBehaveUtil.getTheBiggestWordToSearchByIndex(stepText);
            if (!StringUtil.isEmpty(word)) {
                if (p.getScope() instanceof GlobalSearchScope) {
                    GlobalSearchScope restrictedScope = GlobalSearchScope.getScopeRestrictedByFileTypes((GlobalSearchScope) p.getScope(), StoryFileType.STORY_FILE_TYPE);
                    Query<PsiReference> query = ReferencesSearch.search(new ReferencesSearch.SearchParameters(method, restrictedScope, false, p.getOptimizer()));
                    query.forEach(consumer);
                }
            }
        }
    }
}
