package softuni.springintrousers.services.impl;

import org.springframework.stereotype.Service;
import softuni.springintrousers.repo.TownRepository;
import softuni.springintrousers.services.TownService;

@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;

    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    @Override
    public void seedTown() {

    }
}
