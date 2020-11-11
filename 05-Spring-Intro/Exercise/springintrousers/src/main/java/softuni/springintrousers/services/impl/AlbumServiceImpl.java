package softuni.springintrousers.services.impl;

import org.springframework.stereotype.Service;
import softuni.springintrousers.repo.AlbumRepository;
import softuni.springintrousers.services.AlbumService;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }
}
