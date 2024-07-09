package Colores;
import java.io.*;   
import javax.swing.text.Segment;   
   
import org.fife.ui.rsyntaxtextarea.*;   
   
%%   
   
%public   
%class Color   
%extends AbstractJFlexCTokenMaker   
%unicode   
%type org.fife.ui.rsyntaxtextarea.Token   

%{   
   
   /**   
    * Constructor.  This must be here because JFlex does not generate a   
    * no-parameter constructor.   
    */   
   public Color() {   
   }   
   
      
   private void addHyperlinkToken(int start, int end, int tokenType) {   
      int so = start + offsetShift;   
      addToken(zzBuffer, start,end, tokenType, so, true);   
   }   
     
   private void addToken(int tokenType) {   
      addToken(zzStartRead, zzMarkedPos-1, tokenType);   
   }   
   
   private void addToken(int start, int end, int tokenType) {   
      int so = start + offsetShift;   
      addToken(zzBuffer, start,end, tokenType, so, false);   
   }   
   
   public void addToken(char[] array, int start, int end, int tokenType,   
                  int startOffset, boolean hyperlink) {   
      super.addToken(array, start,end, tokenType, startOffset, hyperlink);   
      zzStartRead = zzMarkedPos;   
   }   

   public String[] getLineCommentStartAndEnd() {   
      return new String[] { "//", null };   
   }   
    
   public Token getTokenList(Segment text, int initialTokenType, int startOffset) {   
   
      resetTokenList();   
      this.offsetShift = -text.offset + startOffset;   
   
      // Start off in the proper state.   
      int state = Token.NULL;   
      switch (initialTokenType) {   
                  case Token.COMMENT_MULTILINE:   
            state = MLC;   
            start = text.offset;   
            break;   
   
         /* No documentation comments */   
         default:   
            state = Token.NULL;   
      }   
   
      s = text;   
      try {   
         yyreset(zzReader);   
         yybegin(state);   
         return yylex();   
      } catch (IOException ioe) {   
         ioe.printStackTrace();   
         return new TokenImpl();   
      }   
   
   }   
    
   private boolean zzRefill() {   
      return zzCurrentPos>=s.offset+s.count;   
   }   
    
   public final void yyreset(Reader reader) {   
      // 's' has been updated.   
      zzBuffer = s.array;     
      //zzStartRead = zzEndRead = s.offset;   
      zzStartRead = s.offset;   
      zzEndRead = zzStartRead + s.count - 1;   
      zzCurrentPos = zzMarkedPos = zzPushbackPos = s.offset;   
      zzLexicalState = YYINITIAL;   
      zzReader = reader;   
      zzAtBOL  = true;   
      zzAtEOF  = false;   
   }   
   
%}   
   
