import java.io.*;

class SymbolTable
{
  Integer tSize = 20;
  public SymHash index[] = new SymHash[tSize];
  public SymbolTable()
  {
    for (int i = 0; i <tSize; i++)
    {
      SymHash create = new SymHash();
      index[i] = create;
    }
  }
  public int hash(String name, int type)
  {
    int value;
    int index = 0;
    if(type == 111)
      index = ((name.charAt(0)) % tSize);
    else
      index = type % tSize;
    value = probe(name, index, type, index-1);
    return value;
  }
  public int probe(String token, int index1, int type, int initial)
  {
    int i = 0;
    if (index1 > tSize - 1)
      index1 = 0;
    for(i = 0; i < index[index1].size(); i++)
    {
      if (index[index1].get(i).name.equals(token))
      {
        return i;
      }
    }
    index[index1].add(token, type, i, 0,0);
    return i;
  }
  public void print()
  {
    String output;
    int index1 = 0;
    int j = 0;
    try
    {
      FileWriter writer = new FileWriter("SymbolTableOut.dat");
      writer.write("Symbol Table:\n");
    for (int i = 0; i < 20; i++)
    {
      output = Integer.toString(i);
      output = output + ":";
      writer.write(output);
      for(j = 0; j < index[i].size(); j++)
      {
        if (index[i].get(j).name.length() > 8)
          output = "\t" + index[i].get(j).name + "\t";
        else
          output = "\t" + index[i].get(j).name + "\t" + "\t";
        output = output + index[i].get(j).type + "\t";
        output = output + index[i].get(j).value + "\t";
        output = output + index[i].get(j).size + "\t";
        output = output + index[i].get(j).eltype + "\t";
        writer.write(output);
        writer.write("\n");
      }
      writer.write("\n");
    }
    writer.close();
  }
  catch (Exception e)
  {
    e.printStackTrace();
  }
  }
}