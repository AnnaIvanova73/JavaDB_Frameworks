package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dtos.jsons.BranchImportDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Town;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.TownRepository;
import hiberspring.service.BranchService;
import hiberspring.util.fileutil.FileUtil;
import hiberspring.util.validation.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static hiberspring.common.Constants.INCORRECT_DATA_MESSAGE;
import static hiberspring.common.Constants.SUCCESSFUL_IMPORT_MESSAGE;

@Service
public class BranchServiceImpl implements BranchService {

    private static final String BRANCH_PATH = "src/main/resources/files/branches.json";

    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final StringBuilder sb;
    private final FileUtil fileUtil;
    private final BranchRepository branchRepository;
    private final TownRepository townRepository;

    public BranchServiceImpl(ModelMapper modelMapper,
                             Gson gson,
                             ValidationUtil validationUtil,
                             StringBuilder sb, FileUtil fileUtil,
                             BranchRepository branchRepository,
                             TownRepository townRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.sb = sb;
        this.fileUtil = fileUtil;
        this.branchRepository = branchRepository;
        this.townRepository = townRepository;
    }

    @Override
    public Boolean branchesAreImported() {
        return this.branchRepository.count() > 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return this.fileUtil.readFile(BRANCH_PATH);
    }

    @Override
    public String importBranches(String branchesFileContent) throws IOException {
        this.sb.setLength(0);

        BranchImportDto[] branchDtos = this.gson.fromJson(readBranchesJsonFile(), BranchImportDto[].class);

        for (BranchImportDto branchDto : branchDtos) {

            if (!this.validationUtil.isValid(branchDto)) {
                this.sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Optional<Town> byNameLike = this.townRepository.findByNameLike(branchDto.getTown());
            Branch branchEntity = this.modelMapper.map(branchDto, Branch.class);
            branchEntity.setTown(byNameLike.get());

            this.branchRepository.saveAndFlush(branchEntity);
            this.sb
                    .append(String.format(SUCCESSFUL_IMPORT_MESSAGE, branchEntity.getClass().getSimpleName(), branchDto.getName()))
                    .append(System.lineSeparator());

        }


        return this.sb.toString().trim();
    }
}
