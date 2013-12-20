/*
 * Copyright 2011-12 Aman Kumar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.kumaraman21.intellijbehave.codeInspector;

import com.github.kumaraman21.intellijbehave.parser.StepPsiElement;
import com.github.kumaraman21.intellijbehave.parser.StoryFileImpl;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.intellij.codeInspection.BaseJavaLocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import org.jbehave.core.annotations.*;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

import static com.google.common.cache.CacheBuilder.newBuilder;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.concurrent.TimeUnit.SECONDS;

public class UnusedStepsInspection extends BaseJavaLocalInspectionTool {

    private static final List<String> JBEHAVE_ANNOTATIONS = newArrayList(
            Given.class.getName(),
            When.class.getName(),
            Then.class.getName(),
            Alias.class.getName(),
            Aliases.class.getName());

    @Nls
    @NotNull
    @Override
    public String getGroupDisplayName() {
        return "JBehave";
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Unused step declaration";
    }

    @NotNull
    @Override
    public String getShortName() {
        return "UnusedStepDeclaration";
    }

    @Override
    public String getStaticDescription() {
        return super.getStaticDescription();
    }

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, final boolean isOnTheFly) {
        return new JavaElementVisitor() {

            @Override
            public void visitElement(final PsiElement element) {
                super.visitElement(element);

                if (!(element instanceof PsiAnnotation)) {
                    return;
                }

                final PsiAnnotation annotation = (PsiAnnotation) element;
                if (!JBEHAVE_ANNOTATIONS.contains(annotation.getQualifiedName())) {
                    return;
                }

                final Project project = element.getProject();
                final StepUsageFinder stepUsageFinder = new StepUsageFinder(project);
                ProjectRootManager.getInstance(project).getFileIndex().iterateContent(stepUsageFinder);
                final Set<StepPsiElement> stepUsages = stepUsageFinder.getStepUsages();

                for (final StepPsiElement stepUsage : stepUsages) {
                    if (stepUsage.getReference().resolve() == annotation) {
                        return;
                    }
                }

                holder.registerProblem(annotation, "Step <code>#ref</code> is never used");
            }
        };
    }

    private static class StepUsageFinder implements ContentIterator {
        private final Project project;
        private final Set<StepPsiElement> stepUsages = newHashSet();

        private static final LoadingCache<CacheKey, List<StepPsiElement>> cache = newBuilder()
                .expireAfterWrite(10, SECONDS)
                .build(new CacheLoader<CacheKey, List<StepPsiElement>>() {
                    @Override
                    public List<StepPsiElement> load(final CacheKey key) throws Exception {
                        return key.getPsiFile().getSteps();
                    }
                });

        private StepUsageFinder(final Project project) {
            this.project = project;
        }

        @Override
        public boolean processFile(final VirtualFile virtualFile) {
            final PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
            if (psiFile instanceof StoryFileImpl) {
                stepUsages.addAll(getSteps((StoryFileImpl) psiFile));
            }
            return true;
        }

        private List<StepPsiElement> getSteps(final StoryFileImpl psiFile) {
            try {
                return cache.getUnchecked(key(psiFile));
            } catch (final Exception e) {
                return newArrayList();
            }
        }

        private CacheKey key(final StoryFileImpl psiFile) {
            return new CacheKey(psiFile);
        }

        public Set<StepPsiElement> getStepUsages() {
            return stepUsages;
        }

        private class CacheKey {
            private final StoryFileImpl psiFile;

            public CacheKey(final StoryFileImpl psiFile) {
                this.psiFile = psiFile;
            }

            public StoryFileImpl getPsiFile() {
                return psiFile;
            }

            @Override
            public boolean equals(final Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                final CacheKey cacheKey = (CacheKey) o;

                if (psiFile.getName() != null ? !psiFile.getName().equals(cacheKey.psiFile.getName()) : cacheKey.psiFile.getName() != null)
                    return false;

                return true;
            }

            @Override
            public int hashCode() {
                return psiFile.getName() != null ? psiFile.getName().hashCode() : 0;
            }
        }
    }
}
