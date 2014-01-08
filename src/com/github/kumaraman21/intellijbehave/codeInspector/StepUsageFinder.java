package com.github.kumaraman21.intellijbehave.codeInspector;

import static com.google.common.cache.CacheBuilder.newBuilder;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.List;
import java.util.Set;

import com.github.kumaraman21.intellijbehave.parser.StepPsiElement;
import com.github.kumaraman21.intellijbehave.parser.StoryFileImpl;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

public class StepUsageFinder implements ContentIterator {
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

	StepUsageFinder(final Project project) {
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
