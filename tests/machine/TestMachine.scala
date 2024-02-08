package machine
import org.junit.Test

import org.junit.Assert._

class TestIntegration {

  // initialisation des objets sous test
  val m= MachineImpl
  m.reinit
  
  // tests
  @Test
  def test_IDK{
    assertEquals(List("Je ne comprends pas votre demande"),m.test(List(" ")))
    assertEquals(List("Je ne comprends pas votre demande"),m.test(List(" ? ")))
    assertEquals(List("Je ne comprends pas votre demande"),m.test(List("je cherche")))
    assertEquals(List("Je ne comprends pas votre demande"),m.test(List("comment tu t'appelles ?")))
    assertEquals(List("Je ne comprends pas votre demande"),m.test(List("Bojor")))
    assertEquals(List("Je ne comprends pas votre demande"),m.test(List("Sal")))
    assertEquals(List("Je ne comprends pas votre demande"),m.test(List("Bosor")))
  }
  
  @Test
  def test_Bonjour{
    assertEquals(List("Bonjour"),m.test(List("Bonjour")))
    assertEquals(List("Bonjour"),m.test(List("BoNjOuR")))
    assertEquals(List("Bonjour"),m.test(List("Bojour"))) 
    assertEquals(List("Bonjour"),m.test(List("Boojour")))
  }
  
  @Test
  def test_Salut{
    assertEquals(List("Salut"),m.test(List("Salut")))
    assertEquals(List("Salut"),m.test(List("Satul")))
    assertEquals(List("Salut"),m.test(List("Salu")))
    assertEquals(List("Salut"),m.test(List("Salur")))
  }
  
  @Test
  def test_Bonsoir{
    assertEquals(List("Bonsoir"),m.test(List("Bonsoir")))
    assertEquals(List("Bonsoir"),m.test(List("Bonsior")))
    assertEquals(List("Bonsoir"),m.test(List("Bosoir")))
    assertEquals(List("Bonsoir"),m.test(List("Boosoir")))
  }
  
  @Test
  def test_Bonjour_IDK{
    assertEquals(List(List("Bonjour"),List("Je ne comprends pas votre demande")),m.test(List("Bonjour, comment tu t'appelles ?")))
    assertEquals(List(List("Bonjour"),List("Je ne comprends pas votre demande")),m.test(List("Bojour, comment tu t'appelles ?")))
    assertEquals(List(List("Bonjour"),List("Je ne comprends pas votre demande")),m.test(List("Boejour, ça va ?")))
    assertEquals(List(List("Bonjour"),List("Je ne comprends pas votre demande")),m.test(List("Bonjour, je cherche la Mar")))
    assertEquals(List(List("Bonjour"),List("Je ne comprends pas votre demande")),m.test(List("Bonjour, je cherche la Gor")))
    assertEquals(List(List("Bonjour"),List("Je ne comprends pas votre demande")),m.test(List("Bonjour, je cherche le Thate")))
  }
  
  @Test
  def test_Salut_IDK{
    assertEquals(List(List("Salut"),List("Je ne comprends pas votre demande")),m.test(List("Salut, comment tu t'appelles ?")))
    assertEquals(List(List("Salut"),List("Je ne comprends pas votre demande")),m.test(List("Salu, comment tu t'appelles ?")))
    assertEquals(List(List("Salut"),List("Je ne comprends pas votre demande")),m.test(List("Salul, ça va ?")))
    assertEquals(List(List("Salut"),List("Je ne comprends pas votre demande")),m.test(List("Salut, je cherche la Mar")))
    assertEquals(List(List("Salut"),List("Je ne comprends pas votre demande")),m.test(List("Salut, je cherche la Gor")))
    assertEquals(List(List("Salut"),List("Je ne comprends pas votre demande")),m.test(List("Salut, je cherche le Thate")))
  }
  
  @Test
  def test_Bonsoir_IDK{
    assertEquals(List(List("Bonsoir"),List("Je ne comprends pas votre demande")),m.test(List("Bonsoir, comment tu t'appelles ?")))
    assertEquals(List(List("Bonsoir"),List("Je ne comprends pas votre demande")),m.test(List("Bosoir, comment tu t'appelles ?")))
    assertEquals(List(List("Bonsoir"),List("Je ne comprends pas votre demande")),m.test(List("Boesoir, ça va ?")))
    assertEquals(List(List("Bonsoir"),List("Je ne comprends pas votre demande")),m.test(List("Bonsoir, je cherche la Mar")))
    assertEquals(List(List("Bonsoir"),List("Je ne comprends pas votre demande")),m.test(List("Bonsoir, je cherche la Gor")))
    assertEquals(List(List("Bonsoir"),List("Je ne comprends pas votre demande")),m.test(List("Bonsoir, je cherche le Thate")))
  }
  
