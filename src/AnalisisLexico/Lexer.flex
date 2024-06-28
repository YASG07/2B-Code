package AnalisisLexico;

%%

%class Lexer
%public
%type Lexer
%line
%column
//%type Tokens
%{
    //Codigo de usuario
    public TablaErrores tablaError = new TablaErrores();
    public TablaToken tablaToken = new TablaToken();
%}
//Brian
D = [0-9]+
LetraIni = [a-zA-ZñÑáéíóúüÁÉÍÓÚÜ]
Letra = [a-zA-ZñÑ_áéíóúüÁÉÍÓÚÜ]
FinalDeLinea = \r | \n |\r\n 
CualquierCaracterExceptoComillasDoblesOBarraInvertida   = ([^\\\"\n])   
CualquierCaracterExceptoApostrofeOBarrainvertida = ([^\\'])
NoSeparador = ([^\t\f\r\n\ \(\)\{\}\[\]\,\.\=\>\<\!\:\+\-\*\/\&\|\^\#\%\"\'a-zA-ZñÑ_áéíóúüÁÉÍÓÚÜ0-9] |"\\")
EntradaCaracter = [^\r\n]
EntradaCaracterMultln = [^"-/"]+
espacio={FinalDeLinea} | [ \t\f]
ComentarioSimple = ("//"{EntradaCaracter}*{FinalDeLinea}*) | ("#"{EntradaCaracter}*{FinalDeLinea}*)
ComentarioMultilinea = "/-"{EntradaCaracterMultln}*"-/"

CadenaCaracteres = ([\"]({CualquierCaracterExceptoComillasDoblesOBarraInvertida})*[\"])
CadenaCaracteresNoCerrada = ([\"]([\\].|[^\\\"])*[^\"]?)
CadChar                 = ([\']({CualquierCaracterExceptoApostrofeOBarrainvertida}?)[\'])
CadCharNoCerrada         = ([\'][^\'\n]*[^\']?) 

Identificador = {LetraIni}({Letra}|{D})*
NumFloat = [-+]?{D}(\.{D})
NumEntero = [-+]?{D}+
NumEnteroPos=[+]?{D}+

//Rodolfo
//Patrones para los errores
IdentificadorError = ({D}{Identificador} | {NoSeparador}+{Identificador}{NoSeparador}* | {NoSeparador}*{Identificador}{NoSeparador}+ | {Identificador}{NoSeparador}+{Identificador})+

CadCharError = ({CadCharNoCerrada})
CadCharError2 = ([\']({CualquierCaracterExceptoApostrofeOBarrainvertida}{CualquierCaracterExceptoApostrofeOBarrainvertida}+)[\'])

CadCaracteresError = ({CadenaCaracteresNoCerrada})

NumFloatError = ([-+]*{D}[:jletter:]*(\.)+ | [-+]*(\.)+{D}[:jletter:]* | [-+]*(\.)+{D}[:jletter:]*(\.)+{D}[:jletter:]* | 
                [-+]*{D}[:jletter:]*(\.)+{D}[:jletter:]*(\.)+{D}[:jletter:]* | [+-][-+]+{D}[:jletter:]*(\.{D}[:jletter:]*) | 
                [-+]?{D}(\.{D}[:jletter:]+) | [-+]?{D}(\.({Identificador}|{IdentificadorError})+))+

NumEntError = [+-][+-]+{D} | [+-][+-]+{Identificador}

OpLogicoFaltanteOR = \|
OpLogicoFaltanteAND = \&

%%
<YYINITIAL>{
   (i|I)(n|N)(t|T) |
   (f|F)(l|L)(o|O)(a|A)(t|T) |
   (c|C)(h|H)(a|A)(r|R) |
   (S|s)(t|T)(r|R)(i|I)(n|N)(g|G) |
   (b|B)(o|O)(o|O)(l|L) |
   (A|a)(r|R)(r|R)(a|A)(y|Y) |
   (O|o)(b|B)(j|J)(e|E)(c|C)(t|T) | 
   (R|r)(e|E)(c|C)(t|T)2 |
   (V|v)(e|E)(c|C)(t|T)(o|O)(r|R)2 |
   (T|t)(i|I)(m|M)(e|E)(S|s)(p|P)(a|A)(n|N) | 
   (R|r)(e|E)(s|S)(o|O)(u|U)(r|R)(c|C)(e|E) |
   (A|a)(A|a)(B|b)(B|b) |
   (F|f)(i|I)(l|L)(e|E) |
   (E|e)(r|R)(r|R)(o|O)(r|R)                          {tablaToken.insertar(new Token(yytext(),"TIPO_DATO",yyline+1,yycolumn+1));}
   

   (i|I)(n|N)(p|P)(u|U)(t|T)(e|E)(v|V)(e|E)(n|N)(t|T)(k|K)(e|E)(y|Y) |
   (i|I)(n|N)(p|P)(u|U)(t|T)(e|E)(v|V)(e|E)(n|N)(t|T)(m|M)(o|O)(u|U)(s|S)(e|E)(b|B)(u|U)(t|T)(t|T)(o|O)(n|N) |
   (P|p)(h|H)(y|Y)(s|S)(i|I)(c|C)(s|S)(S|s)(h|H)(a|A)(p|P)(e|E) |
   (P|p)(h|H)(y|Y)(s|S)(i|I)(c|C)(s|S)(B|b)(o|O)(d|D)(y|Y) {tablaToken.insertar(new Token(yytext(),"FUNCION_INTERNA",yyline+1,yycolumn+1));}

   (c|C)(o|O)(n|N)(s|S)(t|T)                          {tablaToken.insertar(new Token(yytext(),"CONSTANTE",yyline+1,yycolumn+1));}  
   
   (i|I)(m|M)(p|P)(o|O)(r|R)(t|T)(A|a)(l|L)(l|L)      {tablaToken.insertar(new Token(yytext(),"IMPORT",yyline+1,yycolumn+1));}  
   
   
   (C|c)(o|O)(l|L)(o|O)(r|R)                          {tablaToken.insertar(new Token(yytext(),"COLOR",yyline+1,yycolumn+1));}
   (e|E)(x|X)(t|T)(e|E)(n|N)(d|D)(s|S)                {tablaToken.insertar(new Token(yytext(),"EXTENDS",yyline+1,yycolumn+1));}
   (n|N)(e|E)(w|W)                                    {tablaToken.insertar(new Token(yytext(),"NEW",yyline+1,yycolumn+1));}
   (r|R)(e|E)(t|T)(u|U)(r|R)(n|N)                     {tablaToken.insertar(new Token(yytext(),"RETURN",yyline+1,yycolumn+1));}
   (S|s)(t|T)(a|A)(r|R)(t|T)                          {tablaToken.insertar(new Token(yytext(),"START",yyline+1,yycolumn+1));}
   (c|C)(l|L)(a|A)(s|S)(s|S)                          {tablaToken.insertar(new Token(yytext(),"CLASS",yyline+1,yycolumn+1));}
   (v|V)(o|O)(i|I)(d|D)                               {tablaToken.insertar(new Token(yytext(),"VOID",yyline+1,yycolumn+1));}
   (p|P)(r|R)(i|I)(n|N)(t|T)                          {tablaToken.insertar(new Token(yytext(),"PRINT",yyline+1,yycolumn+1));}
   (f|F)(u|U)(n|N)(c|C)                               {tablaToken.insertar(new Token(yytext(),"FUNC",yyline+1,yycolumn+1));}
   (f|F)(o|O)(r|R)                                    {tablaToken.insertar(new Token(yytext(),"FOR",yyline+1,yycolumn+1));}
   (w|W)(h|H)(i|I)(l|L)(e|E)                          {tablaToken.insertar(new Token(yytext(),"WHILE",yyline+1,yycolumn+1));}
   (i|I)(f|F)                                         {tablaToken.insertar(new Token(yytext(),"IF",yyline+1,yycolumn+1));}
   (e|E)(l|L)(i|I)(f|F)                               {tablaToken.insertar(new Token(yytext(),"ELIF",yyline+1,yycolumn+1));}
   (e|E)(l|L)(s|S)(e|E)                               {tablaToken.insertar(new Token(yytext(),"ELSE",yyline+1,yycolumn+1));}
   (t|T)(r|R)(u|U)(e|E)                               {tablaToken.insertar(new Token(yytext(),"TRUE",yyline+1,yycolumn+1));}
   (f|F)(a|A)(l|L)(s|S)(e|E)                          {tablaToken.insertar(new Token(yytext(),"FALSE",yyline+1,yycolumn+1));}
   (b|B)(r|R)(e|E)(a|A)(k|K)                          {tablaToken.insertar(new Token(yytext(),"BREAK",yyline+1,yycolumn+1));}
   (i|I)(n|N)                                         {tablaToken.insertar(new Token(yytext(),"IN",yyline+1,yycolumn+1));}
   (r|R)(a|A)(n|N)(g|G)(e|E)                          {tablaToken.insertar(new Token(yytext(),"RANGE",yyline+1,yycolumn+1));}
   
   "="                         {tablaToken.insertar(new Token(yytext(),"OpAsignacion",yyline+1,yycolumn+1));}
   "+="                        {tablaToken.insertar(new Token(yytext(),"OpAsignacion",yyline+1,yycolumn+1));}
   "-="                        {tablaToken.insertar(new Token(yytext(),"OpAsignacion",yyline+1,yycolumn+1));}
   "%="                        {tablaToken.insertar(new Token(yytext(),"OpAsignacion",yyline+1,yycolumn+1));}
   "*="                        {tablaToken.insertar(new Token(yytext(),"OpAsignacion",yyline+1,yycolumn+1));}
   "/="                        {tablaToken.insertar(new Token(yytext(),"OpAsignacion",yyline+1,yycolumn+1));}
   "!="                        {tablaToken.insertar(new Token(yytext(),"OpComparacion",yyline+1,yycolumn+1));}
   "=="                        {tablaToken.insertar(new Token(yytext(),"OpComparacion",yyline+1,yycolumn+1));}
   ">="                        {tablaToken.insertar(new Token(yytext(),"OpComparacion",yyline+1,yycolumn+1));}
   "<="                        {tablaToken.insertar(new Token(yytext(),"OpComparacion",yyline+1,yycolumn+1));}
   "<"                         {tablaToken.insertar(new Token(yytext(),"OpComparacion",yyline+1,yycolumn+1));}
   ">"                         {tablaToken.insertar(new Token(yytext(),"OpComparacion",yyline+1,yycolumn+1));}
   "+"                         {tablaToken.insertar(new Token(yytext(),"OpAritmetico",yyline+1,yycolumn+1));}
   "*"                         {tablaToken.insertar(new Token(yytext(),"OpAritmetico",yyline+1,yycolumn+1));}
   "/"                         {tablaToken.insertar(new Token(yytext(),"OpAritmetico",yyline+1,yycolumn+1));}
   "-"                         {tablaToken.insertar(new Token(yytext(),"OpAritmetico",yyline+1,yycolumn+1));}
   "%"                         {tablaToken.insertar(new Token(yytext(),"OpAritmetico",yyline+1,yycolumn+1));}
   "{"                         {tablaToken.insertar(new Token(yytext(),"LLAVE_A",yyline+1,yycolumn+1));}
   "}"                         {tablaToken.insertar(new Token(yytext(),"LLAVE_C",yyline+1,yycolumn+1));}

   "["                         {tablaToken.insertar(new Token(yytext(),"CORCH_A",yyline+1,yycolumn+1));}
   "]"                         {tablaToken.insertar(new Token(yytext(),"CORCH_C",yyline+1,yycolumn+1));}
   "("                         {tablaToken.insertar(new Token(yytext(),"PAREN_A",yyline+1,yycolumn+1));}
   ")"                         {tablaToken.insertar(new Token(yytext(),"PAREN_C",yyline+1,yycolumn+1));}
   ":"                         {tablaToken.insertar(new Token(yytext(),"DOS_PUNTOS",yyline+1,yycolumn+1));}
   ","                         {tablaToken.insertar(new Token(yytext(),"COMA",yyline+1,yycolumn+1));}
   "&&"                        {tablaToken.insertar(new Token(yytext(),"OpLogico",yyline+1,yycolumn+1));}
   "||"                        {tablaToken.insertar(new Token(yytext(),"OpLogico",yyline+1,yycolumn+1));}
   "!"                         {tablaToken.insertar(new Token(yytext(),"OpLogico",yyline+1,yycolumn+1));}
   "\."                        {tablaToken.insertar(new Token(yytext(),"OpAccesoMiembros",yyline+1,yycolumn+1));}
   

   {CadChar}                   {tablaToken.insertar(new Token(yytext(),"CadenaCaracter",yyline+1,yycolumn+1));}
   {CadenaCaracteres}          {tablaToken.insertar(new Token(yytext(),"CadenaCaracteres",yyline+1,yycolumn+1));} 


   {CadCaracteresError}        {tablaError.insertar(new ErrorToken(1,"Léxico","Se esperaba otra \". Verifica que la cadena de caracteres sea válida",yytext(),yyline+1,yycolumn+1));}
   {CadCharError}              {tablaError.insertar(new ErrorToken(2,"Léxico","Se esperaba otra \'. Verifica que la cadena de caracter sea válida",yytext(),yyline+1,yycolumn+1));}
   {CadCharError2}             {tablaError.insertar(new ErrorToken(3,"Léxico","Longitud excedida. Se esperaba un caracter",yytext(),yyline+1,yycolumn+1));}
   {OpLogicoFaltanteOR}        {tablaError.insertar(new ErrorToken(4,"Léxico","Se esperaba otro caracter |",yytext(),yyline+1,yycolumn+1));}
   {OpLogicoFaltanteAND}       {tablaError.insertar(new ErrorToken(5,"Léxico","Se esperaba otro caracter &",yytext(),yyline+1,yycolumn+1));}
   {NumFloatError}             {tablaError.insertar(new ErrorToken(6,"Léxico","Número decimal inválido. Verifica que coincida con el formato #.#, donde # representa dígitos",yytext(),yyline+1,yycolumn+1));}
   {NumEntError}               {tablaError.insertar(new ErrorToken(7,"Léxico","Número inválido. Verifica que coincida con el formato +# ó -#, donde # representa dígitos",yytext(),yyline+1,yycolumn+1));}
   {ReservadaError}            {tablaError.insertar(new ErrorToken(8,"Léxico","La cadena escrita no se reconoce como palabra reservada ó identificador. Verifica que contenga símbolos válidos e inténtalo de nuevo",yytext(),yyline+1,yycolumn+1));}
   {IdentificadorError}        {tablaError.insertar(new ErrorToken(9,"Léxico","Identificador inválido. Verifica que inicie con una letra y contenga símbolos válidos e inténtalo de nuevo",yytext(),yyline+1,yycolumn+1));}
    
   {Identificador}             {tablaToken.insertar(new Token(yytext(),"Identificador",yyline+1,yycolumn+1));}       
   {NumEntero}                 {tablaToken.insertar(new Token(yytext(),"NumEntero",yyline+1,yycolumn+1));} 
   {NumFloat}                  {tablaToken.insertar(new Token(yytext(),"NumFlotante",yyline+1,yycolumn+1));} 
      
   {ComentarioSimple}          {/**/}
   {ComentarioMultilinea}      {/**/}  
   {espacio}                   {/**/}
}

.                               {tablaError.insertar(new ErrorToken(9,"Léxico","Simbolo desconocido; revise que el simbolo coincida con el alfabeto",yytext(),yyline+1,yycolumn+1));}
