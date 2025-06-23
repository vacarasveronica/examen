package mpp.client.gui;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
// TODO 1: IMPORT USED MODELS
import mpp.model.Configuratie;
import mpp.model.Joc;
import mpp.model.MainDTO;
import mpp.model.User;
import mpp.services.AppException;
import mpp.services.IObserver;
import mpp.services.IServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class MainController implements Initializable, IObserver {
    @FXML
    private Label labelConf;
    @FXML
    private TextField txtLiteraAleasa;
    @FXML
    private Label concluzieLabel;
    @FXML
    private Label labelLiteraGenerata;
    @FXML private TableView<MainDTO> tabelClasament;
    @FXML private TableColumn<MainDTO, String> colAlias;
    @FXML private TableColumn<MainDTO, LocalDateTime> colIncepere;
    @FXML private TableColumn<MainDTO, Integer> colPunctaj;

    private ObservableList<MainDTO> listaEntitati = FXCollections.observableArrayList();

    private IServices server;

    //date pt joc
    private User loggedUser;
    private Configuratie configuratieJoc;
    private Joc joc;
    private Integer nrIncercari = 0;
    private String literePropuse = "";
    private String litereGenerate = "";
    private Integer nrPuncte = 0;
    private List<String> litere = new ArrayList<>();
    private List<Integer> numere = new ArrayList<>();
    Integer nrAsociatLiteraPropusa  = 0;
    Integer nrAsociatLiteraGenerata = 0;

    private static final Logger logger= LogManager.getLogger();

    public MainController() {
        System.out.println("MainController constructor");
    }

    public MainController(IServices server) {
        this.server = server;
        System.out.println("MainController constructor with server parameters");
    }

    public void setServer(IServices server) {
        this.server = server;
    }


    public void setLoggedUser(User loggedUser) throws AppException, IOException, InterruptedException {
        this.loggedUser = loggedUser;
        initModel();
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        colAlias.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAlias().toString())
        );
        colIncepere.setCellFactory(column -> {
            return new TableCell<MainDTO, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        // Formatare frumoasă a datei și orei
                        String formatted = item.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                        setText(formatted);
                    }
                }
            };
        });

        colIncepere.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTime()));

        colPunctaj.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getPunctaj()).asObject()
        );

        tabelClasament.setItems(listaEntitati);

    }



    private void initModel() throws AppException, IOException, InterruptedException {
        try {
            Iterable<Configuratie> configuratii = server.findAllConfiguratie();
            List<Configuratie> conf = new ArrayList<>();
            for (Configuratie configuratie: configuratii) {
                conf.add(configuratie);
            }
            Collections.shuffle(conf);
            configuratieJoc = conf.get(0);
        } catch (IOException | InterruptedException | AppException e) {
            e.printStackTrace();
        }
        for(char litera :  configuratieJoc.getLitere().toCharArray()){
            litere.add(String.valueOf(litera));
        }
        numere.add(configuratieJoc.getNumar1());
        numere.add(configuratieJoc.getNumar2());
        numere.add(configuratieJoc.getNumar3());
        numere.add(configuratieJoc.getNumar4());

        String afisareLitere = "Acestea sunt literele si cifrele jocului: ";
        for(char litera :  configuratieJoc.getLitere().toCharArray()) {
            afisareLitere = afisareLitere + litera + ' ';
        }
        for(Integer n : numere) {
            afisareLitere = afisareLitere + n.toString() + ' ';
        }
        afisareLitere = afisareLitere.trim();
        labelConf.setText(afisareLitere);

        joc = new Joc(loggedUser,configuratieJoc, 0,LocalDateTime.now(),"","");
        loadTableData();
    }

    private void loadTableData() throws AppException, IOException, InterruptedException {
        Iterable<Joc> j = server.findAllJoc();
        List<Joc> jocuri = new ArrayList<>();
        for(Joc joc : j) {
            jocuri.add(joc);
        }
        List<MainDTO> lisa = new ArrayList<>();
        for (Joc joc : jocuri) {
            MainDTO newJoc = new MainDTO(joc.getUser().getAlias(),joc.getTimpIncepere(),joc.getNrPuncte());
            lisa.add(newJoc);
        }
        //sortare descrescatoare
        Collections.sort(lisa, (j1, j2) -> Integer.compare(j2.getPunctaj(), j1.getPunctaj()));
        listaEntitati.setAll(lisa);
    }

    @FXML
    public void handleIncercare(ActionEvent actionEvent) throws AppException, IOException, InterruptedException {
        logger.traceEntry("in incercarea de a alege");
        String litera = txtLiteraAleasa.getText().trim();
        if(! literePropuse.contains(litera)){
            txtLiteraAleasa.clear();
            concluzieLabel.setText("Ai ales litera: " + litera);
            nrIncercari++;
            literePropuse += litera;
            List<String> listaLiterePtGenerareNefolosite = new ArrayList<>();
            for(String l: litere){
                if(! litereGenerate.contains(l)){
                    listaLiterePtGenerareNefolosite.add(l);
                }
            }
            Collections.shuffle(listaLiterePtGenerareNefolosite);
            String literaGenerata = listaLiterePtGenerareNefolosite.get(0);
            litereGenerate += literaGenerata;
            labelLiteraGenerata.setText(literaGenerata);

            for(int i = 0; i < litere.size();i++)
                if(litere.get(i).equals(litera))
                    nrAsociatLiteraPropusa = numere.get(i);
            for(int i = 0; i < litere.size();i++)
                if(litere.get(i).equals(literaGenerata))
                    nrAsociatLiteraGenerata = numere.get(i);
            if(nrAsociatLiteraPropusa > nrAsociatLiteraGenerata)
                nrPuncte += nrAsociatLiteraPropusa + nrAsociatLiteraGenerata;
            else if (nrAsociatLiteraPropusa < nrAsociatLiteraGenerata)
                nrPuncte -= nrAsociatLiteraPropusa;

            if(nrIncercari == 4){
                endGame("Jocul a luat sfarsit!");
            }

        }
        else{
            concluzieLabel.setText("Ai ales deja litera asta o data!");
        }
    }

    private void endGame(String message) throws AppException, IOException, InterruptedException {
        joc.setNrPuncte(nrPuncte);
        joc.setLiterePropuse(literePropuse);
        joc.setLitereGenerate(litereGenerate);
        server.saveJoc(joc);

        concluzieLabel.setText("Ai obtinut scorul de: " + nrPuncte.toString());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over!");
        alert.setHeaderText(message);


        List<Joc> jocuri = new ArrayList<>();
        for(Joc j : server.findAllJoc())
            jocuri.add(j);
        Set<Integer> scoruriUnice = new TreeSet<>(Comparator.reverseOrder());  // ordonează automat descrescator

        for (Joc j : jocuri) {
            scoruriUnice.add(j.getNrPuncte());
        }

        int playerScor = joc.getNrPuncte();
        int pozitie = 1;

        for (int scor : scoruriUnice) {
            if (scor == playerScor) {
                break;
            }
            pozitie++;
        }

        String content = "Your Position in Leaderboard: " + pozitie;

        alert.setContentText(content);
        alert.showAndWait();

    }

    /* TODO 2: IMPLEMENT THE OBSERVER METHODS*/
    public void updateTable() throws AppException, IOException, InterruptedException {
        loadTableData();
        System.out.println("Tabelul a fost actualizat.");
    }

    @Override
    public void gameAdded(Joc j) throws InterruptedException {
        Platform.runLater(() -> {
            try {
                updateTable(); // Actualizează tabelul când un participant este adăugat
            } catch (AppException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }



    void logout() {
    }
}
