package com.github.kumaraman21.intellijbehave.language;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

import static com.github.kumaraman21.intellijbehave.language.StoryLanguage.STORY_LANGUAGE;

public class StoryFileType extends LanguageFileType {
  public static final StoryFileType STORY_FILE_TYPE = new StoryFileType();

  protected StoryFileType() {
    super(STORY_LANGUAGE);
  }

  @NotNull
  @Override
  public String getName() {
    return "JBehave Story";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "JBehave story files";
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return "story";
  }

  @Override
  public Icon getIcon() {
    return null;
  }

  @Override
  public String getCharset(@NotNull VirtualFile virtualFile, byte[] bytes) {
    return null;
  }
}
