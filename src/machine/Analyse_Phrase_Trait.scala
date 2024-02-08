package  machine

trait Analyse_Phrase_Trait {
  /*
   * 0 : Français
   * 1 : Anglais
   * 2 : Espagnol
   * 3 : Allemand
   * 4 : Italien
   */
  var language: Int = 0 //langue détectée initialisée à Français
  var previousLanguage: Int = 0 //langage en mémoire
  var asking: Boolean = false //si on attend la confirmation
  var checked: Boolean = true //si la langue a été confirmée
  var modified: Boolean = false

  val languages: Array[String] = Array("Français", "Anglais", "Espagnol", "Allemand", "Italien")

  var choiceSelection: Boolean = false // sélection en cours d'un lieu parmi une liste de propositions

  /**
   * Change la langue courante détectée en passant à la suivante
   * @author Alexandre et Sterenn
   */
  def nextLanguage(): Unit

  /**
   * renvoie un boolean indiquant si la langue précédente est identique à la langue actuelle
   * @author Alexandre et Sterenn
   */
  def verifyLanguage(): Boolean
  
  /**
   * Change la langue courante détectée
   * @param keywords les mots clés corrigés de la demande de l'utilisateur
   * @param bdd la base de données utilisée pour detecter la langue
   * @author Sterenn et Alexandre
   */
  def languageDetection(keywords: List[String], bdd: Map[String, Int]): Unit

  /**
   * Confirme la langue détectée precedemment en modifiant checked
   * @param keywords les mots clés corrigés de la demande de l'utilisateur
   * @param bdd la base de données utilisée pour confirmer la langue
   * @author Sterenn et Alexandre
   */
  def languageConfirmation(keywords: List[String], bdd: Map[Int, String]): Unit

  /**
   * La fonction splitSentence prend en entrée une phrase et retourne un tableau de chaque mot de la phrase
   * @author Thomas & Tangui
   * @param s un String
   * @return Un tableau de String
   */
  def decoupePhrase(s: String): List[String]

  /**
   * @param s la phrase donnée, préalable découpée
   * @return un nombre s'il existe
   * @author Ivann
   */
  def chercheNombre(s: List[String]): Option[Int]

  /**
   * La fonction salutationCase traite le cas du bonjour suivi d'une requete. Vérifie si la list de mot clé ne contient pas seulement Bonjour
   * @author Thomas & Tangui
   */
  def filtrageBonjour(l: List[String], s: String): Boolean
  
  
}