package mpp.persistance.repository;

import mpp.model.Configuratie;
import mpp.model.Joc;
import mpp.model.User;
import mpp.persistance.JocRepoInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



public class JocDbRepo implements JocRepoInterface {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public JocDbRepo(Properties props) {
        logger.info("Initializing UtilizatorRepo with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public Joc findOne(Integer id) {
        logger.traceEntry("Finding game with id: {}", id);
        Connection con = dbUtils.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement(
                "SELECT U.id AS userid, U.alias, " +
                        "C.id AS configuratieid, C.litere, C.numar1, C.numar2, C.numar3, C.numar4, " +
                        "J.userId, J.configuratieId, J.timpIncepere, J.literePropuse, J.litereGenerate,J.numarPuncte " +
                        "FROM joc J " +
                        "INNER JOIN configuratie C ON J.configuratieId = C.id " +
                        "INNER JOIN users U ON J.userId = U.id " +
                        "WHERE J.id = ?"
        )){
            preStmt.setInt(1, id);

            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    User user = new User(result.getString("alias"));
                    user.setId(result.getInt("userid"));
                    Configuratie conf = new Configuratie(result.getString("litere"),result.getInt("numar1"),result.getInt("numar2"),result.getInt("numar3"),result.getInt("numar4"));
                    conf.setId(result.getInt("configuratieid"));
                    String dateString = result.getString("timpIncepere");
                    LocalDateTime inceperejoc = LocalDateTime.parse(dateString);
                    Integer scor  = result.getInt("nrPuncte");
                    String literePropuse = result.getString("literePropuse");
                    String litereGenerate = result.getString("litereGenerate");


                    Joc u = new Joc(user,conf,scor,inceperejoc,literePropuse,litereGenerate);
                    u.setId(id);

                    logger.traceExit(u);
                    return u;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit("game not found");
        return null;
    }


    @Override
    public Iterable<Joc> findAll() {
        logger.traceEntry("Getting all games");
        Connection con = dbUtils.getConnection();
        List<Joc> jocuri = new ArrayList<>();

        try (PreparedStatement preStmt = con.prepareStatement(
                "SELECT J.id AS jocid, U.id AS userid, U.alias, " +
                        "C.id AS configuratieid, C.litere, C.numar1, C.numar2, C.numar3, C.numar4, " +
                        "J.userId, J.configuratieId, J.timpIncepere, J.literePropuse, J.litereGenerate,J.numarPuncte " +
                        "FROM joc J " +
                        "INNER JOIN configuratie C ON J.configuratieId = C.id " +
                        "INNER JOIN users U ON J.userId = U.id "
        )) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    User user = new User(result.getString("alias"));
                    user.setId(result.getInt("userid"));

                    Configuratie conf = new Configuratie(
                            result.getString("litere"),
                            result.getInt("numar1"),
                            result.getInt("numar2"),
                            result.getInt("numar3"),
                            result.getInt("numar4")
                    );
                    conf.setId(result.getInt("configuratieid"));

                    LocalDateTime inceperejoc = LocalDateTime.parse(result.getString("timpIncepere"));
                    Integer scor = result.getInt("nrPuncte");
                    String literePropuse = result.getString("literePropuse");
                    String litereGenerate = result.getString("litereGenerate");

                    Joc u = new Joc(user,conf,scor,inceperejoc,literePropuse,litereGenerate);
                    u.setId(result.getInt("jocid"));
                    jocuri.add(u);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit(jocuri);
        return jocuri;
    }


    @Override
    public Joc save(Joc entity) {
        logger.traceEntry("Saving game {}", entity);
        Connection con = dbUtils.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement(
                "INSERT INTO joc (userid, configuratieid, timpIncepere, literePropuse, litereGenerate, nrPuncte) VALUES (?,?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {

            preStmt.setInt(1, entity.getUser().getId());
            preStmt.setInt(2, entity.getConfiguratie().getId());
            preStmt.setString(3, entity.getTimpIncepere().toString());
            preStmt.setString(4, entity.getLiterePropuse());
            preStmt.setString(5, entity.getLitereGenerate());

            preStmt.executeUpdate();

            try (ResultSet rs = preStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    Integer generatedId = rs.getInt(1);
                    entity.setId(generatedId);
                    logger.trace("Saved game with ID: {}", generatedId);
                    return entity;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit();
        return null;
    }


    @Override
    public Joc delete(Integer id) {
        return null;
    }


    @Override
    public Joc update(Joc entity) {
        return null;
    }


}



