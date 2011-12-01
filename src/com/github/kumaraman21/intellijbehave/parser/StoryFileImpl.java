package com.github.kumaraman21.intellijbehave.parser;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

import static com.github.kumaraman21.intellijbehave.language.StoryFileType.STORY_FILE_TYPE;

public class StoryFileImpl extends PsiFileBase {

  public StoryFileImpl(FileViewProvider fileViewProvider) {
    super(fileViewProvider, STORY_FILE_TYPE.getLanguage());
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return STORY_FILE_TYPE;
  }
}
