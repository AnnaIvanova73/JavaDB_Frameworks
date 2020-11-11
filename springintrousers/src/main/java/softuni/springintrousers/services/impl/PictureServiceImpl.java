package softuni.springintrousers.services.impl;

import org.springframework.stereotype.Service;
import softuni.springintrousers.repo.PictureRepository;
import softuni.springintrousers.services.PictureService;


@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }
}
