package machine

import java.text.Normalizer
import scala.io.Source
import Array._
import scala.xml._

object BaseDeDonneesImpl extends BaseDeDonnees {

  private var bddLieux: Map[String, String] = Map()
  private var bddLieuxComplete: Map[String, String] = Map()
  private var bddPhrases: Map[String, String] = Map()
  private var bddSynonymes: Map[String, String] = Map()
  private var lieuxMaj: Set[String] = Set()
  var detectionDataBase:Map[String, Int] = Map()
  var confirmationDataBase:Map[Int,String] = Map()
  private var francaisDataBase:Array[String] = Array()
  private var anglaisDataBase:Array[String] = Array()
  private var espagnolDataBase:Array[String] = Array()
  private var allemandDataBase:Array[String] = Array()
  private var italienDataBase:Array[String] = Array()
  private var dataBases:Array[Array[String]] = Array()

  /**
   * Initialiser les base de données dans des HashMap
   */
  override def initialiser(): Unit = {

    //Import du ficher
    val x = XML.loadFile("doc/vAr.xml")

    //récupération des noms
    val noms = (x \\ "organization" \ "name").map(_.text)

    //récupération des noms de rue/de lieu
    val adresses = (x \\ "organization" \ "addresses").map(addresses => {
      val liste = (addresses \ "address").toList
      if (liste.isEmpty)
        ""
      else
        (liste.head \ "street" \ "name").text
    })

    //récupération des numéros de rue
    val nums = (x \\ "organization" \ "addresses").map(addresses => {
      val liste = (addresses \ "address").toList
      if (liste.isEmpty)
        ""
      else
        (liste.head \ "street" \ "number").text
    })

    //récupération des extension : bis/ter...
    val extension = (x \\ "organization" \ "addresses").map(addresses => {
      val liste = (addresses \ "address").toList
      if (liste.isEmpty)
        ""
      else
        (liste.head \ "street" \ "extension").text
    })

    //récupération des numéros de bâtiments
    val building = (x \\ "organization" \ "addresses").map(addresses => {
      val liste = (addresses \ "address").toList
      if (liste.isEmpty)
        ""
      else
        (liste.head \ "street" \ "building").text
    })

    //On créer une list qui va être concaténée au fur et à mesure avec les adresses complètes
    var adressesComplete: List[String] = List()
    for (i <- 0 to 2116) {
      if (extension(i) == "" && nums(i) == "" && building(i) == "")
        adressesComplete = adressesComplete :+ adresses(i)
      else if (extension(i) == "" && building(i) == "")
        adressesComplete = adressesComplete :+ nums(i) + " " + adresses(i)
      else if (building(i) == "")
        adressesComplete = adressesComplete :+ nums(i) + " " + extension(i) + " " + adresses(i)
      else if (extension(i) == "")
        adressesComplete = adressesComplete :+ nums(i) + " " + building(i) + " " + adresses(i)
      else
        adressesComplete = adressesComplete :+ nums(i) + " " + extension(i) + " " + building(i) + " " + adresses(i)
    }

    bddLieuxComplete = (noms zip adressesComplete).toMap

    val fichierDonnees = Source.fromFile("doc/DonneesInitiales.txt")
    val fichierPhrases = Source.fromFile("doc/DonneesPhrases.txt")
    val fichierSynonymes = Source.fromFile("doc/DonneesSynonymes.txt")
    val detectionFile = Source.fromFile("doc/detection.txt")
    val confirmationFile = Source.fromFile("doc/confirmation.txt")

    //Initialisation des bases de données des différentes langues
    val francaisFile = Source.fromFile("doc/français.txt")
    val anglaisFile = Source.fromFile("doc/anglais.txt")
    val espagnolFile = Source.fromFile("doc/espagnol.txt")
    val allemandFile = Source.fromFile("doc/allemand.txt")
    val italienFile = Source.fromFile("doc/italien.txt")

    //on parcourt les fichiers de bases de données
    for (fichier <- List(fichierDonnees, fichierPhrases, fichierSynonymes, detectionFile, confirmationFile, francaisFile, anglaisFile, espagnolFile, allemandFile, italienFile )) {
      for (line <- fichier.getLines) {  // on parcourt le fichier ligne par ligne
        val splittedLine = line.split(";") //on sépare la ligne en deux parties

        // vérifions que la ligne contient deux parties
        if (splittedLine.size == 2) {
          if(fichier == fichierDonnees) {
            lieuxMaj = lieuxMaj + splittedLine(0) //on rajoute la clé
            bddLieux = bddLieux + (motStandardise(splittedLine(0)) -> splittedLine(1)) //on lie la clé à la valeur
          }
          else if(fichier == fichierPhrases) {
            bddPhrases = bddPhrases + (motStandardise(splittedLine(0)) -> splittedLine(1))
          }
          else if(fichier == fichierSynonymes) {
            bddSynonymes = bddSynonymes + (motStandardise(splittedLine(0)) -> splittedLine(1))
          }
          else if (fichier == detectionFile) {
            val words = splittedLine(0).split(",") //tableau avec tous les mots de detection d'une langue
            for (word <- words){
              detectionDataBase = detectionDataBase + (motStandardise(word) -> splittedLine(1).toInt)
            }
          } else if (fichier == confirmationFile){
            confirmationDataBase = confirmationDataBase + (splittedLine(0).toInt -> motStandardise(splittedLine(1)))
          }
        }
        // 
        if (fichier == francaisFile) {
          francaisDataBase = concat(francaisDataBase, Array(line))
        } else if (fichier == anglaisFile) {
          anglaisDataBase = concat(anglaisDataBase, Array(line))
        } else if (fichier == espagnolFile){
          espagnolDataBase = concat(espagnolDataBase, Array(line))
        } else if (fichier == allemandFile){
          allemandDataBase = concat(allemandDataBase, Array(line))
        } else if (fichier == italienFile) {
          italienDataBase = concat(italienDataBase, Array(line))
        }
      }

      fichier.close()
    }
    dataBases = Array(francaisDataBase, anglaisDataBase, espagnolDataBase, allemandDataBase, italienDataBase) // on crée le tableau des bases de données dans chaque langue
    /*
    println("base italienne :")
    for (mot <- italienDataBase) {
      println("mot + " + mot)
    }
    println("terminé")
    */
  }

