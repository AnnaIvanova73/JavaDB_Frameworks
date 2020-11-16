package softuni.labmapping.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.labmapping.domain.dtos.seed.AddressSeedDto;
import softuni.labmapping.domain.entities.Address;
import softuni.labmapping.domain.repo.AddressRepository;
import softuni.labmapping.services.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;

    public AddressServiceImpl(ModelMapper modelMapper, AddressRepository addressRepository) {
        this.modelMapper = modelMapper;
        this.addressRepository = addressRepository;
    }


    @Override
    public void seedAddress(AddressSeedDto addressSeedDto) {
        Address address = this.modelMapper.map(addressSeedDto,Address.class);
    }
}
