# Final project | Server-side Web Development with Java

Language: Java

This is a project created for the Helsinki University course "Web-palvelinohjelmointi Java" (Server-side Web Development with Java) in April-May 2019.

The project consist of a social media site ("old school Facebook") with following main functionalities:
* User registration
* Searching other users and sending friend requests
* Photo album
* Profile picture
* Personal page with limited number of posts
* Liking posts and pictures
* Commenting posts and pictures

The project is also available on Heroku: https://shrouded-harbor-56113.herokuapp.com/

## Ohjelmointiprojekti | Web-palvelinohjelmointi Java, kevät 2019

Tehtävänanto suomeksi: https://docs.google.com/document/d/1BkJKxzfUmvTP5GXGkFiAOTTyf8yEwsodRjXMlTwL2rQ/edit

Sovelluksen Heroku-osoite: https://shrouded-harbor-56113.herokuapp.com/
* username: testaaja
* password: testaaja

### Sovelluksen käyttöohjeet

#### Kirjautuminen

Sovellus ohjaa käyttäjän aluksi kirjautumissivulle /login. Uusia käyttäjiä varten kirjautumissivulla on linkki /signup-sivulle, missä uusi käyttäjä voi luoda tunnukset sovellukseen. Sekä /login että /signup ovat saatavilla ilman kirjautumista.

#### Tunnusten luominen

Sovellukseen voi luoda tunnukset /signup-sivulla. Käyttäjän tulee antaa seuraavat tiedot: käyttäjätunnus, salasana, kuvaus, profiilipolku (käyttäjän henkilökohtainen sivu löytyy osoitteesta /profiilipolku).

Tiedot validoidaan sekä palvelimella että selaimessa (HTML5-lomakkeen validointitoimintojen avulla).

Käyttäjätunnuksen ja profiilipolun tulee olla uniikki. Sovellus ei anna luoda tunnuksia, jos jompikumpi näistä on jo varattu (tästä ei valitettavasti vielä tule käyttäjälle virheilmoitusta).

#### Käyttäjien haku

Kaikki sovelluksen käyttäjät listataan /users-sivulla, josta käyttäjiä voi myös hakea käyttäjänimen perusteella. Haku on toteutettu harjoituksen vuoksi Javascriptillä, mikä ei välttämättä ole järkevää käyttäjämäärien kasvaessa, sillä nykyisessä toteutuksessa kaikki käyttäjät joudutaan hakemaan sivulle. 

Tästä näkymästä on mahdollista lähettää kaveripyyntöjä muille käyttäjille, jotka eivät vielä ole nykyisen käyttäjän kavereita.

#### Henkilökohtainen sivu

Kunkin käyttäjän henkilökohtainen sivu löytyy polusta /profiilipolku. Sovellus on suunniteltu avoimuuden ja demokraattisuuden periaatteita noudattaen niin, että kirjautuneet käyttäjät voivat tarkastella kaikkien muiden käyttäjien henkilökohtaisia sivuja.

Sivulla näkyy myös käyttäjän käyttäjänimi, profiilikuva ja kuvaus. Mikäli käyttäjä ei ole valinnut profiilikuvaa, näytetään oletuskuva.

Henkilökohtaisella sivulla on nähtävissä myös käyttäjän sivulle tehdyt postaukset sekä valikoima käyttäjän albumissa olevia kuvia ja tämän kavereita (kaikki kuvat ja kaverit löytyvät selkeiden linkkien päästä). 

Vain käyttäjän kaverit pystyvät postaamaan ja kommentoimaan käyttäjän henkilökohtaisella sivulla. Käytettävyyden parantamiseksi postaus- ja kommenttilomakkeet on piilotettu selaimessa muilta kuin käyttäjän kavereilta. Lisäksi käyttäjät voivat tykätä kaveriensa sivuilla olevista postauksista ja kuvista. Kaikkien näiden pyyntöjen käsittely katkaistaan palvelimen puolella, mikäli ne tulevat henkilöltä, joka ei ole käyttäjän ystävä. Näin estetään sovelluksen väärinkäyttö käyttöliittymän ulkopuolelta tulevien pyyntöjen avulla.

Omalla sivullaan käyttäjä näkee myös listan vastausta odottavista kaveripyynnöistä.

#### Albumi

Henkilökohtaiselta sivulta on pääsy kuva-albumiin, johon voi tallentaa korkeintaan 10 jpg-muotoista kuvaa kuvauksineen. Albumista löytyy myös “Edit album” -nappi, jonka avulla käyttäjä pääsee poistamaan kuvia tai valitsemaan jonkun niistä profiilikuvikseen.

#### Uloskirjautuminen

Sovelluksesta kirjaudutaan ulos päävalikon Log out -linkistä.
