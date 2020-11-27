package app.ccb.services.impl;

import app.ccb.domain.dtos.jsons.BranchImportDto;
import app.ccb.domain.entities.Branch;
import app.ccb.repositories.BranchRepository;
import app.ccb.services.BranchService;
import app.ccb.util.fileutil.FileUtil;
import app.ccb.util.validation.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static app.ccb.config.constants.ConstantMsg.INVALID_MSG;

@Service
public class BranchServiceImpl implements BranchService {
    private final static String BRANCH_PATH = "F:\\SpringData\\Java-DB-Spring-Data-Exam-ColonialCouncilBank\\ColonialCouncilBank\\src\\main\\resources\\files\\json\\branches.json";


    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final StringBuilder sb;
    private final ValidationUtil validationUtil;
    private final BranchRepository branchRepository;

    @Autowired
    public BranchServiceImpl(FileUtil fileUtil,
                             Gson gson,
                             ModelMapper modelMapper,
                             StringBuilder sb,
                             ValidationUtil validationUtil,
                             BranchRepository branchRepository) {
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.sb = sb;
        this.validationUtil = validationUtil;
        this.branchRepository = branchRepository;
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
    public String importBranches(String branchesJson) throws IOException {
        this.sb.setLength(0);
        BranchImportDto[] branches = this.gson.fromJson(readBranchesJsonFile(), BranchImportDto[].class);

        for (BranchImportDto branchDto : branches) {

            if(!this.validationUtil.isValid(branchDto)){
                this.sb.append(INVALID_MSG).append(System.lineSeparator());
                continue;
            }

            Branch branchEntity = this.modelMapper.map(branchDto, Branch.class);
            this.branchRepository.saveAndFlush(branchEntity);
            this.sb.append(String.format("Successfully imported %s - %s.",
                    branchEntity.getClass().getSimpleName(),
                    branchEntity.getName())).append(System.lineSeparator());
        }

        return this.sb.toString().trim();
    }
}
