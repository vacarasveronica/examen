package mpp.rest;

import mpp.model.Configuratie;
import mpp.persistance.ConfiguratieRepoInterface;
import org.springframework.stereotype.Service;

@Service
public class ConfiguratieService {

    private final ConfiguratieRepoInterface configuratieRepo;

    public ConfiguratieService(ConfiguratieRepoInterface configuratieRepo) {
        this.configuratieRepo = configuratieRepo;
    }

    public Configuratie update(Integer id,ConfiguratieDTO dto) {
        Configuratie existenta = configuratieRepo.findOne(id);
        existenta.setLitere(dto.litere);
        existenta.setNumar1(dto.numar1);
        existenta.setNumar2(dto.numar2);
        existenta.setNumar3(dto.numar3);
        existenta.setNumar4(dto.numar4);

        return configuratieRepo.update(existenta);
    }

}
