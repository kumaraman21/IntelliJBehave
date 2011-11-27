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
STEP_TEXT_CHAR=[^\n\r]
COMMENT=("!--")[^\r\n]*

%state IN_STEP

%%

<YYINITIAL> {CRLF}+"Given"           { yybegin(IN_STEP); return StoryTokenType.STEP_TYPE; }
<YYINITIAL> {CRLF}+"When"           { yybegin(IN_STEP); return StoryTokenType.STEP_TYPE; }
<YYINITIAL> {CRLF}+"Then"            { yybegin(IN_STEP); return StoryTokenType.STEP_TYPE; }
<YYINITIAL> {CRLF}+"And"             { yybegin(IN_STEP); return StoryTokenType.STEP_TYPE; }
<YYINITIAL> {COMMENT}                { yybegin(YYINITIAL); return StoryTokenType.COMMENT; }
<YYINITIAL> .*                               { yybegin(YYINITIAL); return StoryTokenType.STORY_DESCRIPTION; }

<IN_STEP> {WHITE_SPACE_CHAR} {STEP_TEXT_CHAR}+          { yybegin(YYINITIAL); return StoryTokenType.STEP_TEXT; }

{WHITE_SPACE_CHAR}+                         { return StoryTokenType.WHITE_SPACE; }
.                                                          { return StoryTokenType.BAD_CHARACTER; }
