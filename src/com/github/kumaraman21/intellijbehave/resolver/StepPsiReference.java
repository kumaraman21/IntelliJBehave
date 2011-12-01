package com.github.kumaraman21.intellijbehave.resolver;

import com.github.kumaraman21.intellijbehave.parser.StepPsiElement;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.parsers.StepMatcher;
import org.jbehave.core.parsers.StepPatternParser;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.*;

public class StepPsiReference implements PsiReference {

  private StepPsiElement stepPsiElement;

  public StepPsiReference(StepPsiElement stepPsiElement) {
    this.stepPsiElement = stepPsiElement;
  }

  @Override
  public PsiElement getElement() {
    return stepPsiElement;
  }

  @Override
  public TextRange getRangeInElement() {
    return TextRange.from(0, stepPsiElement.getTextLength());
  }

  @Override
  public PsiElement resolve() {
    //Todo: remove deprecated api use
    DataContext dataContext = DataManager.getInstance().getDataContext();
    final Project project = DataKeys.PROJECT.getData(dataContext);

    StepType stepType = StepType.valueOf(trim(substringBefore(stepPsiElement.getText(), " ")).toUpperCase());
    String stepText = trim(substringAfter(stepPsiElement.getText(), " "));

    StepAnnotationFinder stepAnnotationFinder = new StepAnnotationFinder(project, stepType, stepText);
    ProjectRootManager.getInstance(project).getFileIndex().iterateContent(stepAnnotationFinder);

    return stepAnnotationFinder.getMatchingAnnotation();
  }

private static class StepAnnotationFinder implements ContentIterator {

  private static final Map<StepType, String> STEP_TYPE_TO_ANNOTATION_MAPPING = new HashMap<StepType, String>();

  static {
    STEP_TYPE_TO_ANNOTATION_MAPPING.put(StepType.GIVEN, Given.class.getName());
    STEP_TYPE_TO_ANNOTATION_MAPPING.put(StepType.WHEN, When.class.getName());
    STEP_TYPE_TO_ANNOTATION_MAPPING.put(StepType.THEN, Then.class.getName());
  }

  private Project project;
  private StepType stepType;
  private String stepText;
  private PsiElement matchingAnnotation;

  private StepAnnotationFinder(Project project, StepType stepType, String stepText) {
    this.project = project;
    this.stepType = stepType;
    this.stepText = stepText;
  }

  @Override
  public boolean processFile(VirtualFile virtualFile) {
    PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
    if (psiFile instanceof PsiJavaFile) {
      PsiClass[] psiClasses = ((PsiJavaFile)psiFile).getClasses();

      for (PsiClass psiClass : psiClasses) {
        PsiMethod[] methods = psiClass.getMethods();

        for (PsiMethod method : methods) {
          PsiAnnotation[] annotations = method.getModifierList().getApplicableAnnotations();

          for (PsiAnnotation annotation : annotations) {
            if(annotation.getQualifiedName().equalsIgnoreCase(STEP_TYPE_TO_ANNOTATION_MAPPING.get(stepType))) {
              StepPatternParser stepPatternParser = new RegexPrefixCapturingPatternParser();
              String annotationText = removeStart(removeEnd(annotation.getParameterList().getAttributes()[0].getValue().getText(), "\""), "\"");
              StepMatcher stepMatcher = stepPatternParser.parseStep(stepType, annotationText);

              if(stepMatcher.matches(stepText)) {
                matchingAnnotation = annotation;
                return false;
              }
            }
          }
        }
      }
    }

    return true;
  }

  public PsiElement getMatchingAnnotation() {
    return matchingAnnotation;
  }
}

  @NotNull
  @Override
  public String getCanonicalText() {
    return trim(stepPsiElement.getText());
  }

  @Override
  public PsiElement handleElementRename(String s) throws IncorrectOperationException {
    throw new IncorrectOperationException();
  }

  @Override
  public PsiElement bindToElement(@NotNull PsiElement psiElement) throws IncorrectOperationException {
    throw new IncorrectOperationException();
  }

  @Override
  public boolean isReferenceTo(PsiElement psiElement) {
    return psiElement instanceof StepPsiElement && Comparing.equal(resolve(), psiElement);
  }

  @NotNull
  @Override
  public Object[] getVariants() {
    return new Object[0];
  }

  @Override
  public boolean isSoft() {
    return false;
  }
}
