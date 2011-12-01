package com.github.kumaraman21.intellijbehave.parser;

import com.github.kumaraman21.intellijbehave.resolver.StepPsiReference;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.NotNull;

public class StepPsiElement extends ASTWrapperPsiElement {
  public StepPsiElement(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public PsiReference getReference() {
    return new StepPsiReference(this);
  }
}
