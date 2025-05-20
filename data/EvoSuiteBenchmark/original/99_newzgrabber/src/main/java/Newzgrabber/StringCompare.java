package Newzgrabber;
import Newzgrabber.*;

import java.util.*;

public class StringCompare implements Comparator
{
 public int compare(Object a, Object b)
 {
  return(((String)a).compareTo((String)b));
 }

 public boolean equals(Object b)
 {
  return(this.equals(b));
 }

} 
 