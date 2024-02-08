package gui_client
import scala.swing.BorderPanel
/**
 * Trait User
 */
trait User {
  def name: String;
  def alignment: BorderPanel.Position.Value
}
/**
 * Objet User
 */
object User extends User {
  val name = "Utilisateur";
  val alignment = BorderPanel.Position.East;
}
/**
 * Objet Bot
 */
object Bot extends User {
  val name = "Billy_Bot_DQ";
  val alignment = BorderPanel.Position.West;
}
