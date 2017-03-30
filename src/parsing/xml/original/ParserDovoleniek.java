package parsing.xml.original;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ParserDovoleniek extends DefaultHandler 
{
  private StringBuffer rok = new StringBuffer(10);
  private StringBuffer miesto = new StringBuffer(50);
  private StringBuffer pocetDni = new StringBuffer(50);
  private String krajina;
  private boolean spracovavamRok, spracovavamMiesto, spracovavamPocetDni;
  private TreeMap<Integer,String> dovolenky;
  private int celkovyPocetDni;

  public void vypisDovolenky()
  {
    for (Iterator<Map.Entry<Integer,String>> i = dovolenky.entrySet().iterator(); i.hasNext(); )
    {
      Map.Entry<Integer,String> v = i.next();
      System.out.println(v.getKey() + ": " + v.getValue());
    }
  }

  public void vypisCelkovyPocetDni(){
    System.out.println("Celkovy pocet dni: " + celkovyPocetDni);
  }

  public void startDocument() 
  {
    dovolenky = new TreeMap<Integer,String>();
  }

  public void startElement(String uri, String localName, String qName, Attributes atts) 
  {
    if (qName.equals("rok") == true) 
    {
      spracovavamRok = true;
      rok.setLength(0);
    }
    else if (qName.equals("miesto") == true)
    {
      spracovavamMiesto = true;
      krajina = atts.getValue("krajina");
      miesto.setLength(0);
    } else if (qName.equals("pocetDni") == true){
      spracovavamPocetDni = true;
      pocetDni.setLength(0);
    }
  }    

  public void endElement(String uri, String localName, 
                        String qName) {
    if (qName.equals("rok") == true) 
    {
      spracovavamRok = false; 
    }
    else if (qName.equals("miesto") == true)
    {
      spracovavamMiesto = false;
    }
    else if (qName.equals("dovolenka") == true) 
    {
      miesto.append(" (");
      miesto.append(krajina);
      miesto.append(")");

      dovolenky.put(new Integer(Integer.parseInt(rok.toString())), miesto.toString());
    }else if (qName.equals("pocetDni") == true){
      spracovavamPocetDni = false;
      celkovyPocetDni += Integer.parseInt(pocetDni.toString());
    }
  }    

  public void characters(char[] ch, int start, int length) 
  {
    if (spracovavamRok == true) 
    {  
      rok.append(ch, start, length);
    }        
    else if (spracovavamMiesto == true)
    { 
      miesto.append(ch, start, length);
    }else if (spracovavamPocetDni == true)
    {
      pocetDni.append(ch, start, length);
    }
  }
}