  def dataBaseToUse(language:Int): Array[String] = {
     dataBases(language)
  }
  /**
   * Retourner un mot en minuscule sans accent
   *
   * @param mot mot à transformer
   * @return mot en minuscule sans accent
   * @see <a href="https://stackoverflow.com/a/3322174">StackOverflow issue</a>
   */
  private def motStandardise(mot: String): String = {
    var nouvMot = Normalizer.normalize(mot, Normalizer.Form.NFD)
    nouvMot = nouvMot.replaceAll("\\p{M}", "")
    nouvMot.toLowerCase()
  }

  /**
   * Retourne les phrases correspondantes aux mots clés
   *
   * @param motsCles les mots clés
   * @return (salutation, understanding, listeLieuxTrouves, adresse)
   */
  override def reponse(motsCles: List[String]): (Boolean, Boolean, List[String], Option[String]) = {
    var nomsLieux: List[String] = List()
    var salutations = false
    var understanding = true
    
    for (motCle <- motsCles) { //on parcourt les mots clés de la demande de l'utilisateur
      if (motCle != "") {
        if (existeMot(motCle, BDDPhrase = true)) { //si c'est un mot de politesse
          val phraseStandard = motStandardise(motCle)
          if (Some(bddPhrases(phraseStandard)).contains("Bonjour")) {
            salutations = true //le chatbot dit bonjour
          } else if(Some(bddPhrases(phraseStandard)).contains("Je ne comprends pas votre demande")){//la demande est incompréhensible
            understanding = false
          }
        } else { //si ce n'est pas un mot de politesse
          nomsLieux = nomsLieux :+ motCle // le mot est un lieu
        }
      }
    }

    var adresse: Option[String] = None
    var lieuxTrouves: List[String] = List()

    if (nomsLieux.nonEmpty) { //si l'utilisateur a demandé un lieu
      val (adresseTrouvee, listeLieuxTrouves) = chercheAdresse(nomsLieux)
      lieuxTrouves = listeLieuxTrouves //le ou les lieux trouvés
      adresse = adresseTrouvee //l'adresse du lieu s'il n'y en a qu'un seul
    }
    if ((lieuxTrouves.isEmpty || lieuxTrouves.head == "") && motsCles.nonEmpty) understanding = false
    if(motsCles.size == 1 && salutations) understanding = true
    (salutations, understanding, lieuxTrouves, adresse)
  }

  def trouverMaj(adresseStandardisee: String): String = {
    for (adresse <- lieuxMaj) {
      if (motStandardise(adresse) == adresseStandardisee)
        return adresse
    }
    adresseStandardisee
  }


