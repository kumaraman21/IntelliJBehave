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

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import gnu.trove.THashMap;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class StorySyntaxHighlighter extends SyntaxHighlighterBase {

  private static final Map<IElementType, TextAttributesKey> keys;

  private static final TextAttributesKey STORY_DESCRIPTION_ATTRIBUTES
    = TextAttributesKey.createTextAttributesKey("STORY_DESCRIPTION_ATTRIBUTES",
                                                SyntaxHighlighterColors.NUMBER.getDefaultAttributes());
  private static final TextAttributesKey STEP_TYPE_ATTRIBUTES
    = TextAttributesKey.createTextAttributesKey("STEP_TYPE_ATTRIBUTES",
                                                SyntaxHighlighterColors.KEYWORD.getDefaultAttributes());
  private static final TextAttributesKey STEP_TEXT_ATTRIBUTES
    = TextAttributesKey.createTextAttributesKey("STEP_TEXT_ATTRIBUTES",
                                                SyntaxHighlighterColors.STRING.getDefaultAttributes());
  private static final TextAttributesKey COMMENT_ATTRIBUTES
    = TextAttributesKey.createTextAttributesKey("COMMENT_ATTRIBUTES",
                                                SyntaxHighlighterColors.LINE_COMMENT.getDefaultAttributes());
  private static final TextAttributesKey BAD_CHARACTER_ATTRIBUTES
    = TextAttributesKey.createTextAttributesKey("BAD_CHARACTER_ATTRIBUTES",
                                                SyntaxHighlighterColors.INVALID_STRING_ESCAPE.getDefaultAttributes());

  static {
    keys = new THashMap<IElementType, TextAttributesKey>();

    keys.put(StoryTokenType.STORY_DESCRIPTION, STORY_DESCRIPTION_ATTRIBUTES);
    keys.put(StoryTokenType.STEP_TYPE, STEP_TYPE_ATTRIBUTES);
    keys.put(StoryTokenType.STEP_TEXT, STEP_TEXT_ATTRIBUTES);
    keys.put(StoryTokenType.COMMENT, COMMENT_ATTRIBUTES);
    keys.put(StoryTokenType.BAD_CHARACTER, BAD_CHARACTER_ATTRIBUTES);
  }

  @NotNull
  @Override
  public Lexer getHighlightingLexer() {
    return new StorySyntaxHighlightingLexer();
  }

  @NotNull
  @Override
  public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
    return new TextAttributesKey[]{keys.get(tokenType)};
  }
}
