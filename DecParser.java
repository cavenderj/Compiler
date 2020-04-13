import java.io.*;
import java.util.*;

class DecParser
{
  Scanner infile;
  final int INT = 103;
  final int CHAR = 109;
  final int CC = 108;
  final int CI = 102;
  SymbolTable sym;
  int type, value, intype, invalue, vartype, varvalue;
  //FileInputStream infile = new FileInputStream ("Output1.dat");
  boolean flag = true, flag2;
  public DecParser()
  {
  }
  void parse(SymbolTable symTable) throws IOException
  {
    Scanner kb = new Scanner(System.in);
    int useless;
    int i;
    sym = symTable;
    infile  = new Scanner(new FileInputStream ("Output1.dat"));
    intype = infile.nextInt();
    while(intype != 59)
    {
      intype = infile.nextInt();
      //System.out.println(intype);
    }
      while(flag == true)
      {
        intype = infile.nextInt();
        System.out.println(intype);
        //useless = kb.nextInt();
        //System.out.println("in first while");
        if (intype == 111)
        {
          invalue = infile.nextInt();
          if (invalue == 3)
          {
            //System.out.println("Const");
            intype = infile.nextInt();
            invalue = infile.nextInt();
            //System.out.println(intype + " " + invalue);
            //useless = kb.nextInt();
            constCheck();
            intype = infile.nextInt();
            invalue = infile.nextInt();
            vartype = intype;
            varvalue = invalue;
           // System.out.println(intype + " " + invalue);
            //useless = kb.nextInt();
            changeType();
            intype = infile.nextInt();
            invalue = infile.nextInt();
            if(checkAssign() == true)
            {
              intype = infile.nextInt();
              invalue = infile.nextInt();
            }
            else
            {
              System.out.println("Error: const not intialized");
            }
            flag2 = checkCon();
            while(flag2 == true)
            {
              intype = infile.nextInt();
              invalue = infile.nextInt();
              vartype = intype;
              varvalue = invalue;
              //System.out.println(intype + " " + invalue);
            //  useless = kb.nextInt();
              changeType();
              intype = infile.nextInt();
              invalue = infile.nextInt();
              //System.out.println(intype + " " + invalue);
              //useless = kb.nextInt();
              if(checkAssign() == true)
              {
                intype = infile.nextInt();
                invalue = infile.nextInt();
              }
              else
              {
                System.out.println("Error: const not intialized");
              }
              flag2 = checkCon();
            }
          }
          else if(invalue == 1)
          {
            //System.out.println("Int");
            intype = infile.nextInt();
            if(intype == 111)
            {
              invalue = infile.nextInt();
              if (invalue == 9)
                flag = false;
              else 
                ;//error
            }
            else
            {
              type = INT;
              invalue = infile.nextInt();
              vartype = intype;
              varvalue = invalue;
              //System.out.println(intype + " " + invalue);
              //useless = kb.nextInt();
              changeType();
              intype = infile.nextInt();
              invalue = infile.nextInt();
              //System.out.println(intype + " " + invalue);
              //useless = kb.nextInt();
              if(checkAssign() == true)
              {
                intype = infile.nextInt();
                invalue = infile.nextInt();
              }
              flag2 = checkCon();
              while(flag2 == true)
              {
                intype = infile.nextInt();
                invalue = infile.nextInt();
                vartype = intype;
                varvalue = invalue;
                //System.out.println(intype + " " + invalue);
                //useless = kb.nextInt();
                changeType();
                intype = infile.nextInt();
                invalue = infile.nextInt();
                //System.out.println(intype + " " + invalue);
                //useless = kb.nextInt();
                if(checkAssign() == true)
                {
                  intype = infile.nextInt();
                  invalue = infile.nextInt();
                }
                flag2 = checkCon();
              }
            }
          }
          else if(invalue == 2)
          {
            //System.out.println("Char");
            type = CHAR;
            intype = infile.nextInt();
            invalue = infile.nextInt();
            vartype = intype;
            varvalue = invalue;
           // System.out.println(intype + " " + invalue);
            //useless = kb.nextInt();
            changeType();
            intype = infile.nextInt();
            invalue = infile.nextInt();
            //System.out.println(intype + " " + invalue);
            //useless = kb.nextInt();
            if(checkAssign() == true)
            {
              intype = infile.nextInt();
              invalue = infile.nextInt();
            }
            //System.out.println(intype + " " + invalue);
            //useless = kb.nextInt();
            flag2 = checkCon();
            while(flag2 == true)
            {
              intype = infile.nextInt();
              invalue = infile.nextInt();
              vartype = intype;
              varvalue = invalue;
              //System.out.println(intype + " " + invalue);
              //useless = kb.nextInt();
              changeType();
              intype = infile.nextInt();
              invalue = infile.nextInt();
              //System.out.println(intype + " " + invalue);
              //useless = kb.nextInt();
              if(checkAssign() == true)
              {
                intype = infile.nextInt();
                invalue = infile.nextInt();
              }
              flag2 = checkCon();
            }
          }
        }
        else
          System.out.println("Error: invalid token, missing declaration type");//error
            
      }
      intype = infile.nextInt();
      invalue = infile.nextInt();
      if (invalue == 40)
      {
        intype = infile.nextInt();
        invalue = infile.nextInt();
        if(invalue == 41)
        {
          intype = infile.nextInt();
          invalue = infile.nextInt();
          if(invalue == 123)
          {
            intype = infile.nextInt();
            invalue = infile.nextInt();
            System.out.println("before parse");
            while(!(intype == 111 && invalue == 16))
            {
              intype = infile.nextInt();
              invalue = infile.nextInt();
            }
            intype = infile.nextInt();
            invalue = infile.nextInt();
            if(intype == 105 && invalue == 0)
            {
              intype = infile.nextInt();
              invalue = infile.nextInt();
              if(invalue == 59)
              {
                intype = infile.nextInt();
                invalue = infile.nextInt();
                if(invalue == 125)
                  ;
                else
                  System.out.println("Error: missing '}' at end of class body");//error
              }
              else
                System.out.println("Error: missing ';' after return statement");//error
            }
            else
              System.out.println("Error: missing '0'");//error
          }
          else
            System.out.println("Error: missing '{'");//error
        }
        else
          System.out.println("Error: missing ')'");//error
      }
      else
        System.out.println("Error: missing '('");//error
    infile.close();
    symTable.print();
  }
  boolean checkAssign() //throws IOException
  {
    if(invalue == 61)
    {
      //System.out.println("value assigned");
      intype = infile.nextInt();
      invalue = infile.nextInt();
      if(intype < 100)
      {
        //System.out.println(sym.index[intype].get(invalue).name + " was pulled from the symbol table");
        if(sym.index[intype].get(invalue).type < 20)
        {
          System.out.println("Error: undeclared variable used in initialization");
        }
        else
          invalue = sym.index[intype].get(invalue).value;
      }
      changeValue();
      return true;
    }
    else if(invalue == 91)
    {
      //System.out.println("Array type");
      intype = infile.nextInt();
      invalue = infile.nextInt();
      if(intype == 107 && invalue == 93)
      {
        System.out.println("Error: initialize array size");
      }
      sym.index[vartype].get(varvalue).size = invalue;
      sym.index[vartype].get(varvalue).eltype = type;
      sym.index[vartype].get(varvalue).type = 110;
      sym.index[vartype].get(varvalue).value = 0;
      intype = infile.nextInt();
      invalue = infile.nextInt();
      //System.out.println(intype + " " + invalue);
      if(intype == 107 && invalue == 93)
      {
        return true;
      }
      else
      {
        System.out.println("Error: no closing bracket");
        return true;
      }
    }
    else 
    {
      value = 0;
      sym.index[vartype].get(varvalue).value = 0;
      return false;
    }
  }
  void constCheck()
  {
    if(intype == 111)
    {
      if(invalue == 1)
      {
        //System.out.println("const int");
        type = CI;
      }
      else if(invalue == 2)
      {
        type = CC;
        //System.out.println("const char");
      }
      else
        System.out.println("Error: missing 'int' or 'char'");
    }
    else
      System.out.println("Error: missing 'int' or 'char'");
  }
  boolean checkCon()
  {
    if(invalue == 44)
    {
      //System.out.println("continue");
      return true;
    }
    else if (invalue == 59)
    {
      //System.out.println("semicolon");
      return false;
    }
    else
      //throw error
      System.out.println("Error: invalid token, missing ',' or ';'");
    return false;
  }
  void changeValue()
  {
    //System.out.println(sym.index[vartype].get(varvalue).name + " value = " + invalue);
    sym.index[vartype].get(varvalue).value = invalue;
  }
  void changeType()
  {
    if(sym.index[vartype].get(varvalue).type < 20)
    {
      //System.out.println(sym.index[vartype].get(varvalue).name + " type = " + type);
      sym.index[vartype].get(varvalue).type = type;
    }
    else
    {
      if(sym.index[vartype].get(varvalue).type == 111)
        System.out.println("Error: reserved words cannot be used as identifiers");
      else
        System.out.println("Error: that variable has already been declared");//throw error, already declared
    }
  }
  
}
  
  