package machine

trait ErrorsTolerance { 
   
  /**
   * calcule la distance de Levenshtein entre deux chaines
   * @param s1 String
   * @param s2 String
   * @return un int représentant le nombre minimal de changements qu'il faut effectuer pour rendre les deux chaines égales
   */
  def levenshteinDist(s1:String,s2:String):Int
  
    /**
   * renvoie une string corrigée
   * @param l liste de String, l'expression
   * @param b liste de String, la base de donnée
   */
  def corrige(l:List[String], b:List[String]):List[String]


}