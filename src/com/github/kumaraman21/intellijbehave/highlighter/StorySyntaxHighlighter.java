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
package com.github.kumaraman21.intellijbehave.highlighter;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.CodeInsightColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import gnu.trove.THashMap;

import org.jetbrains.annotations.NotNull;
import java.util.Map;

public class StorySyntaxHighlighter extends SyntaxHighlighterBase {

    private Map<IElementType, TextAttributesKey> textAttributeKeys;

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new StorySyntaxHighlightingLexer();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return new TextAttributesKey[]{getKeys().get(tokenType)};
    }

    private Map<IElementType, TextAttributesKey> getKeys() {
        if (textAttributeKeys == null) {

            TextAttributesKey storyDescription = createKey("JBEHAVE.STORY_DESCRIPTION", SyntaxHighlighterColors.NUMBER);
            TextAttributesKey scenarioText = createKey("JBEHAVE.SCENARIO_TEXT", CodeInsightColors.STATIC_FIELD_ATTRIBUTES);
            TextAttributesKey stepType = createKey("JBEHAVE.STEP_TYPE", SyntaxHighlighterColors.KEYWORD);
            TextAttributesKey stepText = createKey("JBEHAVE.STEP_TEXT", SyntaxHighlighterColors.STRING);
            TextAttributesKey comment = createKey("JBEHAVE.COMMENT", SyntaxHighlighterColors.LINE_COMMENT);
            TextAttributesKey badCharacter = createKey("JBEHAVE.BAD_CHARACTER", SyntaxHighlighterColors.INVALID_STRING_ESCAPE);
            TextAttributesKey meta = createKey("JBEHAVE.META", SyntaxHighlighterColors.LINE_COMMENT);
            TextAttributesKey tableRow = createKey("JBEHAVE.TABLE_ROW", SyntaxHighlighterColors.STRING);

            textAttributeKeys = new THashMap<IElementType, TextAttributesKey>();
            textAttributeKeys.put(StoryTokenType.STORY_DESCRIPTION, storyDescription);
            textAttributeKeys.put(StoryTokenType.SCENARIO_TEXT, scenarioText);
            textAttributeKeys.put(StoryTokenType.STEP_TYPE, stepType);
            textAttributeKeys.put(StoryTokenType.STEP_TEXT, stepText);
            textAttributeKeys.put(StoryTokenType.TABLE_ROW, tableRow);
            textAttributeKeys.put(StoryTokenType.META, meta);
            textAttributeKeys.put(StoryTokenType.COMMENT, comment);
            textAttributeKeys.put(StoryTokenType.BAD_CHARACTER, badCharacter);
        }
        return textAttributeKeys;
    }

    private static TextAttributesKey createKey(String externalName, TextAttributesKey textAttributesKey) {
        return TextAttributesKey.createTextAttributesKey(externalName, textAttributesKey.getDefaultAttributes());
    }


}
