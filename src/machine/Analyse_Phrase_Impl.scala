package machine

import scala.util.matching.Regex

class Analyse_Phrase_Impl extends Analyse_Phrase_Trait {

  def nextLanguage(): Unit = {
    if (language == languages.length - 1){
      language = 0;
    } else {
      language += 1
    }
  }
  
  def languageDetection(keywords: List[String], bdd: Map[String, Int]): Unit = {
    previousLanguage = language
    for (keyword <- keywords) {
      for ((cle, valeur) <- bdd) { // on recherche dans la base de données

        if (keyword == cle) { // si le mot est trouvé
          modified = true // la langue a été detectée et donc modifiée
          language = valeur // on change la langue détectée

          return // s'arrêter dès le premier mot détecté d'une langue
        }
      }
    }
  }
  
  def languageConfirmation(keywords: List[String], bdd: Map[Int, String]): Unit = {
    for ((cle, valeur) <- bdd){ // on recherche dans la base de données
       if (keywords(0) == valeur) { // si le mot est trouvé
           if (language == cle) {
             checked = true //le langage a été confirmé
           }           
       }
     }
  }
  
  def verifyLanguage(): Boolean = {
    previousLanguage == language
  }

  def decoupePhrase(s: String): List[String] = {
    var t = s;
    val regex = List( "[.;:!,?./§ ]+"," de ", " du ", " des ", " le ", " la ", " les ", " l'", " dans")

    for (i <- 0 to regex.length - 1) {
      t = t.split(regex(i)).mkString(" ") 
      // println(i+" "+t+" | "+regex(i))
    }
    
    return t.split(" ").toList
  }

  /**
   * @param s la phrase donnée, préalable découpée
   * @return un nombre s'il existe
   */
  def chercheNombre(s: List[String]): Option[Int] = {
    for(mot <- s) {
      if(mot forall Character.isDigit) {
        if(mot.toInt > 0)
          return Some(mot.toInt)
      }
    }

    None
  }

  def filtrageBonjour(l: List[String], s:String): Boolean = {
    
    var res = false 
    
    if(l.length == 1) {
      if((l(0).toLowerCase==("bonjour")||l(0).toLowerCase==("salut")||l(0).toLowerCase==("bonsoir"))&& (decoupePhrase(s).length >1)){
        res = true
      }
    }
    return res
  }


}