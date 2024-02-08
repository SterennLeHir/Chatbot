package gui_client

import java.awt.Color

import scala.swing._
import scala.swing.event._

import javax.sound.sampled.AudioInputStream
import machine.MachineImpl.ask
import javax.swing.border.LineBorder
import javax.swing.border.BevelBorder
import marytts.LocalMaryInterface
import marytts.MaryInterface
import marytts.exceptions.MaryConfigurationException
import marytts.util.data.audio.AudioPlayer
import marytts.exceptions.SynthesisException

/**
 * Desole d'avance je n'ai pas splitter les fonction dans d'autre
 * fichier car il y avait des erreur a gerer que je n'arrivais pas a debugger
 *
 */

/**
 * MainFrame realizing the CopyThat application
 */
class UI extends MainFrame {
  // ------------------ TXTTOSPEECH
  var marytts: MaryInterface = null;
  var ap: AudioPlayer = null;
  /**
   * Paramétrage de la voix
   * @param voiceName String : langue
   */
  def Voice(voiceName: String) {
    try {
      marytts = new LocalMaryInterface();
      marytts.setVoice(voiceName);
      ap = new AudioPlayer;
    } catch {
      case e: MaryConfigurationException => e.printStackTrace()
    }
  }
  /**
   * Lecture d'un texte
   * @param input String : texte a lire
   */
  def say(input: String) {
    try {
      val as = marytts.generateAudio(input)
      ap.setAudio(as);
      ap.start();
    } catch {
      case e: SynthesisException => System.err.println("Error saying phrase.");

    }
  }

  // ------------------ IHM
  // Propriétés de la fenêtre
  title = "Avatar";
  val BkgdGeneral = new Color(40, 40, 40); //La couleur du background principal
  val BkgdInput = new Color(40, 40, 40); //La couleur du background de la zone de texte editable (input)
  val BkgdText = new Color(0, 100, 255); //La couleur du background des textes de l'utilisateur et du bot
  val fontColor = Color.WHITE; //La couleur d'ecriture de l'application
  preferredSize = new Dimension(800, 1000); //La taille de la fenetre

  // Initialisation de la box vertical qui accueillera la tchat box
  val chat = new BoxPanel(Orientation.Vertical) {
    background = BkgdGeneral //Background du chat a l'initialisation
  };
  // entree de la tchat box
  val inputField = new TextField {
    font = new Font("Arial", 0, 30); //Font du texte
    foreground = fontColor; //Couleur du texte
    background = BkgdInput; //Sa couleur de fond
    listenTo(mouse.clicks, this.keys);
    reactions += {
      case KeyPressed(_, Key.Enter, _, _) => if (this.text != "") { //Si la touche Entree est pressee, envoie le message
        val entree = this.text;
        printToChat(User.name + " : " + entree, BorderPanel.Position.East); //Affiche le message
        val answer = ask(entree);
        for (answers <- answer._1) printToChat(Bot.name + " : " + answers, BorderPanel.Position.West); //Affiche la reponse du bot a ce message
        answer._2 match {
          case 1 => Voice("cmu-slt-hsmm")
          case 4 => Voice("istc-lucia-hsmm")
          case 0 => Voice("upmc-pierre-hsmm")
          case 3 => Voice("dfki-pavoque-neutral-hsmm")
          case _ => Voice("upmc-pierre-hsmm")
        }

        say(answer._1.mkString(" "));
      }
      case _ => scrollToBottom(); //Si l'on touche a la chatbox, actualise la scrollBar afin de revenir aux messages recents et auxquels on repond
    }
  }

  val scrollPanel = new ScrollPane(chat); //La boite de texte dynamique dans laquelle on va afficher le tchat

  var scrollBar = scrollPanel.verticalScrollBar; //La scrollbar de la boite de chat
  val sendButton = new Button("Send") { //Le bouton envoi, qui envoie le message ecrit dans l'inputField, comme le fait la touche entree dans ce dernier
    reactions += {
      case ButtonClicked(_) => if (inputField.text != "") {
        val entree = inputField.text; //Le texte ecrit dans l'inputField
        printToChat(User.name + " : " + entree, BorderPanel.Position.East); //Affiche le message
        val answer = ask(entree);
        for (answers <- answer._1) printToChat(Bot.name + " : " + answers, BorderPanel.Position.West); //Affiche la reponse du bot a ce message

        answer._2 match {
          case 1 => Voice("cmu-slt-hsmm")
          case 4 => Voice("istc-lucia-hsmm")
          case 0 => Voice("upmc-pierre-hsmm")
          case 3 => Voice("dfki-pavoque-neutral-hsmm")
          case _ => Voice("upmc-pierre-hsmm")
        }
        say(answer._1.mkString(" "));
      }
    }
  }
  sendButton.preferredSize = (new Dimension(184, 100)); //La taille du bouton

  val hBox = new BorderPanel() { //Une boite horizontale, qui permet d'afficher cote a cote la zone d'entree de texte et le bouton
    add(inputField, BorderPanel.Position.West); //Ajout de la zone d'entree de texte dans la boite horizontale
    inputField.preferredSize = (new Dimension(600, 100)); //Taille de cette entree de texte
    add(sendButton, BorderPanel.Position.East); //Ajout du bouton dans la boite horizontale
  }
  var direction = BorderPanel.Position.West; //La direction par defaut est gauche
  val window = new BorderPanel {
    add(hBox, BorderPanel.Position.South); //Ajout de la boite horizontale en bas
    add(scrollPanel, BorderPanel.Position.Center); //Ajout du scroll Panel en haut
  }
  contents = window; //Ajout de la fenetre qu'on vient de creer dans la tchatbox
  // --------------------------------------------------------------

  /**
   * Methode qui print la conversation dans la tchat box
   * @param s : Texte entrée
   * @param d : alignement choisi
   */
  def printToChat(s: String, d: BorderPanel.Position.Value) {
    var tchatDisplay = new Button(s) {
      background = BkgdText; //La couleur de fond de la zone de texte
      font = new Font("Arial", 2, 20); //La font du texte
      foreground = fontColor; //La couleur de l'ecriture du texte
      horizontalAlignment = Alignment.Left; //On aligne le texte à gauche dans la zone de texte, puisqu'on lit de gauche a droite
      border = Swing.EmptyBorder(3, 20, 3, 20); //(top, left, bottom, right)
    }
    var fp = new FlowPanel() { //La boite dans laquelle on met le texte
      background = BkgdGeneral; //Le fond de cette boite
      contents += tchatDisplay; //Ajout du texte dans la boite
    }

    var borderPan = new BorderPanel() { //Une seconde boite dans laquelle on met la boite fp, afin de pouvoir la placer a gauche ou droite
      background = BkgdGeneral; //Le fond de cette boite
      add(fp, d); //On ajoute la boite fp
    }
    chat.contents += borderPan; //On ajoute la seconde boite a la boite dynamique chat
    scrollPanel.revalidate(); //On actualise la boite dynamique
    inputField.text = ""; //Et on set l'entree de texte a 'vide'
  }
  /**
   * Fonction qui scroll automatique la page
   */
  def scrollToBottom() { //Fonction qui scroll jusqu'en bas
    scrollBar.value = scrollBar.maximum;
  }
}
