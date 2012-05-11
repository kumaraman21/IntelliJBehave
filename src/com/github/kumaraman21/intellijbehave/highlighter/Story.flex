package com.github.kumaraman21.intellijbehave.highlighter;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

%%

%class _StoryLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

CRLF= \n | \r | \r\n
WHITE_SPACE_CHAR=[\ \n\r\t\f]
TEXT_CHAR=[^\n\r]
COMMENT=("!--")[^\r\n]*

%state IN_SCENARIO
%state IN_STEP

%%

<YYINITIAL> {CRLF}+"Scenario:" {TEXT_CHAR}+           { yybegin(IN_SCENARIO); return StoryTokenType.SCENARIO_TEXT; }
<IN_SCENARIO> {CRLF}+"Scenario:" {TEXT_CHAR}+      { yybegin(IN_SCENARIO); return StoryTokenType.SCENARIO_TEXT; }
<IN_STEP> {CRLF}+"Scenario:" {TEXT_CHAR}+              { yybegin(IN_SCENARIO); return StoryTokenType.SCENARIO_TEXT; }

<IN_SCENARIO> {CRLF}+"Given" {WHITE_SPACE_CHAR}           { yybegin(IN_STEP); return StoryTokenType.STEP_TYPE; }
<IN_SCENARIO> {CRLF}+"When" {WHITE_SPACE_CHAR}           { yybegin(IN_STEP); return StoryTokenType.STEP_TYPE; }
<IN_SCENARIO> {CRLF}+"Then" {WHITE_SPACE_CHAR}            { yybegin(IN_STEP); return StoryTokenType.STEP_TYPE; }
<IN_SCENARIO> {CRLF}+"And" {WHITE_SPACE_CHAR}             { yybegin(IN_STEP); return StoryTokenType.STEP_TYPE; }
<IN_SCENARIO> {CRLF}+{WHITE_SPACE_CHAR}* "|" {TEXT_CHAR}*      { yybegin(IN_SCENARIO); return StoryTokenType.TABLE_ROW; }
<IN_SCENARIO> {CRLF}+"Examples:" ({CRLF}+ {WHITE_SPACE_CHAR}* "|" {TEXT_CHAR}*)+ { yybegin(IN_SCENARIO); return StoryTokenType.EXAMPLE; }

<IN_SCENARIO> {COMMENT}                { return StoryTokenType.COMMENT; }
<YYINITIAL> .*                                    { return StoryTokenType.STORY_DESCRIPTION; }
<IN_SCENARIO> .*                               { return StoryTokenType.STORY_DESCRIPTION; }

<IN_STEP> {TEXT_CHAR}*          { yybegin(IN_SCENARIO); return StoryTokenType.STEP_TEXT; }

{WHITE_SPACE_CHAR}+                         { return StoryTokenType.WHITE_SPACE; }
.                                                          { return StoryTokenType.BAD_CHARACTER; }
