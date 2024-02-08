package machine
import machine.ErrorsToleranceImpl.corrige

object MachineImpl extends MachineDialogue {
  val bdd = BaseDeDonneesImpl
  val analyse = new Analyse_Phrase_Impl
  var addressesChoices: List[String] = List()

  bdd.initialiser()
  var nouvBDD: Set[String] = Set()
  for (lieu <- bdd.listeLieux) {
    nouvBDD = nouvBDD ++ analyse.decoupePhrase(lieu)
  }

  def ask(s: String): (List[String],Int) = {

    val phrase = analyse.decoupePhrase(s)
    var answer: List[String] = List()
    var notUnderstood = false
    println("L'utilisateur demande : " + phrase)

    var finale: List[String] = List("*")

    if (analyse.choiceSelection) { //si l'utilisateur doit choisir un résultat parmi une liste
      analyse.chercheNombre(phrase) match {
        case Some(nombre) =>
          if (nombre <= addressesChoices.size) {
            finale = List(addressesChoices(nombre - 1))
          }
        case None =>
      }

      analyse.choiceSelection = false //l'utilisateur a choisi
    } else { //s'il n'y a pas de choix à faire
      val erreur = corrige(phrase, nouvBDD.toList)
      println("l'erreur est : " + erreur)
      if (erreur.isEmpty) {
        notUnderstood = true
      } else {
        finale = erreur
        if (analyse.filtrageBonjour(erreur, s)) { //cas où l'utilisateur a dit bonjour et quelque chose d'incompréhensible
          finale = finale ++ List("*")
        }
      }
    }
    // Partie de la detection de la langue
    if (!analyse.asking) { // si on est pas dans le mode question
      analyse.languageDetection(finale, bdd.detectionDataBase) //on detecte la langue

      val db = bdd.dataBaseToUse(analyse.language) //on se met dans la bonne base de données
      if (!analyse.verifyLanguage()) { //si la langue a changé
        analyse.asking = true //mode question
        analyse.checked = false //le langage n'est plus confirmé)
                /*
        for (i <- 0 to db.length-1){
          print(db(i))
        }*/
        answer = List(db(2)) //on pose la question de confirmation (ligne 3 de la base de donnée))
        //println( "1 : " + answer)
      } else { //si la langue n'a pas changé
        // on récupère la réponse de la base de données
        val (greetings, understanding, placesFound, address): (Boolean, Boolean, List[String], Option[String]) = bdd.reponse(finale)
        println((greetings, understanding, placesFound, address))
        answer = if(greetings) List(db(6)) else List()

        notUnderstood = !understanding

        println("la salutation : " + answer)
        println("l'incompréhension : " + notUnderstood)
        if (notUnderstood){ //si la demande n'est pas compréhensible
          val db = bdd.dataBaseToUse(analyse.language) // on se met dans la bonne base de données
          answer = answer :+ db(1)
        } else if (placesFound.size == 1 && placesFound.head != "") { // il n'y a qu'un lieu
          val db = bdd.dataBaseToUse(analyse.language) // on se met dans la bonne base de données
          val addressToReply = address match {
            case Some(a) => a
            case None    => "" //il ne peut pas y avoir aucune adresse
          } 
          val sentence = db(0).replace("X", placesFound.head) + " " + addressToReply // on remplace le X dans la phrase type par le lieu demandé
          println("l'adresse du lieu : " + sentence)
          answer = answer :+ sentence
        } else if (placesFound.size > 1) { //si il y a plusieurs choix
          val db = bdd.dataBaseToUse(analyse.language) // on se met dans la bonne base de données
          val sentence = db(4).replace("X", (placesFound.size).toString) // on remplace le X dans la phrase type par le nombre de choix possibles
          answer = answer :+ sentence

          for (i <- placesFound.indices) {
            answer = answer :+ ("\n" + (i + 1) + ") " + placesFound(i))
          }

          answer = answer :+ "\n" + db(5) //on demande le choix à l'utilisateur
          addressesChoices = placesFound
          analyse.choiceSelection = true //l'utilisateur doit faire un choix
        }

      }
    } else { //on attend la confirmation
      analyse.languageConfirmation(finale, bdd.confirmationDataBase) //on confirme ou non la langue
      if (!analyse.checked) { // si la langue n'a pas été confirmée
        analyse.modified = false // on réinitialise la variable pour savoir si on detecte une nouvelle langue
        analyse.languageDetection(finale, bdd.detectionDataBase) //on recherche la langue
        if (!analyse.verifyLanguage() || notUnderstood) { //si la langue a changé ou que la phrase n'est pas compréhensible
          if (analyse.modified) { // si une nouvelle langue a été détectée
            val db = bdd.dataBaseToUse(analyse.language) //on se met dans la bonne base de données
            answer = answer ++ List(db(2)) //on pose la question de confirmation (ligne 3 de la base de donnée)
            println("2 : " + answer)
          } else {
            analyse.nextLanguage()
            //println("nouveau langage = " + analyse.language)
            val db = bdd.dataBaseToUse(analyse.language)
            answer = List(db(2)) //on pose la question de confirmation (ligne 3 de la base de donnée)
            println("3 : " + answer)
          }
        }
      } else { // le langage a été confirmé
        analyse.asking = false
        val db = bdd.dataBaseToUse(analyse.language) //on se met dans la bonne base de données
        answer = List(db(3)) //on demande à l'utilisateur ce qu'il veut dans la bonne langue
        //println( "4 : " + answer)
      }

    }
    println("La réponse est : " + answer)

    (answer,analyse.language)
  }

  // Pour la partie test par le client
  def reinit: Unit = {
    analyse.language = 0
    analyse.previousLanguage = 0
    analyse.asking = false
    analyse.checked = true
    analyse.modified = false
    analyse.choiceSelection = false
  }

  def test(l: List[String]): List[String] = {
    var i = ""
    var listreturn: List[String] = List()
    for (i <- l) {
      listreturn = listreturn ++ ask(i)._1
    }
    //println(listreturn)
    listreturn
  }
}
