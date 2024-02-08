package machine
import java.text.Normalizer;

object ErrorsToleranceImpl extends ErrorsTolerance {

 def stripAccents(s:String): String = {
     val t = Normalizer.normalize(s, Normalizer.Form.NFD);
     t.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
  }
  
   
  def corrige(l:List[String], b:List[String]):List[String] = {
    var s:List[String] = List() 
    var corrected: Boolean = false; //si le mot a déjà été corrigé
    var word:String = ""
    for(i <- 0 to l.length - 1){//on parcourt la liste
      for(j <- 0 to b.length - 1){//on parcourt la bdd
        word = stripAccents(l(i)).toLowerCase()
        if(!corrected && levenshteinDist(word, b(j)) <= 1){//si le mot ressemble à un mot de la bdd et n'a pas déjà été corrigé,on le corrige 
          if (word != "hello" && word != "je" && word != "ja" && word != "wo" && word != "yo" && word != "do" && word != "you"){ //on enlève tous les mots qui sont mal corrigés
            s = s ++ List(b(j))
            corrected = true; // le mot a été corrigé
          } else { 
            s = s ++ List(word) //on garde le mot de base
            corrected = true; // le mot a été corrigé
          }
        }
      }
      corrected = false; //on réinitialise à false pour corriger le mot suivant
    }
    s
  }

  
  def levenshteinDist(s1:String,s2:String):Int = {
 
    val l1 = s1.length
    val l2 = s2.length

    //on crée la matrice D
    val d = Array.ofDim[Int](l1+1,l2+1)
    
     for(i <- 0 to l1){//niche
       d(i)(0) = i
     }
    
     for(j <- 0 to l2){
        d(0)(j) = j
     }
    
    for(i <- 1 to l1){
      for(j <- 1 to l2){
        var cost = 0
        if(s1.charAt(i-1) != s2.charAt(j-1)){
          cost = 1
        }
        d(i)(j) = Math.min(d(i-1)(j)+1, Math.min(d(i)(j-1)+1, d(i-1)(j-1)+cost))
      }
    }
    d(l1)(l2)
  }

}