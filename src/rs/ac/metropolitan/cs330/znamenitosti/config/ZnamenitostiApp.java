package rs.ac.metropolitan.cs330.znamenitosti.config;

import java.io.ByteArrayOutputStream;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import rs.ac.metropolitan.cs330.znamenitosti.R;
import rs.ac.metropolitan.cs330.znamenitosti.model.City;
import rs.ac.metropolitan.cs330.znamenitosti.model.Image;
import rs.ac.metropolitan.cs330.znamenitosti.model.Sight;
import rs.ac.metropolitan.cs330.znamenitosti.service.Settings;

/**
 *
 * @author nikola
 */
public class ZnamenitostiApp extends com.activeandroid.app.Application {

    private Settings settings;

    @Override
    public void onCreate() {
        super.onCreate();
        this.settings = new Settings(this);
        if (getString(R.string.test_level).equals("true")) {
            createTest();
        }
    }

    public Settings getSettings() {
        return settings;
    }

    private void createTest() {
        createCities();
        createStariDvor();
        createDomNarodneSkupstine();
        createNarodniMuzej();
        createNarodnoPozoriste();
        createPetrovaradinskaTvrdjava();
    }

    private void createCities() {
        City.create("Beograd");
        City.create("Novi Sad");
    }

    private void createStariDvor() {
        Image image = Image.create(valueOf(R.drawable.bgd_staridvor));
        new Sight.Creator()
                .setName("Stari Dvor")
                .setDescription("Stari Dvor je reprezentativno zdanje u Beogradu, "
                + "na uglu Ulice Kralja Milana i ulice Dragoslava Jovanovića, čiji glavni "
                + "ulaz gleda na Trg Nikole Pašića. Dvor, koji je podigao kralj Milan Obrenović u periodu "
                + "1881–1884, je bila rezidencija kralja Petra I Karađorđevića (u periodu 1903–1921) "
                + "i kralja Aleksandra I (1921–1922), a danas je sedište Skupštine grada Beograda.")
                .setLatitude(44.81114827013204)
                .setLongitude(20.462695956230164)
                .setCity(City.byName("Beograd"))
                .setImage(image)
                .create();
    }

    private void createDomNarodneSkupstine() {
        Image image = Image.create(valueOf(R.drawable.bgd_savezna));
        new Sight.Creator()
                .setName("Dom Narodne skupštine")
                .setDescription("Zgrada Doma Narodne skupštine Republike Srbije je objekat na "
                + "početku Bulevara kralja Aleksandra u Beogradu. Pod nazivom Savezna skupština je "
                + "poznata još iz vremena SFRJ, mada je u međuvremenu postala Skupština državne "
                + "zajednice SCG; a 23. jula 2006. godine postaje i zvanično Dom Narodne skupštine Srbije.")
                .setLatitude(44.811454628047606)
                .setLongitude(20.465673208236694)
                .setCity(City.byName("Beograd"))
                .setImage(image)
                .create();
    }

    private void createNarodniMuzej() {
        Image image = Image.create(valueOf(R.drawable.bgd_narmuzej));
        new Sight.Creator()
                .setName("Narodni muzej")
                .setDescription("Narodni muzej u Beogradu osnovan je 1844. i najstarija je "
                + "muzejska ustanova u Srbiji. U sastavu Narodnog muzeja su i Galerija fresaka, "
                + "Vukov i Dositejev muzej i Spomen-muzej Nadežde i Rastka Petrovića. "
                + "Najveća vrednost koja se čuva u Narodnom muzeju je Miroslavljevo "
                + "jevanđelje - najstariji i najdragoceniji ćirilički rukopis, nastao oko 1190.")
                .setLatitude(44.81595086166557)
                .setLongitude(20.459960103034973)
                .setCity(City.byName("Beograd"))
                .setImage(image)
                .create();
    }

    private void createNarodnoPozoriste() {
        Image image = Image.create(valueOf(R.drawable.bgd_narodnopoz));
        new Sight.Creator()
                .setName("Narodno pozorište")
                .setDescription("Narodno pozorište u Beogradu je pozorište u Beogradu koje se nalazi na Trgu Republike, "
                + "u samom centru grada. Osnovano je 1868. godine, a u sadašnju zgradu, "
                + "na mestu tadašnje Stambol kapije, uselilo se 1869. godine. "
                + "U okviru njega funkcionišu umetničke jedinice Opera, Balet i Drama, "
                + "a predstave se odigravaju na Velikoj sceni i Sceni Raša Plaović. Danas predstavlja jednu od najreprezentativnijih i najznačajnijih kulturnih institucija Srbije.")
                .setLatitude(44.816738570273515)
                .setLongitude(20.460582375526428)
                .setCity(City.byName("Beograd"))
                .setImage(image)
                .create();
    }

    private void createPetrovaradinskaTvrdjava() {
        Image image = Image.create(valueOf(R.drawable.ns_tvrdjava));
        new Sight.Creator()
                .setName("Petrovaradinska Tvrđava")
                .setDescription("Poznata i kao \"Dunavski Gibraltar\", građena je od 1692. do 1780. godine, "
                + "na mestu srednjovekovne mađarsko turske utvrde. U gradnji su korišćeni najmoderniji planovi "
                + "toga doba francuskog arhitekte, markiza Sebastijana Vobana. Projektanti su bili Marsili, "
                + "Kajzersfeld, Sreder i Vanberg koji tu i počiva. Prostor obuhvata 112 h sa tri sprata u dubinu i 16 km raznih koridora. "
                + "Glavnu odbranu je činilo 400 topova sa 12 hiljada puškarnica. Savršenstvo fortifikacije "
                + "potvrđuje i sjajna pobeda Eugena Savojskog, 1716. godine, koji je branio "
                + "tvrđavu sa svega 70.000 vojnika od turske vojske Damad Ali paše, koja je napadala sa 200.000 vojnika.")
                .setLatitude(45.253136531929954)
                .setLongitude(19.86337512731552)
                .setCity(City.byName("Novi Sad"))
                .setImage(image)
                .create();
    }

    private byte[] valueOf(int resource) {
        Drawable d = getResources().getDrawable(resource);
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
}
