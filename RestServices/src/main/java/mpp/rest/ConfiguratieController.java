package mpp.rest;
import mpp.model.Configuratie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/configuratii")
@CrossOrigin(origins = "http://localhost:3000")
public class ConfiguratieController {

    private final ConfiguratieService service;

    public ConfiguratieController(ConfiguratieService service) {
        this.service = service;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificaConfiguratie(@PathVariable int id, @RequestBody ConfiguratieDTO dto) {
        try {
            Configuratie configuratie = service.update(id, dto);
            return ResponseEntity.status(201).body(configuratie.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
