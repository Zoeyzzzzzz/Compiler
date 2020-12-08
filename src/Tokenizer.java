import java.sql.SQLOutput;

public class Tokenizer {

    private StringIter it;

    public Tokenizer(StringIter it) {
        this.it = it;
    }

    // 这里本来是想实现 Iterator<Token> 的，但是 Iterator 不允许抛异常，于是就这样了
    /**
     * 获取下一个 Token
     *
     * @return
     * @throws TokenizeError 如果解析有异常则抛出
     */
    public Token nextToken() throws TokenizeError {
        it.readAll();

        // 跳过之前的所有空白字符
        skipSpaceCharacters();

        if (it.isEOF()) {
            return new Token(TokenType.EOF, "", it.currentPos(), it.currentPos());
        }

        char peek = it.peekChar();
        //关键字或标识符
        if (Character.isAlphabetic(peek)) {
            return lexIdentOrKeyword();
        }
        //无符号整数或浮点数常量
        else if (Character.isDigit(peek)) {
            return lexUIntOrDouble();
        }
        //字符串常量
        else if (peek=='"') {
            return lexString();
        }
        //字符常量
        else if (peek=='\'') {
            return lexString();
        }
        //标识符
        else if (peek=='_') {
            return lexIdent();
        }
        //运算符或注释
        else {
            return lexOperatorOrAnnotation();
        }
    }

    //无符号整数或者浮点数
    private Token lexUIntOrDouble() throws TokenizeError {
        // 请填空：
        // 直到查看下一个字符不是数字为止:
        // -- 前进一个字符，并存储这个字符
        String num = "";
        while(Character.isDigit(it.peekChar())){
            num += it.nextChar();
        }
        return new Token(TokenType.UINT_LITERAL, Integer.parseInt(num), it.previousPos(), it.currentPos());
        // 解析存储的字符串为无符号整数
        // 解析成功则返回无符号整数类型的token，否则返回编译错误
        //
        // Token 的 Value 应填写数字的值
    }

    private Token lexIdentOrKeyword() throws TokenizeError {
        // 请填空：
        // 直到查看下一个字符不是数字或字母为止:
        // -- 前进一个字符，并存储这个字符
        //
        // 尝试将存储的字符串解释为关键字
        // -- 如果是关键字，则返回关键字类型的 token
        // -- 否则，返回标识符
        //
        // Token 的 Value 应填写标识符或关键字的字符串
        String token="";
        while(Character.isLetterOrDigit(it.peekChar()) || it.peekChar()=='_'){
            token += it.nextChar();
        }

        //test  后面多加了个或
        if(token.equals("fn"))
            return new Token(TokenType.FN_KW, token, it.previousPos(), it.currentPos());

        else if(token.equals("let"))
            return new Token(TokenType.LET_KW, token, it.previousPos(), it.currentPos());

        else if(token.equals("const"))
            return new Token(TokenType.CONST_KW, token, it.previousPos(), it.currentPos());

        else if(token.equals("as"))
            return new Token(TokenType.AS_KW, token, it.previousPos(), it.currentPos());

        else if(token.equals("while"))
            return new Token(TokenType.WHILE_KW, token, it.previousPos(), it.currentPos());

        else if(token.equals("if"))
            return new Token(TokenType.IF_KW, token, it.previousPos(), it.currentPos());

        else if(token.equals("else"))
            return new Token(TokenType.ELSE_KW, token, it.previousPos(), it.currentPos());

        else if(token.equals("return"))
            return new Token(TokenType.RETURN_KW, token, it.previousPos(), it.currentPos());

        else if(token.equals("break"))
            return new Token(TokenType.BREAK_KW, token, it.previousPos(), it.currentPos());

        else if(token.equals("continue"))
            return new Token(TokenType.CONTINUE_KW, token, it.previousPos(), it.currentPos());

        else
            return new Token(TokenType.IDENT, token, it.previousPos(), it.currentPos());
    }

