package mpp.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/jocuri")
public class JocController {

    private final JocService service;

    public JocController(JocService service) {
        this.service = service;
    }

    @GetMapping("/obtineToate/{id}")
    public ResponseEntity<List<JocDTO>> getJocuri(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getJocuri(id));
    }
}
