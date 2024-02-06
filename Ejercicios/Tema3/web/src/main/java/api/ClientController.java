package api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ClientController
{
    @Autowired
    private IClientService productService;

    @GetMapping(value = "/client")
    public ResponseEntity<List<Client>> getClient() {
        return ResponseEntity.ok(productService.findAll());
    }
}