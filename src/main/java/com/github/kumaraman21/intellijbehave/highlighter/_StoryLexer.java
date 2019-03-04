/* The following code was generated by JFlex 1.4.3 on 1/21/14 11:33 AM */

package com.github.kumaraman21.intellijbehave.highlighter;

import java.util.Stack;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 1/21/14 11:33 AM from the specification file
 * <tt>Story.flex</tt>
 */
class _StoryLexer implements FlexLexer {
  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int IN_META = 14;
  public static final int IN_GIVEN = 8;
  public static final int IN_DIRECTIVE = 2;
  public static final int IN_THEN = 12;
  public static final int YYINITIAL = 0;
  public static final int IN_EXAMPLES = 18;
  public static final int IN_SCENARIO = 6;
  public static final int IN_WHEN = 10;
  public static final int IN_TABLE = 16;
  public static final int IN_STORY = 4;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2,  2,  3,  3,  4,  4,  5,  5,  6,  6,  7,  7, 
     8,  8,  9, 9
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\3\1\2\1\0\1\3\1\1\22\0\1\17\1\37\13\0"+
    "\1\40\14\0\1\16\5\0\1\5\1\35\3\0\1\22\1\0\1\30"+
    "\5\0\1\20\5\0\1\6\1\34\2\0\1\32\11\0\1\12\1\0"+
    "\1\7\1\36\1\10\2\0\1\33\1\14\2\0\1\26\1\24\1\11"+
    "\1\15\1\25\1\0\1\13\1\27\1\21\1\0\1\31\1\0\1\23"+
    "\3\0\1\4\uff83\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\12\0\1\1\2\2\10\1\1\3\1\4\1\5\7\3"+
    "\2\4\1\6\1\3\2\4\3\3\1\7\1\10\1\11"+
    "\2\12\1\13\1\4\1\14\7\1\7\0\1\14\10\0"+
    "\2\15\3\0\5\1\6\0\1\16\7\0\5\17\3\0"+
    "\3\1\13\0\1\15\6\0\2\1\1\0\1\20\2\0"+
    "\1\21\1\22\10\0\2\1\2\0\1\23\2\0\6\15"+
    "\2\1\4\0\5\24\5\25\5\26\1\1\16\0\1\27"+
    "\7\0\1\30";