  /**
   * Retourne l'adresse associée au lieu donné
   *
   * @param motsCles liste de mots clés pour un lieu
   * @return (adresse du lieu correctement orthographiée si elle existe, liste des lieux
   */
  override def chercheAdresse(motsCles: List[String]): (Option[String], List[String]) = {
    var adresse = ""
    var nomLieu = ""

    var motsClesExistants: List[String] = List()
    var adresseTrouvee = false
    var plusieursAdresses = false

    // Tant que l'adresse n'est pas trouvée, on parcourt les mots clés donnés.
    // Si le mot n'est pas vide et qu'il existe dans la base de données,
    // on regarde si le mot correspond à plusieurs lieux dans la BDD :
    // - si oui, on va voir les mots clés suivants, s'ils peuvent permettre d'identifier un lieu de manière unique
    // - si non, on renvoie son adresse
    for (motCle <- motsCles) {
      if (motCle != "" && existeMot(motStandardise(motCle), BDDPhrase = false) && !adresseTrouvee) {
        var clesContenantMotCle: List[String] = List()
        var occurencesMotCle = 0

        for (cle <- listeLieux) {
          val motCleStandard = motStandardise(motCle)
          val cleStandard = motStandardise(cle)

          if (cleStandard.contains(motCleStandard)) {
            clesContenantMotCle = clesContenantMotCle :+ cleStandard
            occurencesMotCle += 1
          }
        }

        // s'il n'existe qu'un lieu avec le mot clé actuel
        if (occurencesMotCle == 1) {
          if (bddSynonymes.contains(clesContenantMotCle.head)) { //si le mot est un synonyme
            nomLieu =  bddSynonymes.getOrElse(clesContenantMotCle.head, "*")
            adresse = bddLieux(motStandardise(nomLieu))
          } else {
            adresse = bddLieux(clesContenantMotCle.head)
            nomLieu = trouverMaj(clesContenantMotCle.head)
          }

          adresseTrouvee = true
          plusieursAdresses = false
        } else {
          adresse = ""
          if (motsClesExistants.nonEmpty) {
            val trouve = false
            var motsClesTemp: List[String] = List()

            for (cle <- clesContenantMotCle) {
              val cleStandard = motStandardise(cle)

              if (!trouve) {
                for (motCleExistant <- motsClesExistants) {
                  val motCleExistantStandard = motStandardise(motCle)

                  if (cleStandard contains motCleExistantStandard)
                    motsClesTemp = motsClesTemp :+ motCleExistant
                }

                motsClesExistants = motsClesTemp
              }
            }

            if (motsClesTemp.size == 1) {
              if (bddSynonymes.contains(motsClesTemp.head)) {
                nomLieu =  bddSynonymes.getOrElse(motsClesTemp.head, "*")
                adresse = bddLieux(motStandardise(nomLieu))
              } else {
                nomLieu = trouverMaj(motsClesTemp.head)
                adresse = bddLieux(motsClesTemp.head)

              }
              adresseTrouvee = true
              plusieursAdresses = false
            }
          }
          else {
            plusieursAdresses = true
          }

          motsClesExistants = motsClesExistants :+ motCle
        }
      }
    }

    if(plusieursAdresses) {
      var adressesPossibles: List[String] = List()

      for(motCleExistant <- motsClesExistants) {
        for (cle <- listeLieux) {

          if (cle.contains(motCleExistant)) {
            adressesPossibles = adressesPossibles :+ cle
          }
        }
      }
      (None, adressesPossibles)
    }
    else {
      if(adresse.isEmpty)
        (None, List(nomLieu))
      else
      (Some(adresse), List(nomLieu))
    }
  }

  /**
   * Vérifie si un mot existe dans la BDD, sans se soucier des majuscules ou des accents
   *
   * @param mot       mot dont on vérifie l'existence
   * @param BDDPhrase recherche dans la BDD des phrases
   * @return mot existe dans les clés de la BDD
   */
  private def existeMot(mot: String, BDDPhrase: Boolean): Boolean = {
    var motTrouve = false
    val listeClesBDD = if (BDDPhrase) bddPhrases.keySet.toList else (bddLieux.keySet ++ bddSynonymes.keySet).toList

    for (key <- listeClesBDD) {
      val motStandard = motStandardise(mot)
      val keyStandard = motStandardise(key)

      if (keyStandard.contains(motStandard))
        motTrouve = true
    }

    motTrouve
  }

  /**
   * Créer la liste des noms des lieux
   *
   * @return une liste de noms
   */
  override def listeLieux: List[String] = {
    var a = bddLieux.keySet.toList
    a ++ (bddPhrases.-("*")).keySet.toList ++ bddSynonymes.keySet.toList ++ confirmationDataBase.values.toList ++ detectionDataBase.keySet.toList
  }

  /**
   * Vérifie l'existence exacte d'un lieu
   *
   * @param lieu le lieu à chercher
   * @return s'il existe
   */
  override def existe(lieu: String): Boolean = {
    bddLieux.contains(motStandardise(lieu))
  }
}
