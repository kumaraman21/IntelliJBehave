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
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.CodeInsightColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import gnu.trove.THashMap;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class StorySyntaxHighlighter extends SyntaxHighlighterBase {

    private static final Map<IElementType, TextAttributesKey> ATTRIBUTES = new THashMap<IElementType, TextAttributesKey>();

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new StorySyntaxHighlightingLexer();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return pack(ATTRIBUTES.get(tokenType));
    }

    @NonNls
    public static final String STORY_DESCRIPTION_ID = "JBEHAVE.STORY_DESCRIPTION";
    @NonNls
    public static final String SCENARIO_TYPE_ID = "JBEHAVE.SCENARIO_TYPE";
    @NonNls
    public static final String SCENARIO_TEXT_ID = "JBEHAVE.SCENARIO_TEXT";
    @NonNls
    public static final String STEP_TYPE_ID = "JBEHAVE.STEP_TYPE";
    @NonNls
    public static final String STEP_TEXT_ID = "JBEHAVE.STEP_TEXT";
    @NonNls
    public static final String TABLE_DELIM_ID = "JBEHAVE.TABLE_DELIM";
    @NonNls
    public static final String TABLE_CELL_ID = "JBEHAVE.TABLE_CELL";
    @NonNls
    public static final String META_TYPE_ID = "JBEHAVE.META_TYPE";
    @NonNls
    public static final String META_KEY_ID = "JBEHAVE.META_KEY";
    @NonNls
    public static final String META_TEXT_ID = "JBEHAVE.META_TEXT";
    @NonNls
    public static final String LINE_COMMENT_ID = "JBEHAVE.COMMENT";
    @NonNls
    public static final String BAD_CHARACTER_ID = "JBEHAVE.BAD_CHARACTER";

    // Registering TextAttributes
    static {
        createKey(STORY_DESCRIPTION_ID, SyntaxHighlighterColors.NUMBER);
        createKey(SCENARIO_TYPE_ID, CodeInsightColors.STATIC_FIELD_ATTRIBUTES);
        createKey(SCENARIO_TEXT_ID, CodeInsightColors.STATIC_FIELD_ATTRIBUTES);
        createKey(STEP_TYPE_ID, SyntaxHighlighterColors.KEYWORD);
        createKey(STEP_TEXT_ID, HighlighterColors.TEXT);
        createKey(TABLE_DELIM_ID, SyntaxHighlighterColors.BRACES);
        createKey(TABLE_CELL_ID, SyntaxHighlighterColors.STRING);
        createKey(META_TYPE_ID, SyntaxHighlighterColors.KEYWORD);
        createKey(META_KEY_ID,  SyntaxHighlighterColors.STRING);
        createKey(META_TEXT_ID, SyntaxHighlighterColors.STRING);
        createKey(LINE_COMMENT_ID, SyntaxHighlighterColors.LINE_COMMENT);
        createKey(BAD_CHARACTER_ID, SyntaxHighlighterColors.INVALID_STRING_ESCAPE);
    }

    public static TextAttributesKey STORY_DESCRIPTION = TextAttributesKey.createTextAttributesKey(STORY_DESCRIPTION_ID);
    public static TextAttributesKey SCENARIO_TYPE = TextAttributesKey.createTextAttributesKey(SCENARIO_TYPE_ID);
    public static TextAttributesKey SCENARIO_TEXT = TextAttributesKey.createTextAttributesKey(SCENARIO_TEXT_ID);
    public static TextAttributesKey STEP_TYPE = TextAttributesKey.createTextAttributesKey(STEP_TYPE_ID);
    public static TextAttributesKey STEP_TEXT = TextAttributesKey.createTextAttributesKey(STEP_TEXT_ID);
    public static TextAttributesKey TABLE_DELIM = TextAttributesKey.createTextAttributesKey(TABLE_DELIM_ID);
    public static TextAttributesKey TABLE_CELL = TextAttributesKey.createTextAttributesKey(TABLE_CELL_ID);
    public static TextAttributesKey META_TYPE = TextAttributesKey.createTextAttributesKey(META_TYPE_ID);
    public static TextAttributesKey META_KEY = TextAttributesKey.createTextAttributesKey(META_KEY_ID);
    public static TextAttributesKey META_TEXT = TextAttributesKey.createTextAttributesKey(META_TEXT_ID);
    public static TextAttributesKey LINE_COMMENT = TextAttributesKey.createTextAttributesKey(LINE_COMMENT_ID);
    public static TextAttributesKey BAD_CHARACTER = TextAttributesKey.createTextAttributesKey(BAD_CHARACTER_ID);

    public static List<TextAttributesKey> ALL = Collections.unmodifiableList(Arrays.asList(//
            STORY_DESCRIPTION, SCENARIO_TYPE, SCENARIO_TEXT,//
            STEP_TYPE, STEP_TEXT, TABLE_DELIM, TABLE_CELL,//
            META_TYPE, META_KEY, META_TEXT, //
            LINE_COMMENT, BAD_CHARACTER
            ));

    static {
        ATTRIBUTES.put(StoryTokenType.STORY_DESCRIPTION, STORY_DESCRIPTION);
        ATTRIBUTES.put(StoryTokenType.SCENARIO_TYPE, SCENARIO_TYPE);
        ATTRIBUTES.put(StoryTokenType.SCENARIO_TEXT, SCENARIO_TEXT);
        ATTRIBUTES.put(StoryTokenType.STEP_TYPE, STEP_TYPE);
        ATTRIBUTES.put(StoryTokenType.STEP_TEXT, STEP_TEXT);
        ATTRIBUTES.put(StoryTokenType.TABLE_DELIM, TABLE_DELIM);
        ATTRIBUTES.put(StoryTokenType.TABLE_CELL, TABLE_CELL);
        ATTRIBUTES.put(StoryTokenType.META, META_TYPE);
        ATTRIBUTES.put(StoryTokenType.META_KEY, META_KEY);
        ATTRIBUTES.put(StoryTokenType.META_TEXT, META_TEXT);
        ATTRIBUTES.put(StoryTokenType.COMMENT, LINE_COMMENT);
        ATTRIBUTES.put(StoryTokenType.BAD_CHARACTER, BAD_CHARACTER);
    }

    private static TextAttributesKey createKey(String externalName, TextAttributesKey textAttributesKey) {
        return TextAttributesKey.createTextAttributesKey(externalName, textAttributesKey.getDefaultAttributes());
    }
}