  @Test
  def test_Mairie_De_Rennes{    
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"),m.test(List("Où est donc la Mairie de Rennes ?")))
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"),m.test(List("Où est donc la Marie de Renne ?")))
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"),m.test(List("Où est donc la Maerie de Rannes ?")))
  }
  
  @Test
  def test_Marie{
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"),m.test(List("Je cherche la Mairie ?")))
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"),m.test(List("Je cherche la Marie ?")))
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"),m.test(List("Je cherche la Maorie ?")))
  }
  
  @Test
  def test_Hotel_De_Ville{
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"),m.test(List("hotel de ville")))
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"),m.test(List("hotal de vile")))
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"),m.test(List("hotl de valle")))
  }
  
  @Test
  def test_Mairie_IDK{
    assertEquals(List("Je ne comprends pas votre demande"),m.test(List("Où est donc la Mair de Rannes ?")))
    assertEquals(List("Je ne comprends pas votre demande"),m.test(List("Je cherche la Mari ?")))
    assertEquals(List("Je ne comprends pas votre demande"),m.test(List("hot de valle")))
  }
  
  @Test
  def test_Théâtre_National_De_Bretagne{    
    assertEquals(List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier"),m.test(List("Je cherche le Théâtre National de Bretagne ?")))
    assertEquals(List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier"),m.test(List("Je cherche le Thatre National ?")))
    assertEquals(List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier"),m.test(List("Je cherche le Theatre Natiunal ?")))
    assertEquals(List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier"),m.test(List("Je cherche le Théotre Natinal ?")))
  }
  
  @Test
  def test_Théâtre_De_Bretagne{
    assertEquals(List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier"),m.test(List("Théâtre de Bretagne")))
    assertEquals(List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier"),m.test(List("Thetre de Bretagne")))
    assertEquals(List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier"),m.test(List("Theatre de Betagne")))
    assertEquals(List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier"),m.test(List("Theitre de Brutagne")))
  }
  
  @Test
  def test_TNB{
    assertEquals(List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier"),m.test(List("Où est le TNB ?")))
    assertEquals(List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier"),m.test(List("Où est le TN ?")))
    assertEquals(List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier"),m.test(List("Où est le TNO ?")))
  }
  
  @Test
  def test_Théâtre_La_Paillette{
    assertEquals(List("L'adresse du Théâtre La Paillettes est : 2, Rue du Pré de Bris"),m.test(List("Où est le Théâtre la Paillette ?")))
    assertEquals(List("L'adresse du Théâtre La Paillettes est : 2, Rue du Pré de Bris"),m.test(List("Où est le Théâtre la Paollette ?")))
    assertEquals(List("L'adresse du Théâtre La Paillettes est : 2, Rue du Pré de Bris"),m.test(List("Où est le Thétre la Paillette ?")))
    assertEquals(List("L'adresse du Théâtre La Paillettes est : 2, Rue du Pré de Bris"),m.test(List("Où est le Thatre la Pallette ?")))
  }
  
  @Test
  def test_La_Paillette{
    assertEquals(List("L'adresse du Théâtre La Paillettes est : 2, Rue du Pré de Bris"),m.test(List("la Paillette")))
    assertEquals(List("L'adresse du Théâtre La Paillettes est : 2, Rue du Pré de Bris"),m.test(List("la Pallette")))
    assertEquals(List("L'adresse du Théâtre La Paillettes est : 2, Rue du Pré de Bris"),m.test(List("la Paollette")))
  }
  
  @Test
  def test_Théâtre_Lequel{
    assertEquals(List("Lequel ?"),m.test(List("Je cherche le Théatre")))
    assertEquals(List("Lequel ?"),m.test(List("Je cherche le Thatre")))
    assertEquals(List("Lequel ?"),m.test(List("Je cherche le Theotre")))
    assertEquals(List("Lequel ?"),m.test(List("Je cherche le Théatre La Palette"))) 
    assertEquals(List("Lequel ?"),m.test(List("Je cherche le Théatre Natil")))
    assertEquals(List("Lequel ?"),m.test(List("Thâtre de Betagn")))
  }
    
  @Test
  def test_Théâtre_IDK{
    assertEquals(List("Je ne comprends pas votre demande"),m.test(List("Où est donc le T ?")))
    assertEquals(List("Je ne comprends pas votre demande"),m.test(List("Téate")))
    assertEquals(List("Je ne comprends pas votre demande"),m.test(List("Thâte de Betage")))
  }
  
  @Test
  def test_Gare_SNCF{
    assertEquals(List("L'adresse de la Gare SNCF est : 19, Place de la Gare"),m.test(List("Où est la Gare SNCF ?")))
    assertEquals(List("L'adresse de la Gare SNCF est : 19, Place de la Gare"),m.test(List("Où est la Gar SNCF ?")))
    assertEquals(List("L'adresse de la Gare SNCF est : 19, Place de la Gare"),m.test(List("Où est la Gare SCNF ?")))
  }
  
  @Test
  def test_Gare{
    assertEquals(List("L'adresse de la Gare SNCF est : 19, Place de la Gare"),m.test(List("Je cherche la Gare")))
    assertEquals(List("L'adresse de la Gare SNCF est : 19, Place de la Gare"),m.test(List("Je cherche la Gar")))
    assertEquals(List("L'adresse de la Gare SNCF est : 19, Place de la Gare"),m.test(List("Je cherche la Garo")))
  }
  
  @Test
  def test_Gare_IDK{
    assertEquals(List("Je ne comprends pas votre demande"),m.test(List("Ga")))
    assertEquals(List("Je ne comprends pas votre demande"),m.test(List("Gar SC")))
    assertEquals(List("Je ne comprends pas votre demande"),m.test(List("Ga SNC")))
  }
  
  @Test
  def test_Bonjour_Maire{
    assertEquals(List(List("Bonjour"),List("L'adresse de Mairie de Rennes est : Place de la Mairie")),m.test(List("Bonjour, Mairie")))
    assertEquals(List(List("Bonjour"),List("L'adresse de Mairie de Rennes est : Place de la Mairie")),m.test(List("Bojour, Mairie")))
    assertEquals(List(List("Bonjour"),List("L'adresse de Mairie de Rennes est : Place de la Mairie")),m.test(List("Bonjour, Maire")))
    assertEquals(List(List("Bonjour"),List("L'adresse de Mairie de Rennes est : Place de la Mairie")),m.test(List("Bonjonr, Maurie")))
  }
  
  @Test
  def test_Salut_Maire_De_Rennes{
    assertEquals(List(List("Salut"),List("L'adresse de Mairie de Rennes est : Place de la Mairie")),m.test(List("Salut, Mairie de Rennes")))
    assertEquals(List(List("Salut"),List("L'adresse de Mairie de Rennes est : Place de la Mairie")),m.test(List("Salu, Mairie de Rennes")))
    assertEquals(List(List("Salut"),List("L'adresse de Mairie de Rennes est : Place de la Mairie")),m.test(List("Salut, Maire de Rannes")))
    assertEquals(List(List("Salut"),List("L'adresse de Mairie de Rennes est : Place de la Mairie")),m.test(List("Salot, Maurie de Renne")))
  }
  
  @Test
  def test_Bonsoir_Hotel_De_Ville{
    assertEquals(List(List("Bonsoir"),List("L'adresse de Mairie de Rennes est : Place de la Mairie")),m.test(List("Bonsoir, Mairie de Rennes")))
    assertEquals(List(List("Bonsoir"),List("L'adresse de Mairie de Rennes est : Place de la Mairie")),m.test(List("Bosoir, Mairie de Rennes")))
    assertEquals(List(List("Bonsoir"),List("L'adresse de Mairie de Rennes est : Place de la Mairie")),m.test(List("Bonsoir, Maire de Rannes")))
    assertEquals(List(List("Bonsoir"),List("L'adresse de Mairie de Rennes est : Place de la Mairie")),m.test(List("Bonsour, Maurie de Renne")))
  }
  
  @Test
  def test_Bonjour_Théâtre_National_De_Bretagne{
    assertEquals(List(List("Bonjour"),List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier")),m.test(List("Bonjour, Theatre National de Bretagne")))
    assertEquals(List(List("Bonjour"),List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier")),m.test(List("Bojour, Théâtre National de Bretagne")))
    assertEquals(List(List("Bonjour"),List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier")),m.test(List("Bonjour, Théate Nationl de Betagne")))
    assertEquals(List(List("Bonjour"),List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier")),m.test(List("Bonjonr, Théarre Nationol de Bretagn")))
  }
  
  @Test
  def test_Salut_Théâtre_De_Bretagne{
    assertEquals(List(List("Salut"),List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier")),m.test(List("Salut, Théarte de Bretagne")))
    assertEquals(List(List("Salut"),List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier")),m.test(List("Salu, Théâtre de Bretagne")))
    assertEquals(List(List("Salut"),List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier")),m.test(List("Salut, Theare de Bretogne")))
    assertEquals(List(List("Salut"),List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier")),m.test(List("Salot, Theatro de Betagne")))
  }
  
  @Test
  def test_Bonsoir_TNB{
    assertEquals(List(List("Bonsoir"),List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier")),m.test(List("Bonsoir, TNB")))
    assertEquals(List(List("Bonsoir"),List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier")),m.test(List("Bosoir, TNB")))
    assertEquals(List(List("Bonsoir"),List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier")),m.test(List("Bonsoir, TN")))
    assertEquals(List(List("Bonsoir"),List("L'adresse du Théâtre National de Bretagne est : 1, Rue Saint-Hélier")),m.test(List("Bonsour, TN0")))
  }
  
  @Test
  def test_Bonjour_Théâtre_La_Paillette{
    assertEquals(List(List("Bonjour"),List("L'adresse du Théâtre La Paillettes est : 2, Rue du Pré de Bris")),m.test(List("Bonjour, Theatre La Paillette")))
    assertEquals(List(List("Bonjour"),List("L'adresse du Théâtre La Paillettes est : 2, Rue du Pré de Bris")),m.test(List("Bojour, Théâtre La Paillette")))
    assertEquals(List(List("Bonjour"),List("L'adresse du Théâtre La Paillettes est : 2, Rue du Pré de Bris")),m.test(List("Bonjour, Théate La Paillotte")))
    assertEquals(List(List("Bonjour"),List("L'adresse du Théâtre La Paillettes est : 2, Rue du Pré de Bris")),m.test(List("Bonjonr, Théarre La Paillete")))
  }
  
  @Test
  def test_Salut_La_Paillette{
    assertEquals(List(List("Salut"),List("L'adresse du Théâtre La Paillettes est : 2, Rue du Pré de Bris")),m.test(List("Salut, La Paillette")))
    assertEquals(List(List("Salut"),List("L'adresse du Théâtre La Paillettes est : 2, Rue du Pré de Bris")),m.test(List("Salu, La Paillette")))
    assertEquals(List(List("Salut"),List("L'adresse du Théâtre La Paillettes est : 2, Rue du Pré de Bris")),m.test(List("Salut, La Paillutte")))
    assertEquals(List(List("Salut"),List("L'adresse du Théâtre La Paillettes est : 2, Rue du Pré de Bris")),m.test(List("Salot, La Pailette")))
  }
  
   @Test
  def test_Salut_Gare_SNCF{
    assertEquals(List(List("Salut"),List("L'adresse de la Gare SNCF est : 19, Place de la Gare")),m.test(List("Salut, Gare SNCF")))
    assertEquals(List(List("Salut"),List("L'adresse de la Gare SNCF est : 19, Place de la Gare")),m.test(List("Salu, Gar SNCF")))
    assertEquals(List(List("Salut"),List("L'adresse de la Gare SNCF est : 19, Place de la Gare")),m.test(List("Salut, Gare SNC")))
    assertEquals(List(List("Salut"),List("L'adresse de la Gare SNCF est : 19, Place de la Gare")),m.test(List("Salot, Gari SNCG")))
  }
  
  @Test
  def test_Bonsoir_Gare{
    assertEquals(List(List("Bonsoir"),List("L'adresse de la Gare SNCF est : 19, Place de la Gare")),m.test(List("Bonsoir, Gare")))
    assertEquals(List(List("Bonsoir"),List("L'adresse de la Gare SNCF est : 19, Place de la Gare")),m.test(List("Bosoir, Gar")))
    assertEquals(List(List("Bonsoir"),List("L'adresse de la Gare SNCF est : 19, Place de la Gare")),m.test(List("Bonsoir, Garo")))
    assertEquals(List(List("Bonsoir"),List("L'adresse de la Gare SNCF est : 19, Place de la Gare")),m.test(List("Bonsour, Gare")))
  }
  
}