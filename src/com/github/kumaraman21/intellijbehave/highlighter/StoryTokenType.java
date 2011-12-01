package com.github.kumaraman21.intellijbehave.highlighter;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;

import static com.github.kumaraman21.intellijbehave.language.StoryFileType.STORY_FILE_TYPE;

public class StoryTokenType extends IElementType {

  public StoryTokenType(@NonNls String debugName) {
    super(debugName, STORY_FILE_TYPE.getLanguage());
  }

  public static final IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
  public static final IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;

  public static final IElementType STORY_DESCRIPTION = new StoryTokenType("STORY_DESCRIPTION");
  public static final IElementType STEP_TYPE = new StoryTokenType("STEP_TYPE");
  public static final IElementType STEP_TEXT = new StoryTokenType("STEP_TEXT");
  public static final IElementType COMMENT = new StoryTokenType("COMMENT");
}