    private Token lexOperatorOrAnnotation() throws TokenizeError {
        switch (it.nextChar()) {
            //+
            case '+':
                return new Token(TokenType.PLUS, '+', it.previousPos(), it.currentPos());

            //-或者->
            case '-':
                if(it.peekChar() == '>'){
                    it.nextChar();
                    return new Token(TokenType.ARROW, "->", it.previousPos(), it.currentPos());
                }
                return new Token(TokenType.MINUS, '-', it.previousPos(), it.currentPos());

            case '*':
                return new Token(TokenType.MUL, '*', it.previousPos(), it.currentPos());

            case '/':
                return new Token(TokenType.DIV, '/', it.previousPos(), it.currentPos());

            //=或者==
            case '=':
                if(it.peekChar() == '='){
                    it.nextChar();
                    return new Token(TokenType.EQ, "==", it.previousPos(), it.currentPos());
                }
                return new Token(TokenType.ASSIGN, '=', it.previousPos(), it.currentPos());

            //!=
            case '!':
                if(it.peekChar() == '='){
                    it.nextChar();
                    return new Token(TokenType.NEQ, "!=", it.previousPos(), it.currentPos());
                }
                throw new TokenizeError(ErrorCode.InvalidInput, it.previousPos());

            //<或者<=
            case '<':
                if(it.peekChar() == '='){
                    it.nextChar();
                    return new Token(TokenType.LE, "<=", it.previousPos(), it.currentPos());
                }
                return new Token(TokenType.LT, '<', it.previousPos(), it.currentPos());

                //>或者>=
            case '>':
                if(it.peekChar()=='='){
                    it.nextChar();
                    return new Token(TokenType.GE, ">=", it.previousPos(), it.currentPos());
                }
                return new Token(TokenType.GT, '>', it.previousPos(), it.currentPos());

            case '(':
                return new Token(TokenType.L_PAREN, '(', it.previousPos(), it.currentPos());

            case ')':
                return new Token(TokenType.R_PAREN, ')', it.previousPos(), it.currentPos());

            case '{':
                return new Token(TokenType.L_BRACE, '{', it.previousPos(), it.currentPos());

            case '}':
                return new Token(TokenType.R_BRACE, '}', it.previousPos(), it.currentPos());

            case ',':
                return new Token(TokenType.COMMA, ',', it.previousPos(), it.currentPos());

            case ':':
                return new Token(TokenType.COLON, ':', it.previousPos(), it.currentPos());

            case ';':
                return new Token(TokenType.SEMICOLON, ';', it.previousPos(), it.currentPos());

            default:
                // 不认识这个输入，摸了
                throw new TokenizeError(ErrorCode.InvalidInput, it.previousPos());
        }
    }

    private void skipSpaceCharacters() {
        while (!it.isEOF() && Character.isWhitespace(it.peekChar())) {
            it.nextChar();
        }
    }

    //标识符
    private Token lexIdent() throws TokenizeError {
        String token="";
        int i = 100;
        while(Character.isLetterOrDigit(it.peekChar()) && i>0){
            token = token + it.nextChar();
            i--;
        }
        return new Token(TokenType.IDENT, token, it.previousPos(), it.currentPos());
    }

//    //标识符
//    private Token lexIdent() throws TokenizeError {
//        String token="";
//        while(Character.isLetterOrDigit(it.peekChar())){
//            token = token + it.nextChar();
//        }
//        return new Token(TokenType.IDENT, token, it.previousPos(), it.currentPos());
//    }

//    //字符串常量
//    private Token lexString() throws TokenizeError {
//        String stringLiteral = "\"";
//        it.nextChar();
//        int i = 65535;
//        while(i>0){
//            if(it.peekChar() != '"') stringLiteral = stringLiteral + it.nextChar();
//            //双引号可能被转义
//            else{
//                if(stringLiteral.charAt(stringLiteral.length()-1) == '\\'){
//                    if(stringLiteral.length() > 1 && stringLiteral.charAt(stringLiteral.length()-2) == '\\'){
//                        stringLiteral += it.nextChar();
//                        break;
//                    }
//                    else stringLiteral += it.nextChar();
//                }
//                else{
//                    stringLiteral += it.nextChar();
//                    break;
//                }
//            }
//            i--;
//        }
//        //不要双引号
//        String without = stringLiteral.substring(1, stringLiteral.length()-1);
//        return new Token(TokenType.STRING_LITERAL, without, it.previousPos(), it.currentPos());
//    }
    //字符串常量
    private Token lexString() throws TokenizeError {
        String stringLiteral = "";
        char pre = it.nextChar();
        int i = 65535;
        while(i>0){
            char now = it.nextChar();
            if(pre == '\\'){
                if(now == 'n') stringLiteral += "\n";
                else if(now == '\\') stringLiteral += "\\";
                else if(now == '"') stringLiteral += '"';
                else stringLiteral += now;
            }
            else if(now == '"' && pre != '\\') break;
            else if(now != '\\') stringLiteral += now;
            pre = now;
            i--;
        }
        return new Token(TokenType.STRING_LITERAL, stringLiteral, it.previousPos(), it.currentPos());
    }
}
