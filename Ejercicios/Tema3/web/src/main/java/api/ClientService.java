package api;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClientService {
    @Override
    public List<Client> findAll() {
        return List.of(
                new Client(1, "Apple", "Cupertino, California, EE. UU.", "https://www.apple.com/"),
                new Client(2, "Google", "Mountain View, California, EE. UU.", "https://www.google.com/"),
                new Client(3, "Microsoft", "Redmond, Washington, EE. UU.", "https://www.microsoft.com/"),
                new Client(4, "Amazon", "Seattle, Washington, EE. UU.", "https://www.amazon.com/"),
                new Client(5, "Tesla", "Palo Alto, California, EE. UU.", "https://www.tesla.com/"),
                new Client(6, "Facebook", "Menlo Park, California, EE. UU.", "https://www.meta.com/"),
                new Client(7, "Alibaba", "Hangzhou, China", "https://www.alibabagroup.com/"),
                new Client(8, "Tencent", "Shenzhen, China", "https://www.tencent.com/en-us/index.html"),
                new Client(9, "Samsung", "Suwon, Corea del Sur", "https://www.samsung.com/"),
                new Client(10, "Toyota", "Toyota City, Japón", "https://www.toyota-global.com/")
        );
    }
}