  private static int [] zzUnpackAction() {
    int [] result = new int[198];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\41\0\102\0\143\0\204\0\245\0\306\0\347"+
    "\0\u0108\0\u0129\0\u014a\0\u016b\0\u018c\0\u01ad\0\u01ce\0\u01ef"+
    "\0\u0210\0\u0231\0\u0252\0\u0273\0\u0294\0\u018c\0\u02b5\0\u018c"+
    "\0\u02d6\0\u02f7\0\u0318\0\u0339\0\u035a\0\u037b\0\u039c\0\u03bd"+
    "\0\u03de\0\u03ff\0\u0420\0\u0441\0\u0462\0\u0483\0\u04a4\0\u04c5"+
    "\0\u04e6\0\u0507\0\u0528\0\u0549\0\u018c\0\u018c\0\u018c\0\u01ad"+
    "\0\u056a\0\u058b\0\u05ac\0\u05cd\0\u05ee\0\u060f\0\u0630\0\u0651"+
    "\0\u0672\0\u0693\0\u06b4\0\u06d5\0\u06f6\0\u0717\0\u018c\0\u0738"+
    "\0\u0759\0\u077a\0\u079b\0\u07bc\0\u07dd\0\u07fe\0\u0420\0\u081f"+
    "\0\u0840\0\u0861\0\u0882\0\u08a3\0\u08c4\0\u08e5\0\u0906\0\u0927"+
    "\0\u0948\0\u0969\0\u098a\0\u09ab\0\u09cc\0\u09ed\0\u0a0e\0\u0a2f"+
    "\0\u0a50\0\u0a71\0\u0a92\0\u0ab3\0\u0ad4\0\u0af5\0\u0b16\0\u018c"+
    "\0\u0b37\0\u0b58\0\u0b79\0\u0b9a\0\u0bbb\0\u0bdc\0\u0bfd\0\u0c1e"+
    "\0\u0c3f\0\u0c60\0\u0c81\0\u0ca2\0\u0cc3\0\u0ce4\0\u0d05\0\u0d26"+
    "\0\u0d47\0\u0d68\0\u0d89\0\u0daa\0\u0dcb\0\u018c\0\u0dec\0\u0e0d"+
    "\0\u0e2e\0\u0e4f\0\u0e70\0\u0e91\0\u0eb2\0\u0ed3\0\u0ef4\0\u018c"+
    "\0\u0f15\0\u0f36\0\u018c\0\u018c\0\u0f57\0\u0f78\0\u0f99\0\u0fba"+
    "\0\u0b37\0\u0fdb\0\u0ffc\0\u101d\0\u103e\0\u105f\0\u1080\0\u10a1"+
    "\0\u018c\0\u10c2\0\u10e3\0\u1104\0\u1125\0\u1146\0\u1167\0\u1188"+
    "\0\u11a9\0\u11ca\0\u11eb\0\u120c\0\u122d\0\u124e\0\u126f\0\u018c"+
    "\0\u0b37\0\u1290\0\u12b1\0\u12d2\0\u018c\0\u0b37\0\u12f3\0\u1314"+
    "\0\u1335\0\u018c\0\u0b37\0\u1356\0\u1377\0\u1398\0\u13b9\0\u13da"+
    "\0\u13fb\0\u141c\0\u143d\0\u145e\0\u147f\0\u14a0\0\u14c1\0\u14e2"+
    "\0\u1503\0\u1524\0\u1545\0\u1566\0\u1587\0\u018c\0\u15a8\0\u15c9"+
    "\0\u15ea\0\u160b\0\u162c\0\u164d\0\u166e\0\u018c";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[198];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\13\1\14\1\15\1\13\1\16\1\13\1\17\11\13"+
    "\1\20\1\13\1\21\5\13\1\22\1\13\1\23\1\13"+
    "\1\23\1\24\1\13\1\25\1\13\1\26\3\27\1\30"+
    "\1\26\1\31\10\26\1\27\1\32\1\26\1\33\5\26"+
    "\1\34\1\26\1\35\1\26\1\36\2\26\1\37\1\26"+
    "\1\13\1\40\1\41\36\13\1\42\1\40\1\41\36\42"+
    "\1\43\1\44\1\45\32\43\1\46\4\43\1\44\1\45"+
    "\32\43\1\47\4\43\1\44\1\45\32\43\1\50\3\43"+
    "\1\51\1\40\1\41\2\51\1\52\33\51\1\53\1\54"+
    "\1\55\1\53\1\56\34\53\1\26\1\40\1\41\1\57"+
    "\13\26\1\57\21\26\1\13\2\0\36\13\2\0\1\15"+
    "\77\0\1\60\2\0\36\60\1\13\2\0\4\13\1\61"+
    "\32\13\2\0\5\13\1\62\31\13\2\0\20\13\1\63"+
    "\16\13\2\0\11\13\1\64\25\13\2\0\30\13\1\65"+
    "\6\13\2\0\6\13\1\66\30\13\2\0\35\13\1\67"+
    "\1\0\3\27\13\0\1\27\30\0\1\70\41\0\1\71"+
    "\53\0\1\72\31\0\1\73\57\0\1\74\40\0\1\75"+
    "\45\0\1\76\2\0\1\41\1\0\1\77\1\0\1\100"+
    "\11\0\1\101\1\0\1\102\5\0\1\103\1\0\1\104"+
    "\1\0\1\104\1\105\1\0\1\106\5\0\1\77\1\0"+
    "\1\100\11\0\1\101\1\0\1\102\5\0\1\103\1\0"+
    "\1\104\1\0\1\104\1\105\1\0\1\106\1\0\1\42"+
    "\2\0\36\42\1\107\1\110\1\111\36\107\2\0\1\45"+
    "\1\0\1\77\1\0\1\100\11\0\1\101\1\0\1\102"+
    "\5\0\1\103\1\0\1\104\1\0\1\104\2\0\1\106"+
    "\5\0\1\77\1\0\1\100\11\0\1\101\1\0\1\102"+
    "\5\0\1\103\1\0\1\104\1\0\1\104\2\0\1\106"+
    "\1\0\1\107\1\110\1\111\6\107\1\112\30\107\1\110"+
    "\1\111\6\107\1\113\30\107\1\110\1\111\6\107\1\114"+
    "\27\107\1\51\2\0\2\51\1\0\33\51\1\52\3\0"+
    "\13\52\1\0\21\52\1\53\2\0\1\53\1\0\34\53"+
    "\2\0\1\55\36\0\1\13\2\0\5\13\1\115\31\13"+
    "\2\0\16\13\1\116\20\13\2\0\7\13\1\117\27\13"+
    "\2\0\26\13\1\65\10\13\2\0\5\13\1\120\31\13"+
    "\2\0\33\13\1\121\3\13\2\0\35\13\1\16\10\0"+
    "\1\122\51\0\1\123\31\0\1\124\57\0\1\125\17\0"+
    "\1\126\40\0\1\127\70\0\1\130\7\0\1\131\41\0"+
    "\1\132\53\0\1\133\31\0\1\134\57\0\1\135\16\0"+
    "\1\136\67\0\1\137\1\140\1\0\1\111\1\140\1\141"+
    "\23\140\1\142\1\140\1\143\1\140\1\143\1\144\4\140"+
    "\2\0\1\140\1\141\23\140\1\142\1\140\1\143\1\140"+
    "\1\143\1\144\3\140\1\107\1\110\1\111\33\107\1\145"+
    "\3\107\1\110\1\111\33\107\1\146\3\107\1\110\1\111"+
    "\33\107\1\147\2\107\1\13\2\0\6\13\1\150\30\13"+
    "\2\0\7\13\1\151\27\13\2\0\21\13\1\152\15\13"+
    "\2\0\6\13\1\121\30\13\2\0\14\13\1\16\21\13"+
    "\11\0\1\153\41\0\1\154\52\0\1\155\24\0\1\156"+
    "\41\0\1\157\40\0\1\160\27\0\1\130\2\0\36\130"+
    "\10\0\1\161\51\0\1\162\31\0\1\163\57\0\1\135"+
    "\17\0\1\164\66\0\1\165\42\0\1\77\17\0\1\166"+
    "\35\0\1\167\57\0\1\170\16\0\1\171\27\0\1\107"+
    "\1\110\1\111\14\107\1\172\22\107\1\110\1\111\14\107"+
    "\1\173\22\107\1\110\1\111\14\107\1\174\21\107\1\13"+
    "\2\0\7\13\1\175\27\13\2\0\13\13\1\16\23\13"+
    "\2\0\22\13\1\176\13\13\12\0\1\177\44\0\1\200"+
    "\47\0\1\201\24\0\1\202\46\0\1\203\40\0\1\204"+
    "\32\0\1\205\41\0\1\206\52\0\1\207\25\0\1\165"+
    "\46\0\1\77\52\0\1\170\17\0\1\210\66\0\1\211"+
    "\2\0\1\212\1\110\1\111\36\212\1\213\1\110\1\111"+
    "\36\213\1\214\1\110\1\111\36\214\1\13\2\0\10\13"+
    "\1\215\26\13\2\0\23\13\1\216\12\13\13\0\1\217"+
    "\53\0\1\220\31\0\1\221\33\0\1\222\44\0\1\77"+
    "\47\0\1\223\24\0\1\211\27\0\1\212\1\224\1\225"+
    "\36\212\1\213\1\226\1\227\36\213\1\214\1\230\1\231"+
    "\36\214\1\13\2\0\11\13\1\232\25\13\2\0\5\13"+
    "\1\233\30\13\14\0\1\234\34\0\1\235\43\0\1\236"+
    "\53\0\1\237\12\0\1\240\1\0\1\225\1\240\1\241"+
    "\23\240\1\242\1\240\1\243\1\240\1\243\1\244\4\240"+
    "\2\0\1\240\1\241\23\240\1\242\1\240\1\243\1\240"+
    "\1\243\1\244\3\240\1\245\1\0\1\227\1\245\1\246"+
    "\23\245\1\247\1\245\1\250\1\245\1\250\1\251\4\245"+
    "\2\0\1\245\1\246\23\245\1\247\1\245\1\250\1\245"+
    "\1\250\1\251\3\245\1\252\1\0\1\231\1\252\1\253"+
    "\23\252\1\254\1\252\1\255\1\252\1\255\1\256\4\252"+
    "\2\0\1\252\1\253\23\252\1\254\1\252\1\255\1\252"+
    "\1\255\1\256\3\252\1\13\2\0\12\13\1\257\24\13"+
    "\2\0\24\13\1\151\11\13\15\0\1\260\52\0\1\261"+
    "\25\0\1\262\34\0\1\263\44\0\1\264\57\0\1\265"+
    "\16\0\1\266\43\0\1\267\57\0\1\270\16\0\1\271"+
    "\43\0\1\272\57\0\1\273\16\0\1\274\27\0\1\13"+
    "\2\0\13\13\1\121\22\13\16\0\1\275\40\0\1\276"+
    "\37\0\1\277\52\0\1\206\42\0\1\265\17\0\1\300"+
    "\66\0\1\301\33\0\1\270\17\0\1\302\66\0\1\303"+
    "\33\0\1\273\17\0\1\304\66\0\1\305\21\0\1\306"+
    "\37\0\1\165\33\0\1\301\46\0\1\240\32\0\1\303"+
    "\46\0\1\245\32\0\1\305\46\0\1\252\21\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[5775];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;
  private static final char[] EMPTY_BUFFER = new char[0];
  private static final int YYEOF = -1;
  private static java.io.Reader zzReader = null; // Fake

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\12\0\2\1\1\11\10\1\1\11\1\1\1\11\24\1"+
    "\3\11\10\1\7\0\1\11\10\0\2\1\3\0\5\1"+
    "\6\0\1\1\7\0\1\11\4\1\3\0\3\1\13\0"+
    "\1\11\6\0\2\1\1\0\1\11\2\0\2\11\10\0"+
    "\2\1\2\0\1\11\2\0\10\1\4\0\1\11\4\1"+
    "\1\11\4\1\1\11\5\1\16\0\1\11\7\0\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[198];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** this buffer may contains the current text array to be matched when it is cheap to acquire it */
  private char[] zzBufferArray;

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
    private Stack<Integer> yystates = new Stack<Integer> () {{ push(YYINITIAL); }};
    private int currentStepStart = 0;
    public boolean trace = false;

    public void yystatePush(int yystate) {
        if(trace) System.out.println(">>>> PUSH: " + LexicalState.fromLexer(yystate) + " [" + reverseAndMap(yystates) + "]");
        yybegin(yystate);
        yystates.push(yystate);
    }

    private String reverseAndMap(Stack<Integer> yystates) {
        StringBuilder builder = new StringBuilder();
        for(int i=yystates.size()-1; i>=0; i--) {
            if(builder.length()>0)
                builder.append(", ");
            builder.append(LexicalState.fromLexer(yystates.get(i)));
        }
        return builder.toString();
    }

    public void yystatePopNPush(int yystate) {
        yystatePopNPush(1, yystate);
    }

    public void yystatePopNPush(int nb, int yystate) {
        if(trace) System.out.println(">>>> POP'n PUSH : #" + nb + ", " + LexicalState.fromLexer(yystate) + " [" + reverseAndMap(yystates) + "]");
        for (int i = 0; i < nb; i++) {
            yystatePop();
        }
        yystatePush(yystate);
    }

    public int yystatePop() {
        int popped = yystates.pop();
        if(trace) System.out.println(">>>> POP : " + LexicalState.fromLexer(popped) + " [" + reverseAndMap(yystates) + "]");
        if(!yystates.isEmpty()) {
            yybegin(yystates.peek());
        }// otherwise hopes a push will follow right after
        return popped;
    }

  public final int lastIndexOfCrLf(final CharSequence source) {
        final int length = source.length();
        boolean foundRfOrRn = false;

        for (int i = length - 1; i >= 0; i--) {
            final char c = source.charAt(i);
            if (c == '\r' || c == '\n') {
                foundRfOrRn = true;
            } else {
                if (foundRfOrRn) {
                    return i + 1;
                }
            }
        }

        if (foundRfOrRn) {
            return 0;
        } else {
            return -1;
        }
    }

    public void retrieveMultilineText() {
        yypushback(yytext().length() - lastIndexOfCrLf(yytext()));
        if(currentStepStart != 0) {
            zzStartRead = currentStepStart;
        }
    }

    public void setStepStart() {
        if(currentStepStart==0){
            currentStepStart = getTokenStart();
        }
    }

    public boolean checkAhead(char c) {

        if (zzMarkedPos >= zzBuffer.length()) {
            return false;
        }
        return zzBuffer.charAt(zzMarkedPos) == c;
    }


  _StoryLexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  _StoryLexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 106) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }

  public final int getTokenStart(){
    return zzStartRead;
  }

  public final int getTokenEnd(){
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end,int initialState){
    zzBuffer = buffer;
    zzBufferArray = com.intellij.util.text.CharArrayUtil.fromSequenceWithoutCopying(buffer);
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzPushbackPos = 0;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
  }

  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position <tt>pos</tt> from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBufferArray != null ? zzBufferArray[zzStartRead+pos]:zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() {
    if (!zzEOFDone) {
      zzEOFDone = true;
        return;

    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advance() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;
    char[] zzBufferArrayL = zzBufferArray;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL.charAt(zzCurrentPosL++);
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL.charAt(zzCurrentPosL++);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 9: 
          { return StoryTokenType.TABLE_CELL;
          }
        case 25: break;
        case 14: 
          { yystatePop();                    return StoryTokenType.COMMENT;
          }
        case 26: break;
        case 17: 
          { yystatePopNPush(2, IN_WHEN);     currentStepStart = 0; return StoryTokenType.WHEN_TYPE;
          }
        case 27: break;
        case 8: 
          { return StoryTokenType.META_KEY;
          }
        case 28: break;
        case 2: 
          { yystatePush(IN_STORY); yypushback(yytext().length());
          }
        case 29: break;
        case 18: 
          { yystatePopNPush(2, IN_THEN);     currentStepStart = 0; return StoryTokenType.THEN_TYPE;
          }
        case 30: break;
        case 22: 
          { yypushback(yytext().length() - 4); currentStepStart = 0; return StoryTokenType.THEN_TYPE;
          }
        case 31: break;
        case 10: 
          { yystatePop(); yypushback(1);
          }
        case 32: break;
        case 5: 
          { yystatePopNPush(1, IN_TABLE);    return StoryTokenType.TABLE_DELIM;
          }
        case 33: break;
        case 20: 
          { yypushback(yytext().length() - 4); currentStepStart = 0; return StoryTokenType.GIVEN_TYPE;
          }
        case 34: break;
        case 12: 
          { yystatePush(IN_DIRECTIVE); yypushback(yytext().length());
          }
        case 35: break;
        case 21: 
          { yypushback(yytext().length() - 4); currentStepStart = 0; return StoryTokenType.WHEN_TYPE;
          }
        case 36: break;
        case 7: 
          { return StoryTokenType.META_TEXT;
          }
        case 37: break;
        case 11: 
          { return StoryTokenType.TABLE_DELIM;
          }
        case 38: break;
        case 19: 
          { yystatePopNPush(2, IN_GIVEN);    currentStepStart = 0; return StoryTokenType.GIVEN_TYPE;
          }
        case 39: break;
        case 6: 
          { return StoryTokenType.SCENARIO_TEXT;
          }
        case 40: break;
        case 23: 
          { yystatePopNPush(2, IN_EXAMPLES); return StoryTokenType.EXAMPLE_TYPE;
          }
        case 41: break;
        case 13: 
          { retrieveMultilineText(); return StoryTokenType.STEP_TEXT;
          }
        case 42: break;
        case 1: 
          { return StoryTokenType.STORY_DESCRIPTION;
          }
        case 43: break;
        case 3: 
          { return StoryTokenType.BAD_CHARACTER;
          }
        case 44: break;
        case 16: 
          { yystatePopNPush(2, IN_META);     return StoryTokenType.META;
          }
        case 45: break;
        case 24: 
          { yystatePopNPush(2, IN_SCENARIO); return StoryTokenType.SCENARIO_TYPE;
          }
        case 46: break;
        case 15: 
          { setStepStart();
          }
        case 47: break;
        case 4: 
          { return StoryTokenType.WHITE_SPACE;
          }
        case 48: break;
        default:
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
            return null;
          }
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}