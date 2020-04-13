import java.io.*;
  
class Incoming
{
  public static void main(String[] args) throws IOException
  {
    System.out.println("In the beginning");
    SymbolTable symTable = new SymbolTable();
    LexAnalyizer scan = new LexAnalyizer(symTable);
    scan.reader();
    DecParser dec = new DecParser();
    dec.parse(symTable);
    IWParser iw = new IWParser();
    int max = iw.caller(symTable);
    CodeGen cg = new CodeGen();
    cg.generate(symTable,max);
    
  }
}