package mpp.rest;


import mpp.model.Joc;
import mpp.model.User;
import mpp.persistance.JocRepoInterface;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JocService {
    private final JocRepoInterface jocRepo;

    public JocService(JocRepoInterface jocRepo) {
        this.jocRepo = jocRepo;
    }

    public boolean macarUnaLaFel(String literePropuse, String litereGenerate){
        for(int i=0 ; i< literePropuse.length() ; i++){
            if(literePropuse.charAt(i) == litereGenerate.charAt(i)){
                return true;
            }
        }
        return false;
    }

    public List<JocDTO> getJocuri(Integer userId){
        Iterable<Joc> toate = jocRepo.findAll();
        List<JocDTO> rezultat = new ArrayList<>();
        for(Joc joc : toate){
            if(joc.getUser().getId().equals(userId) && macarUnaLaFel(joc.getLiterePropuse(),joc.getLitereGenerate())){
                rezultat.add(new JocDTO(joc.getNrPuncte(),joc.getLiterePropuse(),joc.getLitereGenerate()));
            }
        }
        return rezultat;
    }
}
