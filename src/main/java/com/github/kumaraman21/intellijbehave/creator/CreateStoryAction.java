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
package com.github.kumaraman21.intellijbehave.creator;

import static com.github.kumaraman21.intellijbehave.language.StoryFileType.STORY_FILE_TYPE;

import com.intellij.ide.IdeBundle;
import com.intellij.ide.actions.CreateElementActionBase;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.highlighter.HtmlFileType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.fileTypes.FileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.codeStyle.CodeStyleManager;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class CreateStoryAction extends CreateElementActionBase {

  public CreateStoryAction() {
    super("Create New Story File", STORY_FILE_TYPE.getDescription(), STORY_FILE_TYPE.getIcon());
  }

  @Override
  protected void invokeDialog(@NotNull Project project, @NotNull PsiDirectory directory, @NotNull Consumer<? super PsiElement[]> elementsConsumer) {
    var validator = new CreateElementActionBase.MyInputValidator(project, directory);
    Messages.showInputDialog(project, "Enter a new file name:", "New Story File", Messages.getQuestionIcon(), "", validator);
    elementsConsumer.accept(validator.getCreatedElements());
  }

  @NotNull
  @Override
  protected PsiElement @NotNull [] create(@NotNull String newName, PsiDirectory directory) throws Exception {
    final var template = FileTemplateManager.getDefaultInstance().getTemplate(STORY_FILE_TYPE.getName());

    String fileName = getFileName(newName);
    Project project = directory.getProject();

    directory.checkCreateFile(fileName);
    var psiFile = PsiFileFactory.getInstance(project).createFileFromText(fileName, STORY_FILE_TYPE, template.getText());

    if (template.isReformatCode()) {
      CodeStyleManager.getInstance(project).reformat(psiFile);
    }
    psiFile = (PsiFile)directory.add(psiFile);

    FileEditorManager.getInstance(project).openFile(psiFile.getVirtualFile(), true);

    return new PsiElement[]{psiFile};
  }

  @Override
  protected String getErrorTitle() {
    return "Cannot Create Story File";
  }

  @NotNull
  @Override
  protected String getActionName(PsiDirectory directory, @NotNull String newName) {
    return IdeBundle.message("progress.creating.file", STORY_FILE_TYPE.getName(), newName, directory.getName());
  }

  @Override
  public void update(final @NotNull AnActionEvent e) {
    super.update(e);
    Presentation presentation = e.getPresentation();
    final FileType fileType = FileTypeManager.getInstance().getFileTypeByExtension(HtmlFileType.DOT_DEFAULT_EXTENSION);
    if (fileType == FileTypes.PLAIN_TEXT) {
      presentation.setEnabledAndVisible(false);
    }
  }

  private String getFileName(String name) {
    return name.endsWith("." + STORY_FILE_TYPE.getDefaultExtension()) ? name : name + "." + STORY_FILE_TYPE.getDefaultExtension();
  }
}
