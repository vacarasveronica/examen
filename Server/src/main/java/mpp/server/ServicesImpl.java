package mpp.server;

// TODO 1: IMPORT USED MODELS AND REPOSITORIES
import mpp.model.Configuratie;
import mpp.model.Joc;
import mpp.model.User;
import mpp.persistance.ConfiguratieRepoInterface;
import mpp.persistance.JocRepoInterface;
import mpp.persistance.UserRepoInterface;
import mpp.services.AppException;
import mpp.services.IObserver;
import mpp.services.IServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServicesImpl implements IServices {

    // TODO 2: DEFINE/ADD REPOSITORIES
    UserRepoInterface userRepo;
    ConfiguratieRepoInterface configRepo;
    JocRepoInterface jocRepo;

    private static final Logger logger = LogManager.getLogger(ServicesImpl.class);

    private Map<String, IObserver> loggedClients;

    // TODO 3: ADD REPOSITORIES TO CONSTRUCTOR
    public ServicesImpl(UserRepoInterface userRepo, ConfiguratieRepoInterface configRepo, JocRepoInterface jocRepo) {
        this.userRepo = userRepo;
        this.configRepo = configRepo;
        this.jocRepo = jocRepo;
        logger.info("Initializing ServicesImpl");
        loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public void login(String alias, IObserver client) {
        loggedClients.put(alias, client);
    }

    @Override
    public Iterable<User> findAllUser() throws IOException, InterruptedException {
        return userRepo.findAll();
    }

    @Override
    public Iterable<Configuratie> findAllConfiguratie() throws IOException, InterruptedException, AppException {
        return configRepo.findAll();
    }

    @Override
    public Iterable<Joc> findAllJoc() throws IOException, InterruptedException, AppException {
        return jocRepo.findAll();
    }

    @Override
    public Joc saveJoc(Joc joc) throws AppException, IOException, InterruptedException {
        Joc j = jocRepo.save(joc);
        //notifyObservers(joc);
        return j;
    }

}