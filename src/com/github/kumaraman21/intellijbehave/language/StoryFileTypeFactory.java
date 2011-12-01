package com.github.kumaraman21.intellijbehave.language;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class StoryFileTypeFactory extends FileTypeFactory {
  @Override
  public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
    fileTypeConsumer.consume(StoryFileType.STORY_FILE_TYPE, StoryFileType.STORY_FILE_TYPE.getDefaultExtension());
  }
}
