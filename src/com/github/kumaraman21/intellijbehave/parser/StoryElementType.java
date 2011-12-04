package com.github.kumaraman21.intellijbehave.parser;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import static com.github.kumaraman21.intellijbehave.language.StoryFileType.STORY_FILE_TYPE;

public class StoryElementType extends IElementType {

  public StoryElementType(@NotNull @NonNls String debugName) {
    super(debugName, STORY_FILE_TYPE.getLanguage());
  }

  public static final IFileElementType STORY_FILE = new IFileElementType(STORY_FILE_TYPE.getLanguage());
  public static final IElementType STORY = new StoryElementType("STORY");
  public static final IElementType SCENARIO = new StoryElementType("SCENARIO");
  public static final IElementType STEP = new StoryElementType("STEP");
}
