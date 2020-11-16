package softuni.gameshop.services;

import softuni.gameshop.domain.dtos.UserLoginDto;
import softuni.gameshop.domain.dtos.seed.UserRegisterDto;

public interface UserService {

    String registerUser(UserRegisterDto userDtoSeed);

    String tryToLoggIn(UserLoginDto userLoginDto);

    String logout ();

    String addItem(String title);
    String removeItem(String title);
    String buyItem();
    String printLoggedUserOwnedGames();
}
