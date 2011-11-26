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
package com.github.kumaraman21.intellijbehave.highlighter;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
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

  public static final TokenSet COMMENTS = TokenSet.create(COMMENT);
  public static final TokenSet WHITE_SPACES = TokenSet.create(WHITE_SPACE);
}
