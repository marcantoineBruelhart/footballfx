package ch.fhnw.oop2.footballfx.client.presentationmodel;

import java.util.List;

import ch.fhnw.oop2.footballfx.client.business.FootballService;
import ch.fhnw.oop2.footballfx.client.business.ServerConnectionException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PresentationModel {

    private final FootballService footballService = new FootballService();
    private ObservableList<Player> data = FXCollections.observableArrayList();

    private final StringProperty applicationTitle = new SimpleStringProperty("JavaFX Application");

    private final StringProperty playerPlatz = new SimpleStringProperty();
    private final StringProperty playerName = new SimpleStringProperty();
    private final StringProperty playerBirthDate = new SimpleStringProperty();
    private final StringProperty playerCountry = new SimpleStringProperty();
    private final StringProperty playerVerband = new SimpleStringProperty();
    private final StringProperty playerPosition = new SimpleStringProperty();
    private final StringProperty playerHundertesSpiel = new SimpleStringProperty();
    private final StringProperty playerGegen = new SimpleStringProperty();
    private final StringProperty playerFifa = new SimpleStringProperty();
    private final StringProperty playerRSSSF = new SimpleStringProperty();
    private final StringProperty playerLaenderspiele = new SimpleStringProperty();
    private final StringProperty playerStartJahr = new SimpleStringProperty();
    private final StringProperty playerEndJahr = new SimpleStringProperty();

    public PresentationModel() {
        try {
            data = loadDataFromServer();
        } catch (ServerConnectionException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        showPlayerDetails(data.get(1));
    }

    public ObservableList<Player> getData() {
        return data;
    }

    private ObservableList<Player> loadDataFromServer() throws ServerConnectionException {
        List<Player> playerss = footballService.retrieveAllPlayers();
        return FXCollections.observableArrayList(playerss);
    }

    public void showPlayerDetails(Player player) {
        if (player != null) {
            playerPlatz.set(player.getPlatz().get());
            playerName.set(player.getName().get());
            playerBirthDate.set(player.getBirthday().get());
            playerCountry.set(player.getCountry().get());
            playerVerband.set(player.getVerband().get());
            playerPosition.set(player.getPosition().get());
            playerHundertesSpiel.set(player.getHundertesSpiel().get());
            playerGegen.set(player.getGegner().get());
            playerFifa.set(player.getFifa_spiele().get());
            playerRSSSF.set(player.getRsssf_spiele().get());
            playerStartJahr.set(player.getStartJahr().get());
            playerEndJahr.set(player.getEndJahr().get());
        } else {
            playerName.set("");
            playerBirthDate.set("");
            playerCountry.set("");
            playerVerband.set("");
            playerPosition.set("");
            playerHundertesSpiel.set("");
            playerGegen.set("");
            playerFifa.set("");
            playerRSSSF.set("");
            playerPlatz.set("");
            playerStartJahr.set("");
            playerEndJahr.set("");
        }
    }


    public StringProperty applicationTitleProperty() {
        return applicationTitle;
    }

    public StringProperty getPlayerPlatz() {
        return playerPlatz;
    }

    public StringProperty getPlayerName() {
        return playerName;
    }

    public StringProperty getPlayerCountry() {
        return playerCountry;
    }

    public StringProperty getPlayerVerband() {
        return playerVerband;
    }

    public StringProperty getPlayerFifa() {
        return playerFifa;
    }

    public StringProperty getPlayerRSSSF() {
        return playerRSSSF;
    }

    public StringProperty getPlayerHundertesSpiel() {
        return playerHundertesSpiel;
    }

    public StringProperty getPlayerStartJahr() {
        return playerStartJahr;
    }

    public StringProperty getPlayerBirthDate() {
        return playerBirthDate;
    }

    public StringProperty getPlayerPosition() {
        return playerPosition;
    }

    public StringProperty getPlayerGegen() {
        return playerGegen;
    }

    public StringProperty getPlayerEndJahr() {
        return playerEndJahr;
    }

    public void addPlayer(Player player) {
        Player p = new Player();
        p.setName("der player");

        try {
            footballService.createPlayer(p);
        } catch (ServerConnectionException e) {
            System.err.println(e);
        }
    }

    public void updatePlayer(Player player) {
        try {
            footballService.updatePlayer(player);
        } catch (ServerConnectionException e) {
            System.err.println(e);
        }
    }

    public void removePlayer(Player player) {
        try {
            footballService.deletePlayer(player);
        } catch (ServerConnectionException e) {
            System.err.println(e);
        }
    }
}
