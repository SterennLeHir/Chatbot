package machine

trait BaseDeDonnees {
  private var detectionDataBase:Map[String, Int] = Map()
   private var confirmationDataBase:Map[String,Int] = Map() 
  /**
   * Initialise les bases de données dans des Map ou des Array
   */
  def initialiser(): Unit

  /** Retourne les phrases correspondantes aux mots clés
   *
   * @param motsCles les mots clés
   * @return les phrases de réponses
   */
  def reponse(motsCles: List[String]): (Boolean, Boolean, List[String], Option[String])

  /**
   * Retourne l'adresse associée au lieu donné
   *
   * @param motsCles liste de mots clés pour un lieu
   * @return (adresse du lieu correctement orthographiée si elle existe, liste des lieux
   */
  def chercheAdresse(motsCles: List[String]): (Option[String], List[String])

  /** Créer la liste des noms des lieux
   *
   * @return une liste de noms
   */
  def listeLieux: List[String]

  /** Vérifie l'existence exacte d'un lieu
   *
   * @param lieu le lieu à chercher
   * @return s'il existe
   */
  def existe(lieu: String): Boolean

  /**
   * renvoie la base de données dans la langue à utiliser par le chatbot
   * @author Alexandre et Sterenn
   */
  def dataBaseToUse(language:Int): Array[String]
}