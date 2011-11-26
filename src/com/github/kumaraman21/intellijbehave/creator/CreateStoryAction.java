/*
 * Copyright 2000-2011 JetBrains s.r.o.
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

import com.intellij.ide.IdeBundle;
import com.intellij.ide.actions.CreateElementActionBase;
import com.intellij.ide.fileTemplates.FileTemplate;
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
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

import static com.github.kumaraman21.intellijbehave.language.StoryFileType.STORY_FILE_TYPE;

public class CreateStoryAction extends CreateElementActionBase {

  public CreateStoryAction() {
    super(IdeBundle.message("action.create.new.filetype", STORY_FILE_TYPE.getName()),
          STORY_FILE_TYPE.getDescription(), STORY_FILE_TYPE.getIcon());
  }

  @NotNull
  @Override
  protected PsiElement[] invokeDialog(Project project, PsiDirectory directory) {
    CreateElementActionBase.MyInputValidator validator = new CreateElementActionBase.MyInputValidator(project, directory);
    Messages.showInputDialog(project, IdeBundle.message("prompt.enter.new.filetype.name", STORY_FILE_TYPE.getName()),
                             IdeBundle.message("title.new.filetype", STORY_FILE_TYPE.getName()), Messages.getQuestionIcon(), "", validator);
    return validator.getCreatedElements();
  }

  @Override
  protected void checkBeforeCreate(String newName, PsiDirectory directory) throws IncorrectOperationException {
    newName = getFileName(newName);
    directory.checkCreateFile(newName);
  }

  @NotNull
  @Override
  protected PsiElement[] create(String newName, PsiDirectory directory) throws Exception {
    final FileTemplate template = FileTemplateManager.getInstance().getTemplate(STORY_FILE_TYPE.getName());

    String fileName = getFileName(newName);
    Project project = directory.getProject();

    directory.checkCreateFile(fileName);
    PsiFile psiFile = PsiFileFactory.getInstance(project)
      .createFileFromText(fileName, STORY_FILE_TYPE, template.getText());

    if (template.isReformatCode()) {
      CodeStyleManager.getInstance(project).reformat(psiFile);
    }
    psiFile = (PsiFile)directory.add(psiFile);

    final VirtualFile virtualFile = psiFile.getVirtualFile();
    FileEditorManager.getInstance(project).openFile(virtualFile, true);

    return new PsiElement[]{psiFile};
  }

  @Override
  protected String getErrorTitle() {
    return IdeBundle.message("title.cannot.create.filetype", STORY_FILE_TYPE.getName());
  }

  @Override
  protected String getCommandName() {
    return IdeBundle.message("command.name.create.new.file", STORY_FILE_TYPE.getName());
  }

  @Override
  protected String getActionName(PsiDirectory directory, String newName) {
    return IdeBundle.message("progress.creating.filetype.in.directory", STORY_FILE_TYPE.getName(), newName, directory.getName());
  }

  public void update(final AnActionEvent e) {
    super.update(e);
    Presentation presentation = e.getPresentation();
    final FileTypeManager manager = FileTypeManager.getInstance();
    final FileType fileType = manager.getFileTypeByExtension(HtmlFileType.DOT_DEFAULT_EXTENSION);
    if (fileType == FileTypes.PLAIN_TEXT) {
      presentation.setEnabled(false);
      presentation.setVisible(false);
    }
  }

  private String getFileName(String name) {
      return name + "." + STORY_FILE_TYPE.getDefaultExtension();
    }
}