Letra                     = [a-zA-Zñ_\-áéíóúüÁÉÍÓÚÜ]  
LetraIni                  = [a-zA-ZñáéíóúüÁÉÍÓÚÜ] 
Digit                     = ([0-9]+)   
CualquierCaracterExceptoApostrofeOBarrainvertida   = ([^\\'])   
CualquierCaracterExceptoComillasDoblesOBarraInvertida   = ([^\\\"\n])   
NoSeparador                 = ([^\t\f\r\n\ \(\)\{\}\[\]\,\.\=\>\<\!\:\+\-\*\/\&\|\^\%\"\#\'a-zA-Z] | "\\")  
IdentificadorIni               = ({LetraIni})   
IdentificadorCuerpo            = ({Letra}|{Digit})   
WhiteSpace            = ([ \t\f]+)   

CadChar               = ([\']({CualquierCaracterExceptoApostrofeOBarrainvertida}?)[\'])   
CadCharNoCerrada         = ([\'][^\'\n]*)   
ErrorCharLiteral         = ({CadCharNoCerrada}[\'])   
CadenaCaracteres            = ([\"]({CualquierCaracterExceptoComillasDoblesOBarraInvertida})*[\"])   
CadenaCaracteresNoCerrada      = ([\"]([\\].|[^\\\"])*[^\"]?)   
ErrorStringLiteral         = ({CadenaCaracteresNoCerrada}[\"])   

MLCBegin               = "/-"   
MLCEnd               = "-/"   
LineCommentBegin         = "//" | "#"

NumEntero         = ([+-]?{Digit})   
ErrorNumFormat         = [+-][+-]+{Digit} | [+-][+-]+{Identificador}   
NumFloat          = [-+]?{Digit}+(\.{Digit}+)
ErrorNumFloatFormat     =  ([-+]*{Digit}[:jletter:]*(\.)+ | [-+]*(\.)+{Digit}[:jletter:]* | [-+]*(\.)+{Digit}[:jletter:]*(\.)+{Digit}[:jletter:]* | [-+]*{Digit}[:jletter:]*(\.)+{Digit}[:jletter:]*(\.)+{Digit}[:jletter:]* | [+-][-+]+{Digit}[:jletter:]*(\.{Digit}[:jletter:]*) | [-+]?{Digit}(\.{Digit}[:jletter:]+) | [-+]?{Digit}(\.({Identificador}|{IdentificadorError})+))+

Separator               = ([\(\)\{\}\[\]])   
Separator2            = ([,.:])   

Identificador            = ({IdentificadorIni}{IdentificadorCuerpo}*)   
IdentificadorError = ({Digit}{Identificador} | {NoSeparador}+{Identificador}{NoSeparador}* | {NoSeparador}*{Identificador}{NoSeparador}+ | {Identificador}{NoSeparador}+{Identificador})+

Reservada = (i|I)(n|N)(t|T)|(f|F)(l|L)(o|O)(a|A)(t|T)|(c|C)(o|O)(n|N)(s|S)(t|T)|(c|C)(h|H)(a|A)(r|R)|(S|s)(t|T)(r|R)(i|I)(n|N)(g|G)|(b|B)(o|O)(o|O)(l|L)|(A|a)(r|R)(r|R)(a|A)(y|Y)|(C|c)(o|O)(l|L)(o|O)(r|R)|(R|r)(e|E)(c|C)(t|T)2|(D|d)(i|I)(c|C)(t|T)(i|I)(o|O)(n|N)(a|A)(r|R)(y|Y)|(e|E)(x|X)(t|T)(e|E)(n|N)(d|D)(s|S)|(V|v)(e|E)(c|C)(t|T)(o|O)(r|R)2|(F|f)(i|I)(l|L)(e|E)|(r|R)(e|E)(t|T)(u|U)(r|R)(n|N)|(n|N)(e|E)(w|W)|(A|a)(A|a)(B|b)(B|b)|(T|t)(i|I)(m|M)(e|E)(S|s)(p|P)(a|A)(n|N)|(R|r)(e|E)(s|S)(o|O)(u|U)(r|R)(c|C)(e|E)|(O|o)(b|B)(j|J)(e|E)(c|C)(t|T)|(S|s)(t|T)(a|A)(r|R)(t|T)|(P|p)(h|H)(y|Y)(s|S)(i|I)(c|C)(s|S)(S|s)(h|H)(a|A)(p|P)(e|E)|(c|C)(l|L)(a|A)(s|S)(s|S)|(v|V)(o|O)(i|I)(d|D)|(p|P)(r|R)(i|I)(n|N)(t|T)|(P|p)(h|H)(y|Y)(s|S)(i|I)(c|C)(s|S)(B|b)(o|O)(d|D)(y|Y)|(r|R)(a|A)(n|N)(g|G)(e|E)|(f|F)(u|U)(n|N)(c|C)|(E|e)(r|R)(r|R)(o|O)(r|R)|(f|F)(o|O)(r|R)|(w|W)(h|H)(i|I)(l|L)(e|E)|(i|I)(f|F)|(e|E)(l|L)(i|I)(f|F)|(e|E)(l|L)(s|S)(e|E)|(t|T)(r|R)(u|U)(e|E)|(f|F)(a|A)(l|L)(s|S)(e|E)|(b|B)(r|R)(e|E)(a|A)(k|K)|(i|I)(m|M)(p|P)(o|O)(r|R)(t|T)(A|a)(l|L)(l|L)|(i|I)(n|N)|(i|I)(n|N)(p|P)(u|U)(t|T)(e|E)(v|V)(e|E)(n|N)(t|T)(k|K)(e|E)(y|Y)|(i|I)(n|N)(p|P)(u|U)(t|T)(e|E)(v|V)(e|E)(n|N)(t|T)(m|M)(o|O)(u|U)(s|S)(e|E)(b|B)(u|U)(t|T)(t|T)(o|O)(n|N) 

ReservadaError = ({NumFloat}|{ErrorNumFloatFormat})*{NoSeparador}+{Reservada} | {Reservada}{NoSeparador}+({NumFloat}|{ErrorNumFloatFormat})* | {Reservada}{IdentificadorError} | {IdentificadorError}{Reservada} | {Reservada}({NumFloat}|{ErrorNumFloatFormat})+

%state MLC   
   
%%   
   
<YYINITIAL> {   
   
   /* Keywords */ 
    
    (e|E)(x|X)(t|T)(e|E)(n|N)(d|D)(s|S) |
    (f|F)(i|I)(l|L)(e|E) |
    (r|R)(e|E)(t|T)(u|U)(r|R)(n|N) |
    (n|N)(e|E)(w|W) |
    (a|A)(a|A)(b|B)(b|B) |
    (o|O)(b|B)(j|J)(e|E)(c|C)(t|T) |
    (p|P)(h|H)(y|Y)(s|S)(i|I)(c|C)(s|S)(s|S)(h|H)(a|A)(p|P)(e|E) |
    (c|C)(l|L)(a|A)(s|S)(s|S) |
    (v|V)(o|O)(i|I)(d|D) |
    (i|I)(n|N) |
    (i|I)(n|N)(p|P)(u|U)(t|T)(e|E)(v|V)(e|E)(n|N)(t|T)(k|K)(e|E)(y|Y) |
    (i|I)(n|N)(p|P)(u|U)(t|T)(e|E)(v|V)(e|E)(n|N)(t|T)(m|M)(o|O)(u|U)(s|S)(e|E)(b|B)(u|U)(t|T)(t|T)(o|O)(n|N) |
    (p|P)(h|H)(y|Y)(s|S)(i|I)(c|C)(s|S)(b|B)(o|O)(d|D)(y|Y) |
    (f|F)(u|U)(n|N)(c|C)    { addToken(Token.RESERVED_WORD); } 

    
    (f|F)(o|O)(r|R) |
    (w|W)(h|H)(i|I)(l|L)(e|E) |
    (i|I)(f|F) |
    (e|E)(l|L)(i|I)(f|F) |
    (e|E)(l|L)(s|S)(e|E) |
    (b|B)(r|R)(e|E)(a|A)(k|K)       { addToken(Token.RESERVED_WORD_2); }   
   
   /* Data types */   
    (i|I)(n|N)(t|T)|
    (f|F)(l|L)(o|O)(a|A)(t|T)|
    (c|C)(o|O)(n|N)(s|S)(t|T)|
    (c|C)(h|H)(a|A)(r|R)|
    (s|S)(t|T)(r|R)(i|I)(n|N)(g|G)|
    (b|B)(o|O)(o|O)(l|L)|
    (a|A)(r|R)(r|R)(a|A)(y|Y)|
    (c|C)(o|O)(l|L)(o|O)(r|R)|
    (r|R)(e|E)(c|C)(t|T)2|
    (e|E)(r|R)(r|R)(o|O)(r|R)|
    (v|V)(e|E)(c|C)(t|T)(o|O)(r|R)2     { addToken(Token.DATA_TYPE); }   
    
    /* bool */
    (t|T)(r|R)(u|U)(e|E)|
    (f|F)(a|A)(l|L)(s|S)(e|E)                               { addToken(Token.LITERAL_BOOLEAN); }


   /* Functions */   
    (t|T)(i|I)(m|M)(e|E)(s|S)(p|P)(a|A)(n|N)|
    (r|R)(e|E)(s|S)(o|O)(u|U)(r|R)(c|C)(e|E)|
    (s|S)(t|T)(a|A)(r|R)(t|T)|   
    (p|P)(r|R)(i|I)(n|N)(t|T)|   
    (r|R)(a|A)(n|N)(g|G)(e|E)|
    (i|I)(m|M)(p|P)(o|O)(r|R)(t|T)(a|A)(l|L)(l|L)      { addToken(Token.FUNCTION); }   
    
    {ReservadaError}           { addToken(Token.ERROR_IDENTIFIER); }

   {Identificador}            { addToken(Token.IDENTIFIER); }   
   {IdentificadorError}       { addToken(Token.ERROR_IDENTIFIER); } 

   {WhiteSpace}            { addToken(Token.WHITESPACE); }   
   
   /* String/Character literals. */   
   {CadChar}            { addToken(Token.LITERAL_CHAR); }   
   {CadCharNoCerrada}      { addToken(Token.ERROR_CHAR); addNullToken(); return firstToken; }   
   {ErrorCharLiteral}         { addToken(Token.ERROR_CHAR); }   
   {CadenaCaracteres}            { addToken(Token.LITERAL_STRING_DOUBLE_QUOTE); }   
   {CadenaCaracteresNoCerrada}      { addToken(Token.ERROR_STRING_DOUBLE); addNullToken(); return firstToken; }   
   {ErrorStringLiteral}      { addToken(Token.ERROR_STRING_DOUBLE); }   
   
   /* Comment literals. */   
   {MLCBegin}               { start = zzMarkedPos-2; yybegin(MLC); }   
   {LineCommentBegin}.*      { addToken(Token.COMMENT_EOL); addNullToken(); return firstToken; }   
   
   /* Separators. */   
   {Separator}               { addToken(Token.SEPARATOR); }   
   {Separator2}            { addToken(Token.IDENTIFIER); }   
   
   /* Operators. */   
   "!" | "%" | "%=" | "&&" | "*" | "*=" | "+" | "+=" | "-" | "-=" |   
   "/" | "/=" | "<"  | "<=" | "=" | "==" | ">" | "||"| ">="       { addToken(Token.OPERATOR); }   
   
   /* Numbers */   
   {NumEntero}              { addToken(Token.LITERAL_NUMBER_DECIMAL_INT); }   
   {ErrorNumFormat}         { addToken(Token.ERROR_NUMBER_FORMAT); }   
   {NumFloat}               { addToken(Token.LITERAL_NUMBER_FLOAT); }
   {ErrorNumFloatFormat}    { addToken(Token.ERROR_NUMBER_FORMAT); }

   /* Ended with a line not in a string or comment. */   
   \n |   
   <<EOF>>                  { addNullToken(); return firstToken; }   
   
   /* Catch any other (unhandled) characters. */   
   .                     { addToken(Token.ERROR_CHAR); }   
   
}   
   
<MLC> {   
   [^\n-]+            {}   
   {MLCEnd}         { yybegin(YYINITIAL); addToken(start,zzStartRead+2-1, Token.COMMENT_MULTILINE); }   
   "-"               {}   
   \n |  
   <<EOF>>            { addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); return firstToken; }   
}   
