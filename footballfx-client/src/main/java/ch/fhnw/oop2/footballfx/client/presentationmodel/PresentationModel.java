package ch.fhnw.oop2.footballfx.client.presentationmodel;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import ch.fhnw.oop2.footballfx.client.business.FootballService;
import ch.fhnw.oop2.footballfx.client.business.ServerConnectionException;
import ch.fhnw.oop2.footballfx.client.dataacess.FileAccessException;
import ch.fhnw.oop2.footballfx.client.dataacess.FileDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PresentationModel {

    private final Path DATA_SOURCE = Paths.get("player.csv");
    private final FileDao fileDao = new FileDao();
    private final FootballService footballService = new FootballService();
    private ObservableList<Player> data = FXCollections.observableArrayList();

    private final StringProperty applicationTitle = new SimpleStringProperty("JavaFX Application");

    private final StringProperty playerNumber = new SimpleStringProperty();
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
    private final StringProperty playerErstesSpiel = new SimpleStringProperty();
    private final StringProperty playerLetztesSpiel = new SimpleStringProperty();

    public PresentationModel() {
        try {
            data = loadDataFromServer();
        } catch (ServerConnectionException e) {
            try {
                data = loadDataFromCsv();
            } catch (FileAccessException e1) {
                System.err.println(e.getMessage());
                System.exit(-1);
            }
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

    private ObservableList<Player> loadDataFromCsv() throws FileAccessException {
        Stream<String> stream = fileDao.readFile(DATA_SOURCE);
        ObservableList<Player> players = FXCollections.observableArrayList();

        stream.skip(1).forEach(row -> {
            String[] splitedData = row.split(";");
            Player player = new Player();
            player.setName(splitedData[1]);
            player.setBirthday(splitedData[2]);
            player.setCountry(splitedData[3]);
            player.setVerband(splitedData[4]);
            player.setPosition(splitedData[5]);
            player.setHundertesSpiel(splitedData[6]);
            player.setGegner(splitedData[7]);
            player.setFifa_spiele(splitedData[8]);
            player.setRsssf_spiele(splitedData[9]);
            player.setPlatz(splitedData[0]);
            player.setStartjahr(splitedData[10]);
            player.setEndjahr(splitedData[11]);
            players.add(player);
        });
        return players;
    }

    public void showPlayerDetails(Player player) {
        if (player != null) {
            playerName.set(player.getName().get());
            playerBirthDate.set(player.getBirthday().get());
            playerCountry.set(player.getCountry().get());
            playerVerband.set(player.getVerband().get());
            playerPosition.set(player.getPosition().get());
            playerHundertesSpiel.set(player.getHundertesSpiel().get());
            playerGegen.set(player.getGegner().get());
            playerFifa.set(player.getFifa_spiele().get());
            playerRSSSF.set(player.getRsssf_spiele().get());
            playerNumber.set(player.getPlatz().get());
            playerErstesSpiel.set(player.getStartJahr().get());
            playerLetztesSpiel.set(player.getEndJahr().get());
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
            playerNumber.set("");
            playerErstesSpiel.set("");
            playerLetztesSpiel.set("");
        }
    }


    public StringProperty applicationTitleProperty() {
        return applicationTitle;
    }

    public StringProperty getPlayerNumber() {return playerNumber; }

    public StringProperty getPlayerName() {
        return playerName;
    }

    public StringProperty getPlayerCountry() {
        return playerCountry;
    }

    public StringProperty getPlayerLaenderspiele() {
        return playerLaenderspiele;
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

    public StringProperty getPlayerErstesSpiel() {
        return playerErstesSpiel;
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

    public StringProperty getPlayerLetztesSpiel() {
        return playerLetztesSpiel;
    }

    public void addPlayer() {
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
