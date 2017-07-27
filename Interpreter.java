import java.io.*;
import java.util.Scanner;

class Token
{
  public static String INTEGER = "INTEGER";
  public static String PLUS = "PLUS";
  public static String MINUS = "MINUS";
  public static String MULTIPLY = "MULTIPLY";
  public static String DIVIDE = "DIVIDE";
  public static String EOF = "EOF";

  String value,type;
  public Token(String value,String type){
    this.value = value;
    this.type = type;
  }

  public String getValue(){
    return this.value;
  }
  public String getType(){
    return this.type;
  }
}

class Interpreter{
  String text;
  Token currentToken;
  int pos;

  public Interpreter(String text){
    this.text = text.replaceAll("\\s+","");
    pos = 0;
  }

  public Token getNextToken()throws Exception{

    if(pos>text.length()-1){
      return new Token("",Token.EOF);
    }

    char currentChar = text.charAt(pos);
    String multi = ""+currentChar;
    if(Character.isDigit(currentChar)){
      Token token = new Token(String.valueOf(currentChar),Token.INTEGER);
      pos += 1;
      while (pos < text.length() && Character.isDigit(text.charAt(pos))){
        currentChar = text.charAt(pos);
        multi +=currentChar;
        token = new Token(multi,Token.INTEGER);
        if(pos < (text.length()-1))
          pos += 1;
        else
          break;
      }
      return token;
    }
    else if(currentChar == '+'){
      Token token = new Token(String.valueOf(currentChar),Token.PLUS);
      pos += 1;
      return token;
    }
    else if(currentChar == '-'){
      Token token = new Token(String.valueOf(currentChar),Token.MINUS);
      pos += 1;
      return token;
    }
    else if(currentChar == '*'){
      Token token = new Token(String.valueOf(currentChar),Token.MULTIPLY);
      pos += 1;
      return token;
    }
    else if(currentChar == '/'){
      Token token = new Token(String.valueOf(currentChar),Token.DIVIDE);
      pos += 1;
      return token;
    }
    else{
      error();
      return null;
    }
  }

  public void error()throws Exception{
    throw new Exception();
  }

  public void eat(String tokenType)throws Exception{
    if(currentToken.getType() == tokenType){
      currentToken = getNextToken();
    }
    else{
      error();
    }
  }

  public String term()throws Exception{
    Token token = currentToken;
    eat(Token.INTEGER);
    return token.getValue();
  }

  public int expr()throws Exception{
    currentToken = getNextToken();
    int result = Integer.parseInt(term());

    if(currentToken.getType() == Token.PLUS ||
    currentToken.getType() == Token.MINUS){
    while(currentToken.getType() == Token.PLUS ||
    currentToken.getType() == Token.MINUS){
      if(currentToken.getType() == Token.PLUS){
        eat(Token.PLUS);
        result = result + Integer.parseInt(term());
      }
      if(currentToken.getType() == Token.MINUS){
        eat(Token.MINUS);
        result = result - Integer.parseInt(term());
      }
    }
  }

  if(currentToken.getType() == Token.MULTIPLY ||
  currentToken.getType() == Token.DIVIDE){
  while(currentToken.getType() == Token.MULTIPLY ||
  currentToken.getType() == Token.DIVIDE){
    if(currentToken.getType() == Token.MULTIPLY){
      eat(Token.MULTIPLY);
      result = result * Integer.parseInt(term());
    }
    if(currentToken.getType() == Token.DIVIDE){
      eat(Token.DIVIDE);
      result = result / Integer.parseInt(term());
    }
  }
}
    return result;
  }

  public static void main(String [] args) throws Exception
  {
    while(true){
    Scanner reader = new Scanner(System.in);
    System.out.println("Enter an arithmetic expression: ");
    String exp = reader.nextLine();
    Interpreter interpreter = new Interpreter(exp);
    int res = interpreter.expr();
    System.out.println(res);
  }
  }
}
