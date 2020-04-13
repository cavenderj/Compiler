import java.util.ArrayList;

class SymHash
{
  public ArrayList<Symbol> sym = new ArrayList<Symbol>();
  public SymHash()
  {
  }
  public Symbol get(int index)
  {
    return sym.get(index);
  }
  public void add(String name, int type, int value, int size, int eltype)
  {
    sym.add(new Symbol(name, type, value, size, eltype));
  }
  public int size()
  {
    return sym.size();
  }
}